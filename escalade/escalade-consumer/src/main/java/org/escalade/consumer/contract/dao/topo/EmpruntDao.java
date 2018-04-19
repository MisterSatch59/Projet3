package org.escalade.consumer.contract.dao.topo;

import java.util.List;

import org.escalade.model.bean.topo.Emprunt;

/**
 * EmpruntDao
 * @author Oltenos
 *
 */
public interface EmpruntDao {

	/**
	 * Retourne la list des emprunt réalisé par l'utilisateur
	 * @param pseudoEmprunteur
	 * @return List<ExemplaireTopo>
	 */
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur);

}
