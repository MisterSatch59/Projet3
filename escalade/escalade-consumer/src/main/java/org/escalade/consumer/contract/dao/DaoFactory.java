package org.escalade.consumer.contract.dao;

import org.escalade.consumer.contract.dao.spot.SpotDao;
import org.escalade.consumer.contract.dao.texte.CommentaireDao;
import org.escalade.consumer.contract.dao.texte.ZoneTexteDao;
import org.escalade.consumer.contract.dao.topo.EmpruntDao;
import org.escalade.consumer.contract.dao.topo.ExemplaireTopoDao;
import org.escalade.consumer.contract.dao.topo.TopoDao;
import org.escalade.consumer.contract.dao.utilisateur.UtilisateurDao;

/**
 * DaoFactory
 * @author Oltenos
 *
 */
public interface DaoFactory {
	
	/**
	 * Retourne le SpotDao
	 * @return SpotDao
	 */
	public SpotDao getSpotDao();
	
	/**
	 * Retourne le CommentaireDao
	 * @return CommentaireDao
	 */
	public CommentaireDao getCommentaireDao();
	
	/**
	 * Retourne le ZoneTexteDao
	 * @return ZoneTexteDao
	 */
	public ZoneTexteDao getZoneTexteDao();
	
	/**
	 * Retourne le TopoDao
	 * @return TopoDao
	 */
	public TopoDao getTopoDao();
	
	/**
	 * Retourne le UtilisateurDao
	 * @return UtilisateurDao
	 */
	public UtilisateurDao getUtilisateurDao();
	
	/**
	 * Retourne le ExemplaireTopoDao
	 * @return ExemplaireTopoDao
	 */
	public ExemplaireTopoDao getExemplaireTopoDao();

	/**
	 * Retourne le EmpruntDao
	 * @return EmpruntDao
	 */
	public EmpruntDao getEmpruntDao();
	
}
