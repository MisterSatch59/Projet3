package org.escalade.business.contract.manager;

import java.util.List;

import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

/**
 * TopoManager
 * 
 * @author Oltenos
 *
 */
public interface TopoManager {

	/**
	 * Retourne le topo correspondant
	 * 
	 * @param titre
	 * @return
	 * @throws NotFoundException 
	 * @throws FunctionalException 
	 */
	public Topo getTopo(String titre) throws NotFoundException, FunctionalException;
	
	/**
	 * Retourne la liste des topos
	 * @return List<Topo>
	 */
	public List<Topo> getListTopos();

	/**
	 * Enregistre le topo dans la base de données
	 * 
	 * @param topo
	 * @throws FunctionalException
	 *             FunctionalException levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public void createTopo(Topo topo) throws FunctionalException, TechnicalException;

	/**
	 * Modifie le topo dans la base de données</br>
	 * ATTENTION le titre ne doit pas être modifié (clé dans la base de donnée)
	 * 
	 * @param topo
	 * @throws FunctionalException
	 *             FunctionalException levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public void updateTopo(Topo topo) throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base de données le topo correspondant
	 * 
	 * @param titre
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	public void deleteTopo(String titre) throws FunctionalException, TechnicalException;

}
