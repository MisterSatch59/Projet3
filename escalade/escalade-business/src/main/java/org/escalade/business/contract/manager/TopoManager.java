package org.escalade.business.contract.manager;

import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;

/**
 * TopoManager
 * @author Oltenos
 *
 */
public interface TopoManager {
	
	/**
	 * Retourne le topo correspondant
	 * @param titre
	 * @return
	 */
	public Topo getTopo(String titre);
	
	/**
	 * Enregistre le topo dans la base de données
	 * @param topo
	 * @throws FunctionalException FunctionalException levée en cas de non validation du bean
	 */
	public void createTopo(Topo topo) throws FunctionalException;
	
	/**
	 * Modifie le topo dans la base de données</br>
	 * ATTENTION le titre ne peut être modifié (clé dans la base de donnée)
	 * @param topo
	 * @throws FunctionalException FunctionalException levée en cas de non validation du bean
	 */
	public void updateTopo(Topo topo) throws FunctionalException;
	
	/**
	 * Supprime de la base de données le topo correspondant 
	 * @param titre
	 */
	public void deleteTopo(String titre);

}
