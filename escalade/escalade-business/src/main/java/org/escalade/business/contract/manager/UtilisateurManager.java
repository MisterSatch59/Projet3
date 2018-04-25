package org.escalade.business.contract.manager;

import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;

/**
 * UtilisateurManager
 * @author Oltenos
 *
 */
public interface UtilisateurManager {

	/**
	 * Retourne l'utilisateur correspondant au pseudo
	 * @param pseudo
	 * @return Utilisateur
	 */
	public Utilisateur getUtilisateur(String pseudo);

	/**
	 * Enregistre l'utilisateur dans la base de données
	 * @param utilisateur
	 * @throws FunctionalException levée en cas de non validation du bean
	 */
	public void createUtilisateur(Utilisateur utilisateur) throws FunctionalException;
	
	/**
	 * Modifie l'utilisateur dans la base de données
	 * ATTENTION le pseudo ne peut pas être modifié (clé dans la base de données)
	 * @param utilisateur
	 * @throws FunctionalException levée en cas de non validation du bean
	 */
	public void updateUtilisateur(Utilisateur utilisateur) throws FunctionalException;
	
	/**
	 * Supprime de la base de données l'utilisateur correspondant au pseudo
	 * @param pseudo
	 */
	public void deleteUtilisateur(String pseudo);

}
