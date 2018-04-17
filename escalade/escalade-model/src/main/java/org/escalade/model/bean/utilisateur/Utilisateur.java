package org.escalade.model.bean.utilisateur;

/**
 * Bean utilisateur
 *
 * @author Oltenos
 */
public class Utilisateur {
	/**
	 * Pseudo de l'utilisateur
	 */
	private String pseudo;
	/**
	 * Adresse e-mail de l'utilisateur
	 */
	private String adresseMail;
	/**
	 * adresse de l'image de l'avatar de l'utilisateur
	 */
	private String avatar;
	
	/**
	 * Constructeur avec paramètres
	 * @param pseudo
	 * @param adresseMail
	 * @param avatar
	 */
	public Utilisateur(String pseudo, String adresseMail, String avatar) {
		this.pseudo = pseudo;
		this.adresseMail = adresseMail;
		this.avatar = avatar;
	}

	//Getters et Setters
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getAdresseMail() {
		return adresseMail;
	}
	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
};
