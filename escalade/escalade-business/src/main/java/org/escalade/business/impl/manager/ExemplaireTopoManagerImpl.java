package org.escalade.business.impl.manager;

import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.ExemplaireTopoManager;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.exception.FunctionalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Named("exemplaireTopoManager")
public class ExemplaireTopoManagerImpl extends AbstractManagerImpl implements ExemplaireTopoManager {
	private static final Logger LOGGER = LogManager.getLogger(ExemplaireTopoManagerImpl.class);

	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) {
		LOGGER.traceEntry("pseudoProprietaire = " + pseudoProprietaire);
		
		if(pseudoProprietaire!=null) {
			List<ExemplaireTopo> result = this.getDaoFactory().getExemplaireTopoDao().getListExemplaireTopo(pseudoProprietaire);
			
			LOGGER.traceExit(result);
			return result;
		}
		
		LOGGER.traceExit(null);
		return null;
	}
	
	@Override
	public ExemplaireTopo getExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);
		
		ExemplaireTopo result = this.getDaoFactory().getExemplaireTopoDao().getExemplaireTopo(id);
		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo) throws FunctionalException {
		LOGGER.traceEntry("spot = " + exemplaireTopo);
		
		Set<ConstraintViolation<ExemplaireTopo>> violations = this.getValidator().validate(exemplaireTopo);
		LOGGER.debug("resultat validation spot = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				exemplaireTopo = this.getDaoFactory().getExemplaireTopoDao().createExemplaireTopo(exemplaireTopo);
	
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
			for (ConstraintViolation<ExemplaireTopo> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
			
		LOGGER.traceExit(exemplaireTopo);
		return exemplaireTopo;
	}

	@Override
	public void deleteExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);
		
		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getExemplaireTopoDao().deleteExemplaireTopo(id);

			TransactionStatus vTScommit = vTransactionStatus;
			vTransactionStatus = null;
			this.getPlatformTransactionManager().commit(vTScommit);
		} finally {
			if (vTransactionStatus != null) {
				this.getPlatformTransactionManager().rollback(vTransactionStatus);
			}
		}
		
		LOGGER.traceExit();
	}


	
	

}
