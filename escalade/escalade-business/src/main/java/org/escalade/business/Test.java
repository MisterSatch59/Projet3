package org.escalade.business;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.topo.Emprunt;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Named
public class Test {
	
	@Inject
	private DaoFactory daoFactory;
	
	@Inject
    @Named("txManagerTicket")
    private PlatformTransactionManager platformTransactionManager;
	
	public void test() {
		/*ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Departement d = new Departement(null,"Nord");
		
		Set<ConstraintViolation<Departement>> param = validator.validate(d);
		for (ConstraintViolation<Departement> constraintViolation : param) {
			System.out.println("constraintViolation : " + constraintViolation.getConstraintDescriptor().getMessageTemplate());
		}
		System.out.println(d.toString());*/
		
		Emprunt e = new Emprunt(0,new Date(),new Date(),daoFactory.getUtilisateurDao().getUtilisateur("Max"),daoFactory.getExemplaireTopoDao().getExemplaireTopo(1));
		TransactionStatus vTransactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			daoFactory.getEmpruntDao().createEmprunt(e);
		} catch (Throwable vEx) {
			platformTransactionManager.rollback(vTransactionStatus);
			throw vEx;
		}
		platformTransactionManager.commit(vTransactionStatus);
		
		
	}
}
