package org.escalade.consumer.contract.dao.topo;

import java.util.List;

import org.escalade.model.bean.topo.ExemplaireTopo;

/**
 * ExemplaireTopoDao
 * @author Oltenos
 *
 */
public interface ExemplaireTopoDao {
	
	/**
	 * Retourne la list des exemplaire de topo possédé par l'utilisateur
	 * @param pseudoProprietaire
	 * @return List<ExemplaireTopo>
	 */
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire);
	/**
	 * Retourne l'exempalte de topo à partir de son identifiant
	 * @param id
	 * @return ExemplaireTopo
	 */
	public ExemplaireTopo getExemplaireTopo(int id);
}
