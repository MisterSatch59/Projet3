package org.escalade.business.impl.manager;

import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.UtilisateurManager;
import org.escalade.business.impl.PasswordUtils;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Implémentation de UtilisateurManager
 * 
 * @author Oltenos
 *
 */
@Named("utilisateurManager")
public class UtilisateurManagerImpl extends AbstractManagerImpl implements UtilisateurManager {
	private static final Logger LOGGER = LogManager.getLogger(UtilisateurManagerImpl.class);

	@Override
	public Utilisateur getUtilisateur(String pseudo) throws FunctionalException {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if (pseudo == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		Utilisateur result = this.getDaoFactory().getUtilisateurDao().getUtilisateur(pseudo);

		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("utilisateur = " + utilisateur);

		if (utilisateur == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		// Génération du sel
		utilisateur.setSel(PasswordUtils.getSalt(20));
		// Génération du mot de passe sécurisé pour enregistrement dans la base de données
		String mdpSecurise = PasswordUtils.generateSecurePassword(utilisateur.getMdp(), utilisateur.getSel());
		utilisateur.setMdp(mdpSecurise);

		Set<ConstraintViolation<Utilisateur>> violations = this.getValidator().validate(utilisateur);
		LOGGER.debug("resultat validation utilisateur = " + violations);

		if (violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getUtilisateurDao().createUtilisateur(utilisateur);

				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
					throw new TechnicalException("Technical error with the database");
				}
			}
		} else {
			throw new FunctionalException("Invalid informations sent to database");
		}

		LOGGER.traceExit();
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur, String nouveauMdp)
			throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("utilisateur = " + utilisateur);

		if (utilisateur == null) {
			throw new FunctionalException("Invalid informations");
		}

		if (nouveauMdp != null) {//si nouveauMdp est null le mot de passe n'est pas modifié dans la base de données
			// Génération d'un nouveau sel
			utilisateur.setSel(PasswordUtils.getSalt(20));
			// Génération du mot de passe sécurisé pour enregistrement dans la base de données
			String mdpSecurise = PasswordUtils.generateSecurePassword(nouveauMdp, utilisateur.getSel());
			utilisateur.setMdp(mdpSecurise);
		}

		Set<ConstraintViolation<Utilisateur>> violations = this.getValidator().validate(utilisateur);
		LOGGER.debug("resultat validation utilisateur = " + violations);

		if (violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getUtilisateurDao().updateUtilisateur(utilisateur);

				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
					throw new TechnicalException("Technical error with the database");
				}
			}
		} else {
			throw new FunctionalException("Invalid informations sent to database");
		}

		LOGGER.traceExit();
	}

	@Override
	public void deleteUtilisateur(String pseudo) throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if (pseudo == null) {
			throw new FunctionalException("Invalid informations");
		}

		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getUtilisateurDao().deleteUtilisateur(pseudo);
			TransactionStatus vTScommit = vTransactionStatus;
			vTransactionStatus = null;
			this.getPlatformTransactionManager().commit(vTScommit);
		} finally {
			if (vTransactionStatus != null) {
				this.getPlatformTransactionManager().rollback(vTransactionStatus);
				throw new TechnicalException("Technical error with the database");
			}
		}

		LOGGER.traceExit();
	}

	@Override
	public Utilisateur authentification(String pseudo, String mdp) throws FunctionalException {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if (pseudo == null || mdp == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		Utilisateur utilisateurBD = getUtilisateur(pseudo);

		if (utilisateurBD == null) {
			LOGGER.traceExit(false);
			return (null);
		} else {
			// Hachage du mdp proposé en utilsant le sel de l'utilisateur pour comparaison avec le mot de passe enregistré dans la base de données
			String mdpHache = PasswordUtils.generateSecurePassword(mdp, utilisateurBD.getSel());
			if (mdpHache.equals(utilisateurBD.getMdp())) {
				LOGGER.traceExit(true);
				return (utilisateurBD);
			} else {
				LOGGER.traceExit(false);
				return (null);
			}
		}
	}
}
