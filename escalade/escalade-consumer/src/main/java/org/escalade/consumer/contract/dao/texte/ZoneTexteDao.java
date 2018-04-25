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
	 * Enregistre la zone de texte dans la base de données et retourne la zoneTexte modifié (avec l'id dans la base de données)
	 * @param zoneTexte
	 * @return ZoneTexte (avec id)
	 */
	public ZoneTexte createZoneTexte(ZoneTexte zoneTexte);
	
	/**
	 * Modifie la zone de texte dans la base de données</br>
	 * ATTENTION : l'id doit correspondre à l'id dans la base de donnée</br>
	 * La modification sera en effet réalisée dans la ligne de la base de données correspondant à l'id
	 * @param zoneTexte
	 */
	public void updateZoneTexte(ZoneTexte zoneTexte);
	
	/**
	 * Supprime de la base de données la zone de texte correspondant à l'identifiant
	 * @param id
	 */
	public void deleteZoneTexte(int id);

}
