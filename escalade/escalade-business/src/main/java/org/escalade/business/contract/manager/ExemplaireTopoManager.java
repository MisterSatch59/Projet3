package org.escalade.business.contract.manager;

import java.util.List;

import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.exception.FunctionalException;

/**
 * ExemplaireTopoManager
 * @author Oltenos
 *
 */
public interface ExemplaireTopoManager {
	
	/**
	 * Retourne la liste des exemplaires de topo possédés par l'utilisateur
	 * @param pseudoProprietaire
	 * @return List<ExemplaireTopo>
	 */
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire);
	/**
	 * Retourne l'exemplaire de topo à partir de son identifiant
	 * @param id
	 * @return ExemplaireTopo
	 */
	public ExemplaireTopo getExemplaireTopo(int id);
	/**
	 * Enregistre l'exemplaire de topo dans la base de données et le retourne modifié (avec son identifiant dans la base de données)
	 * @param exTopo
	 * @return ExemplaireTopo
	 * @throws FunctionalException levée en cas de non validation du bean
	 */
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo) throws FunctionalException;
	/**
	 * Supprime de la base données un exemplaire de topo à partir de son identifiant
	 * @param id
	 */
	public void deleteExemplaireTopo(int id);
	
}
