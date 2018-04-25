package org.escalade.business.impl;

import org.escalade.business.contract.manager.EmpruntManager;
import org.escalade.business.contract.manager.ExemplaireTopoManager;

import javax.inject.Inject;
import javax.inject.Named;

import org.escalade.business.contract.ManagerFactory;
import org.escalade.business.contract.manager.SpotManager;
import org.escalade.business.contract.manager.TopoManager;
import org.escalade.business.contract.manager.UtilisateurManager;

/**
 * Implémentation de ManagerFactory
 * @author Oltenos
 *
 */
@Named("managerFactory")
public class ManagerFactoryImpl implements ManagerFactory {
	
	@Inject
	private UtilisateurManager utilisateurManager;
	@Inject
	private SpotManager spotManager;
	@Inject
	private TopoManager topoManager;
	@Inject
	private ExemplaireTopoManager exemplaireTopoManager;
	@Inject
	private EmpruntManager empruntManager;

	@Override
	public UtilisateurManager getUtilisateurManager() {
		return utilisateurManager;
	}

	@Override
	public SpotManager getSpotManager() {
		return spotManager;
	}

	@Override
	public TopoManager getTopoManager() {
		return topoManager;
	}

	@Override
	public ExemplaireTopoManager getExemplaireTopoManager() {
		return exemplaireTopoManager;
	}

	@Override
	public EmpruntManager getEmpruntManager() {
		return empruntManager;
	}

}
