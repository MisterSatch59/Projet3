package org.escalade.webapp.action.emprunt;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

public class EmprunterAction extends ActionSupport  implements SessionAware {
	//implements SessionAware si nécessaire uniquement
		private static final long serialVersionUID = 1L;
		private static final Logger LOGGER = LogManager.getLogger(EmprunterAction.class);
		
		// ==================== Attributs ====================
		@Inject
		private ManagerFactory managerFactory;
		
		// ----- Paramètres en entrée
		
		private int exemplaireId;
		private Date debut;
		private Date fin;
		
		// ----- Eléments en entrée et sortie
		
		// ----- Eléments en sortie
		
		// ================= Eléments Struts =======================
		private Map<String, Object> session;
			
		@Override//Si implements SessionAware 
		public void setSession(Map<String, Object> session) {
			this.session = session;
		}
		
		// ==================== Getters/Setters ====================
		
		// ----- Paramètres en entrée (setters uniquement)
		
		public void setExemplaireId(int exemplaireId) {
			this.exemplaireId = exemplaireId;
		}

		public void setDebut(Date debut) {
			this.debut = debut;
		}

		public void setFin(Date fin) {
			this.fin = fin;
		}
		
		// ----- Eléments en entrée et sortie (setters et getters)
		
		// ----- Eléments en sortie (getters uniquement)
		
		// =================  Méthodes d'action  ====================
		
		/**
		 * Action de création d'un emprunt
		 * @return SUCCESS
		 * @throws NotFoundException
		 * @throws FunctionalException
		 * @throws TechnicalException
		 */
		public String emprunter() throws NotFoundException, FunctionalException, TechnicalException {
			LOGGER.traceEntry("exemplaireId = " + exemplaireId + "debut = " + debut + "fin = " + fin);
			String result = ActionSupport.SUCCESS;
	
			Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
	
			ExemplaireTopo exemplaire = managerFactory.getExemplaireTopoManager().getExemplaireTopo(exemplaireId);
			Emprunt emprunt = new Emprunt(0, debut, fin, utilisateur, exemplaire);
	
			managerFactory.getEmpruntManager().createEmprunt(emprunt);
	
			LOGGER.traceExit(result);
			return result;
		}

	}
