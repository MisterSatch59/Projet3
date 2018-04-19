package org.escalade.consumer.impl.dao.topo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.topo.EmpruntDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.topo.Emprunt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Named("empruntDao")
public class EmpruntDaoImpl extends AbstractDaoImpl implements EmpruntDao{

	@Inject
	private RowMapper<Emprunt> empruntRM;

	@Override
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur) {
		String vSQL = "SELECT * FROM public.emprunt WHERE pseudo_emprunteur = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<Emprunt> listEmprunt = vJdbcTemplate.query(vSQL, empruntRM, pseudoEmprunteur);

		return listEmprunt;
	}

}
