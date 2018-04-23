package org.escalade.consumer.impl.dao.topo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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

	@Inject
	private RowMapper<ExemplaireTopo> exemplaireTopoRM;
	@Inject
	private DaoFactory daoFactory;
	
	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) {
		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE pseudo_proprietaire = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> listExemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, pseudoProprietaire);

		return listExemplaireTopo;
	}

	@Override
	public ExemplaireTopo getExemplaireTopo(int id) {
		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE id = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> exemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, id);
		
		if(exemplaireTopo.isEmpty()) {
			return null;
		}else {
			return exemplaireTopo.get(0);
		}
	}

	@Override
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exTopo) {
		if(exTopo!=null) {
			ZoneTexte condition = daoFactory.getZoneTexteDao().createZoneTexte(exTopo.getCondition());
			
			String vSQL = "INSERT INTO public.exemplaire_topo (titre_topo, pseudo_proprietaire, condition_id) VALUES (:titreTopo, :pseudoProprietaire, :conditionId)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titreTopo", exTopo.getTopo().getTitre());
			vParams.addValue("pseudoProprietaire", exTopo.getProprietaire().getPseudo());
			vParams.addValue("conditionId", condition.getId());
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}
		return exTopo;
	}

	@Override
	public void deleteExemplaireTopo(int id) {
		ZoneTexte condition = this.getExemplaireTopo(id).getCondition();
		
		String vSQL = "DELETE FROM public.exemplaire_topo WHERE id = :id";
		
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		
		vJdbcTemplate.update(vSQL, vParams);
		
		if(condition != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(condition.getId());
		}
	}


}
