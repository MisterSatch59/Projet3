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

	// ----- Eléments en entrée et sortie
	private String titre;
	private String texte;
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

	// ----- Eléments en entrée et sortie (setters et getters)

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	// ----- Eléments en sortie (getters uniquement)

	public Topo getTopo() {
		return topo;
	}

	// ================= Méthodes d'action ====================

	/**
	 *  Action d'ajout d'un exemplaire d'un topo
	 * @return SUCCESS ou INPUT en arriavant sur la formulaire ou en cas de données invalide dans la formulaire (méthode validate())
	 * @throws NotFoundException
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	public String ajouter() throws NotFoundException, FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.trace("titreTopo = " + titreTopo+ " titre = " + titre + " texte = " + texte );
		
		topo = managerFactory.getTopoManager().getTopo(titreTopo);
		
		if(titre==null) {//Arrivé sur le formulaire
			result = ActionSupport.INPUT;
		}else {//Traitement du formulaire
			//Création des paragraphes à partir du conteu dans le textarea
			List<String> listParagraphes = new ArrayList<String>();
			String[] paragraphes = texte.split("\n");
			for (int i = 0; i < paragraphes.length; i++) {
				listParagraphes.add(paragraphes[i]);
			}
			
			//Création du commentaire
			ZoneTexte zt = new ZoneTexte(0,titre);
			zt.setListParagraphes(listParagraphes);
			
			//Création et enregistrement dans la base de données de l'exemplaire
			ExemplaireTopo exemplaire = new ExemplaireTopo(0,topo,(Utilisateur) session.get("utilisateur"),zt);
			managerFactory.getExemplaireTopoManager().createExemplaireTopo(exemplaire);
		}

		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Validation du formulaire
	 */
	@Override
	public void validate() {
		LOGGER.traceEntry();
		if(titre!=null) {// Pas de validation à réaliser en arrivant sur le formulaire
			
			//Vérification que les champs ne sont pas null
			if(titre.isEmpty()) {
				addFieldError("titre", getText("error.titre"));
			}

			if(texte.isEmpty()) {
				addFieldError("texte", getText("error.texte"));
			}
			
		}
		
		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}

}
