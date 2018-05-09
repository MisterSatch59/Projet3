package org.escalade.consumer.impl.dao.spot;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.spot.SpotDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.recherche.RechercheSpot;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private static final Logger LOGGER = LogManager.getLogger(SpotDaoImpl.class);

	@Inject
	RowMapper<Spot> spotRM;
	@Inject
	RowMapper<Departement> departementRM;
	@Inject
	RowMapper<Ville> villeRM;
	@Inject
	DaoFactory daoFactory;

	@Override
	public Spot getSpot(int id) {
		LOGGER.traceEntry("id = " + id);
		
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
		
		LOGGER.traceExit(spot);
		return spot;
	}

	/**
	 * Retourne la liste des types du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getTypes(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);
		
		String vSQL = "SELECT type FROM public.spot_type WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listTypes = vJdbcTemplate.queryForList(vSQL, vParams, String.class);
		
		LOGGER.traceExit(listTypes);
		return listTypes;
	}

	/**
	 * Retourne la liste des orientations du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getOrientations(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);
		
		String vSQL = "SELECT orientation FROM public.spot_orientation WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listOrientations = vJdbcTemplate.queryForList(vSQL, vParams, String.class);
		
		LOGGER.traceExit(listOrientations);
		return listOrientations;
	}

	/**
	 * Retourne la liste des profils du spot à partir de son identifiant
	 * @param spotId
	 * @return List<String>
	 */
	private List<String> getProfils(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);
		
		String vSQL = "SELECT profil FROM public.spot_profil WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listProfils = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		LOGGER.traceExit(listProfils);
		return listProfils;
	}

	/**
	 * Retourne la liste des photos du spot à partir de son identifiant
	 * @param id
	 * @return
	 */
	private List<String> getListPhotos(int spotId) {
		LOGGER.traceEntry("spotId = " + spotId);
		
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_spot ON public.photo_spot.photo_id = public.photo.id WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		
		LOGGER.traceExit(listPhotos);
		return listPhotos;
	}

	@Override
	public List<String> getListTopo(int spotId){
		LOGGER.traceEntry("spotId = " + spotId);
		
		String vSQL = "SELECT titre FROM public.spot_topo WHERE spot_id = :spotId";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listSpots = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		
		LOGGER.traceExit(listSpots);
		return listSpots;
	}
	
	@Override
	public Spot createSpot(Spot spot) {
		LOGGER.traceEntry("spot = " + spot);
		
		if (spot != null) {
			int villeId = IdDansBD(spot.getVille());
			if (villeId == 0) {
				villeId = this.createVille(spot.getVille());
			} else {
				spot.getVille().setId(villeId);
			}

			ZoneTexte presentation = daoFactory.getZoneTexteDao().createZoneTexte(spot.getPresentation());

			String vSQL = "INSERT INTO public.spot"
					+ "(nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)"
					+ "VALUES "
					+ "(:nom,:pseudoAuteur,:ouvert,:adapteEnfants,:latitude,:longitude,:villeId,:presentationId,:nbSecteur,:nbVoie,:hauteurMin,:hauteurMax,:difficulteMin,:difficulteMax)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("nom", spot.getNom());
			vParams.addValue("pseudoAuteur", spot.getAuteur().getPseudo());
			vParams.addValue("ouvert", spot.getOuvert());
			vParams.addValue("adapteEnfants", spot.getAdapteEnfants());
			vParams.addValue("latitude", spot.getLatitude());
			vParams.addValue("longitude", spot.getLongitude());

			vParams.addValue("villeId", villeId);
			if(presentation!=null) {
				vParams.addValue("presentationId", presentation.getId());
			}
			else {
				vParams.addValue("presentationId", null);
			}

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
		
		LOGGER.traceExit(spot);
		return spot;
	}
	
	/**
	 * Retourne l'identifiant de la ville dans la base de données si elle existe, retourne 0 sinon
	 * @return int
	 */
	private int IdDansBD(Ville ville) {
		LOGGER.traceEntry("ville = " + ville);
		
		String vSQL = "SELECT id FROM public.ville WHERE (cp= :cp AND departement= :departement AND nom= :nom)";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("cp", ""+ville.getCP());
		vParams.addValue("departement", ville.getDepartement().getNumero());
		vParams.addValue("nom", ville.getNom());

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Integer> id = vJdbcTemplate.queryForList(vSQL, vParams, Integer.class);
		if(id.isEmpty()) {
			
			LOGGER.traceExit(0);
			return 0;
		}else {
			
			LOGGER.traceExit(id.get(0));
			return id.get(0);
		}
	}

	/**
	 * Enregistre dans la base de données les types associés au spot
	 * @param types
	 * @param spotId
	 */
	private void createTypes(List<String> types, int spotId) {
		LOGGER.traceEntry("types = " + types + " - spotId = " + spotId);
		
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
		
		LOGGER.traceExit();
	}

	/**
	 * Enregistre dans la base de données  les orientations associées au spot
	 * @param orientations
	 * @param spotId
	 */
	private void createOrientations(List<String> orientations, int spotId) {
		LOGGER.traceEntry("orientations = " + orientations + " - spotId = " + spotId);
		
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
		
		LOGGER.traceExit();
	}

	/**
	 * Enregistre dans la base de données les profils associés au spot
	 * @param profils
	 * @param spotId
	 */
	private void createProfils(List<String> profils, int spotId) {
		LOGGER.traceEntry("profils = " + profils + " - spotId = " + spotId);
		
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
		
		LOGGER.traceExit();
	}

	/**
	 * Enregistre dans la base de données les photos associées au spot
	 * @param listPhotos
	 * @param spotId
	 */
	private void createPhotos(List<String> listPhotos, int spotId) {
		LOGGER.traceEntry("listPhotos = " + listPhotos + " - spotId = " + spotId);
		
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
		
		LOGGER.traceExit();
	}

	/**
	 * Enregistre dans la base de données  la ville dans la base de donnée et retourne son identifiant
	 * @param ville
	 * @return int
	 */
	private int createVille(Ville ville) {
		LOGGER.traceEntry("ville = " + ville);
		
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
			
			LOGGER.traceExit(id);
			return id;
		}
		
		LOGGER.traceExit(0);
		return 0;
	}

	@Override
	public void updateSpot(Spot spot) {
		LOGGER.traceEntry("spot =" + spot);
		
		if (spot != null) {
			int villeId = IdDansBD(spot.getVille());
			if (villeId == 0) {
				villeId = this.createVille(spot.getVille());
			} else {
				spot.getVille().setId(villeId);
			}
			if(spot.getPresentation()!=null) {
				if(spot.getPresentation().getId()>0) {
					daoFactory.getZoneTexteDao().updateZoneTexte(spot.getPresentation());
				}else {
					spot.setPresentation(daoFactory.getZoneTexteDao().createZoneTexte(spot.getPresentation()));
				}
			}

			String vSQL = "UPDATE public.spot SET "
					+ "nom = :nom, "
					+ "ouvert = :ouvert, "
					+ "adapte_enfants = :adapteEnfants, "
					+ "latitude = :latitude, "
					+ "longitude = :longitude, "
					+ "ville_id = :villeId, "
					+ "nb_secteur = :nbSecteur, "
					+ "nb_voie = :nbVoie, "
					+ "hauteur_min = :hauteurMin, "
					+ "hauteur_max = :hauteurMax, "
					+ "difficulte_min = :difficulteMin, "
					+ "difficulte_max = :difficulteMax, "
					+ "presentation_id = :presentationId "
					+ "WHERE id = :id";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("nom", spot.getNom());
			vParams.addValue("ouvert", spot.getOuvert());
			vParams.addValue("adapteEnfants", spot.getAdapteEnfants());
			vParams.addValue("latitude", spot.getLatitude());
			vParams.addValue("longitude", spot.getLongitude());
			vParams.addValue("villeId", villeId);
			vParams.addValue("nbSecteur", spot.getNbSecteur());
			vParams.addValue("nbVoie", spot.getNbVoie());
			vParams.addValue("hauteurMin", spot.getHauteurMin());
			vParams.addValue("hauteurMax", spot.getHauteurMax());
			vParams.addValue("difficulteMin", spot.getDifficulteMin());
			vParams.addValue("difficulteMax", spot.getDifficulteMax());
			if(spot.getPresentation()!=null) {
				vParams.addValue("presentationId", spot.getPresentation().getId());
			}else {
				vParams.addValue("presentationId", null);
			}

			vParams.addValue("id", spot.getId());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
			
			int spotId=spot.getId();
			this.deleteTypes(spotId);
			this.deleteOrientations(spotId);
			this.deleteProfils(spotId);
			this.deletePhotos(spotId);
			this.createTypes(spot.getTypes(), spotId);
			this.createOrientations(spot.getOrientations(), spotId);
			this.createProfils(spot.getProfils(), spotId);
			this.createPhotos(spot.getListPhotos(), spotId);
			
			daoFactory.getCommentaireDao().deleteAllCommentaires(spotId);
			List<Commentaire> commentaires = spot.getListCommentaires();
			for (Commentaire commentaire : commentaires) {
				daoFactory.getCommentaireDao().createCommentaire(spotId, commentaire);
			}
		}
		
		LOGGER.traceExit();
	}

	@Override
	public void deleteSpot(int id) {
		LOGGER.traceEntry("id = " + id);
		
		daoFactory.getCommentaireDao().deleteAllCommentaires(id);
		
		ZoneTexte presentation = this.getSpot(id).getPresentation();
		
		//Rq : suppression en cascade des types, profils orientations et photos
		String vSQL = "DELETE FROM public.spot WHERE id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", id);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		if(presentation != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(presentation.getId());
		}
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données les types associés au spot
	 * @param spotId
	 */
	private void deleteTypes(int spotId) {
		LOGGER.traceEntry("spotId =" + spotId);
		
		String vSQL = "DELETE FROM public.spot_type WHERE spot_id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données les orientations associées au spot
	 * @param spotId
	 */
	private void deleteOrientations(int spotId) {
		LOGGER.traceEntry("spotId =" + spotId);
		
		String vSQL = "DELETE FROM public.spot_orientation WHERE spot_id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données les profils associés au spot
	 * @param spotId
	 */
	private void deleteProfils(int spotId) {
		LOGGER.traceEntry("spotId =" + spotId);
		
		String vSQL = "DELETE FROM public.spot_profil WHERE spot_id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données les photos associés au spot
	 * @param spotId
	 */
	private void deletePhotos(int spotId) {
		LOGGER.traceEntry("spotId =" + spotId);
		
		String vSQL = "DELETE FROM public.photo_spot WHERE spot_id = :spotId";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("spotId", spotId);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}

	@Override
	public List<Spot> rechercheSpot(RechercheSpot criteres) {
		LOGGER.traceEntry("criteres = " + criteres);
		
		StringBuilder vSQL = new StringBuilder("SELECT"
				+ " spot.id AS spot_id,spot.nom AS spot_nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max,"
				+ " ville.id AS ville_id,cp,ville.nom AS ville_nom," + " numero, departement.nom AS nom_departement"
				+ " FROM public.spot INNER JOIN public.ville ON spot.ville_id = ville.id"
				+ " INNER JOIN public.departement ON ville.departement = departement.numero" + " WHERE 1 = 1");
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		if (criteres != null) {
			if (criteres.getDifficulteMax() != null) {
				vSQL.append(" AND difficulte_min <= :difficulteMax");
				vParams.addValue("difficulteMax", criteres.getDifficulteMax());
			}
			if (criteres.getDifficulteMin() != null) {
				vSQL.append(" AND difficulte_max >= :difficulteMin");
				vParams.addValue("difficulteMin", criteres.getDifficulteMin());
			}
			if (criteres.getDepartement() != null) {
				vSQL.append(" AND departement.numero = :numero");
				vParams.addValue("numero", criteres.getDepartement().getNumero());
			}
			if (criteres.getNomVille() != null) {
				vSQL.append(" AND ville.nom = :nomVille");
				vParams.addValue("nomVille", criteres.getNomVille());
			}
			if (criteres.getCpVille() != null) {
				vSQL.append(" AND ville.cp = :cp");
				vParams.addValue("cp", criteres.getCpVille());
			}
		}

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Spot> listSpot = vJdbcTemplate.query(vSQL.toString(), vParams, spotRM);
		
		if(listSpot!=null) {
			for (Spot spot : listSpot) {
				spot.setTypes(this.getTypes(spot.getId()));
				spot.setOrientations(this.getOrientations(spot.getId()));
				spot.setProfils(this.getProfils(spot.getId()));
				spot.setListPhotos(this.getListPhotos(spot.getId()));
				spot.setListCommentaires(daoFactory.getCommentaireDao().getListCommentaire(spot.getId()));
			}
		}

		LOGGER.traceExit(listSpot);
		return listSpot;
	}

	@Override
	public Departement getDepartement(String numero) {
		LOGGER.traceEntry("numero = " + numero);
		
		String vSQL = "SELECT * FROM public.departement WHERE numero = :numeroDep";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("numeroDep", numero);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Departement> listDepartement = vJdbcTemplate.query(vSQL, vParams, departementRM);
		
		if (listDepartement.isEmpty()) {
			LOGGER.traceExit(null);
			return null;
		} else {
			LOGGER.traceExit(listDepartement.get(0));
			return listDepartement.get(0);
		}
	}
	
	@Override
	public List<Departement> getDepartements() {
		LOGGER.traceEntry();
		
		String vSQL = "SELECT * FROM public.departement";
		
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Departement> listDepartement = vJdbcTemplate.query(vSQL, departementRM);
		
		LOGGER.traceExit(listDepartement);
		return listDepartement;
	}

	@Override
	public List<Ville> getVilles(String numeroDepartement) {
		LOGGER.traceEntry("numeroDepartement = " + numeroDepartement);
		
		List<Ville> listVille;
		if(numeroDepartement==null || numeroDepartement.isEmpty()) {
			String vSQL = "SELECT * FROM public.ville ORDER BY nom";
			
			JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

			listVille = vJdbcTemplate.query(vSQL, villeRM);
		}else {
			String vSQL = "SELECT * FROM public.ville WHERE departement = :numeroDepartement ORDER BY nom" ;
			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("numeroDepartement", numeroDepartement);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
			
			listVille = vJdbcTemplate.query(vSQL, vParams, villeRM);
		}
		
		LOGGER.traceExit(listVille);
		return listVille;
	}
	
	@Override
	public List<String> getTypes() {
		LOGGER.traceEntry();
		
		String vSQL = "SELECT nom FROM public.type";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listTypes = vJdbcTemplate.queryForList(vSQL, String.class);
		
		LOGGER.traceExit(listTypes);
		return listTypes;
	}

	@Override
	public List<String> getOrientations() {
		LOGGER.traceEntry();
		
		String vSQL = "SELECT nom FROM public.orientation";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listOrientations = vJdbcTemplate.queryForList(vSQL, String.class);
		
		LOGGER.traceExit(listOrientations);
		return listOrientations;
	}

	@Override
	public List<String> getProfils() {
		LOGGER.traceEntry();
		
		String vSQL = "SELECT nom FROM public.profil";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listProfils = vJdbcTemplate.queryForList(vSQL, String.class);
		
		LOGGER.traceExit(listProfils);
		return listProfils;
	}

	@Override
	public List<String> getDifficultes() {
		LOGGER.traceEntry();
		
		String vSQL = "SELECT nom FROM public.difficulte";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listDifficultes = vJdbcTemplate.queryForList(vSQL, String.class);
		
		LOGGER.traceExit(listDifficultes);
		return listDifficultes;
	}

}
