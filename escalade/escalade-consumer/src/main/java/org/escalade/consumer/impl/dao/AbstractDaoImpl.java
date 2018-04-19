package org.escalade.consumer.impl.dao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * 
 * Superclasse des DAO permettant la gestion de la datasource pour tous les DAO 
 * 
 * @author Oltenos
 *
 */
public abstract class AbstractDaoImpl {

	/**
	 * DataSource d'accés à la base de données
	 */
	@Inject
	@Named("dataSource")
	private DataSource dataSource;

	/**
	 * getter de la DataSource
	 * @return DataSource
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}
}
