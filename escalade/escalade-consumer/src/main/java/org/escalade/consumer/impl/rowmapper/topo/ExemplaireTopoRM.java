package org.escalade.consumer.impl.rowmapper.topo;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper pour le Bean ExemplaireTopo
 * 
 * @author Oltenos
 *
 */
@Named
public class ExemplaireTopoRM implements RowMapper<ExemplaireTopo> {
	private static final Logger LOGGER = LogManager.getLogger(ExemplaireTopoRM.class);

	@Inject
	private DaoFactory daoFactory;

	@Override
	public ExemplaireTopo mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		int id = pRS.getInt("id");
		Topo topo = daoFactory.getTopoDao().getTopo(pRS.getString("titre_topo"));
		Utilisateur proprietaire = daoFactory.getUtilisateurDao().getUtilisateur(pRS.getString("pseudo_proprietaire"));

		int conditionId = pRS.getInt("condition_id");
		ZoneTexte condition = null;
		if (!pRS.wasNull()) {
			condition = daoFactory.getZoneTexteDao().getZoneTexte(conditionId);
		}
		ExemplaireTopo exemplaireTopo = new ExemplaireTopo(id, topo, proprietaire, condition);

		LOGGER.traceExit(exemplaireTopo);
		return exemplaireTopo;
	}

}
