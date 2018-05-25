package org.escalade.webapp.action.utilisateur;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe Action de connection à un compte utilisateur
 * 
 * @author Olenos
 *
 */
public class LoginAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(LoginAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	
	private String mdp;
	
	// ----- Eléments en entrée et sortie
	
	private String pseudo;
	
	// ----- Eléments en sortie
	


	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)
	
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	// ----- Eléments en entrée et sortie (setters et getters)
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	// ----- Eléments en sortie (getters uniquement)
	
	
	// ================= Eléments Struts =======================
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
    private HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    
	// ================= Méthodes d'action ====================

	/**
	 * Action de connection à un compte utilisateur
	 * @return SUCCESS ou INPUT en arrivant sur le formulaire ou si les données entrées sont incorrectes
	 * @throws FunctionalException 
	 */
	public String login() throws FunctionalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		if (pseudo == null) {// = arrivé dans le formulaire (sinon pseudo n'est pas null)
			result = ActionSupport.INPUT;
		}else { //  = traitement du formulaire
			
			if(managerFactory.getUtilisateurManager().getUtilisateur(pseudo)==null) {//Vérification du pseudo
				result = ActionSupport.INPUT;
				addFieldError("pseudo", getText("error.pseudoExistePas"));
			} else {
				Utilisateur utilisateur = managerFactory.getUtilisateurManager().authentification(pseudo, mdp);//Vérification du mot de passe
				if(utilisateur==null) {//C'est à dire que le mdp est faux
					addFieldError("mdp", getText("error.mdpIncorrecte"));
					result = ActionSupport.INPUT;
				}else {//Connection : changement sessionId + mise en session de l'utilisateur
					this.servletRequest.changeSessionId();
					this.session.put("utilisateur", utilisateur);
				}
			}
		}

		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action de déconnection d'un compte utilisateur
	 * @return SUCCESS
	 */
	public String logout() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		// Retrait de l'utilisateur de la session
		this.session.remove("utilisateur");
		// Invalidation de la session
		this.servletRequest.getSession().invalidate();

		// Message de confirmation
		this.addActionMessage(this.getText("confirmationDecoUtilisateur"));

		LOGGER.traceExit(result);
		return result;
	}
}
