package org.escalade.consumer.impl.dao.topo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.topo.ExemplaireTopoDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Named("exemplaireTopoDao")
public class ExemplaireTopoDaoImpl extends AbstractDaoImpl implements ExemplaireTopoDao {
	private static final Logger LOGGER = LogManager.getLogger(ExemplaireTopoDaoImpl.class);

	@Inject
	private RowMapper<ExemplaireTopo> exemplaireTopoRM;
	@Inject
	private DaoFactory daoFactory;
	
	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) {
		LOGGER.traceEntry("pseudoProprietaire = " + pseudoProprietaire);
		
		if(pseudoProprietaire!=null) {
			String vSQL = "SELECT * FROM public.exemplaire_topo WHERE pseudo_proprietaire = ?";

			JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

			List<ExemplaireTopo> listExemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, pseudoProprietaire);

			LOGGER.traceExit(listExemplaireTopo);
			return listExemplaireTopo;
		}
		
		LOGGER.traceExit(null);
		return null;

	}

	@Override
	public ExemplaireTopo getExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);
		
		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE id = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> exemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, id);
		
		if(exemplaireTopo.isEmpty()) {
			LOGGER.traceExit("null");
			return null;
		}else {
			LOGGER.traceExit(exemplaireTopo.get(0));
			return exemplaireTopo.get(0);
		}
	}

	@Override
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo) {
		LOGGER.traceEntry("exTopo = " + exemplaireTopo);
		
		if(exemplaireTopo!=null) {
			ZoneTexte condition = daoFactory.getZoneTexteDao().createZoneTexte(exemplaireTopo.getCondition());
			
			String vSQL = "INSERT INTO public.exemplaire_topo (titre_topo, pseudo_proprietaire, condition_id) VALUES (:titreTopo, :pseudoProprietaire, :conditionId)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titreTopo", exemplaireTopo.getTopo().getTitre());
			vParams.addValue("pseudoProprietaire", exemplaireTopo.getProprietaire().getPseudo());
			vParams.addValue("conditionId", condition.getId());
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}
		
		LOGGER.traceExit(exemplaireTopo);
		return exemplaireTopo;
	}

	@Override
	public void deleteExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);
		
		ZoneTexte condition = this.getExemplaireTopo(id).getCondition();
		
		String vSQL = "DELETE FROM public.exemplaire_topo WHERE id = :id";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		if(condition != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(condition.getId());
		}
		
		LOGGER.traceExit();
	}


}
