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

}
