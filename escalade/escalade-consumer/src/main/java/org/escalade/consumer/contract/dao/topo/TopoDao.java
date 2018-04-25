package org.escalade.consumer.contract.dao.topo;

import org.escalade.model.bean.topo.Topo;

/**
 * TopoDao
 * @author Oltenos
 *
 */
public interface TopoDao {
	
	/**
	 * Retourne le topo correspondant au titre
	 * @param titre
	 * @return Topo
	 */
	public Topo getTopo(String titre);
	
	/**
	 * Enregistre le topo dans la base de données
	 * @param topo
	 */
	public void createTopo(Topo topo);
	
	/**
	 * Modifie le topo dans la base de données</br>
	 * ATTENTION le titre ne peut être modifié (clé dans la base de donnée)
	 * @param topo
	 */
	public void updateTopo(Topo topo);
	
	/**
	 * Supprime le topo de la base de données
	 * @param titre
	 */
	public void deleteTopo(String titre);

}
