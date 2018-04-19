package org.escalade.consumer.impl.dao.texte;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.texte.CommentaireDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.Commentaire;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation de CommentaireDao
 * @author Oltenos
 *
 */
@Named("commentaireDao")
public class CommentaireDaoImpl extends AbstractDaoImpl implements CommentaireDao {

	@Inject
	private RowMapper<Commentaire> commentaireRM;
	
	@Override
	public List<Commentaire> getListCommentaire(int spotId) {
		String vSQL = "SELECT * FROM public.commentaire WHERE spot_id = " + spotId + " ORDER BY date ASC";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Commentaire> commentaires = vJdbcTemplate.query(vSQL, commentaireRM);

		return commentaires;
	}

}
