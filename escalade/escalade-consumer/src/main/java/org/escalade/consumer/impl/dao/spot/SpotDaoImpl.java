package org.escalade.consumer.impl.dao.spot;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.spot.SpotDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Implementation de SpotDao
 * 
 * @author Oltenos
 *
 */
@Named("spotDao")
public class SpotDaoImpl extends AbstractDaoImpl implements SpotDao {

	@Inject
	RowMapper<Spot> spotRM;
	@Inject
	DaoFactory daoFactory;

	//Read
	@Override
	public Spot getSpot(int id) {
		String vSQL = "SELECT"
				+ " spot.id AS spot_id,spot.nom AS spot_nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max,"
				+ " ville.id AS ville_id,cp,ville.nom AS ville_nom," + " numero, departement.nom AS nom_departement"
				+ " FROM public.spot INNER JOIN public.ville ON spot.ville_id = ville.id"
				+ " INNER JOIN public.departement ON ville.departement = departement.numero" + " WHERE spot.id = :id";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Spot> listSpot = vJdbcTemplate.query(vSQL, vParams, spotRM);

		Spot spot;
		if (listSpot.isEmpty()) {
			spot = null;
		} else {
			spot = listSpot.get(0);
			spot.setTypes(this.getTypes(spot.getId()));
			spot.setOrientations(this.getOrientations(spot.getId()));
			spot.setProfils(this.getProfils(spot.getId()));
			spot.setListPhotos(this.getListPhotos(spot.getId()));
			spot.setListCommentaires(daoFactory.getCommentaireDao().getListCommentaire(spot.getId()));
		}

		return spot;
	}

	/**
	 * Retourne la liste des types du spot à partir de son identifiant
	 * 
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getTypes(int spotId) {
		String vSQL = "SELECT type FROM public.spot_type WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listTypes = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listTypes;
	}

	/**
	 * Retourne la liste des orientations du spot à partir de son identifiant
	 * 
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getOrientations(int spotId) {
		String vSQL = "SELECT orientation FROM public.spot_orientation WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listOrientations = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listOrientations;
	}

	/**
	 * Retourne la liste des profils du spot à partir de son identifiant
	 * 
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getProfils(int spotId) {
		String vSQL = "SELECT profil FROM public.spot_profil WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listProfils = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listProfils;
	}

	/**
	 * Retourne la liste des photos du spot à partir de son identifiant
	 * 
	 * @param id
	 * @return
	 */
	private List<String> getListPhotos(int spotId) {
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_spot ON public.photo_spot.photo_id = public.photo.id WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listPhotos;
	}

	// Create
	@Override
	public Spot createSpot(Spot spot) {
		if (spot != null) {
			int villeId;
			if (spot.getVille().getId() <= 0) {
				villeId = this.createVille(spot.getVille());
			} else {
				villeId = spot.getVille().getId();
			}

			ZoneTexte presentation = daoFactory.getZoneTexteDao().createZoneTexte(spot.getPresentation());

			String vSQL = "INSERT INTO public.spot"
					+ "(nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)"
					+ "VALUES "
					+ "(:nom,:pseudoAuteur,:ouvert,:adapteEnfants,:latitude,:longitude,:villeId,:presentationId,:nbSecteur,:nbVoie,:hauteurMin,:hauteurMax,:difficulteMin,:difficulteMax)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("nom", spot.getNom());
			vParams.addValue("pseudoAuteur", spot.getAuteur().getPseudo());
			vParams.addValue("ouvert", spot.isOuvert());
			vParams.addValue("adapteEnfants", spot.isAdapteEnfants());
			vParams.addValue("latitude", spot.getLatitude());
			vParams.addValue("longitude", spot.getLongitude());

			vParams.addValue("villeId", villeId);
			vParams.addValue("presentationId", presentation.getId());

			vParams.addValue("nbSecteur", spot.getNbSecteur());
			vParams.addValue("nbVoie", spot.getNbVoie());
			vParams.addValue("hauteurMin", spot.getHauteurMin());
			vParams.addValue("hauteurMax", spot.getHauteurMax());
			vParams.addValue("difficulteMin", spot.getDifficulteMin());
			vParams.addValue("difficulteMax", spot.getDifficulteMax());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			KeyHolder keyHolder = new GeneratedKeyHolder();// permet de récupérer la clef généré par autoincrement
			vJdbcTemplate.update(vSQL, vParams, keyHolder);
			int spotId = (int) keyHolder.getKeys().get("id");
			spot.setId(spotId);

			this.createTypes(spot.getTypes(), spotId);
			this.createOrientations(spot.getOrientations(), spotId);
			this.createProfils(spot.getProfils(), spotId);
			this.createPhotos(spot.getListPhotos(), spotId);
		}
		return spot;
	}

	/**
	 * Crée les types associés au spot
	 * 
	 * @param types
	 * @param spotId
	 */
	private void createTypes(List<String> types, int spotId) {
		if (types != null) {
			for (String type : types) {
				String vSQL = "INSERT INTO public.spot_type (type,spot_id) VALUES (:type,:spotId)";

				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("type", type);
				vParams.addValue("spotId", spotId);

				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

				vJdbcTemplate.update(vSQL, vParams);
			}
		}
	}

	/**
	 * Crée les orientations associées au spot
	 * 
	 * @param orientations
	 * @param spotId
	 */
	private void createOrientations(List<String> orientations, int spotId) {
		if (orientations != null) {
			for (String orientation : orientations) {
				String vSQL = "INSERT INTO public.spot_orientation (orientation,spot_id) VALUES (:orientation,:spotId)";

				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("orientation", orientation);
				vParams.addValue("spotId", spotId);

				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

				vJdbcTemplate.update(vSQL, vParams);
			}
		}
	}

	/**
	 * Crée les profils associés au spot
	 * 
	 * @param profils
	 * @param spotId
	 */
	private void createProfils(List<String> profils, int spotId) {
		if (profils != null) {
			for (String profil : profils) {
				String vSQL = "INSERT INTO public.spot_profil (profil,spot_id) VALUES (:profil,:spotId)";

				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("profil", profil);
				vParams.addValue("spotId", spotId);

				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

				vJdbcTemplate.update(vSQL, vParams);
			}
		}
	}

	/**
	 * Crée les photos associé au spot
	 * 
	 * @param listPhotos
	 * @param spotId
	 */
	private void createPhotos(List<String> listPhotos, int spotId) {
		if (listPhotos != null) {
			for (String photo : listPhotos) {
				String vSQL = "INSERT INTO public.photo (nom_fichier) VALUES (:nomFichier)";

				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("nomFichier", photo);

				KeyHolder keyHolder = new GeneratedKeyHolder();
				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
				int photoId = (int) keyHolder.getKeys().get("id");

				vJdbcTemplate.update(vSQL, vParams);

				String vSQL2 = "INSERT INTO public.photo_spot (spot_id, photo_id) VALUES (:spotId, :photoId)";

				MapSqlParameterSource vParams2 = new MapSqlParameterSource();
				vParams2.addValue("spotId", spotId);
				vParams2.addValue("photoId", photoId);

				NamedParameterJdbcTemplate vJdbcTemplate2 = new NamedParameterJdbcTemplate(getDataSource());

				vJdbcTemplate2.update(vSQL2, vParams2);
			}
		}
	}

	/**
	 * crée la ville dans la base de donnée et retourne son identifiant
	 * 
	 * @param ville
	 * @return identifiant de la ville
	 */
	private int createVille(Ville ville) {
		if (ville != null) {
			String vSQL = "INSERT INTO public.ville (cp,departement,nom) VALUES (:cp,:departement,:nom)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("cp", ville.getCP());
			vParams.addValue("departement", ville.getDepartement().getNumero());
			vParams.addValue("nom", ville.getNom());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			KeyHolder keyHolder = new GeneratedKeyHolder();// permet de récupérer la clef généré par autoincrement
			vJdbcTemplate.update(vSQL, vParams, keyHolder);

			int id = (int) keyHolder.getKeys().get("id");
			return id;
		}
		return 0;
	}

	// Update
	@Override
	public void updateSpot(Spot spot) {
		// TODO

	}

	//Delete
	@Override
	public void deleteSpot(int id) {
		List<Commentaire> listComm = daoFactory.getCommentaireDao().getListCommentaire(id);
		if(listComm!=null) {
			for (Commentaire commentaire : listComm) {
				daoFactory.getCommentaireDao().deleteCommentaire(commentaire.getId());
			}
		}
		
		ZoneTexte presentation = this.getSpot(id).getPresentation();
		
		String vSQL = "DELETE FROM public.spot WHERE id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", id);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		if(presentation != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(presentation.getId());
		}
	}
}
