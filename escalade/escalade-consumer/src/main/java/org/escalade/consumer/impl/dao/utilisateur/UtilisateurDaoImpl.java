package org.escalade.consumer.impl.dao.utilisateur;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.utilisateur.UtilisateurDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * Implementation de UtilisateurDao
 * @author Oltenos
 *
 */
@Named ("utilisateurDao")
public class UtilisateurDaoImpl  extends AbstractDaoImpl implements UtilisateurDao {

	@Inject
	RowMapper<Utilisateur> utilisateurRM;
	
	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		String vSQL = "SELECT pseudo,mail,avatar,admin FROM public.utilisateur WHERE pseudo = '" + pseudo +"'";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Utilisateur> utilisateur = vJdbcTemplate.query(vSQL, utilisateurRM);
		if (utilisateur.isEmpty()) {
			return null;
		} else {
			return utilisateur.get(0);
		}
	}
	
}
