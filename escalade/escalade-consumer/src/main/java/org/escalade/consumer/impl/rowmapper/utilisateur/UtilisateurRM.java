package org.escalade.consumer.impl.rowmapper.utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

@Named
public class UtilisateurRM implements RowMapper<Utilisateur> {
	private static final Logger LOGGER = LogManager.getLogger(UtilisateurRM.class);

	@Override
	public Utilisateur mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();
		
		String pseudo = pRS.getString("pseudo");
		String mail = pRS.getString("mail");
		String avatar = pRS.getString("avatar");
		boolean admin = pRS.getBoolean("admin");
		String mdp = pRS.getString("mdp");
		String sel = pRS.getString("sel");

		Utilisateur utilisateur = new Utilisateur(pseudo, mail, avatar, admin);
		utilisateur.setMdp(mdp);
		utilisateur.setSel(sel);
		
		LOGGER.traceExit(utilisateur);
		return utilisateur;
	}

}
