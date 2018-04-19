package org.escalade.consumer.impl.rowmapper.topo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.Topo;
import org.springframework.jdbc.core.RowMapper;

@Named
public class TopoRM implements RowMapper<Topo> {

	@Inject
	private DaoFactory daoFactory;
	
	@Override
	public Topo mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		String titre = pRS.getString("titre");
		
		int descriptionId = pRS.getInt("description_id");
		ZoneTexte description = null;
		if(!pRS.wasNull()) {
			description = daoFactory.getZoneTexteDao().getZoneTexte(descriptionId);
		}
		
		Topo topo = new Topo(titre,description);
		
		return topo;
	}

}
