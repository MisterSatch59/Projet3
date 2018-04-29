package org.escalade.model.bean.topo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;

/**
 * emplaire d'un topo
 *
 * @author Oltenos
 */
public class ExemplaireTopo {

	/**
	 * Identifiant de l'exemplaire du topo
	 */
	private int id;
	/**
	 * Topo dont il est un exemplaire
	 */
	@NotNull (message="le topo doit être rensigné")
	@Valid
	private Topo topo;
	/**
	 * propriétaire du topo
	 */
	@NotNull (message="le propriétaire doit être rensigné")
	@Valid
	private Utilisateur proprietaire;
	/**
	 * Condition de prêt du topo
	 */
	@NotNull (message="les conditions de prêt doivent être rensignées")
	@Valid
	private ZoneTexte condition;

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param id
	 * @param topo
	 * @param proprietaire
	 * @param condition
	 */
	public ExemplaireTopo(int id, Topo topo, Utilisateur proprietaire, ZoneTexte condition) {
		super();
		this.id = id;
		this.topo = topo;
		this.proprietaire = proprietaire;
		this.condition = condition;
	}

	// Getters et Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Topo getTopo() {
		return topo;
	}
	public void setTopo(Topo topo) {
		this.topo = topo;
	}
	public Utilisateur getProprietaire() {
		return proprietaire;
	}
	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}
	public ZoneTexte getCondition() {
		return condition;
	}
	public void setCondition(ZoneTexte condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "ExemplaireTopo [id=" + id + ", topo=" + topo + "]";
	}
	
	

};
