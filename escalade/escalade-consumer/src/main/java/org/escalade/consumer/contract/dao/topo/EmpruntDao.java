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
	 * Retourne la liste des emprunts réalisés par l'utilisateur
	 * @param pseudoEmprunteur
	 * @return List<ExemplaireTopo>
	 */
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur);
	
	/**
	 * Retourne la liste des emprunts d'un exemplaire donné
	 * @param exemplaireTopoId
	 * @return
	 */
	public List<Emprunt> getListEmprunt(int exemplaireTopoId);

	/**
	 * Enregistre l'emprunt dans la base de données
	 * @param emprunt
	 * @return Emprunt (avec id)
	 */
	public Emprunt createEmprunt(Emprunt emprunt);
	
	/**
	 * Supprime l'emprunt de la base de données
	 * @param id
	 */
	public void deleteEmprunt(int id);
	
}
