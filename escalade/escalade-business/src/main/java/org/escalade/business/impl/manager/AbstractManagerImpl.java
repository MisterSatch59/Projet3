package org.escalade.business.impl.manager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.Validator;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * Superclasse des Manager permettant la gestion du transactionManager de
 * Spring, du daoFactory et du Validator pour tous les Manager
 * 
 * @author Oltenos
 *
 */
public abstract class AbstractManagerImpl {

	/**
	 * gestionnaire (Manager) de transaction (Spring)
	 */
	@Inject
	@Named("txManager")
	private PlatformTransactionManager platformTransactionManager;

	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	/**
	 * Injection du DaoFactory pour tous les Manager
	 */
	@Inject
	private DaoFactory daoFactory;

	protected DaoFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * Récupération du Validator pour tous les Manager
	 */
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public Validator getValidator() {
		return validator;
	}
}
