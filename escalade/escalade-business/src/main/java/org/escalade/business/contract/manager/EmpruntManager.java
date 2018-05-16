package org.escalade.business.contract.manager;

import java.util.List;

import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.TechnicalException;

/**
 * EmpruntManager
 * 
 * @author Oltenos
 *
 */
public interface EmpruntManager {

	/**
	 * Retourne la liste des emprunts réalisés par l'utilisateur
	 * 
	 * @param pseudoEmprunteur
	 * @return List<Emprunt>
	 * @throws FunctionalException
	 */
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur) throws FunctionalException;

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
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException
	 */
	public Emprunt createEmprunt(Emprunt emprunt) throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base de données l'emprunt correspondant à l'identifiant
	 * 
	 * @param id
	 * @throws TechnicalException
	 */
	public void deleteEmprunt(int id) throws TechnicalException;
}
