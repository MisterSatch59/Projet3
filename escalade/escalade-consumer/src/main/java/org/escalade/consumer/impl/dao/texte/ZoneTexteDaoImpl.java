package org.escalade.consumer.impl.dao.texte;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

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

	@Inject
	private RowMapper<ZoneTexte> zoneTexteRM;

	@Override
	public ZoneTexte getZoneTexte(int id) {
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
		return zoneTexte;
	}

	/**
	 * Retourne la liste des paragraphe d'une zone de texte à partir de son
	 * identifiant
	 * 
	 * @param zoneTexteId
	 * @return List<String>
	 */
	private List<String> getListParaphes(int zoneTexteId) {
		String vSQL = "SELECT texte FROM public.paragraphe WHERE zone_texte_id = :id ORDER BY num_ordre ASC";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", zoneTexteId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listParaphes = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listParaphes;
	}

	@Override
	public ZoneTexte createZoneTexte(ZoneTexte zoneTexte) {
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
		return zoneTexte;
	}

	/**
	 * Création des paragraphes dans la base de données associé à la zoneTexte
	 * 
	 * @param zoneTexteId
	 * @param listParagraphes
	 */
	private void createListParagraphes(int zoneTexteId, List<String> listParagraphes) {
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
	}

	@Override
	public void deleteZoneTexte(int id) {
		//Remarque : suppression des paragraphes en cascade
		String vSQL2 = "DELETE FROM public.zone_texte WHERE id = :id";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);

		NamedParameterJdbcTemplate vJdbcTemplate2 = new NamedParameterJdbcTemplate(getDataSource());

		vJdbcTemplate2.update(vSQL2, vParams);
	}

	@Override
	public void updateZoneTexte(ZoneTexte zoneTexte) {
		//TODO
	}
}
