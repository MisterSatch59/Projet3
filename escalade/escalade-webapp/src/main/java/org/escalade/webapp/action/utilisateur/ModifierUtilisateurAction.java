package org.escalade.webapp.action.utilisateur;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class ModifierUtilisateurAction extends ActionSupport implements SessionAware {
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
	
	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

	// ================= Eléments Struts =======================
	private Map<String, Object> session;

	@Override // Si implements SessionAware
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
	
	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	public String modifier() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		
		Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
		
		utilisateur.setMail(email);
		
		if(mdp.isEmpty()) {
			managerFactory.getUtilisateurManager().updateUtilisateur(utilisateur, null);
		}else {
			managerFactory.getUtilisateurManager().updateUtilisateur(utilisateur, mdp);
		}
		
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

		Set<ConstraintViolation<Utilisateur>> valueViolationsUtilisateur = validator.validateValue(Utilisateur.class, "mail", email);
		if (!valueViolationsUtilisateur.isEmpty())
			addFieldError("email", getText("error.email"));
		
		if(!mdp.isEmpty()||!mdp2.isEmpty()) {
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
