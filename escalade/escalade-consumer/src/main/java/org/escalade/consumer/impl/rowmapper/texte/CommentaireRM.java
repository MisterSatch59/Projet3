package org.escalade.consumer.impl.rowmapper.texte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

@Named
public class CommentaireRM implements RowMapper<Commentaire> {
	private static final Logger LOGGER = LogManager.getLogger(CommentaireRM.class);

	@Inject
	private DaoFactory daoFactory;

	@Override
	public Commentaire mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();

		int id = pRS.getInt("id");

		Date date = pRS.getDate("date");
		Utilisateur auteur = daoFactory.getUtilisateurDao().getUtilisateur(pRS.getString("pseudo_auteur"));
		boolean alerte = pRS.getBoolean("alerte");

		ZoneTexte zoneTexte = daoFactory.getZoneTexteDao().getZoneTexte(id);
		String titre = zoneTexte.getTitre();
		List<String> listParagraphes = zoneTexte.getListParagraphes();

		Commentaire commentaire = new Commentaire(id, titre, listParagraphes, date, auteur, alerte);

		LOGGER.traceExit(commentaire);
		return commentaire;
	}

}
