package org.escalade.consumer.contract.dao.texte;

import java.util.List;

import org.escalade.model.bean.texte.Commentaire;

/**
 * CommentaireDao
 * @author Oltenos
 *
 */
public interface CommentaireDao {

	/**
	 * Retourne la liste des commentaires du spot à partir de son identifiant
	 * @param spotId
	 * @return List<Commentaire>
	 */
	public List<Commentaire> getListCommentaire(int spotId);
	
	/**
	 * Enregistre le commentaire dans la base de données et retourne le commentaire modifié (avec son identifiant dans la abse de donnée)
	 * @param spotId
	 * @param commentaire
	 * @return Commentaire
	 */
	public Commentaire createCommentaire(int spotId, Commentaire commentaire);
	
	/**
	 * Supprime de la base de données le commentaire correspondant à l'identifiant
	 * @param id
	 */
	public void deleteCommentaire(int id);
	
	/**
	 * Supprime de la base de données les commentaires du spot à partir de son identifiant
	 * @param spotId
	 */
	public void deleteAllCommentaires(int spotId);

}
