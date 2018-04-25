package org.escalade.business.impl.manager;

import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.manager.EmpruntManager;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.exception.FunctionalException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Implémentation de EmpruntManager
 * @author Oltenos
 *
 */
@Named("empruntManager")
public class EmpruntManagerImpl extends AbstractManagerImpl implements EmpruntManager {
	private static final Logger LOGGER = LogManager.getLogger(EmpruntManagerImpl.class);
	
	@Override
	public List<Emprunt> getListEmprunt(String pseudoEmprunteur) {
		LOGGER.traceEntry("pseudoEmprunteur = " + pseudoEmprunteur);
		
		if(pseudoEmprunteur!=null) {
			List<Emprunt> result = this.getDaoFactory().getEmpruntDao().getListEmprunt(pseudoEmprunteur);
			
			LOGGER.traceExit(result);
			return result;
		}

		LOGGER.traceExit(null);
		return null;
	}

	@Override
	public List<Emprunt> getListEmprunt(int exemplaireTopoId) {
		LOGGER.traceEntry("exemplaireTopoId = " + exemplaireTopoId);
		
		List<Emprunt> result = this.getDaoFactory().getEmpruntDao().getListEmprunt(exemplaireTopoId);
		
		LOGGER.traceExit(result);
		return result;
	}
	
	@Override
	public Emprunt createEmprunt(Emprunt emprunt) throws FunctionalException {
		LOGGER.traceEntry("emprunt = " + emprunt);
		
		Set<ConstraintViolation<Emprunt>> violations = this.getValidator().validate(emprunt);
		LOGGER.debug("resultat validation spot = " + violations);
		
		if(violations.isEmpty()) {
			TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
			try {
				emprunt = this.getDaoFactory().getEmpruntDao().createEmprunt(emprunt);
	
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
			for (ConstraintViolation<Emprunt> constraintViolation : violations) {
				if(i==0) {
					message += constraintViolation.getMessage();
					i++;
				}else{
					message += ", " + constraintViolation.getMessage();
				}
			}
			throw new FunctionalException(message);
		}
			
		LOGGER.traceExit(emprunt);
		return emprunt;
	}

	@Override
	public void deleteEmprunt(int id) {
		LOGGER.traceEntry("id = " + id);
		
		TransactionStatus vTransactionStatus = this.getPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			this.getDaoFactory().getEmpruntDao().deleteEmprunt(id);;

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
