package org.escalade.consumer.impl.rowmapper.spot;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper pour le bean Spot
 * @author Oltenos
 *
 */
@Named
public class SpotRM implements RowMapper<Spot>{
	private static final Logger LOGGER = LogManager.getLogger(SpotRM.class);

	@Inject
	private DaoFactory daoFactory;
	
	@Override
	public Spot mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		LOGGER.traceEntry();
		
		//Création du Bean département
		String numero = pRS.getString("numero");
		String nomDepartement = pRS.getString("nom_departement");
		Departement departement = new Departement(numero,nomDepartement);
		
		//Création du Bean Ville (utilise le bean département ci dessus)
		int villeId= pRS.getInt("ville_id");
		String cp= pRS.getString("cp");
		String nomVille = pRS.getString("ville_nom");
		Ville ville = new Ville(villeId,nomVille,cp,departement);
		
		//Création du Bean Spot
		int id = pRS.getInt("spot_id");
		String nom = pRS.getString("spot_nom");
		boolean ouvert = pRS.getBoolean("ouvert");
		
		Boolean adapteEnfants = pRS.getBoolean("adapte_enfants");
		if(pRS.wasNull()) {
			adapteEnfants=null;
		}
		
		String latitude = pRS.getString("latitude");
		String longitude = pRS.getString("longitude");
		int nbSecteur = pRS.getInt("nb_secteur");
		int hauteurMin = pRS.getInt("hauteur_min");
		int hauteurMax = pRS.getInt("hauteur_max");
		String nbVoie = pRS.getString("nb_voie");
		String difficulteMin = pRS.getString("difficulte_min");
		String difficulteMax = pRS.getString("difficulte_max");
		
		Utilisateur auteur = daoFactory.getUtilisateurDao().getUtilisateur(pRS.getString("pseudo_auteur"));
		
		int presentationId = pRS.getInt("presentation_id");
		ZoneTexte presentation = null;
		if(!pRS.wasNull()) {
			presentation = daoFactory.getZoneTexteDao().getZoneTexte(presentationId);
		}
		
		Spot spot = new Spot(
				id,
				nom,
				ouvert,
				adapteEnfants,
				latitude,
				longitude,
				ville,
				nbSecteur,
				hauteurMax,
				hauteurMin,
				nbVoie,
				difficulteMin,
				difficulteMax,
				auteur,
				presentation);
		
		LOGGER.traceExit(spot);
		return spot;
	}

}
