package org.escalade.webapp.converter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;
import org.escalade.model.bean.spot.Ville;

public class VilleLocator extends StrutsTypeConverter  {
	private static final Logger LOGGER = LogManager.getLogger(VilleLocator.class);

	@Override
	public Object convertFromString(Map pContext, String[] pValues, Class pToClass) {
		LOGGER.traceEntry("pValues[0] = " + pValues[0]);
		Object vRetour = null;
		

		
		LOGGER.traceExit(vRetour);
		return vRetour;
	}

	@Override
	public String convertToString(Map pContext, Object pObject) {
		LOGGER.traceEntry("pObject = " + pObject);
		
		String vString;
		if (pObject instanceof Ville) {
			Ville vVille = (Ville) pObject;
			vString = vVille.getNom();
		} else {
			vString = "";
		}
		
		LOGGER.traceExit(vString);
		return vString;
	}

}
