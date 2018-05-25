package org.escalade.webapp.action.exemplaire;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.exception.NotFoundException;

import com.opensymphony.xwork2.ActionSupport;

public class InfoExemplaireAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(InfoExemplaireAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private int exemplaireId;

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie
	
	private ExemplaireTopo exemplaire;
	private List<Emprunt> listEmprunt;
	
	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)
	
	public void setExemplaireId(int exemplaireId) {
		this.exemplaireId = exemplaireId;
	}
	
	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)
	
	public ExemplaireTopo getExemplaire() {
		return exemplaire;
	}
	
	public List<Emprunt> getListEmprunt() {
		return listEmprunt;
	}

	// ================= Méthodes d'action ====================

	/**
	 * Action d'affichage des détail d'un exemplaire possédé
	 * @return SUCCESS
	 * @throws NotFoundException
	 */
	public String versInfo() throws NotFoundException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		LOGGER.trace("exemplaireId = " + exemplaireId);
		
		//Récupére les info de l'exemplaire et la liste de toutes les reservations réalisés de cet exemplaire
		exemplaire = managerFactory.getExemplaireTopoManager().getExemplaireTopo(exemplaireId);
		listEmprunt = managerFactory.getEmpruntManager().getListEmprunt(exemplaireId);

		LOGGER.trace("exemplaire = " + exemplaire + "listEmprunt = " + listEmprunt);
		
		LOGGER.traceExit(result);
		return result;
	}
}
