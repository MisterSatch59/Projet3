package org.escalade.model.bean.utilisateur;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean utilisateur
 *
 * @author Oltenos
 */
public class Utilisateur {

	/**
	 * Pseudo de l'utilisateur
	 */
	@Size(min = 2, max = 30)
	private String pseudo;
	/**
	 * Adresse e-mail de l'utilisateur
	 */
	@Email
	private String mail;
	/**
	 * adresse de l'image de l'avatar de l'utilisateur - peut être null
	 */
	@Size(max = 100)
	private String avatar;
	/**
	 * Indique si l'utilisateur est un administrateur
	 */
	private boolean admin;
	/**
	 * Mot de passe
	 */
	@NotNull
	@Size(max = 100)
	private String mdp;
	/**
	 * sel utilisé avant hachage du mdp
	 */
	@NotNull
	@Size(min = 20, max = 20)
	private String sel;

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param pseudo
	 * @param mail
	 * @param avatar
	 * @param admin
	 */
	public Utilisateur(String pseudo, String mail, String avatar, boolean admin) {
		this.pseudo = pseudo;
		this.mail = mail;
		this.avatar = avatar;
		this.admin = admin;
	}

	// Getters et Setters
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

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	@Override
	public String toString() {
		return "Utilisateur [pseudo=" + pseudo + "]";
	}

};
