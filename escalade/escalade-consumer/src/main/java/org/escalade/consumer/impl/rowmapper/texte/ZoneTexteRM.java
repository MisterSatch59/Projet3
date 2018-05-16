package org.escalade.consumer.impl.rowmapper.texte;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper pour le Bean ZoneTexte
 * 
 * @author Oltenos
 *
 */
@Named
public class ZoneTexteRM implements RowMapper<ZoneTexte> {
	private static final Logger LOGGER = LogManager.getLogger(ZoneTexteRM.class);

	@Override
	public ZoneTexte mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		int id = pRS.getInt("id");
		String titre = pRS.getString("titre");
		ZoneTexte zoneTexte = new ZoneTexte(id, titre);

		LOGGER.traceExit(zoneTexte);
		return zoneTexte;
	}

}
