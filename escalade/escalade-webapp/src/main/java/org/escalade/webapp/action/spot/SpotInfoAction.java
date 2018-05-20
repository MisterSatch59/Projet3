package org.escalade.webapp.action.spot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action d'affichage des informatins d'un spot
 * @author Oltenos
 *
 */
public class SpotInfoAction extends ActionSupport  implements SessionAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SpotInfoAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	private int spotId;

	private String titre;
	private String texteCommentaire;
	private boolean alerte;
	
	private int commentaireId;

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie
	private Spot spot;
	private List<String> listTopo;

	private List<Commentaire> listCommentaires;

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setTexteCommentaire(String texteCommentaire) {
		this.texteCommentaire = texteCommentaire;
	}

	public void setAlerte(boolean alerte) {
		this.alerte = alerte;
	}
	
	public void setCommentaireId(int commentaireId) {
		this.commentaireId = commentaireId;
	}
	

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	public Spot getSpot() {
		return spot;
	}

	public List<String> getListTopo() {
		return listTopo;
	}

	public List<Commentaire> getListCommentaires() {
		return listCommentaires;
	}

	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	// ================= Méthodes d'action ====================

	/**
	 * Action affichant la fiche d'un spot (spotInfo.jsp)
	 * 
	 * @return SUCCESS
	 * @throws NotFoundException 
	 */
	public String getInfo() throws NotFoundException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		LOGGER.debug("spotId = " + spotId);

		spot = managerFactory.getSpotManager().getSpot(spotId);

		listTopo = managerFactory.getSpotManager().getListTopo(spotId);

		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action ajoutant un commantaire et retournant le spot modifié
	 * 
	 * @return SUCCESS ou ERROR
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 * @throws NotFoundException 
	 */
	public String doAJAXnouveauCommentaire() throws FunctionalException, TechnicalException, NotFoundException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		LOGGER.debug("spotId = " + spotId);
		LOGGER.debug("titre = " + titre);
		LOGGER.debug("texteCommentaire = " + texteCommentaire);
		LOGGER.debug("alerte = " + alerte);

		//Utilisation de l'utilisateur en session comme auteur du commentaire
		Utilisateur auteur = (Utilisateur) session.get("utilisateur");
		//Utilisation de la date du jour pour le commentaire
		Date date = new java.util.Date();
		
		//Création de la liste des paragrpahes à partir du contenu du textarea
		List<String> listParagraphes = new ArrayList<String>();
		String[] paragraphes = texteCommentaire.split("\n");
		for (int i = 0; i < paragraphes.length; i++) {
			listParagraphes.add(paragraphes[i]);
		}

		//Création du commentaire dans la base de données
		Commentaire commentaire = new Commentaire(0, titre, listParagraphes, date, auteur, alerte);
		managerFactory.getSpotManager().createCommentaire(spotId, commentaire);

		//Récupération de la listes des commentaires pour affichage
		listCommentaires = managerFactory.getSpotManager().getSpot(spotId).getListCommentaires();

		LOGGER.debug("listCommentaires = " + listCommentaires);
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Validation du formulaire
	 */
	@Override
	public void validate() {
		LOGGER.traceEntry();
		
		if(titre!=null) {
			if(texteCommentaire.isEmpty()) {
				addFieldError("texteCommentaire",getText("erreur.commentaire.texte"));
			}
			
			if(titre.isEmpty()) {
				addFieldError("titre",getText("erreur.commentaire.titre"));
			}
			
			try {
				listCommentaires = managerFactory.getSpotManager().getSpot(spotId).getListCommentaires();
			} catch (NotFoundException e) {
				LOGGER.error(e);
				this.addActionError(getText(e.getMessage()));
			}
		}
		
		LOGGER.traceExit("hasFieldErrors() ? : " + hasFieldErrors());
	}
	
	/**
	 * Action de suppression d'un commentaire
	 * @return SUCCESS
	 * @throws TechnicalException 
	 * @throws NotFoundException 
	 */
	public String doAJAXsupprimerCommentaire() throws TechnicalException, NotFoundException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("commentaireId = " + commentaireId);
		
		managerFactory.getSpotManager().deleteCommentaire(commentaireId);
		
		listCommentaires = managerFactory.getSpotManager().getSpot(spotId).getListCommentaires();
				
		LOGGER.traceExit(result);
		return result;
	}

}
