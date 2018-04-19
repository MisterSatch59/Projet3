package org.escalade.consumer.impl.rowmapper.texte;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;

import org.escalade.model.bean.texte.ZoneTexte;
import org.springframework.jdbc.core.RowMapper;

@Named
public class ZoneTexteRM  implements RowMapper<ZoneTexte> {

	@Override
	public ZoneTexte mapRow(ResultSet pRS, int pRowNum) throws SQLException {
		int id = pRS.getInt("id");
		String titre  = pRS.getString("titre");
		ZoneTexte zoneTexte = new ZoneTexte(id,titre);
		return zoneTexte;
	}

}
