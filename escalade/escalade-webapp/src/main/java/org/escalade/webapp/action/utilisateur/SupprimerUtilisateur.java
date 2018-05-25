package org.escalade.webapp.action.utilisateur;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

public class SupprimerUtilisateur extends ActionSupport implements ServletRequestAware, SessionAware {
	// implements SessionAware si nécessaire uniquement
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SupprimerUtilisateur.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée

	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie

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

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	// ================= Méthodes d'action ====================

	/**
	 * Action de suppression d'un compte utilisateur
	 * @return SUCCESS
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	public String supprimer() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		// récupération de l'utilisateur en session
		Utilisateur utilisateur = (Utilisateur) session.get("utilisateur");

		// Retrait de l'utilisateur de la session
		this.session.remove("utilisateur");

		// Invalidation de la session
		this.servletRequest.getSession().invalidate();

		// suppression de la base de donnée (Rq remplacement par "Utilisateur Supprimé"
		// pour les spot et commentaire créé)
		managerFactory.getUtilisateurManager().deleteUtilisateur(utilisateur.getPseudo());

		// Suppression de l'image de l'avatar
		String avatar = utilisateur.getAvatar();
		LOGGER.debug("avatar = " + avatar);
		if (avatar != null && !avatar.equals("no_avatar.png")) {
			String path = servletRequest.getServletContext().getRealPath("/img/avatar");
			File file = new File(path, utilisateur.getAvatar());
			FileUtils.deleteQuietly(file);
		}

		// Message de confirmation
		this.addActionMessage(this.getText("confirmationSuppressionUtilisateur"));

		LOGGER.traceExit(result);
		return result;
	}
}
