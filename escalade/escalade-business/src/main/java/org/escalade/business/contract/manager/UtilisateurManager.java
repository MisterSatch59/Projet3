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
	 * Rq : un sel sera généré et le mot de passe sécurisé
	 * @param utilisateur
	 * @throws FunctionalException levée en cas de non validation du bean
	 */
	public void createUtilisateur(Utilisateur utilisateur) throws FunctionalException;
	
	/**
	 * Modifie l'utilisateur dans la base de données
	 * Modifie le mot de passe si nouveauMdp n'est pas null
	 * ATTENTION le pseudo ne peut pas être modifié (clé dans la base de données)
	 * @param utilisateur
	 * @throws FunctionalException levée en cas de non validation du bean
	 */
	public void updateUtilisateur(Utilisateur utilisateur , String nouveauMdp) throws FunctionalException;
	
	/**
	 * Supprime de la base de données l'utilisateur correspondant au pseudo
	 * @param pseudo
	 */
	public void deleteUtilisateur(String pseudo);

	/**
	 * Vérifie la concordance du couple pseudo-mot de passe dans la base de donnée et 
	 * retourne l'utilisateur si le mdp correpond au pseudo et null sinon
	 * @param pseudo
	 * @param mdp
	 * @return Utilisateur
	 */
	public Utilisateur authentification(String pseudo, String mdp);
}
