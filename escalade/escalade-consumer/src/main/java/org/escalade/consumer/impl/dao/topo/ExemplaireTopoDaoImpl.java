package org.escalade.consumer.impl.dao.topo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.topo.ExemplaireTopoDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Named("exemplaireTopoDao")
public class ExemplaireTopoDaoImpl extends AbstractDaoImpl implements ExemplaireTopoDao {

	@Inject
	private RowMapper<ExemplaireTopo> exemplaireTopoRM;

	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) {
		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE pseudo_proprietaire = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> listExemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, pseudoProprietaire);

		return listExemplaireTopo;
	}

	@Override
	public ExemplaireTopo getExemplaireTopo(int id) {
		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE id = " + id;

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> exemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM);
		
		if(exemplaireTopo.isEmpty()) {
			return null;
		}else {
			return exemplaireTopo.get(0);
		}
	}

}
