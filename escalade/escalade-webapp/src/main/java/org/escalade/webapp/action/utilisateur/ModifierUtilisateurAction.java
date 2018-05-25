package org.escalade.webapp.action.utilisateur;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class ModifierUtilisateurAction extends ActionSupport implements SessionAware, ServletRequestAware {
	// implements SessionAware si nécessaire uniquement
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ModifierUtilisateurAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private String mdp;
	private String mdp2;
	private String email;
	private File myFile;
	@SuppressWarnings("unused")
	private String myFileContentType;
	private String myFileFileName;
	
	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override // Si implements SessionAware
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public void setMdp2(String mdp2) {
		this.mdp2 = mdp2;
	}

	public void setEmail(String email) {
		this.email = email;
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

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	/**
	 * Action de modification d'un compte utilisateur
	 * @return SUCCESS
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	public String modifier() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
		
		// traitement upload fichier
		String destPath = request.getServletContext().getRealPath("/img/avatar");//path d'enregistrement de l'image avatar

		String fileName;
		if (myFileFileName != null) {//si aucun fichier d'uploader, pas de changement de l'avatar
			fileName = utilisateur.getPseudo() + ".png";

			try {
				File destFile = new File(destPath, fileName);
				FileUtils.copyFile(myFile, destFile);

			} catch (IOException e) {
				LOGGER.debug(e);
				return ERROR;
			}
			
			utilisateur.setAvatar(fileName);
		}
		
		//Modification de l'adresse e-mail
		utilisateur.setMail(email);
		
		//Modification du mdp si un nouveau mdp est entré
		//Si le champ mdp est vide, il faut insérer null en second paramètre (et non pas un String vide) pour ne pas changer le mot de passe
		if(mdp.isEmpty()) {
			managerFactory.getUtilisateurManager().updateUtilisateur(utilisateur, null);
		}else {
			managerFactory.getUtilisateurManager().updateUtilisateur(utilisateur, mdp);
		}
		
		//Message confirmation de modification de l'utilisateur
		this.addActionMessage(this.getText("confirmationModificationUtilisateur"));

		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Validation du formulaire
	 */
	@Override
	public void validate() {
		LOGGER.traceEntry();

		Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
		
		//Utilisation de la JSR 349 pour vérifié la validité des données pour chaque champ du formlaire
		Set<ConstraintViolation<Utilisateur>> valueViolationsUtilisateur = validator.validateValue(Utilisateur.class, "mail", email);
		if (!valueViolationsUtilisateur.isEmpty())
			addFieldError("email", getText("error.email"));
		
		if(!mdp.isEmpty()||!mdp2.isEmpty()) {
			if (!mdp.equals(mdp2)) {
				addFieldError("mdp2", getText("error.mdpDiff"));
			} else {
				//Vérification de la compléxité du mot de passe (8caractères, 1 lettre, 1 caractère spécial et 1 chiffre minimum)
				Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W).{8,}$");
				Matcher matcher = pattern.matcher(mdp);
				if (!matcher.lookingAt()) {
					addFieldError("mdp", getText("error.mdp"));
				}
			}
		}
		
		//vérification du fichier uploadé
		if(myFileFileName!=null) {
			String[] tab = myFileFileName.split("\\.");
			if(!tab[tab.length-1].equalsIgnoreCase("png")) {
				addFieldError("myFile", getText("error.format"));
			}
		}

		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}
}
