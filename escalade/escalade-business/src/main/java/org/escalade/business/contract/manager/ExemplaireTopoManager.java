package org.escalade.business.contract.manager;

import java.util.Date;
import java.util.List;

import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;

/**
 * ExemplaireTopoManager
 * 
 * @author Oltenos
 *
 */
public interface ExemplaireTopoManager {

	/**
	 * Retourne la liste des exemplaires de topo possédés par l'utilisateur
	 * 
	 * @param pseudoProprietaire
	 * @return List<ExemplaireTopo>
	 * @throws FunctionalException
	 */
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) throws FunctionalException;

	/**
	 * Retourne la liste des exemplaires existant d'un topo à partir de son titre et
	 * disponible entre les dates indiquées
	 * 
	 * @param titreTopo
	 * @param fin
	 * @param debut
	 * @return List<ExemplaireTopo>
	 * @throws FunctionalException
	 */
	public List<ExemplaireTopo> getListExemplaireTitreTopo(String titreTopo, Date debut, Date fin)
			throws FunctionalException;

	/**
	 * Retourne l'exemplaire de topo à partir de son identifiant
	 * 
	 * @param id
	 * @return ExemplaireTopo
	 * @throws NotFoundException
	 */
	public ExemplaireTopo getExemplaireTopo(int id) throws NotFoundException;

	/**
	 * Enregistre l'exemplaire de topo dans la base de données et le retourne
	 * modifié (avec son identifiant dans la base de données)
	 * 
	 * @param exTopo
	 * @return ExemplaireTopo
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException
	 */
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo)
			throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base données un exemplaire de topo à partir de son identifiant
	 * 
	 * @param id
	 * @throws TechnicalException
	 */
	public void deleteExemplaireTopo(int id) throws TechnicalException;

}
