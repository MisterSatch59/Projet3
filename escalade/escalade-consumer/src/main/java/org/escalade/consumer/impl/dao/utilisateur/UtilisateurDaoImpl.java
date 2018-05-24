package org.escalade.consumer.impl.dao.utilisateur;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger LOGGER = LogManager.getLogger(UtilisateurDaoImpl.class);

	@Inject
	RowMapper<Utilisateur> utilisateurRM;

	@Override
	public Utilisateur getUtilisateur(String pseudo) {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if (pseudo != null && !pseudo.isEmpty()) {
			//Recherche de l'utilisateur dans la base de données
			String vSQL = "SELECT pseudo,mail,mdp,sel,avatar,admin FROM public.utilisateur WHERE pseudo = :pseudo";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", pseudo);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			List<Utilisateur> utilisateur = vJdbcTemplate.query(vSQL, vParams, utilisateurRM);
			if (utilisateur.isEmpty()) {//Retourne null si la recherche ne retourne aucun resultat
				LOGGER.traceExit("null");
				return null;
			} else {//Retoune le premier (et unique pseudo étant la clé primaire de la table) élément sinon
				LOGGER.traceExit(utilisateur.get(0));
				return utilisateur.get(0);
			}
		} else {//Retourne null si le pseudo est incorrecte
			LOGGER.traceExit("null");
			return null;
		}
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		LOGGER.traceEntry("utilisateur = " + utilisateur);

		if (utilisateur != null && utilisateur.getPseudo() != null && !utilisateur.getPseudo().isEmpty()) {//Vérifie que l'utilisateur n'est pas null et qu'un pseudo est bien insérer
			//Enregistrement dans la base de données
			String vSQL = "INSERT INTO public.utilisateur (pseudo,mail,mdp,sel,avatar,admin) VALUES (:pseudo,:mail,:mdp,:sel,:avatar,:admin)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", utilisateur.getPseudo());
			vParams.addValue("mail", utilisateur.getMail());
			vParams.addValue("mdp", utilisateur.getMdp());
			vParams.addValue("sel", utilisateur.getSel());
			vParams.addValue("avatar", utilisateur.getAvatar());
			vParams.addValue("admin", utilisateur.isAdmin());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}

		LOGGER.traceExit();
	}

	@Override
	public void deleteUtilisateur(String pseudo) {
		LOGGER.traceEntry("pseudo = " + pseudo);

		if (pseudo != null && !pseudo.isEmpty()) {//Vérifie que le pseudo est ni null ni vide
			//remplace l'utilisateur par l'utilisateur "Utilisateur supprimer" dans les tables commentaires et spot dont l'utilisateur et auteur
			
			String vSQL1 = "UPDATE public.spot SET pseudo_auteur='Utilisateur Supprimé' WHERE pseudo_auteur= :pseudo";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", pseudo);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL1, vParams);
			
			String vSQL2 = "UPDATE public.commentaire SET pseudo_auteur='Utilisateur Supprimé' WHERE pseudo_auteur= :pseudo";
			vJdbcTemplate.update(vSQL2, vParams);
			
			
			//Suppression de la base de données
			String vSQL3 = "DELETE FROM public.utilisateur WHERE pseudo = :pseudo";
			vJdbcTemplate.update(vSQL3, vParams);
		}

		LOGGER.traceExit();
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		LOGGER.traceEntry("utilisateur = " + utilisateur);

		if (utilisateur != null && utilisateur.getPseudo() != null && !utilisateur.getPseudo().isEmpty()) {//Vérifie que l'utilisateur n'est pas null et qu'un pseudo est bien insérer
			//Enregistrement des modifications dans la base de données
			String vSQL = "UPDATE public.utilisateur SET mail = :mail, mdp = :mdp, sel = :sel, avatar = :avatar, admin = :admin WHERE pseudo = :pseudo";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudo", utilisateur.getPseudo());
			vParams.addValue("mail", utilisateur.getMail());
			vParams.addValue("mdp", utilisateur.getMdp());
			vParams.addValue("sel", utilisateur.getSel());
			vParams.addValue("avatar", utilisateur.getAvatar());
			vParams.addValue("admin", utilisateur.isAdmin());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}

		LOGGER.traceExit();
	}
}
