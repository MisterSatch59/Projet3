package org.escalade.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.recherche.RechercheSpot;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action avec la gestion actions concernant les spots
 * @author Oltenos
 *
 */
public class SpotAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SpotAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;


	// ----- Paramètres en entrée
	
	private Departement departement;
	private String ville;
	private String difficulteMin;
	private String difficulteMax;
	private int commentaireId;
	private String titre;
	private String texteCommentaire;
	private boolean alerte;
	
	// ----- Eléments en entrée et sortie
	
	private int spotId;
	private String message;
	
    // ----- Eléments en sortie

	private List<Departement> listDepartements;
	private List<Ville> listVille;
	private List<String> listDifficultes;
	private List<String> listTypes;
	private List<String> listProfils;
	private List<String> listOrientations;
	private List<Ville> listVilles;
	private List<Spot> resultatRecherche;
	private Spot spot;
	private List<String> listTopo;

		
	// ==================== Getters/Setters ====================
	
	// ----- Paramètres en entrée (setters uniquement)
	
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setDifficulteMin(String difficulteMin) {
		this.difficulteMin = difficulteMin;
	}

	public void setDifficulteMax(String difficulteMax) {
		this.difficulteMax = difficulteMax;
	}
	
	public void setCommentaireId(int commentaireId) {
		this.commentaireId = commentaireId;
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
	
	// ----- Eléments en entrée et sortie (setters et getters)
	
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	public int getSpotId() {
		return spotId;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
    // ----- Eléments en sortie (getters uniquement)
	
	public List<Departement> getListDepartements() {
		return listDepartements;
	}
	
	public List<Ville> getListVille() {
		return listVille;
	}

	public List<String> getListDifficultes() {
		return listDifficultes;
	}
	
	public List<String> getListTypes() {
		return listTypes;
	}
	
	public List<String> getListProfils() {
		return listProfils;
	}

	public List<String> getListOrientations() {
		return listOrientations;
	}

	public List<Ville> getListVilles() {
		return listVilles;
	}

	public List<Spot> getResultatRecherche() {
		return resultatRecherche;
	}
	
	public Spot getSpot() {
		return spot;
	}
	
	public List<String> getListTopo() {
		return listTopo;
	}
	
	// =================  Méthodes d'action  ====================

	/**
	 * Action de chargement de la page de recherche de spot (spots.jsp)<br/>
	 * L'action transmet la liste des départements et des difficultés pour les listes déroulantes de la page de recherche.
	 * @return SUCCESS
	 */
	public String versRecherche() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		listDepartements = managerFactory.getSpotManager().getDepartements();
		listVille = managerFactory.getSpotManager().getVilles("");
		listDifficultes = managerFactory.getSpotManager().getDifficultes();
		
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action AJAX de mise à jour de la liste des villes aprés sélection du département dans (spots.jsp)
	 * @return
	 */
	public String doAJAXgetVilles() {
		LOGGER.traceEntry();
		
		LOGGER.debug("departement = " + departement);
		
		String result = ActionSupport.SUCCESS;
		if (departement == null) {
			listVilles = managerFactory.getSpotManager().getVilles(null);
		} else {
			listVilles = managerFactory.getSpotManager().getVilles(departement.getNumero());
		}

		LOGGER.debug("listVilles = " + listVilles);
		
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action AJAX de chargement du résultats de la recherche (spots.jsp)
	 * @return SUCCESS ou ERROR
	 */
	public String doAJAXrecherche() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		listDepartements = managerFactory.getSpotManager().getDepartements();
		listDifficultes = managerFactory.getSpotManager().getDifficultes();
		
		RechercheSpot recherche=new RechercheSpot();
		
		recherche.setDepartement(departement);
		
		if(ville!=null && !ville.isEmpty()) {
		    recherche.setNomVille(ville);
		}else {
			recherche.setCpVille(null);
			recherche.setNomVille(null);
		}
		
		if(difficulteMin!=null && difficulteMin.isEmpty())
			recherche.setDifficulteMin(null);
		else
			recherche.setDifficulteMin(difficulteMin);
		
		if(difficulteMax!=null && difficulteMax.isEmpty())
			recherche.setDifficulteMax(null);
		else
			recherche.setDifficulteMax(difficulteMax);
		
		LOGGER.debug("recherche = " + recherche);
		
		try {
			resultatRecherche = managerFactory.getSpotManager().rechercheSpot(recherche);
		} catch (FunctionalException e) {
			LOGGER.error(e);
			this.addActionError(getText(e.getMessage()));
			result=ActionSupport.ERROR;
		}
		
		LOGGER.debug("resultatRecherche" + resultatRecherche);
		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action affichant la fiche d'un spot (spotInfo.jsp)
	 * @return SUCCESS
	 */
	public String spotInfo() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("spotId = " + spotId);
		
		spot = managerFactory.getSpotManager().getSpot(spotId);
		listTopo=managerFactory.getSpotManager().getListTopo(spotId);
				
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action de suppression d'un spot
	 * @return SUCCESS
	 */
	public String supprimerSpot() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("spotId = " + spotId);
		
		managerFactory.getSpotManager().deleteSpot(spotId);
		
		message = getText("message.supprimé");
				
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action de redirection avec message
	 * @return SUCCESS
	 */
	public String message() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("message = " + message);
						
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action de suppression d'un commentaire
	 * @return SUCCESS
	 */
	public String supprimerCommentaire() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("commentaireId = " + commentaireId);
		
		managerFactory.getSpotManager().deleteCommentaire(commentaireId);
				
		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action de chargement de la page de modification d'un spot
	 * @return
	 */
	public String versModifier() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		LOGGER.debug("spotId = " + spotId);
		
		spot = managerFactory.getSpotManager().getSpot(spotId);
		listDepartements = managerFactory.getSpotManager().getDepartements();
		listTypes = managerFactory.getSpotManager().getTypes();
		listProfils = managerFactory.getSpotManager().getProfils();
		listDifficultes = managerFactory.getSpotManager().getDifficultes();
		listOrientations = managerFactory.getSpotManager().getOrientations();
		
		LOGGER.debug("spot = " + spot);
		
		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action ajoutant un commantaire et retournant le spot modifié
	 * @return SUCCESS ou ERROR
	 */
	public String doAJAXnouveauCommentaire() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		LOGGER.debug("spotId = " + spotId);
		LOGGER.debug("titre = " + titre);
		LOGGER.debug("texteCommentaire = " + texteCommentaire);
		LOGGER.debug("alerte = " + alerte);

		
		Utilisateur auteur = managerFactory.getUtilisateurManager().getUtilisateur("Max");//TODO gestion de l'auteur à faire (ici en dur...)

		Date date = new java.util.Date(); 
		
		List<String> listParagraphes = new ArrayList<String> ();
		String[] paragraphes = texteCommentaire.split("\n");
		for (int i = 0; i < paragraphes.length; i++) {
			listParagraphes.add(paragraphes[i]);
		}

		Commentaire commentaire = new Commentaire(0, titre, listParagraphes, date, auteur , alerte);
		try {
			managerFactory.getSpotManager().createCommentaire(spotId, commentaire);
		} catch (FunctionalException e) {
			LOGGER.error(e);
			this.addActionError(getText(e.getMessage()));
			result=ActionSupport.ERROR;
		}
		
		spot = managerFactory.getSpotManager().getSpot(spotId);

		LOGGER.debug("spot = " + spot);
		LOGGER.traceExit(result);
		return result;
	}
}
