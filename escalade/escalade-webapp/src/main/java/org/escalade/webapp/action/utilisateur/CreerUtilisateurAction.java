package org.escalade.webapp.action.utilisateur;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action de création d'un utilisateur
 * 
 * @author Olenos
 *
 */
public class CreerUtilisateurAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(CreerUtilisateurAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	private String mdp;
	private String mdp2;

	// ----- Eléments en entrée et sortie
	// Rq les données de formulaire sont également en sortie afin de conserver les
	// valeur déja remplis
	// en cas d'erreur dans les donnés (retour vers la page avec validate())
	private String pseudo;
	private String email;

	// ----- Eléments en sortie

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public void setMdp2(String mdp2) {
		this.mdp2 = mdp2;
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
	 */
	public String creer() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		if (pseudo == null) {// = entré dans le formulaire
			result = ActionSupport.INPUT;
		} else {// = traitement du formulaire
			Utilisateur utilisateur = new Utilisateur(pseudo, email, null, false);
			utilisateur.setMdp(mdp);

			try {
				managerFactory.getUtilisateurManager().createUtilisateur(utilisateur);
				this.addActionMessage(getText("creerUtilisateur.utilisateurCreer"));
			} catch (FunctionalException e) {
				result = ActionSupport.INPUT;
				this.addActionError(e.getMessage());
			}
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
			
			
			if(managerFactory.getUtilisateurManager().getUtilisateur(pseudo)!=null) {
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
				Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W).{8,}$");
				Matcher matcher = pattern.matcher(mdp);
				if (!matcher.lookingAt()) {
					addFieldError("mdp", getText("error.mdp"));
				}
			}
		}

		LOGGER.traceExit("hasFieldErrors ? : " + this.hasFieldErrors());
	}

}
