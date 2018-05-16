package org.escalade.webapp.action.emprunt;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class SupprimerEmpruntAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SupprimerEmpruntAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	private int empruntId;
	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setEmpruntId(int empruntId) {
		this.empruntId = empruntId;
	}

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	public String supprimer() throws TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		managerFactory.getEmpruntManager().deleteEmprunt(empruntId);

		this.addActionMessage(this.getText("confirmationSuppressionEmprunt"));

		LOGGER.traceExit(result);
		return result;
	}

}
