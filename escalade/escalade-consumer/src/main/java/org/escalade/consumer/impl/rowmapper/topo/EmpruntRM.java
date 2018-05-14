package org.escalade.consumer.impl.rowmapper.topo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

@Named
public class EmpruntRM implements RowMapper<Emprunt> {
	private static final Logger LOGGER = LogManager.getLogger(EmpruntRM.class);

	@Inject
	private DaoFactory daoFactory;

	@Override
	public Emprunt mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		int id = pRS.getInt("id");
		Date dateDebut = pRS.getDate("debut");
		Date DateFin = pRS.getDate("fin");
		Utilisateur emprunteur = daoFactory.getUtilisateurDao().getUtilisateur(pRS.getString("pseudo_emprunteur"));
		ExemplaireTopo exemplaire = daoFactory.getExemplaireTopoDao().getExemplaireTopo(pRS.getInt("exemplaire_topo_id"));
		Emprunt emprunt = new Emprunt(id, dateDebut, DateFin, emprunteur, exemplaire);

		LOGGER.traceExit(emprunt);
		return emprunt;
	}
}
