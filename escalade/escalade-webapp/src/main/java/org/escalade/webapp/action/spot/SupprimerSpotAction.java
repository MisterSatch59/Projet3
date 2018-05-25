package org.escalade.webapp.action.spot;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe action de suppression d'un spot
 * @author Oltenos
 *
 */
public class SupprimerSpotAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SupprimerSpotAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	private int spotId;

	// ----- Eléments en entrée et sortie
	
	// ----- Eléments en sortie

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	
	// ----- Eléments en entrée et sortie (setters et getters)
	
	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	/**
	 * Action de suppression d'un spot
	 * 
	 * @return SUCCESS
	 * @throws TechnicalException 
	 */
	public String supprimerSpot() throws TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		LOGGER.debug("spotId = " + spotId);

		//Suppression du spot dans la base de données
		managerFactory.getSpotManager().deleteSpot(spotId);

		//Message de confirmation
		this.addActionMessage(getText("spot.confirmation.supprimé"));

		LOGGER.traceExit(result);
		return result;
	}

}
