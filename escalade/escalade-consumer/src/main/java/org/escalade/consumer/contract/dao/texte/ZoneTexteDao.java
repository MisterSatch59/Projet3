package org.escalade.consumer.contract.dao.texte;

import org.escalade.model.bean.texte.ZoneTexte;

/**
 * ZoneTexteDao
 * @author Oltenos
 *
 */
public interface ZoneTexteDao {
	/**
	 * Retourne la zone de texte correspondant à l'identifiant
	 * @param id
	 * @return
	 */
	public ZoneTexte getZoneTexte(int id);

}
