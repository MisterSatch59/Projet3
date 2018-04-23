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
	
	/**
	 * Enregistre la zone de texte dans la base de données
	 * @param utilisateur
	 * @return ZoneTexte (avec id)
	 */
	public ZoneTexte createZoneTexte(ZoneTexte zoneTexte);
	
	/**
	 * Supprime de la base de données la zone de texte correspondant à l'identifiant
	 * @param id
	 */
	public void deleteZoneTexte(int id);

	/**
	 * Modifie la zone de texte dans la base de données
	 * @param utilisateur
	 */
	public void updateZoneTexte(ZoneTexte zoneTexte);

}
