package org.escalade.webapp.converter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;
import org.escalade.model.bean.spot.Departement;

/**
 * Locator pour le bean Departement
 * @author Oltenos
 *
 */
public class DepartementLocator extends StrutsTypeConverter {
	private static final Logger LOGGER = LogManager.getLogger(DepartementLocator.class);

	@Override
	public Object convertFromString(Map pContext, String[] pValues, Class pToClass) {
		LOGGER.traceEntry("pValues[0] = " + pValues[0]);
		Object vRetour = null;

		if (pValues != null) {
			if (pValues.length == 1) {
				if(pValues[0].isEmpty()) {
					vRetour=null;
				}else {
					String vValue = pValues[0];
					String[] vDep = vValue.split(" - ");
					vRetour = new Departement(vDep[0], vDep[1]);
				}
			} else {
				vRetour = performFallbackConversion(pContext, pValues, pToClass);
			}
		}
		
		LOGGER.traceExit(vRetour);
		return vRetour;
	}

	@Override
	public String convertToString(Map pContext, Object pObject) {
		LOGGER.traceEntry("pObject = " + pObject);
		
		String vString;
		if (pObject instanceof Departement) {
			Departement vDepartement = (Departement) pObject;
			vString = vDepartement.getNumero() + " - " + vDepartement.getNom();
		} else {
			vString = "";
		}
		
		LOGGER.traceExit(vString);
		return vString;
	}

}
