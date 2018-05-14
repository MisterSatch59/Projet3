package org.escalade.webapp.action.spot;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.recherche.RechercheSpot;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action de recherche de spot
 * @author Oltenos
 *
 */
public class RechercheSpotAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(RechercheSpotAction.class);
	
	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;
	
	// ----- Paramètres en entrée
	private Departement departement;
	private String ville;
	private String difficulteMin;
	private String difficulteMax;
	
	// ----- Eléments en entrée et sortie
	
    // ----- Eléments en sortie

	private List<Departement> listDepartements;
	private List<Ville> listVille;
	private List<String> listDifficultes;
	
	private List<Spot> resultatRecherche;
	
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

	// ----- Eléments en entrée et sortie (setters et getters)

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
	
	public List<Spot> getResultatRecherche() {
		return resultatRecherche;
	}
	
		
	// =================  Méthodes d'action  ====================
	
	/**
	 * Action de chargement de la page de recherche de spot (spots.jsp)<br/>
	 * L'action transmet la liste des départements et des difficultés pour les listes déroulantes de la page de recherche.
	 * @return SUCCESS
	 * @throws FunctionalException 
	 */
	public String versRecherche() throws FunctionalException {
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
	 * @throws FunctionalException 
	 */
	public String doAJAXgetVilles() throws FunctionalException {
		LOGGER.traceEntry();
		
		LOGGER.debug("departement = " + departement);
		
		String result = ActionSupport.SUCCESS;
		if (departement == null) {
			listVille = managerFactory.getSpotManager().getVilles(null);
		} else {
			listVille = managerFactory.getSpotManager().getVilles(departement.getNumero());
		}

		LOGGER.debug("listVille = " + listVille);
		
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action AJAX de chargement du résultats de la recherche (spots.jsp)
	 * @return SUCCESS ou ERROR
	 * @throws FunctionalException 
	 */
	public String doAJAXrecherche() throws FunctionalException {
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
		
		resultatRecherche = managerFactory.getSpotManager().rechercheSpot(recherche);
		
		LOGGER.debug("resultatRecherche" + resultatRecherche);
		LOGGER.traceExit(result);
		return result;
	}
}
