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
	 * Enregistre le commentaire dans la base de données
	 * @param spotId
	 * @param commentaire
	 * @return Commentaire(avec id)
	 */
	public Commentaire createCommentaire(int spotId, Commentaire commentaire);
	
	/**
	 * Supprime de la base de données le commentaire correspondant à l'identifiant
	 * @param id
	 */
	public void deleteCommentaire(int id);
	
	/**
	 * Modifie le commentaire dans la base de données
	 * @param id
	 */
	public void updateCommentaire(Commentaire commentaire);

}
