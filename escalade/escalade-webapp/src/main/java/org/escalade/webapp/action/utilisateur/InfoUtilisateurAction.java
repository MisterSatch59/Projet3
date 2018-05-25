package org.escalade.webapp.action.utilisateur;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;

import com.opensymphony.xwork2.ActionSupport;

public class InfoUtilisateurAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(InfoUtilisateurAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie
	private List<Emprunt> listEmprunt;
	private List<ExemplaireTopo> listExemplaireTopo;

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)
	public List<Emprunt> getListEmprunt() {
		return listEmprunt;
	}
	
	public List<ExemplaireTopo> getListExemplaireTopo() {
		return listExemplaireTopo;
	}

	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	// ================= Méthodes d'action ====================
	/**
	 * Action de chargement de la page d'information du compte utilisateur
	 * @return SUCCESS
	 * @throws FunctionalException
	 */
	public String info() throws FunctionalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
		
		//Chargement de l'utilisateur et de la liste des exemplaires possédés
		listEmprunt = managerFactory.getEmpruntManager().getListEmprunt(utilisateur.getPseudo());
		listExemplaireTopo = managerFactory.getExemplaireTopoManager().getListExemplaireTopo(utilisateur.getPseudo());
		
		LOGGER.trace("listEmprunt = " + listEmprunt);
		LOGGER.traceExit(result);
		return result;
	}

}
