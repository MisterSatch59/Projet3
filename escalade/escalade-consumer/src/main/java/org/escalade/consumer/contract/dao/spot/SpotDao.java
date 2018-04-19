package org.escalade.consumer.contract.dao.spot;

import org.escalade.model.bean.spot.Spot;

/**
 * SpotDao
 * @author Oltenos
 *
 */
public interface SpotDao {
	/**
	 * Créer un spot
	 * @param spot
	 */
	public void createSpot(Spot spot);
	/**
	 * Retourne le Spot correspondant à l'identifiant
	 * @param id
	 * @return Spot
	 */
	public Spot getSpot(int id);
	/**
	 * Modifie le spot
	 * @param spot
	 */
	public void updateSpot(Spot spot);
	/**
	 * Supprime le Spot correspondant à l'identifiant
	 * @param id
	 */
	public void deleteSpot(int id);

}
