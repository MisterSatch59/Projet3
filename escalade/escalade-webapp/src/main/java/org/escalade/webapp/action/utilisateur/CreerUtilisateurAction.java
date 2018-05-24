package org.escalade.webapp.action.utilisateur;

import java.io.File;
import java.io.IOException;
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
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action de création d'un utilisateur
 * 
 * @author Olenos
 *
 */
public class CreerUtilisateurAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(CreerUtilisateurAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private String mdp;
	private String mdp2;
	
	private File myFile;
	@SuppressWarnings("unused")
	private String myFileContentType;
	private String myFileFileName;

	// ----- Eléments en entrée et sortie
	// Rq les données de formulaire sont également en sortie afin de conserver les
	// valeur déja remplis
	// en cas d'erreur dans les donnés (retour vers la page avec validate())
	private String pseudo;
	private String email;

	// ----- Eléments en sortie
	
	// ================= Eléments Struts =======================
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

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	/**
	 * Action d'accés au formulaire et de création d'un utilisateur
	 * 
	 * @return INPUT pour accéder au formulaire ou SUCCES aprés validation du
	 *         formulaire
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	public String creer() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		if (pseudo == null) {// = arrivé sur le formulaire
			result = ActionSupport.INPUT;
		} else {// = traitement du formulaire

			// traitement upload fichier
			String destPath = request.getServletContext().getRealPath("/img/avatar");
			
			String fileName;
			if(myFileFileName==null) {
				fileName="no_avatar.png";
			}else {
				fileName = pseudo + ".png";

				try {
					File destFile = new File(destPath, fileName);
					FileUtils.copyFile(myFile, destFile);

				} catch (IOException e) {
					LOGGER.debug(e);
					return ERROR;
				}
			}

			Utilisateur utilisateur = new Utilisateur(pseudo, email, fileName, false);
			utilisateur.setMdp(mdp);

			managerFactory.getUtilisateurManager().createUtilisateur(utilisateur);
			this.addActionMessage(getText("creerUtilisateur.utilisateurCreer"));
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

		if (pseudo != null) {// Pas de validation à réaliser en arrivant sur le formulaire
			Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
			
			//Utilisation de la JSR 349 pour vérifié la validité des données pour chaque champ du formulaire
			
			Utilisateur utilisateur = null;
			try {
				utilisateur = managerFactory.getUtilisateurManager().getUtilisateur(pseudo);
			} catch (FunctionalException e) {
				LOGGER.error(e);
				this.addActionError(getText(e.getMessage()));
			}
			if(utilisateur!=null) {
				addFieldError("pseudo", getText("error.pseudoExiste"));
			}

			Set<ConstraintViolation<Utilisateur>> valueViolationsUtilisateur = validator.validateValue(Utilisateur.class, "pseudo", pseudo);
			if (!valueViolationsUtilisateur.isEmpty())
				addFieldError("pseudo", getText("error.pseudo"));

			valueViolationsUtilisateur = validator.validateValue(Utilisateur.class, "mail", email);
			if (!valueViolationsUtilisateur.isEmpty())
				addFieldError("email", getText("error.email"));

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
			
			if(myFileFileName!=null) {
				String[] tab = myFileFileName.split("\\.");
				if(!tab[tab.length-1].equalsIgnoreCase("png")) {
					addFieldError("myFile", getText("error.format"));
				}
			}
		}

		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}
}
