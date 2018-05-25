package org.escalade.webapp.action.exemplaire;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class SupprimerExemplaireTopoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SupprimerExemplaireTopoAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private int exemplaireId;

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setExemplaireId(int exemplaireId) {
		this.exemplaireId = exemplaireId;
	}

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	/**
	 * Action de suppression d'un exemplaire de topo
	 * @return SUCCESS
	 * @throws TechnicalException
	 */
	public String supprimerExemplaire() throws TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		//Supprime l'exemplaire de la base de données
		managerFactory.getExemplaireTopoManager().deleteExemplaireTopo(exemplaireId);
		
		//Message de confirmation
		this.addActionMessage(getText("confirmationSuppressionExemplaire"));
		
		LOGGER.traceExit(result);
		return result;
	}

}
