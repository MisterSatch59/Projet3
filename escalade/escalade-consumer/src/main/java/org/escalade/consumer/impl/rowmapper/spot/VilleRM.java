package org.escalade.consumer.impl.rowmapper.spot;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Ville;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper pour le bean Ville
 * 
 * @author Oltenos
 *
 */
@Named
public class VilleRM implements RowMapper<Ville> {
	private static final Logger LOGGER = LogManager.getLogger(VilleRM.class);

	@Inject
	private DaoFactory daoFactory;

	@Override
	public Ville mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		// Création du Bean département
		String numeroDepartement = pRS.getString("departement");
		Departement departement = daoFactory.getSpotDao().getDepartement(numeroDepartement);

		// Création du Bean Ville (utilise le bean département ci dessus)
		int villeId = pRS.getInt("id");
		String cp = pRS.getString("cp");
		String nomVille = pRS.getString("nom");
		Ville ville = new Ville(villeId, nomVille, cp, departement);

		LOGGER.traceExit(ville);
		return ville;
	}

}
