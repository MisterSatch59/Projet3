package org.escalade.consumer.contract.dao.topo;

import java.util.List;

import org.escalade.model.bean.topo.Emprunt;

/**
 * EmpruntDao
 * 
 * @author Oltenos
 *
 */
public interface EmpruntDao {

	/**
	 * Retourne la liste des emprunts réalisés par l'utilisateur
	 * 
	 * @param pseudoEmprunteur
	 * @return List<Emprunt>
	 */
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur);

	/**
	 * Retourne la liste des emprunts d'un exemplaire donné
	 * 
	 * @param exemplaireTopoId
	 * @return List<Emprunt>
	 */
	public List<Emprunt> getListEmprunt(int exemplaireTopoId);

	/**
	 * Enregistre l'emprunt dans la base de données et le retourne modifié (avec son
	 * identifiant dans la base de données)
	 * 
	 * @param emprunt
	 * @return Emprunt
	 */
	public Emprunt createEmprunt(Emprunt emprunt);

	/**
	 * Supprime de la base de données l'emprunt correspondant à l'identifiant
	 * 
	 * @param id
	 */
	public void deleteEmprunt(int id);

}
