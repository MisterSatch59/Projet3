package org.escalade.webapp.action.spot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe action de modification et de création d'un spot
 * @author Oltenos
 *
 */
public class CreerSpotAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(CreerSpotAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	
	private File myFile;
	@SuppressWarnings("unused")
	private String myFileContentType;
	private String myFileFileName;
	
	// ----- Eléments en entrée et sortie
	
	//Rq les données de formulaire sont également en sortie afin de conserver les valeur déja remplis
	//en cas d'erreur dans les donnés (retour vers la page avec validate())
	private String nom;
	private Departement departement;
	private String villeNom;
	private String villeCP;
	private boolean ouvert;
	private String difficulteMin;
	private String difficulteMax;
	private String accessibleEnfants;
	private String hauteurMin;
	private String hauteurMax;
	private String nbSecteur;
	private String nbVoie;
	private String latitude;
	private String longitude;
	private String descriptionTitre;
	private String descriptionTexte;
	private String types;
	private String profils;
	private String orientations;
	
	// ----- Eléments en sortie
	private List<Departement> listDepartements;
	private List<String> listDifficultes;
	private List<String> listTypes;
	private List<String> listProfils;
	private List<String> listOrientations;
	
	private int spotId;

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)
	
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	
	// ----- Eléments en entrée et sortie (setters et getters)
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public String getVilleNom() {
		return villeNom;
	}

	public void setVilleNom(String villeNom) {
		this.villeNom = villeNom;
	}

	public String getVilleCP() {
		return villeCP;
	}

	public void setVilleCP(String villeCP) {
		this.villeCP = villeCP;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public String getDifficulteMin() {
		return difficulteMin;
	}

	public void setDifficulteMin(String difficulteMin) {
		this.difficulteMin = difficulteMin;
	}

	public String getDifficulteMax() {
		return difficulteMax;
	}

	public void setDifficulteMax(String difficulteMax) {
		this.difficulteMax = difficulteMax;
	}

	public String getAccessibleEnfants() {
		return accessibleEnfants;
	}

	public void setAccessibleEnfants(String accessibleEnfants) {
		this.accessibleEnfants = accessibleEnfants;
	}

	public String getHauteurMin() {
		return hauteurMin;
	}

	public void setHauteurMin(String hauteurMin) {
		this.hauteurMin = hauteurMin;
	}

	public String getHauteurMax() {
		return hauteurMax;
	}

	public void setHauteurMax(String hauteurMax) {
		this.hauteurMax = hauteurMax;
	}

	public String getNbSecteur() {
		return nbSecteur;
	}

	public void setNbSecteur(String nbSecteur) {
		this.nbSecteur = nbSecteur;
	}

	public String getNbVoie() {
		return nbVoie;
	}

	public void setNbVoie(String nbVoie) {
		this.nbVoie = nbVoie;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescriptionTitre() {
		return descriptionTitre;
	}

	public void setDescriptionTitre(String descriptionTitre) {
		this.descriptionTitre = descriptionTitre;
	}

	public String getDescriptionTexte() {
		return descriptionTexte;
	}

	public void setDescriptionTexte(String descriptionTexte) {
		this.descriptionTexte = descriptionTexte;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getProfils() {
		return profils;
	}

	public void setProfils(String profils) {
		this.profils = profils;
	}

	public String getOrientations() {
		return orientations;
	}

	public void setOrientations(String orientations) {
		this.orientations = orientations;
	}
	

	// ----- Eléments en sortie (getters uniquement)

	public List<Departement> getListDepartements() {
		return listDepartements;
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

	public int getSpotId() {
		return spotId;
	}
	
	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	// ================= Méthodes d'action ====================

	/**
	 * Action de chargement de la page de création d'un spot
	 * @return SUCCESS
	 */
	public String versCreer() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		listDepartements = managerFactory.getSpotManager().getDepartements();
		listTypes = managerFactory.getSpotManager().getTypes();
		listProfils = managerFactory.getSpotManager().getProfils();
		listDifficultes = managerFactory.getSpotManager().getDifficultes();
		listOrientations = managerFactory.getSpotManager().getOrientations();

		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action de création d'un spot
	 * @throws FunctionalException 
	 * @throws TechnicalException 
	 * @returnSUCCESS
	 */
	public String creer() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		Spot spot = new Spot();
		
		spot.setNom(nom);
		
		//Création de la ville si elle n'existe pas dans la base de données
		List<Ville> listVilles = managerFactory.getSpotManager().getVilles(departement.getNumero());
		for (Ville ville : listVilles) {
			if(ville.getNom().equalsIgnoreCase(villeNom)&&ville.getCP().equalsIgnoreCase(villeCP)) {
				spot.setVille(ville);
			}
		}
		if(spot.getVille()==null) {
			spot.setVille(new Ville(0,villeNom,villeCP,departement));
		}
		
		spot.setOuvert(ouvert);
		spot.setDifficulteMin(difficulteMin);
		spot.setDifficulteMax(difficulteMax);
		
		//adapteEnfants
		LOGGER.debug("accessibleEnfants = " + accessibleEnfants);
		if(accessibleEnfants.isEmpty()) {
			spot.setAdapteEnfants(null);
		}else {
			spot.setAdapteEnfants(Boolean.parseBoolean(accessibleEnfants));
		}
		
		//hauteurMin et hauteurMax
		if(!hauteurMin.isEmpty()) {
			spot.setHauteurMin(Integer.parseInt(hauteurMin));
		}else {
			spot.setHauteurMin(0);
		}
		spot.setHauteurMax(Integer.parseInt(hauteurMax));
		
		//nbSecteur
		if(!nbSecteur.isEmpty()) {
			spot.setNbSecteur(Integer.parseInt(nbSecteur));
		}else {
			spot.setNbSecteur(0);
		}
		
		spot.setNbVoie(nbVoie);
		spot.setLatitude(latitude);
		spot.setLongitude(longitude);
		
		//Création du Bean ZonteTexte "presentation" si remplis, laisse null sinon
		if(descriptionTitre.isEmpty()||descriptionTexte.isEmpty()) {
			spot.setPresentation(null);
		}else {
			ZoneTexte presentation = spot.getPresentation();
			if(presentation==null) {
				presentation = new ZoneTexte(0, descriptionTitre);
			}else {
				presentation.setTitre(descriptionTitre);
			}
			List<String> listParagraphes = new ArrayList<String>();
			String[] paragraphes = descriptionTexte.split("\n");
			for (int i = 0; i < paragraphes.length; i++) {
				listParagraphes.add(paragraphes[i]);
			}
			presentation.setListParagraphes(listParagraphes);
			spot.setPresentation(presentation);
		}

		//Création des listes Types, profils et orientations
		List<String> listTypesSpot = Arrays.asList(types.split(", "));
		List<String> listProfilsSpot = Arrays.asList(profils.split(", "));
		List<String> listOrientationsSpot = Arrays.asList(orientations.split(", "));
		spot.setTypes(listTypesSpot);
		spot.setProfils(listProfilsSpot);
		spot.setOrientations(listOrientationsSpot);
		
		//Ajout de l'utilisateur connecté comme auteur de la fiche spot
		Utilisateur auteur = (Utilisateur) session.get("utilisateur");
		spot.setAuteur(auteur);
		
		//Ajout dans la base de donnés (appel du SpotManager)
		spot = managerFactory.getSpotManager().createSpot(spot);

		//Récupération de l'id du spot dans la base de données nécéssaire pour la redirection vers l'action d'affichage de la fiche du spot
		spotId = spot.getId();
		LOGGER.debug("spot = " + spot);
		
		
		// traitement upload fichier
		String destPath = request.getServletContext().getRealPath("/img/spot");

		String fileName;
		if (myFileFileName != null) {
			String[] tab = myFileFileName.split("\\.");
			fileName = spotId + "_presentation." + tab[tab.length-1];
			try {
				File destFile = new File(destPath, fileName);
				FileUtils.copyFile(myFile, destFile);
			} catch (IOException e) {
				LOGGER.debug(e);
				return ERROR;
			}
			List<String> listPhotos = new ArrayList<String>();
			listPhotos.add(fileName);
			spot.setListPhotos(listPhotos);
			
			managerFactory.getSpotManager().updateSpot(spot);
		}
		
		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Validation du formulaire de modification
	 */
	@Override
	public void validate() {
		LOGGER.traceEntry();
		if(nom!=null) {//si nom est null c'est la méthode versModifier qui est appeler (donc pas de validation), sinon n'est pas null mais au minimum vide
			Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator(); 
			
			//Utilisation de la JSR 349 pour vérifié la validité des données pour chaque champ du formlaire
			
			Set<ConstraintViolation<Spot>> valueViolationsSpot = validator.validateValue(Spot.class, "nom", nom);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("nom",getText("error.nom"));

			Set<ConstraintViolation<Ville>> valueViolationsVille = validator.validateValue(Ville.class, "nom", villeNom);
			if(!valueViolationsVille.isEmpty())
				addFieldError("villeNom",getText("error.ville.nom"));
			
			valueViolationsVille = validator.validateValue(Ville.class, "cp", villeCP);
			if(!valueViolationsVille.isEmpty())
				addFieldError("villeCP",getText("error.ville.cp"));

			valueViolationsVille = validator.validateValue(Ville.class, "departement", departement);
			if(!valueViolationsVille.isEmpty())
				addFieldError("departement",getText("error.departement"));
			
			valueViolationsSpot = validator.validateValue(Spot.class, "difficulteMin", difficulteMin);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("difficulteMin",getText("error.difficulteMin"));
			
			valueViolationsSpot = validator.validateValue(Spot.class, "difficulteMax", difficulteMax);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("difficulteMax",getText("error.difficulteMax"));
			
			if(difficulteMin.compareToIgnoreCase(difficulteMax)>0) {
				addFieldError("difficulteMax",getText("erreur.difficulte"));
			}
			
			int hMin=0;
			if(!hauteurMin.isEmpty()) {
				hMin=Integer.parseInt(hauteurMin);
			}
			valueViolationsSpot = validator.validateValue(Spot.class, "hauteurMin", hMin);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("hauteurMin",getText("erreur.hauteurMin"));
			
			int hMax=0;
			if(!hauteurMax.isEmpty()) {
				hMax=Integer.parseInt(hauteurMax);
			}else {
				addFieldError("hauteurMax",getText("erreur.hauteurMax"));
			}
			valueViolationsSpot = validator.validateValue(Spot.class, "hauteurMax", hMax);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("hauteurMax",getText("erreur.hauteurMax"));

			if(hMax<hMin) {
				addFieldError("hauteurMax",getText("erreur.hauteur"));
			}

			int nSecteur=0;
			if(!nbSecteur.isEmpty()) {
				nSecteur=Integer.parseInt(nbSecteur);
			}
			valueViolationsSpot = validator.validateValue(Spot.class, "nbSecteur", nSecteur);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("nbSecteur",getText("erreur.nbSecteur"));

			valueViolationsSpot = validator.validateValue(Spot.class, "nbVoie", nbVoie);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("nbVoie",getText("erreur.nbVoie"));
			
			valueViolationsSpot = validator.validateValue(Spot.class, "latitude", latitude);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("latitude",getText("erreur.latitude"));
			
			valueViolationsSpot = validator.validateValue(Spot.class, "longitude", longitude);
			if (!valueViolationsSpot.isEmpty())
				addFieldError("longitude",getText("erreur.longitude"));
			
			if(!descriptionTexte.isEmpty()) {
				Set<ConstraintViolation<ZoneTexte>> valueViolationsZoneTexte = validator.validateValue(ZoneTexte.class, "titre", descriptionTitre);
				if (!valueViolationsZoneTexte.isEmpty())
					addFieldError("descriptionTitre",getText("erreur.presentation.titre"));
			}
			
			if(!descriptionTitre.isEmpty()&&descriptionTexte.isEmpty()) {
				addFieldError("descriptionTitre",getText("erreur.presentation.texte"));
			}
			
			if(types.isEmpty())
				addFieldError("types",getText("erreur.types"));
			if(profils.isEmpty())
				addFieldError("profils",getText("erreur.profils"));
			if(orientations.isEmpty())
				addFieldError("orientations",getText("erreur.orientations"));
			
			if(myFileFileName!=null) {
				String[] tab = myFileFileName.split("\\.");
				if(!tab[tab.length-1].equalsIgnoreCase("png")&&!tab[tab.length-1].equalsIgnoreCase("jpg")&&!tab[tab.length-1].equalsIgnoreCase("jpeg")) {
					addFieldError("myFile", getText("error.format"));
				}
			}
		}
		
		if(this.hasFieldErrors()) {//Si il y a une erreur il faut redonner les liste suivantes
			listDepartements = managerFactory.getSpotManager().getDepartements();
			listTypes = managerFactory.getSpotManager().getTypes();
			listProfils = managerFactory.getSpotManager().getProfils();
			listDifficultes = managerFactory.getSpotManager().getDifficultes();
			listOrientations = managerFactory.getSpotManager().getOrientations();
		}
		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}
}
