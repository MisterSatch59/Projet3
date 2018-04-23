package org.escalade.model.bean.utilisateur;

import javax.validation.constraints.NotNull;

/**
 * Bean utilisateur
 *
 * @author Oltenos
 */
public class Utilisateur {
	/**
	 * Pseudo de l'utilisateur
	 */
	@NotNull
	private String pseudo;
	/**
	 * Adresse e-mail de l'utilisateur
	 */
	@NotNull
	private String mail;
	/**
	 * adresse de l'image de l'avatar de l'utilisateur - peut être null
	 */
	private String avatar;
	/**
	 * Indique si l'utilisateur est un administrateur
	 */
	private boolean admin;
	
	/**
	 * Constructeur avec paramètres
	 * @param pseudo
	 * @param mail
	 * @param avatar
	 * @param admin
	 */
	public Utilisateur(String pseudo, String mail, String avatar, boolean admin) {
		this.pseudo = pseudo;
		this.mail = mail;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
