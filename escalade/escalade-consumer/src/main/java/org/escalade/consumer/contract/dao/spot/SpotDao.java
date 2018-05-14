package org.escalade.consumer.contract.dao.spot;

import java.util.List;

import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.spot.Ville;
import org.escalade.model.recherche.RechercheSpot;

/**
 * SpotDao
 * 
 * @author Oltenos
 *
 */
public interface SpotDao {

	/**
	 * Enregistre le spot dans la base de donnée et retourne le spot modifié (avec
	 * l'identifiant dans la base de données)</br>
	 * Remarque : le spot n'a pas de commentaire à la création
	 * 
	 * @param spot
	 * @return Spot
	 */
	public Spot createSpot(Spot spot);

	/**
	 * Retourne le Spot correspondant à l'identifiant
	 * 
	 * @param id
	 * @return Spot
	 */
	public Spot getSpot(int id);

	/**
	 * Modifie le spot dans la base de donnée</br>
	 * ATTENTION : l'id doit correspondre à l'id dans la base de donnée La
	 * modification sera en effet réalisée dans la ligne de la base de données
	 * correspondant à l'id
	 * 
	 * @param spot
	 */
	public void updateSpot(Spot spot);

	/**
	 * Supprime de la base de données le Spot correspondant à l'identifiant
	 * 
	 * @param id
	 */
	public void deleteSpot(int id);

	/**
	 * Recherche des spots à partir des critères en paramètre (contenues dans le
	 * bean RechercheSpot)
	 * 
	 * @param criteres
	 * @return List<Spot>
	 */
	public List<Spot> rechercheSpot(RechercheSpot criteres);

	/**
	 * Retourne le département correspondant au numero
	 * 
	 * @param numero
	 * @return Departement
	 */
	public Departement getDepartement(String numero);

	/**
	 * Retourne la liste des départements
	 * 
	 * @return List<Departement>
	 */
	public List<Departement> getDepartements();

	/**
	 * Retourne la liste des villes d'un département contenu dans la base de donné,
	 * retourne la liste de toute le villes si NumeroDepartement est vide ou null
	 * 
	 * @param numeroDepartement
	 * @return List<Ville>
	 */
	public List<Ville> getVilles(String numeroDepartement);

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

}
