package org.escalade.consumer.impl.dao.texte;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * 
 * @author Oltenos
 *
 */
@Named("commentaireDao")
public class CommentaireDaoImpl extends AbstractDaoImpl implements CommentaireDao {
	private static final Logger LOGGER = LogManager.getLogger(CommentaireDaoImpl.class);

	@Inject
	private RowMapper<Commentaire> commentaireRM;
	@Inject
	private DaoFactory daoFactory;

	@Override
	public List<Commentaire> getListCommentaire(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);

		String vSQL = "SELECT * FROM public.commentaire WHERE spot_id = :spotId ORDER BY date ASC";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Commentaire> commentaires = vJdbcTemplate.query(vSQL, vParams, commentaireRM);

		LOGGER.traceExit(commentaires);
		return commentaires;
	}

	@Override
	public Commentaire createCommentaire(int spotId, Commentaire commentaire) {
		LOGGER.traceEntry("spotId = " + spotId + "commentaire = " + commentaire);

		if (commentaire != null) {
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

		LOGGER.traceExit(commentaire);
		return commentaire;
	}

	@Override
	public void deleteCommentaire(int id) {
		LOGGER.traceEntry("id = " + id);

		// Suppression en cascade du commentaire à partir de la zone de texte
		daoFactory.getZoneTexteDao().deleteZoneTexte(id);

		LOGGER.traceExit();
	}

	@Override
	public void deleteAllCommentaires(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);

		List<Commentaire> commentaires = this.getListCommentaire(spotId);
		if (commentaires != null) {
			for (Commentaire commentaire : commentaires) {
				// Suppression en cascade du commentaire à partir de la zone de texte
				daoFactory.getZoneTexteDao().deleteZoneTexte(commentaire.getId());
			}
		}

		LOGGER.traceExit();

	}
}
