package org.escalade.consumer.impl.dao.texte;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.texte.ZoneTexteDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Implementation de ZoneTexteDao
 * 
 * @author Oltenos
 *
 */
@Named("zoneTexteDao")
public class ZoneTexteDaoImpl extends AbstractDaoImpl implements ZoneTexteDao {
	private static final Logger LOGGER = LogManager.getLogger(ZoneTexteDaoImpl.class);

	@Inject
	private RowMapper<ZoneTexte> zoneTexteRM;

	@Override
	public ZoneTexte getZoneTexte(int id) {
		LOGGER.traceEntry("id = " + id);

		String vSQL = "SELECT * FROM public.zone_texte WHERE id = :id";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<ZoneTexte> zoneTexteResult = vJdbcTemplate.query(vSQL, vParams, zoneTexteRM);

		ZoneTexte zoneTexte;
		if (zoneTexteResult.isEmpty()) {
			zoneTexte = null;
		} else {
			zoneTexte = zoneTexteResult.get(0);
			zoneTexte.setListParagraphes(this.getListParaphes(zoneTexte.getId()));
		}

		LOGGER.traceExit(zoneTexte);
		return zoneTexte;
	}

	/**
	 * Retourne la liste des paragraphes d'une zone de texte à partir de son
	 * identifiant
	 * 
	 * @param zoneTexteId
	 * @return List<String>
	 */
	private List<String> getListParaphes(int zoneTexteId) {
		LOGGER.traceEntry("zoneTexteId = " + zoneTexteId);

		String vSQL = "SELECT texte FROM public.paragraphe WHERE zone_texte_id = :id ORDER BY num_ordre ASC";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", zoneTexteId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listParaphes = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		LOGGER.traceExit(listParaphes);
		return listParaphes;
	}

	@Override
	public ZoneTexte createZoneTexte(ZoneTexte zoneTexte) {
		LOGGER.traceEntry("zoneTexte = " + zoneTexte);

		if (zoneTexte != null) {
			String vSQL = "INSERT INTO public.zone_texte (titre) VALUES (:titre)";

			SqlParameterSource vParams = new BeanPropertySqlParameterSource(zoneTexte);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			KeyHolder keyHolder = new GeneratedKeyHolder();// permet de récupérer la clef primaire généré par
															// autoincrement
			vJdbcTemplate.update(vSQL, vParams, keyHolder);
			int zoneTexteId = (int) keyHolder.getKeys().get("id");
			zoneTexte.setId(zoneTexteId);// enregistre l'id créer dans le bean

			List<String> listParagraphes = zoneTexte.getListParagraphes();
			this.createListParagraphes(zoneTexteId, listParagraphes);
		}

		LOGGER.traceExit(zoneTexte);
		return zoneTexte;
	}

	/**
	 * Création des paragraphes de la zone de texte dans la base de données
	 * 
	 * @param zoneTexteId
	 * @param listParagraphes
	 */
	private void createListParagraphes(int zoneTexteId, List<String> listParagraphes) {
		LOGGER.traceEntry("zoneTexteId = " + zoneTexteId + "listParagraphes = " + listParagraphes);

		if (listParagraphes != null) {
			int i = 0;
			for (String paragraphe : listParagraphes) {
				String vSQL = "INSERT INTO public.paragraphe (zone_texte_id,num_ordre,texte) VALUES (:zone_texte_id, :num_ordre, :texte)";

				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("zone_texte_id", zoneTexteId);
				vParams.addValue("num_ordre", i);
				vParams.addValue("texte", paragraphe);

				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

				vJdbcTemplate.update(vSQL, vParams);
				i++;
			}
		}

		LOGGER.traceExit();
	}

	@Override
	public void deleteZoneTexte(int id) {
		LOGGER.traceEntry("id = " + id);

		// Remarque : suppression des paragraphes en cascade
		String vSQL = "DELETE FROM public.zone_texte WHERE id = :id";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		vJdbcTemplate.update(vSQL, vParams);

		LOGGER.traceExit();
	}

	@Override
	public void updateZoneTexte(ZoneTexte zoneTexte) {
		LOGGER.traceEntry("zoneTexte = " + zoneTexte);

		if (zoneTexte != null) {
			String vSQL = "UPDATE public.zone_texte SET titre = :titre WHERE id = :id";

			SqlParameterSource vParams = new BeanPropertySqlParameterSource(zoneTexte);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);

			List<String> listParagraphes = zoneTexte.getListParagraphes();
			this.deleteListParagraphes(zoneTexte.getId());
			this.createListParagraphes(zoneTexte.getId(), listParagraphes);
		}

		LOGGER.traceExit();
	}

	/**
	 * Supprime de la base données les paragraphes de la zone de texte
	 * 
	 * @param zoneTexteId
	 */
	private void deleteListParagraphes(int zoneTexteId) {
		LOGGER.traceEntry("zoneTexteId = " + zoneTexteId);

		String vSQL = "DELETE FROM public.paragraphe WHERE zone_texte_id = :zoneTexteId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("zoneTexteId", zoneTexteId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		vJdbcTemplate.update(vSQL, vParams);

		LOGGER.traceExit();
	}
}
