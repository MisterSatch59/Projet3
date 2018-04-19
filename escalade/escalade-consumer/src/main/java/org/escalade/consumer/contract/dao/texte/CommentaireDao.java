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
	 * Retournes la liste des commentaires associés au spot à partir de son identifiant (spotId)
	 * @param spotId
	 * @return la liste des commentaires associés au spot
	 */
	public List<Commentaire> getListCommentaire(int spotId);

}
