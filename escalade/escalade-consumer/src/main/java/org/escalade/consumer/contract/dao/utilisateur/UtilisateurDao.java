package org.escalade.consumer.contract.dao.utilisateur;

import org.escalade.model.bean.utilisateur.Utilisateur;

/**
 * UtilisateurDao
 * @author Oltenos
 *
 */
public interface UtilisateurDao {

	/**
	 * Retourne l'utilisateur correspondant au pseudo
	 * @param pseudo
	 * @return Utilisateur
	 */
	public Utilisateur getUtilisateur(String pseudo);
	
	/**
	 * Enregistre l'utilisateur dans la base de données
	 * @param utilisateur
	 */
	public void createUtilisateur(Utilisateur utilisateur);
	
	/**
	 * Supprime de la base de données l'utilisateur correspondant au pseudo
	 * @param pseudo
	 */
	public void deleteUtilisateur(String pseudo);

	/**
	 * Modifie l'utilisateur dans la base de données
	 * @param utilisateur
	 */
	public void updateUtilisateur(Utilisateur utilisateur);
}
