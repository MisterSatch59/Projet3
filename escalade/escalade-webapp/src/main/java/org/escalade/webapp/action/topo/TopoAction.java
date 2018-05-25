package org.escalade.webapp.action.topo;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe action d'accés à la liste de topos
 * 
 * @author Oltenos
 *
 */
public class TopoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(TopoAction.class);

	// ==================== Attributs ====================
	@Inject
	private ManagerFactory managerFactory;

	// ----- Paramètres en entrée
	private String titreTopo;
	
	private Date debut;
	private Date fin;
	// ----- Eléments en entrée et sortie

	// ----- Eléments en sortie
	private List<Topo> listTopos;
	
	private Topo topo;
	
	private List<ExemplaireTopo> listExemplaire;

	// ==================== Getters/Setters ====================

	// ----- Paramètres en entrée (setters uniquement)

	public void setTitreTopo(String titreTopo) {
		this.titreTopo = titreTopo;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}	

	// ----- Eléments en entrée et sortie (setters et getters)

	// ----- Eléments en sortie (getters uniquement)

	public List<Topo> getListTopos() {
		return listTopos;
	}

	public Topo getTopo() {
		return topo;
	}
	
	public List<ExemplaireTopo> getListExemplaire() {
		return listExemplaire;
	}

	// ================= Méthodes d'action ====================

	/**
	 * Action affichant la page avec la liste des topos
	 * 
	 * @return SUCCESS
	 */
	public String versTopos() {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;

		//Chargement de la liste de tous les topos
		listTopos = managerFactory.getTopoManager().getListTopos();

		LOGGER.trace("listTopos = " + listTopos);
		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action affichant la page avec les informations d'un topo
	 * @return SUCCESS
	 * @throws FunctionalException
	 * @throws NotFoundException
	 */
	public String infoTopo() throws NotFoundException, FunctionalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		LOGGER.trace("titreTopo = " + titreTopo);

		//Chargement du topo à afficher
		topo = managerFactory.getTopoManager().getTopo(titreTopo);

		LOGGER.traceExit(result);
		return result;
	}
	
	/**
	 * Action AJAX permettant d'afficher la liste des exemplaires dispo du topo aux dates souhaités
	 * @return SUCCESS
	 * @throws FunctionalException 
	 */
	public String doAJAXtopoDispo() throws FunctionalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		LOGGER.trace("titreTopo = " + titreTopo + " debut = " + debut + " fin = " + fin);
		
		//Chargement de la list des exemplaires du topo et dispo aux dates souhaités
		listExemplaire = managerFactory.getExemplaireTopoManager().getListExemplaireTitreTopo(titreTopo, debut, fin);

		LOGGER.traceExit(result);
		return result;
	}

	/**
	 * Action de suppression d'un topo
	 * @return SUCCESS
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	public String supprimerTopo() throws FunctionalException, TechnicalException {
		LOGGER.traceEntry();
		String result = ActionSupport.SUCCESS;
		LOGGER.trace("titreTopo = " + titreTopo);

		//Supprime le topo de la base de données
		managerFactory.getTopoManager().deleteTopo(titreTopo);
		
		//Message de confirmation
		this.addActionMessage(this.getText("confirmationTopoSupprimé"));

		LOGGER.traceExit(result);
		return result;
	}
}
