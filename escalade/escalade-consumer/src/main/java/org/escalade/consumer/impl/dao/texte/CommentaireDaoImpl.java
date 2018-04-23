package org.escalade.consumer.impl.dao.texte;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.texte.CommentaireDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Implementation de CommentaireDao
 * @author Oltenos
 *
 */
@Named("commentaireDao")
public class CommentaireDaoImpl extends AbstractDaoImpl implements CommentaireDao {

	@Inject
	private RowMapper<Commentaire> commentaireRM;
	@Inject
	private DaoFactory daoFactory;
	
	@Override
	public List<Commentaire> getListCommentaire(int spotId) {
		String vSQL = "SELECT * FROM public.commentaire WHERE spot_id = :spotId ORDER BY date ASC";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Commentaire> commentaires = vJdbcTemplate.query(vSQL, vParams, commentaireRM);

		return commentaires;
	}

	@Override
	public Commentaire createCommentaire(int spotId, Commentaire commentaire) {
		if(commentaire!=null) {
			ZoneTexte zt = daoFactory.getZoneTexteDao().createZoneTexte(commentaire);
			String vSQL = "INSERT INTO public.commentaire (id,date,pseudo_auteur,alerte,spot_id) VALUES (:id, :date,:pseudoAuteur,:alerte,:spotId)";

			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", zt.getId());
			vParams.addValue("date", commentaire.getDate());
			vParams.addValue("pseudoAuteur", commentaire.getAuteur().getPseudo());
			vParams.addValue("alerte", commentaire.isAlerte());
			vParams.addValue("spotId", spotId);
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			vJdbcTemplate.update(vSQL, vParams, keyHolder);
			commentaire.setId((int) keyHolder.getKeys().get("id"));
		}
		return commentaire;
	}

	@Override
	public void deleteCommentaire(int id) {
		//Suppression en cascade du commentaire à partir de la zone de texte
		daoFactory.getZoneTexteDao().deleteZoneTexte(id);
	}

	@Override
	public void updateCommentaire(Commentaire commentaire) {
		// TODO
	}

}
