package org.escalade.business.contract.manager;

import java.util.List;

import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;
import org.escalade.model.recherche.RechercheSpot;

/**
 * SpotManager
 * 
 * @author Oltenos
 *
 */
public interface SpotManager {

	/**
	 * Retourne le Spot correspondant à l'identifiant
	 * 
	 * @param id
	 * @return Spot
	 * @throws NotFoundException 
	 */
	public Spot getSpot(int id) throws NotFoundException;

	/**
	 * Enregistre le spot dans la base de données et le retourne modifié (avec son
	 * identifiant dans la base de données)
	 * 
	 * @param spot
	 * @return Spot
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public Spot createSpot(Spot spot) throws FunctionalException, TechnicalException;

	/**
	 * Modifie le spot dans la base de données</br>
	 * ATTENTION : l'id doit correspondre à l'id dans la base de donnée</br>
	 * La modification sera en effet réalisée dans la ligne de la base de données
	 * correspondant à l'id
	 * 
	 * @param spot
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public void updateSpot(Spot spot) throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base de données le spot correspondant à l'id
	 * 
	 * @param id
	 * @throws TechnicalException 
	 */
	public void deleteSpot(int id) throws TechnicalException;

	/**
	 * Enregistre le commentaire dans la base de données et le retourne modifié (avec
	 * son identifiant dans la base de données)
	 * 
	 * @param spotId
	 * @param commentaire
	 * @return Commentaire
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 * @throws TechnicalException 
	 */
	public Commentaire createCommentaire(int spotId, Commentaire commentaire) throws FunctionalException, TechnicalException;

	/**
	 * Supprime de la base de données le commentaire correspondant à l'identifiant
	 * 
	 * @param id
	 * @throws TechnicalException 
	 */
	public void deleteCommentaire(int id) throws TechnicalException;

	/**
	 * Recherche des spots à partir des critères en paramètres (contenues dans le
	 * bean RechercheSpot)
	 * 
	 * @param criteres
	 * @return List<Spot>
	 * @throws FunctionalException
	 *             levée en cas de non validation du bean
	 */
	public List<Spot> rechercheSpot(RechercheSpot criteres) throws FunctionalException;

	/**
	 * Retourne la liste des départements
	 * 
	 * @return List<Departement>
	 */
	public List<Departement> getDepartements();

	/**
	 * Retourne la liste des types
	 * 
	 * @return List<String>
	 */
	public List<String> getTypes();

	/**
	 * Retourne la liste des orientations
	 * 
	 * @return List<String>
	 */
	public List<String> getOrientations();

	/**
	 * Retourne la liste des profils
	 * 
	 * @return List<String>
	 */
	public List<String> getProfils();

	/**
	 * Retourne la liste des difficultés
	 * 
	 * @return List<String>
	 */
	public List<String> getDifficultes();

	/**
	 * Retourne la liste des titres des topo auquels le spot appartient
	 * 
	 * @param spotId
	 * @return List<String>
	 */
	List<String> getListTopo(int spotId);

	/**
	 * Retourne la liste des villes d'un département contenu dans la base de données,
	 * retourne la liste de toute le villes si NumeroDepartement est vide ou null
	 * 
	 * @param NumeroDepartement
	 * @return List<Ville>
	 * @throws FunctionalException 
	 */
	public List<Ville> getVilles(String NumeroDepartement) throws FunctionalException;

}
