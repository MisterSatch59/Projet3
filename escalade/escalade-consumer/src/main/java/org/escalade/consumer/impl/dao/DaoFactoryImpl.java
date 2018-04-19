package org.escalade.consumer.impl.dao;

import javax.inject.Inject;
import javax.inject.Named;

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
		return spotDao;
	}

	@Override
	public CommentaireDao getCommentaireDao() {
		return commentaireDao;
	}

	@Override
	public ZoneTexteDao getZoneTexteDao() {
		return zoneTexteDao;
	}
		
	@Override
	public TopoDao getTopoDao() {
		return topoDao;
	}

	@Override
	public UtilisateurDao getUtilisateurDao() {
		return utilisateurDao;
	}

	@Override
	public ExemplaireTopoDao getExemplaireTopoDao() {
		return exemplaireTopoDao;
	}

	@Override
	public EmpruntDao getEmpruntDao() {
		return empruntDao;
	}



	
}
