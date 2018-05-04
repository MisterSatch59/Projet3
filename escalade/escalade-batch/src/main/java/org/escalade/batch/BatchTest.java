package org.escalade.batch;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.business.contract.ManagerFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Ville;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchTest {
	private static final Logger LOGGER = LogManager.getLogger(BatchTest.class);
	
	public static void main(String[] args) {
		@SuppressWarnings({ "resource" })
		ApplicationContext vApplicationContext = new ClassPathXmlApplicationContext("classpath:/bootstrapContext.xml");
		
		ManagerFactory managerFactory = (ManagerFactory) vApplicationContext.getBean("managerFactory");
		
		List<Ville> result = managerFactory.getSpotManager().getVilles("83");
		
		for (Ville ville : result) {
			System.out.println(ville);
		}
		
		LOGGER.traceExit();
	}
}
