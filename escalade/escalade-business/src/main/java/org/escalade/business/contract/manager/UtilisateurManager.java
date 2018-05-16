package org.escalade.business.contract.manager;

import org.escalade.model.bean.utilisateur.Utilisateur;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

/**
 * UtilisateurManager
 * 
 * @author Oltenos
 *
 */
public interface UtilisateurManager {

	/**
	 * Retourne l'utilisateur correspondant au pseudo
	 * 
	 * @param pseudo
	 * @return Utilisateur
	 * @throws NotFoundException 
	 * @throws FunctionalException 
	 */
	public Utilisateur getUtilisateur(String pseudo) throws FunctionalException;

	/**
	 * Enregistre l'utilisateur dans la base de données Rq : un sel sera généré et
	 * le mot de passe sécurisé
	 * 
	 * @param utilisateur
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public void createUtilisateur(Utilisateur utilisateur) throws FunctionalException, TechnicalException;

	/**
	 * Modifie l'utilisateur dans la base de données Modifie le mot de passe si
	 * nouveauMdp n'est pas null<br/>
	 * ATTENTION le pseudo ne peut pas être modifié (clé dans la base de données)
	 * 
	 * @param utilisateur
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public void updateUtilisateur(Utilisateur utilisateur, String nouveauMdp) throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base de données l'utilisateur correspondant au pseudo
	 * 
	 * @param pseudo
	 * @throws FunctionalException
	 * @throws TechnicalException 
	 */
	public void deleteUtilisateur(String pseudo) throws FunctionalException, TechnicalException;

	/**
	 * Vérifie la concordance du couple pseudo-mot de passe dans la base de donnée
	 * et retourne l'utilisateur si le mdp correpond au pseudo et null sinon
	 * 
	 * @param pseudo
	 * @param mdp
	 * @return Utilisateur
	 * @throws FunctionalException 
	 * @throws NotFoundException 
	 */
	public Utilisateur authentification(String pseudo, String mdp) throws FunctionalException;
}
