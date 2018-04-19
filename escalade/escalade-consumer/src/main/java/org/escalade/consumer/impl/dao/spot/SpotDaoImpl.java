package org.escalade.consumer.impl.dao.spot;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.spot.SpotDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.spot.Spot;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * Implementation de SpotDao
 * @author Oltenos
 *
 */
@Named("spotDao")
public class SpotDaoImpl extends AbstractDaoImpl implements SpotDao {

	@Inject
	RowMapper<Spot> spotRM;

	@Override
	public void createSpot(Spot spot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spot getSpot(int id) {
		String vSQL = "SELECT" + 
				" spot.id AS spot_id,spot.nom AS spot_nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max," + 
				" ville.id AS ville_id,cp,ville.nom AS ville_nom," + 
				" numero, departement.nom AS nom_departement" + 
				" FROM public.spot INNER JOIN public.ville ON spot.ville_id = ville.id" + 
				" INNER JOIN public.departement ON ville.departement = departement.numero" + 
				" WHERE spot.id =" + id;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Spot> listSpot = vJdbcTemplate.query(vSQL, spotRM);
		Spot spot;
		if (listSpot.isEmpty()) {
			spot = null;
		} else {
			spot = listSpot.get(0);
			spot.setTypes(this.getTypes(spot.getId()));
			spot.setOrientations(this.getOrientations(spot.getId()));
			spot.setProfils(this.getProfils(spot.getId()));
			spot.setListPhotos(this.getListPhotos(spot.getId()));
		}
				
		return spot;
	}


	/**
	 * Retourne la liste des types du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getTypes(int spotId) {
		String vSQL = "SELECT type FROM public.spot_type WHERE spot_id = " + spotId;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listTypes = vJdbcTemplate.queryForList(vSQL, String.class);

		return listTypes;
	}
	/**
	 * Retourne la liste des orientations du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getOrientations(int spotId) {
		String vSQL = "SELECT orientation FROM public.spot_orientation WHERE spot_id = " + spotId;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listOrientations = vJdbcTemplate.queryForList(vSQL, String.class);

		return listOrientations;
	}
	/**
	 * Retourne la liste des profils du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getProfils(int spotId) {
		String vSQL = "SELECT profil FROM public.spot_profil WHERE spot_id =" + spotId;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listProfils = vJdbcTemplate.queryForList(vSQL, String.class);

		return listProfils;
	}
	/**
	 * Retourne la liste des photos du spot à partir de son identifiant
	 * @param id
	 * @return
	 */
	private List<String> getListPhotos(int spotId) {
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_spot ON public.photo_spot.photo_id = public.photo.id WHERE spot_id = " + spotId;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, String.class);

		return listPhotos;
	}

	@Override
	public void updateSpot(Spot spot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpot(int id) {
		// TODO Auto-generated method stub
		
	}
	
	

}
