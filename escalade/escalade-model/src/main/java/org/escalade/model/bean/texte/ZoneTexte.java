package org.escalade.model.bean.texte;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean d'une zone de texte
 *
 * @author Oltenos
 */
public class ZoneTexte {

	/**
	 * Identifiant
	 */
	private int id;
	/**
	 * titre de la zone de texte
	 */
	@NotNull
	@Size(min = 1, max = 100)
	private String titre;
	/**
	 * liste des paragraphe ordonnées de la zone de texte
	 */
	@NotNull
	private List<@NotNull @NotEmpty String> listParagraphes;

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param id
	 * @param titre
	 */
	public ZoneTexte(int id, String titre) {
		this.id = id;
		this.titre = titre;
	}

	// Getters et Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<String> getListParagraphes() {
		return listParagraphes;
	}

	public void setListParagraphes(List<String> listParagraphes) {
		this.listParagraphes = listParagraphes;
	}

	@Override
	public String toString() {
		return "ZoneTexte [id=" + id + ", titre=" + titre + "]";
	}

};
