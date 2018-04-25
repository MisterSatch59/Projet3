package org.escalade.consumer.impl.dao;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.spot.SpotDao;
import org.escalade.consumer.contract.dao.texte.CommentaireDao;
import org.escalade.consumer.contract.dao.texte.ZoneTexteDao;
import org.escalade.consumer.contract.dao.topo.EmpruntDao;
import org.escalade.consumer.contract.dao.topo.ExemplaireTopoDao;
import org.escalade.consumer.contract.dao.topo.TopoDao;
import org.escalade.consumer.contract.dao.utilisateur.UtilisateurDao;

/**
 * Implementation de DaoFactory
 * 
 * @author Oltenos
 *
 */
@Named("daoFactory")
public class DaoFactoryImpl implements DaoFactory {
	private static final Logger LOGGER = LogManager.getLogger(DaoFactoryImpl.class);
	
	@Inject
	private SpotDao spotDao;
	@Inject
	private CommentaireDao commentaireDao;
	@Inject
	private ZoneTexteDao zoneTexteDao;
	@Inject
	private TopoDao topoDao;
	@Inject
	private UtilisateurDao utilisateurDao;
	@Inject
	private ExemplaireTopoDao exemplaireTopoDao;
	@Inject
	private EmpruntDao empruntDao;

	@Override
	public SpotDao getSpotDao() {
		LOGGER.traceExit(spotDao);
		return spotDao;
	}

	@Override
	public CommentaireDao getCommentaireDao() {
		LOGGER.traceExit(commentaireDao);
		return commentaireDao;
	}

	@Override
	public ZoneTexteDao getZoneTexteDao() {
		LOGGER.traceExit(zoneTexteDao);
		return zoneTexteDao;
	}
		
	@Override
	public TopoDao getTopoDao() {
		LOGGER.traceExit(topoDao);
		return topoDao;
	}

	@Override
	public UtilisateurDao getUtilisateurDao() {
		LOGGER.traceExit(utilisateurDao);
		return utilisateurDao;
	}

	@Override
	public ExemplaireTopoDao getExemplaireTopoDao() {
		LOGGER.traceExit(exemplaireTopoDao);
		return exemplaireTopoDao;
	}

	@Override
	public EmpruntDao getEmpruntDao() {
		LOGGER.traceExit(empruntDao);
		return empruntDao;
	}



	
}
