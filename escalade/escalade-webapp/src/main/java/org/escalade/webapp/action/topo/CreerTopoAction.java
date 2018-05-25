package org.escalade.webapp.action.topo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class CreerTopoAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(CreerTopoAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	
	private File myFile;
	@SuppressWarnings("unused")
	private String myFileContentType;
	private String myFileFileName;
	private List<Integer> listSpotId;

	// ----- Eléments en entrée et sortie
	private String titre;
	private String descriptionTitre;
	private String descriptionTexte;
	// ----- Eléments en sortie
	
	private Map<Integer,String> listTTSpot;
	private String titreTopo;

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
	
	public void setListSpotId(List<Integer> listSpotId) {
		this.listSpotId = listSpotId;
	}

	// ----- Eléments en entrée et sortie (setters et getters)

	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
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
	
	// ----- Eléments en sortie (getters uniquement)

	public Map<Integer, String> getListTTSpot() {
		return listTTSpot;
	}
	
	public String getTitreTopo() {
		return titreTopo;
	}

	
	// ================= Eléments Struts =======================

	public HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	// ================= Méthodes d'action ====================

	/**
	 * Action de modification d'un topo
	 * @return SUCCESS ou INPUT en arrivant sur le formulaire ou lors d'errur dans les données entrés (méthode validate())
	 * @throws NotFoundException
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	public String creer() throws NotFoundException, FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		if(descriptionTitre==null) {//Arrivé sur le formulaire
			//Chargement de la liste de tous les spots nécéssaire pour le formulaire
			listTTSpot = managerFactory.getSpotManager().getAllSpot();

			result = ActionSupport.INPUT;
		}else {//traitement du formulaire
			Topo topo = null;
			
			//Création du Bean ZonteTexte "presentation" si remplis, laisse null sinon
			if(descriptionTitre.isEmpty()||descriptionTexte.isEmpty()) {
				topo = new Topo(titre, null);
			}else {
				ZoneTexte presentation = new ZoneTexte(0, descriptionTitre);
				
				List<String> listParagraphes = new ArrayList<String>();
				String[] paragraphes = descriptionTexte.split("\n");
				for (int i = 0; i < paragraphes.length; i++) {
					listParagraphes.add(paragraphes[i]);
				}
				presentation.setListParagraphes(listParagraphes);
				topo = new Topo(titre, presentation);
			}
			
			// traitement upload fichier image
			String destPath = request.getServletContext().getRealPath("/img/topo");

			String fileName;
			if (myFileFileName != null) {
				String[] tab = myFileFileName.split("\\.");
				fileName = topo.getTitre() + "_0." + tab[tab.length - 1];
				try {
					File destFile = new File(destPath, fileName);
					FileUtils.copyFile(myFile, destFile);
				} catch (IOException e) {
					LOGGER.debug(e);
					return ERROR;
				}
				List<String> listPhotos = new ArrayList<String>();
				listPhotos.add(fileName);
				topo.setListPhotos(listPhotos);
			}
			
			//Traitement de la liste des spots
			List<Spot> spots= new ArrayList <Spot>();
			for (Integer integer : listSpotId) {
				Spot spot = managerFactory.getSpotManager().getSpot(integer);
				spots.add(spot);
			}
			topo.setListSpot(spots);
			
			//création du topo dans la base de données
			managerFactory.getTopoManager().createTopo(topo);
			
			//chargement du titre du topo pour affichage  avec l'action infoTopo
			titreTopo=topo.getTitre();
		}
		

		
		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Validation du formulaire de création
	 */
	@Override
	public void validate() {
		LOGGER.traceEntry();
		if(descriptionTitre!=null) {//si descriptionTitre est null c'est la méthode versModifier qui est appeler (donc pas de validation), sinon n'est pas null mais au minimum vide
			Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
			
			//Utilisation de la JSR 349 pour vérifié la validité des données pour chaque champ du formlaire
			if(titre.isEmpty()) {
				addFieldError("titre", getText("error.titre"));
			}else {
				Set<ConstraintViolation<Topo>> valueViolationsZoneTexte = validator.validateValue(Topo.class, "titre", titre);
				if (!valueViolationsZoneTexte.isEmpty())
					addFieldError("descriptionTitre",getText("erreur.presentation.titre"));
			}
			
			if(descriptionTitre.isEmpty()||descriptionTexte.isEmpty()) {
				addFieldError("descriptionTitre",getText("erreur.description.topo"));
			} else {
				Set<ConstraintViolation<ZoneTexte>> valueViolationsZoneTexte = validator.validateValue(ZoneTexte.class, "titre", descriptionTitre);
				if (!valueViolationsZoneTexte.isEmpty())
					addFieldError("descriptionTitre",getText("erreur.presentation.titre"));
			}
			
			//Vérification du format du fichier (png ou jpg)
			if(myFileFileName!=null) {
				String[] tab = myFileFileName.split("\\.");
				if(!tab[tab.length-1].equalsIgnoreCase("png")&&!tab[tab.length-1].equalsIgnoreCase("jpg")&&!tab[tab.length-1].equalsIgnoreCase("jpeg")) {
					addFieldError("myFile", getText("error.format"));
				}
			}
		}
		
		if(this.hasFieldErrors()) {
			//Chargement de la liste de tous les spots nécéssaire pour le formulaire
			listTTSpot = managerFactory.getSpotManager().getAllSpot();
		}
		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}

}
