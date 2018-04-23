package org.escalade.consumer.contract.dao.spot;

import org.escalade.model.bean.spot.Spot;

/**
 * SpotDao
 * @author Oltenos
 *
 */
public interface SpotDao {
	/**
	 * Enregistre le spot dans la base de donnée
	 * Remarque le spot n'a pas de commentaire à la création
	 * @param spot
	 * @return Spot (avec id)
	 */
	public Spot createSpot(Spot spot);
	/**
	 * Retourne le Spot correspondant à l'identifiant
	 * @param id
	 * @return Spot
	 */
	public Spot getSpot(int id);
	/**
	 * Modifie le spot dans la base de donnée
	 * @param spot
	 */
	public void updateSpot(Spot spot);
	/**
	 * Supprime de la base de données le Spot correspondant à l'identifiant
	 * @param id
	 */
	public void deleteSpot(int id);

}
