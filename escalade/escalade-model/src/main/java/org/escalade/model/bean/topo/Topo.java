package org.escalade.model.bean.topo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull
	@Size(min = 2, max = 100)
	private String titre;
	/**
	 * Liste des spots contenus dans le topo
	 */
	private List<@Valid Spot> listSpot;
	/**
	 * texte de description du topo
	 */
	@Valid
	@NotNull
	private ZoneTexte description;
	/**
	 * liste des noms des photos du topo
	 */
	private List<String> listPhotos;

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param titre
	 * @param description
	 */
	public Topo(String titre, ZoneTexte description) {
		this.titre = titre;
		this.description = description;
	}

	// Getters et Setters
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

	@Override
	public String toString() {
		return "Topo [titre=" + titre + "]";
	}

};
