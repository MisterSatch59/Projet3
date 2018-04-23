package org.escalade.consumer.impl.dao.utilisateur;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.utilisateur.UtilisateurDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Implementation de UtilisateurDao
 * 
 * @author Oltenos
 *
 */
@Named("utilisateurDao")
public class UtilisateurDaoImpl extends AbstractDaoImpl implements UtilisateurDao {

	@Inject
	RowMapper<Utilisateur> utilisateurRM;

	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		if (pseudo!=null && !pseudo.isEmpty())
		{
			String vSQL = "SELECT pseudo,mail,avatar,admin FROM public.utilisateur WHERE pseudo = :pseudo";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", pseudo);
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			List<Utilisateur> utilisateur = vJdbcTemplate.query(vSQL, vParams, utilisateurRM);
			if (utilisateur.isEmpty()) {
				return null;
			} else {
				return utilisateur.get(0);
			}
		}else {
			return null;
		}
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		if(utilisateur!=null && utilisateur.getPseudo()!=null && !utilisateur.getPseudo().isEmpty())
		{
			String vSQL = "INSERT INTO public.utilisateur (pseudo,mail,mdp,avatar,admin) VALUES (:pseudo,:mail,:mdp,:avatar,:admin)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", utilisateur.getPseudo());
			vParams.addValue("mail", utilisateur.getMail());
			vParams.addValue("mdp", "testSansMDP");						//TODO A MODIFIER POUR LA GESTION DES MOTS DE PASSE
			vParams.addValue("avatar", utilisateur.getAvatar());
			vParams.addValue("admin", utilisateur.isAdmin());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}
	}

	@Override
	public void deleteUtilisateur(String pseudo) {
		if(pseudo!=null && !pseudo.isEmpty()) {
			String vSQL = "DELETE FROM public.utilisateur WHERE pseudo = :pseudo";
			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", pseudo);
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		//TODO
	}
}
