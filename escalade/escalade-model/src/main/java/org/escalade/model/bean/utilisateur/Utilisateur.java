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
	 * Indique si l'utilisateur est un administrateur
	 */
	private boolean admin;
	
	/**
	 * Constructeur avec paramètres
	 * @param pseudo
	 * @param adresseMail
	 * @param avatar
	 * @param admin
	 */
	public Utilisateur(String pseudo, String adresseMail, String avatar, boolean admin) {
		this.pseudo = pseudo;
		this.adresseMail = adresseMail;
		this.avatar = avatar;
		this.admin=admin;
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
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
};
