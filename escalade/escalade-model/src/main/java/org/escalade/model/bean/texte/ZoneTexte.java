package org.escalade.model.bean.texte;

import java.util.List;

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
	private String titre;
	/**
	 * liste des paragraphe ordonnées de la zone de texte
	 */
	private List<String> listParagraphes;
	
	/**
	 * Constructeur avec paramètres
	 * @param id
	 * @param titre
	 */
	public ZoneTexte(int id, String titre) {
		this.id = id;
		this.titre = titre;
	}
	
	//Getters et Setters
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

};
