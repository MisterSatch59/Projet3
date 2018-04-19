package org.escalade.consumer.contract.dao.utilisateur;

import org.escalade.model.bean.utilisateur.Utilisateur;

/**
 * UtilisateurDao
 * @author Oltenos
 *
 */
public interface UtilisateurDao {

	/**
	 * retorunhe l'utilisateur correspondant au pseudo
	 * @param pseudo
	 * @return Utilisateur
	 */
	Utilisateur getUtilisateur(String pseudo);

}
