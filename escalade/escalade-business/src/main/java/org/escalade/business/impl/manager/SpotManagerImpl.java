package org.escalade.business.impl.manager;

import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.SpotManager;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.recherche.RechercheSpot;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Impléménation dez SoptManager
 * @author Oltenos
 *
 */
@Named("spotManager")
public class SpotManagerImpl extends AbstractManagerImpl implements SpotManager {
	private static final Logger LOGGER = LogManager.getLogger(SpotManagerImpl.class);

	@Override
	public Spot getSpot(int id) {
		LOGGER.traceEntry("id = " + id);
		
		Spot result = this.getDaoFactory().getSpotDao().getSpot(id);
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Spot createSpot(Spot spot) throws FunctionalException {
		LOGGER.traceEntry("spot = " + spot);
		
		Set<ConstraintViolation<Spot>> violations = this.getValidator().validate(spot);
		LOGGER.debug("resultat validation spot = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				spot = this.getDaoFactory().getSpotDao().createSpot(spot);
	
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
			for (ConstraintViolation<Spot> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
			
		LOGGER.traceExit(spot);
		return spot;
	}

	@Override
	public void updateSpot(Spot spot) throws FunctionalException {
		LOGGER.traceEntry("spot = " + spot);
		
		Set<ConstraintViolation<Spot>> violations = this.getValidator().validate(spot);
		LOGGER.debug("resultat validation spot = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getSpotDao().updateSpot(spot);
	
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
			for (ConstraintViolation<Spot> constraintViolation : violations) {
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
	public void deleteSpot(int id) {
		LOGGER.traceEntry("id = " + id);
		
		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getSpotDao().deleteSpot(id);

			TransactionStatus vTScommit = vTransactionStatus;
			vTransactionStatus = null;
			this.getPlatformTransactionManager().commit(vTScommit);
		} finally {
			if (vTransactionStatus != null) {
				this.getPlatformTransactionManager().rollback(vTransactionStatus);
			}
		}
		
		LOGGER.traceExit();
	}

	@Override
	public Commentaire createCommentaire(int spotId, Commentaire commentaire) throws FunctionalException {
		LOGGER.traceEntry("spotId = " + spotId + ", commentaire" + commentaire);
		
		Set<ConstraintViolation<Commentaire>> violations = this.getValidator().validate(commentaire);
		LOGGER.debug("resultat validation commentaire = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				commentaire = this.getDaoFactory().getCommentaireDao().createCommentaire(spotId, commentaire);
	
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
			for (ConstraintViolation<Commentaire> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
			
		LOGGER.traceExit(commentaire);
		return commentaire;
	}

	@Override
	public void deleteCommentaire(int id) {
		LOGGER.traceEntry("id = " + id);
		
		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getCommentaireDao().deleteCommentaire(id);

			TransactionStatus vTScommit = vTransactionStatus;
			vTransactionStatus = null;
			this.getPlatformTransactionManager().commit(vTScommit);
		} finally {
			if (vTransactionStatus != null) {
				this.getPlatformTransactionManager().rollback(vTransactionStatus);
			}
		}
		
		LOGGER.traceExit();
	}

	
	@Override
	public List<Spot> rechercheSpot(RechercheSpot criteres) throws FunctionalException {
		LOGGER.traceEntry("criteres = " + criteres);
		
		Set<ConstraintViolation<RechercheSpot>> violations = this.getValidator().validate(criteres);
		LOGGER.debug("resultat validation spot = " + violations);
		
		List<Spot> result;
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				result = this.getDaoFactory().getSpotDao().rechercheSpot(criteres);
	
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
			for (ConstraintViolation<RechercheSpot> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
			
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<Departement> getDepartements() {
		LOGGER.traceEntry();
		
		List<Departement> result = this.getDaoFactory().getSpotDao().getDepartements();
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<Ville> getVilles(String NumeroDepartement) {
		LOGGER.traceEntry("NumeroDepartement = " + NumeroDepartement);
		
		List<Ville> result = this.getDaoFactory().getSpotDao().getVilles(NumeroDepartement);
		
		LOGGER.traceExit(result);
		return result;
	}
	
	@Override
	public List<String> getTypes() {
		LOGGER.traceEntry();
		
		List<String> result = this.getDaoFactory().getSpotDao().getTypes();
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<String> getOrientations() {
		LOGGER.traceEntry();
		
		List<String> result = this.getDaoFactory().getSpotDao().getOrientations();
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<String> getProfils() {
		LOGGER.traceEntry();
		
		List<String> result = this.getDaoFactory().getSpotDao().getProfils();
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<String> getDifficultes() {
		LOGGER.traceEntry();
		
		List<String> result = this.getDaoFactory().getSpotDao().getDifficultes();
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<String> getListTopo(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);
		
		List<String> result = this.getDaoFactory().getSpotDao().getListTopo(spotId);
		
		LOGGER.traceExit(result);
		return result;
	}


	
}
