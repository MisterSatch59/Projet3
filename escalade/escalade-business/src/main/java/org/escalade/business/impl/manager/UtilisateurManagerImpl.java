package org.escalade.business.impl.manager;

import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.UtilisateurManager;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Implémentation de UtilisateurManager
 * @author Oltenos
 *
 */
@Named("utilisateurManager")
public class UtilisateurManagerImpl extends AbstractManagerImpl implements UtilisateurManager {
	private static final Logger LOGGER = LogManager.getLogger(UtilisateurManagerImpl.class);
	
	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		LOGGER.traceEntry("pseudo = " + pseudo);
		
		if(pseudo!=null) {
			Utilisateur result = this.getDaoFactory().getUtilisateurDao().getUtilisateur(pseudo);
			
			LOGGER.traceExit(result);
			return result;
		}
		
		LOGGER.traceExit(null);
		return null;
	}
	
	@Override
	public void createUtilisateur(Utilisateur utilisateur) throws FunctionalException {
		LOGGER.traceEntry("utilisateur = " + utilisateur);
		
		Set<ConstraintViolation<Utilisateur>> violations = this.getValidator().validate(utilisateur);
		LOGGER.debug("resultat validation utilisateur = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getUtilisateurDao().createUtilisateur(utilisateur);
	
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}else {
			String message = "";
			int i=0;
			for (ConstraintViolation<Utilisateur> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
		
		LOGGER.traceExit();
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) throws FunctionalException {
		LOGGER.traceEntry("utilisateur = " + utilisateur);
		
		Set<ConstraintViolation<Utilisateur>> violations = this.getValidator().validate(utilisateur);
		LOGGER.debug("resultat validation utilisateur = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getUtilisateurDao().updateUtilisateur(utilisateur);
	
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}else {
			String message = "";
			int i=0;
			for (ConstraintViolation<Utilisateur> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}

		LOGGER.traceExit();
	}

	@Override
	public void deleteUtilisateur(String pseudo) {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if(pseudo!=null) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getUtilisateurDao().deleteUtilisateur(pseudo);
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}

		LOGGER.traceExit();
	}
}
