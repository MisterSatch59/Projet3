package org.escalade.business.impl.manager;

import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.TopoManager;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.exception.FunctionalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Implémnetation de TopoManager
 * @author Oltenos
 *
 */
@Named("topoManager")
public class TopoManagerImpl extends AbstractManagerImpl implements TopoManager {
	private static final Logger LOGGER = LogManager.getLogger(TopoManagerImpl.class);

	@Override
	public Topo getTopo(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		if(titre!=null) {
			Topo result = this.getDaoFactory().getTopoDao().getTopo(titre);
			
			LOGGER.traceExit(result);
			return result;
		}
		
		LOGGER.traceExit(null);
		return null;
	}

	@Override
	public void createTopo(Topo topo) throws FunctionalException {
		LOGGER.traceEntry("topo = " + topo);
		
		Set<ConstraintViolation<Topo>> violations = this.getValidator().validate(topo);
		LOGGER.debug("resultat validation topo = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().createTopo(topo);
	
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}else {
			String message = "";
			int i=0;
			for (ConstraintViolation<Topo> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
		
		LOGGER.traceExit();
	}

	@Override
	public void updateTopo(Topo topo) throws FunctionalException {
		LOGGER.traceEntry("topo = " + topo);
		
		Set<ConstraintViolation<Topo>> violations = this.getValidator().validate(topo);
		LOGGER.debug("resultat validation topo = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().updateTopo(topo);
	
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}else {
			String message = "";
			int i=0;
			for (ConstraintViolation<Topo> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
		
		LOGGER.traceExit();
	}

	@Override
	public void deleteTopo(String titre) {
		LOGGER.traceEntry("titre = " + titre);
		
		if(titre!=null) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				this.getDaoFactory().getTopoDao().deleteTopo(titre);
	
				TransactionStatus vTScommit = vTransactionStatus;
				vTransactionStatus = null;
				this.getPlatformTransactionManager().commit(vTScommit);
			} finally {
				if (vTransactionStatus != null) {
					this.getPlatformTransactionManager().rollback(vTransactionStatus);
				}
			}
		}

		LOGGER.traceExit();
	}

}
