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
	private String titre;
	/**
	 * Liste des spot contenus dans le topo
	 */
	private List<Spot> listSpot;
	/**
	 * texte de description du topo
	 */
	private ZoneTexte description;
	/**
	 * liste des noms des photos du topo
	 */
	private List<String> listPhotos;
	
	/**
	 * Constructeur avec paramètres
	 * @param titre
	 * @param description
	 */
	public Topo(String titre, ZoneTexte description) {
		this.titre = titre;
		this.description = description;
	}
	//Getters et Setters
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
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
	public List<String> getListPhotos() {
		return listPhotos;
	}
	public void setListPhotos(List<String> photos) {
		this.listPhotos = photos;
	}
	
};
