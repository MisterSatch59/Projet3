package org.escalade.consumer.impl.dao.topo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.topo.TopoDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.Topo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Implementation de TopoDao
 * @author Oltenos
 *
 */
@Named("topoDao")
public class TopoDaoImpl  extends AbstractDaoImpl implements TopoDao {
	private static final Logger LOGGER = LogManager.getLogger(TopoDaoImpl.class);

	@Inject
	private RowMapper<Topo> topoRM;
	@Inject
	private DaoFactory daoFactory;

	@Override
	public Topo getTopo(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		if(titre!=null && !titre.isEmpty()) {
			String vSQL = "SELECT titre,description_id FROM public.topo WHERE titre = :titre";
	
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titre", titre);
	
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
	
			List<Topo> listTopo = vJdbcTemplate.query(vSQL, vParams, topoRM);
	
			Topo topo;
			if (listTopo.isEmpty()) {
				topo = null;
			} else {
				topo = listTopo.get(0);
				topo.setListPhotos(this.getListPhotos(topo.getTitre()));
				topo.setListSpot(this.getListSpots(topo.getTitre()));
			}
			
			LOGGER.traceExit(topo);
			return topo;
		}else {
			LOGGER.traceExit("null");
			return null;
		}
	}
	
	/**
	 * Retourne la liste des photos du topo à partir de son titre
	 * @param titre
	 * @return List<String>
	 */
	private List<String> getListPhotos(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_topo ON public.photo_topo.photo_id = public.photo.id WHERE titre_topo = :titreTopo" ;

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titreTopo", titre);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		LOGGER.traceExit(listPhotos);
		return listPhotos;
	}
	
	/**
	 * Retourne la liste des spots du topo à partir de son titre
	 * @param titre
	 * @return List<Spot>
	 */
	private List<Spot> getListSpots(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		String vSQL = "SELECT spot_id FROM public.spot_topo WHERE titre = :titre";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titre", titre);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Integer> listSpotsId = vJdbcTemplate.queryForList(vSQL, vParams, Integer.class);
		
		List<Spot> listSpot = new ArrayList<Spot>();
		
		if(listSpotsId!=null) {
			for (Integer integer : listSpotsId) {
				Spot spot = daoFactory.getSpotDao().getSpot(integer.intValue());
				listSpot.add(spot);
			}
		}
		
		LOGGER.traceExit(listSpot);
		return listSpot;
	}
	
	@Override
	public void createTopo(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		if(topo!=null) {
			ZoneTexte description = daoFactory.getZoneTexteDao().createZoneTexte(topo.getDescription());
			
			String vSQL = "INSERT INTO public.topo (titre,description_id) VALUES (:titre,:descriptionId)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titre", topo.getTitre());
			vParams.addValue("descriptionId", description.getId());
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
			
			this.createPhotos(topo);
			this.createListSpot(topo);
		}
		
		LOGGER.traceExit();
	}
	
	/**
	 * Enregistre les photos du topo dans la base deonnées
	 * @param topo
	 */
	private void createPhotos(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		List<String> photos = topo.getListPhotos();
		if(photos!=null) {
			for (String photo : photos) {
				String vSQL = "INSERT INTO public.photo (nom_fichier) VALUES (:nomFichier)";
	
				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("nomFichier", photo);
				
				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
				
				KeyHolder keyHolder = new GeneratedKeyHolder();
				vJdbcTemplate.update(vSQL, vParams, keyHolder);
				int photoId = (int) keyHolder.getKeys().get("id");
				
				String vSQL2 = "INSERT INTO public.photo_topo (titre_topo, photo_id) VALUES (:titreTopo, :photoId)";
	
				MapSqlParameterSource vParams2 = new MapSqlParameterSource();
				vParams2.addValue("titreTopo", topo.getTitre());
				vParams2.addValue("photoId", photoId);
				
				NamedParameterJdbcTemplate vJdbcTemplate2 = new NamedParameterJdbcTemplate(getDataSource());
				
				vJdbcTemplate2.update(vSQL2, vParams2);
			}
		}
		
		LOGGER.traceExit();
	}
	
	/**
	 * Enregistre la liste des spot du topo dans la base de données
	 * @param topo
	 */
	private void createListSpot(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		List<Spot> spots = topo.getListSpot();
		if(spots!=null) {
			for (Spot spot : spots) {
				
				String vSQL = "INSERT INTO public.spot_topo (titre, spot_id) VALUES (:titre, :spotId)";
	
				MapSqlParameterSource vParams = new MapSqlParameterSource();
				vParams.addValue("titre", topo.getTitre());
				vParams.addValue("spotId", spot.getId());
				
				NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
				
				vJdbcTemplate.update(vSQL, vParams);
			}
		}
		
		LOGGER.traceExit();
	}

	@Override
	public void updateTopo(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		daoFactory.getZoneTexteDao().updateZoneTexte(topo.getDescription());
		
		
		this.deletePhotos(topo);
		this.deleteListSpot(topo);
		this.createPhotos(topo);
		this.createListSpot(topo);
		
		LOGGER.traceExit();
	}
	
	@Override
	public void deleteTopo(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		if(titre!=null && !titre.isEmpty()) {
			ZoneTexte description = this.getTopo(titre).getDescription();
			
			//Rq : supprime en cascade les exemplaire de topo, liste des spot associé et les photos
			String vSQL = "DELETE FROM public.topo WHERE titre = :titre";
			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titre", titre);
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
			
			vJdbcTemplate.update(vSQL, vParams);
			
			if(description != null) {
				daoFactory.getZoneTexteDao().deleteZoneTexte(description.getId());
			}
		}
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données les photos associés au topo
	 * @param topo
	 */
	private void deletePhotos(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		String vSQL = "DELETE FROM public.photo_topo WHERE titre_topo = :titreTopo";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titreTopo", topo.getTitre());
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}
	
	/**
	 * Supprime de la base de données la listes des spots associés au topo
	 * @param topo
	 */
	private void deleteListSpot(Topo topo) {
		LOGGER.traceEntry("topo = " + topo);
		
		String vSQL = "DELETE FROM public.spot_topo WHERE titre = :titre";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titre", topo.getTitre());
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}
}
