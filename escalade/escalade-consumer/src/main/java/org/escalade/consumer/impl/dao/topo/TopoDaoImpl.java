package org.escalade.consumer.impl.dao.topo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.topo.TopoDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.topo.Topo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

	@Override
	public Topo getTopo(String titre) {
		String vSQL = "SELECT titre,description_id FROM public.topo WHERE titre = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Topo> listTopo = vJdbcTemplate.query(vSQL, topoRM, titre);
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
		String vSQL = "SELECT nom_fichier FROM public.photo INNER JOIN public.photo_topo ON public.photo_topo.photo_id = public.photo.id WHERE titre_topo = ?" ;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<String> listPhotos = vJdbcTemplate.queryForList(vSQL, String.class, titre);

		return listPhotos;
	}
	
	/**
	 * Retourne la liste des spots du topo à partir de son titre
	 * @param titre
	 * @return List<Spot>
	 */
	private List<Spot> getListSpots(String titre) {
		String vSQL = "SELECT spot_id FROM public.spot_topo WHERE titre = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Integer> listSpotsId = vJdbcTemplate.queryForList(vSQL, Integer.class,titre);
		
		List<Spot> listSpot = new ArrayList<Spot>();
		
		for (Integer integer : listSpotsId) {
			Spot spot = daoFactory.getSpotDao().getSpot(integer.intValue());
			listSpot.add(spot);
		}
		
		return listSpot;
	}

}
