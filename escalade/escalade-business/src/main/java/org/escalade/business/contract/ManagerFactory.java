package org.escalade.business.contract;

import org.escalade.business.contract.manager.EmpruntManager;
import org.escalade.business.contract.manager.ExemplaireTopoManager;
import org.escalade.business.contract.manager.SpotManager;
import org.escalade.business.contract.manager.TopoManager;
import org.escalade.business.contract.manager.UtilisateurManager;

/**
 * ManagerFactory
 * 
 * @author Oltenos
 *
 */
public interface ManagerFactory {

	/**
	 * Retourne l'UtilisateurManager
	 * 
	 * @return UtilisateurManager
	 */
	public UtilisateurManager getUtilisateurManager();

	/**
	 * Retourne le SpotManager
	 * 
	 * @return SpotManager
	 */
	public SpotManager getSpotManager();

	/**
	 * Retourne le TopoManager
	 * 
	 * @return TopoManager
	 */
	public TopoManager getTopoManager();

	/**
	 * Retourne l'ExemplaireManager
	 * 
	 * @return ExemplaireTopoManager
	 */
	public ExemplaireTopoManager getExemplaireTopoManager();

	/**
	 * Retourne l'EmpruntManager
	 * 
	 * @return EmpruntManager
	 */
	public EmpruntManager getEmpruntManager();

}
