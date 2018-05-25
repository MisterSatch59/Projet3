package org.escalade.webapp.action.topo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

public class ModifierTopoAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ModifierTopoAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	
	private String descriptionTitre;
	private String descriptionTexte;
	private File myFile;
	@SuppressWarnings("unused")
	private String myFileContentType;
	private String myFileFileName;
	private List<Integer> listSpotId;

	// ----- Eléments en entrée et sortie
	
	private String titreTopo;

	// ----- Eléments en sortie
	
	private Topo topo;
	private Map<Integer,String> listTTSpot;
	private Map<Integer,String> listSpot;

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	
	public void setDescriptionTitre(String descriptionTitre) {
		this.descriptionTitre = descriptionTitre;
	}


	public void setDescriptionTexte(String descriptionTexte) {
		this.descriptionTexte = descriptionTexte;
	}


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
	public String getTitreTopo() {
		return titreTopo;
	}

	public void setTitreTopo(String titreTopo) {
		this.titreTopo = titreTopo;
	}
	
	// ----- Eléments en sortie (getters uniquement)

	public Topo getTopo() {
		return topo;
	}
	
	public Map<Integer, String> getListSpot() {
		return listSpot;
	}
	
	public Map<Integer, String> getListTTSpot() {
		return listTTSpot;
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
	public String modifier() throws NotFoundException, FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		if(descriptionTitre==null) {//Arrivé sur le formulaire
			
			//Chargement du topo pour préremplir le formulaire avec les données actuelles
			topo = managerFactory.getTopoManager().getTopo(titreTopo);
			//Chargement de la liste de tous les spots
			listTTSpot = managerFactory.getSpotManager().getAllSpot();
			
			//Chargement de la liste des spot du topo et retire ceux ci de la liste de tous les spots
			listSpot = new TreeMap<Integer,String>();
			List<Spot> spots = topo.getListSpot();
			for (Spot spot : spots) {
				listSpot.put(spot.getId(), spot.getNom());
				listTTSpot.remove(spot.getId());
			}
			
			result = ActionSupport.INPUT;
		}else {//traitement du formulaire
			//Chargement du topo à modifier
			topo =managerFactory.getTopoManager().getTopo(titreTopo);
			
			//Création du Bean ZonteTexte "presentation" si remplis, laisse null sinon
			if(descriptionTitre.isEmpty()||descriptionTexte.isEmpty()) {
				topo.setDescription(null);
			}else {
				ZoneTexte presentation = topo.getDescription();
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
				topo.setDescription(presentation);
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
			
			//modification du topo dans la base données
			managerFactory.getTopoManager().updateTopo(topo);
			
			this.addActionMessage(this.getText("confirmationModificationTopo"));
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
		if(descriptionTitre!=null) {//si descriptionTitre est null c'est la méthode versModifier qui est appeler (donc pas de validation), sinon n'est pas null mais au minimum vide
			Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator(); 
			
			//Utilisation de la JSR 349 pour vérifié la validité des données pour chaque champ du formlaire
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
		
		if(this.hasFieldErrors()) {//Chargement des données pour le formulaire
			try {
				topo = managerFactory.getTopoManager().getTopo(titreTopo);
			} catch (NotFoundException | FunctionalException e) {
				LOGGER.error(e);
				this.addActionError(getText(e.getMessage()));
			}

			listTTSpot = managerFactory.getSpotManager().getAllSpot();
			
			listSpot = new TreeMap<Integer,String>();
			List<Spot> spots = topo.getListSpot();
			for (Spot spot : spots) {
				listSpot.put(spot.getId(), spot.getNom());
				listTTSpot.remove(spot.getId());
			}
		}
		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}

}
