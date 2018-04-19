package org.escalade.consumer.impl.dao.texte;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.texte.ZoneTexteDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation de ZoneTexteDao
 * @author Oltenos
 *
 */
@Named("zoneTexteDao")
public class ZoneTexteDaoImpl extends AbstractDaoImpl implements ZoneTexteDao {

	@Inject
	private RowMapper<ZoneTexte> zoneTexteRM;

	@Override
	public ZoneTexte getZoneTexte(int id) {
		String vSQL = "SELECT * FROM public.zone_texte WHERE id = " + id;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ZoneTexte> zoneTexteResult = vJdbcTemplate.query(vSQL, zoneTexteRM);

		ZoneTexte zoneTexte;
		if (zoneTexteResult.isEmpty()) {
			zoneTexte= null;
		} else {
			zoneTexte= zoneTexteResult.get(0);
			zoneTexte.setListParagraphes(this.getListParaphes(zoneTexte.getId()));
		}
		
		return zoneTexte;
	}

	/**
	 * Retourne la liste des paragraphe d'une zone de texte à partir de son identifiant
	 * @param id
	 * @return List<String>
	 */
	private List<String> getListParaphes(int id) {
		String vSQL = "SELECT texte FROM public.paragraphe WHERE zone_texte_id = "+ id +" ORDER BY num_ordre ASC";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listParaphes = vJdbcTemplate.queryForList(vSQL, String.class);

		return listParaphes;
	}
}
