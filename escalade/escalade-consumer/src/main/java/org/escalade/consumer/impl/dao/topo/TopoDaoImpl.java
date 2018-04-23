package org.escalade.consumer.impl.dao.topo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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

	@Inject
	private RowMapper<Topo> topoRM;
	@Inject
	private DaoFactory daoFactory;

	//READ
	@Override
	public Topo getTopo(String titre) {
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
		return topo;
	}
	/**
	 * Retourne la liste des photos du topo à partir de son titre
	 * @param titre
	 * @return List<String>
	 */
	private List<String> getListPhotos(String titre) {
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_topo ON public.photo_topo.photo_id = public.photo.id WHERE titre_topo = :titreTopo" ;

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titreTopo", titre);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, vParams, String.class);

		return listPhotos;
	}
	/**
	 * Retourne la liste des spots du topo à partir de son titre
	 * @param titre
	 * @return List<Spot>
	 */
	private List<Spot> getListSpots(String titre) {
		String vSQL = "SELECT spot_id FROM public.spot_topo WHERE titre = :titre";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titre", titre);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		List<Integer> listSpotsId = vJdbcTemplate.queryForList(vSQL, vParams, Integer.class);
		
		List<Spot> listSpot = new ArrayList<Spot>();
		
		for (Integer integer : listSpotsId) {
			Spot spot = daoFactory.getSpotDao().getSpot(integer.intValue());
			listSpot.add(spot);
		}
		
		return listSpot;
	}
	
	//CREATE
	@Override
	public void createTopo(Topo topo) {
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
	}
	
	private void createPhotos(Topo topo) {
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
	}
	private void createListSpot(Topo topo) {
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
	}

	//UPDATE
	@Override
	public void updateTopo(Topo topo) {
		// TODO
		
	}
	
	//DELETE
	@Override
	public void deleteTopo(String titre) {
		ZoneTexte description = this.getTopo(titre).getDescription();
		
		String vSQL = "DELETE FROM public.topo WHERE titre = :titre";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("titre", titre);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		if(description != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(description.getId());
		}
	}
}
