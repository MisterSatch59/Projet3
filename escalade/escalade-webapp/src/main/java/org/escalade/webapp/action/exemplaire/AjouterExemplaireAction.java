package org.escalade.webapp.action.exemplaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class AjouterExemplaireAction extends ActionSupport implements SessionAware {
	// implements SessionAware si nécessaire uniquement
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(AjouterExemplaireAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private String titreTopo;

	private String titre;
	private String texte;

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

	private Topo topo;

	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override // Si implements SessionAware
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setTitreTopo(String titreTopo) {
		this.titreTopo = titreTopo;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	public Topo getTopo() {
		return topo;
	}

	// ================= Méthodes d'action ====================

	public String ajouter() throws NotFoundException, FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.trace("titreTopo = " + titreTopo+ " titre = " + titre + " texte = " + texte );
		
		topo = managerFactory.getTopoManager().getTopo(titreTopo);
		
		if(titre==null) {
			result = ActionSupport.INPUT;
		}else {
			List<String> listParagraphes = new ArrayList<String>();
			String[] paragraphes = texte.split("\n");
			for (int i = 0; i < paragraphes.length; i++) {
				listParagraphes.add(paragraphes[i]);
			}
			
			ZoneTexte zt = new ZoneTexte(0,titre);
			zt.setListParagraphes(listParagraphes);
			
			ExemplaireTopo exemplaire = new ExemplaireTopo(0,topo,(Utilisateur) session.get("utilisateur"),zt);
			
			managerFactory.getExemplaireTopoManager().createExemplaireTopo(exemplaire);
		}

		LOGGER.traceExit(result);
		return result;
	}

}
