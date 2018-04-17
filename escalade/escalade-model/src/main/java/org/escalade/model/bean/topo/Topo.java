package org.escalade.model.bean.topo;

import java.util.List;

import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.texte.ZoneTexte;

/**
 * Bean topo
 *
 * @author Oltenos
 */
public class Topo {
	/**
	 * titre du topo
	 */
	private String Titre;
	/**
	 * Liste des spot contenus dans le topo
	 */
	private List<Spot> listSpot;
	/**
	 * texte de description du topo
	 */
	private ZoneTexte description;

	/**
	 * Constructeur avec paramètres
	 * @param titre
	 * @param listSpot
	 * @param description
	 */
	public Topo(String titre, List<Spot> listSpot, ZoneTexte description) {
		super();
		Titre = titre;
		this.listSpot = listSpot;
		this.setDescription(description);
	}

	//Getters et Setters
	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}
	public List<Spot> getListSpot() {
		return listSpot;
	}
	public void setListSpot(List<Spot> listSpot) {
		this.listSpot = listSpot;
	}

	public ZoneTexte getDescription() {
		return description;
	}

	public void setDescription(ZoneTexte description) {
		this.description = description;
	}
	
};
