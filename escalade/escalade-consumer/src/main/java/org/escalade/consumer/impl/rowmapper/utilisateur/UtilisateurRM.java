package org.escalade.consumer.impl.rowmapper.utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Named;

import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

@Named
public class UtilisateurRM implements RowMapper<Utilisateur> {

	@Override
	public Utilisateur mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		String pseudo = pRS.getString("pseudo");
		String mail = pRS.getString("mail");
		String avatar = pRS.getString("avatar");
		boolean admin = pRS.getBoolean("admin");

		Utilisateur utilisateur = new Utilisateur(pseudo, mail, avatar, admin);
		return utilisateur;
	}

}
