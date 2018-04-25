package org.escalade.consumer.impl.dao.topo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.topo.EmpruntDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.topo.Emprunt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Named("empruntDao")
public class EmpruntDaoImpl extends AbstractDaoImpl implements EmpruntDao{
	private static final Logger LOGGER = LogManager.getLogger(EmpruntDaoImpl.class);

	@Inject
	private RowMapper<Emprunt> empruntRM;

	@Override
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur) {
		LOGGER.traceEntry("pseudoEmprunteur = " + pseudoEmprunteur);
		
		if(pseudoEmprunteur!=null) {
			String vSQL = "SELECT * FROM public.emprunt WHERE pseudo_emprunteur = ?";
			
			JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
	
			List<Emprunt> listEmprunt = vJdbcTemplate.query(vSQL, empruntRM, pseudoEmprunteur);
			
			LOGGER.traceExit(listEmprunt);
			return listEmprunt;
		}

		LOGGER.traceExit(null);
		return null;
	}

	@Override
	public List<Emprunt> getListEmprunt(int exemplaireTopoId) {
		LOGGER.traceEntry("exemplaireTopoId = " + exemplaireTopoId);
		
		String vSQL = "SELECT * FROM public.emprunt WHERE exemplaire_topo_id = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Emprunt> listEmprunt = vJdbcTemplate.query(vSQL, empruntRM, exemplaireTopoId);

		LOGGER.traceExit(listEmprunt);
		return listEmprunt;
	}

	@Override
	public Emprunt createEmprunt(Emprunt emprunt) {
		LOGGER.traceEntry("emprunt = " + emprunt);
		
		if(emprunt!=null) {
			String vSQL = "INSERT INTO public.emprunt (pseudo_emprunteur,exemplaire_topo_id,debut,fin) VALUES (:pseudoEmprunteur, :exemplaireTopoId, :debut, :fin)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("pseudoEmprunteur", emprunt.getEmprunteur().getPseudo());
			vParams.addValue("exemplaireTopoId", emprunt.getExemplaire().getId());
			vParams.addValue("debut", emprunt.getDateDebut());
			vParams.addValue("fin", emprunt.getDateFin());
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}
		
		LOGGER.traceExit(emprunt);
		return emprunt;
	}

	@Override
	public void deleteEmprunt(int id) {
		LOGGER.traceEntry("id = " + id);
		
		String vSQL = "DELETE FROM public.emprunt WHERE id = :id";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		LOGGER.traceExit();
	}

}
