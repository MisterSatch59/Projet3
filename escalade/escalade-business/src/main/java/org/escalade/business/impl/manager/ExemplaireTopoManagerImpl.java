package org.escalade.business.impl.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.ExemplaireTopoManager;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.exception.FunctionalException;
import org.escalade.model.exception.NotFoundException;
import org.escalade.model.exception.TechnicalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Named("exemplaireTopoManager")
public class ExemplaireTopoManagerImpl extends AbstractManagerImpl implements ExemplaireTopoManager {
	private static final Logger LOGGER = LogManager.getLogger(ExemplaireTopoManagerImpl.class);

	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) throws FunctionalException {
		LOGGER.traceEntry("pseudoProprietaire = " + pseudoProprietaire);

		if (pseudoProprietaire == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		List<ExemplaireTopo> result = this.getDaoFactory().getExemplaireTopoDao().getListExemplaireTopo(pseudoProprietaire);

		if (result == null) {
			result = new ArrayList<ExemplaireTopo>();
		}

		LOGGER.traceExit(result);
		return result;

	}
	
	@Override
	public List<ExemplaireTopo> getListExemplaireTitreTopo(String titreTopo, Date debut, Date fin) throws FunctionalException{
		LOGGER.traceEntry("titreTopo = " + titreTopo);

		if (titreTopo == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}
		
		List<ExemplaireTopo> result = this.getDaoFactory().getExemplaireTopoDao().getListExemplaireTitreTopo(titreTopo,debut, fin);
		
		if (result == null) {
			result = new ArrayList<ExemplaireTopo>();
		}

		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public ExemplaireTopo getExemplaireTopo(int id) throws NotFoundException {
		LOGGER.traceEntry("id = " + id);

		ExemplaireTopo result = this.getDaoFactory().getExemplaireTopoDao().getExemplaireTopo(id);
		
		if(result==null) {
			throw new NotFoundException("The copy of book copy was not found with id = " + id);
		}

		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo) throws FunctionalException, TechnicalException {
		LOGGER.traceEntry("spot = " + exemplaireTopo);
		
		if (exemplaireTopo == null) {
			throw new FunctionalException("Invalid informations sent to database");
		}

		Set<ConstraintViolation<ExemplaireTopo>> violations = this.getValidator().validate(exemplaireTopo);
		LOGGER.debug("resultat validation spot = " + violations);

		if (violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager()
					.getTransaction(new DefaultTransactionDefinition());
			try {
				exemplaireTopo = this.getDaoFactory().getExemplaireTopoDao().createExemplaireTopo(exemplaireTopo);

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

		LOGGER.traceExit(exemplaireTopo);
		return exemplaireTopo;
	}

	@Override
	public void deleteExemplaireTopo(int id) throws TechnicalException {
		LOGGER.traceEntry("id = " + id);

		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager()
				.getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getExemplaireTopoDao().deleteExemplaireTopo(id);

			TransactionStatus vTScommit = vTransactionStatus;
			vTransactionStatus = null;
			this.getPlatformTransactionManager().commit(vTScommit);
		} finally {
			if (vTransactionStatus != null) {
				this.getPlatformTransactionManager().rollback(vTransactionStatus);
				throw new TechnicalException("Technical error with the database");
			}
		}

		LOGGER.traceExit();
	}

}
