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

	public String supprimerExemplaire() throws TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		managerFactory.getExemplaireTopoManager().deleteExemplaireTopo(exemplaireId);

		LOGGER.traceExit(result);
		return result;
	}

}
