package org.escalade.consumer.impl.rowmapper.spot;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.model.bean.spot.Departement;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper pour le bean Departement
 * 
 * @author Oltenos
 *
 */
@Named
public class DepartementRM implements RowMapper<Departement> {
	private static final Logger LOGGER = LogManager.getLogger(DepartementRM.class);

	@Override
	public Departement mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		// Création du Bean département
		String numero = pRS.getString("numero");
		String nomDepartement = pRS.getString("nom");
		Departement departement = new Departement(numero, nomDepartement);

		LOGGER.traceExit(departement);
		return departement;
	}

}
