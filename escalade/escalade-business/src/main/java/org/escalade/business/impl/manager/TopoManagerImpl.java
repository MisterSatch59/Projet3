package org.escalade.business.impl.manager;

import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.TopoManager;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Implémnetation de TopoManager
 * 
 * @author Oltenos
 *
 */
@Named("topoManager")
public class TopoManagerImpl extends AbstractManagerImpl implements TopoManager {
	private static final Logger LOGGER = LogManager.getLogger(TopoManagerImpl.class);

	@Override
	public Topo getTopo(String titre) throws NotFoundException, FunctionalException {
		LOGGER.traceEntry("titre = " + titre);
		
		if (titre == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}
		
		Topo result = this.getDaoFactory().getTopoDao().getTopo(titre);
		
		if (result == null) {
			throw new NotFoundException("The book was not found with title = " + titre);
		}

		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public void createTopo(Topo topo) throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("topo = " + topo);
		
		if (topo == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		Set<ConstraintViolation<Topo>> violations = this.getValidator().validate(topo);
		LOGGER.debug("resultat validation topo = " + violations);

		if (violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().createTopo(topo);

				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
					throw new TechnicalException("Technical error with the database");
				}
			}
		} else {
			throw new FunctionalException("Invalid informations sent to database");
		}

		LOGGER.traceExit();
	}

	@Override
	public void updateTopo(Topo topo) throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("topo = " + topo);
		
		if (topo == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		Set<ConstraintViolation<Topo>> violations = this.getValidator().validate(topo);
		LOGGER.debug("resultat validation topo = " + violations);

		if (violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().updateTopo(topo);

				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
					throw new TechnicalException("Technical error with the database");
				}
			}
		} else {
			throw new FunctionalException("Invalid informations sent to database");
		}

		LOGGER.traceExit();
	}

	@Override
	public void deleteTopo(String titre) throws  FunctionalException, TechnicalException  {
		LOGGER.traceEntry("titre = " + titre);
		
		if (titre == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		if (titre != null) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().deleteTopo(titre);

				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
					throw new TechnicalException("Technical error with the database");
				}
			}
		}

		LOGGER.traceExit();
	}

}
