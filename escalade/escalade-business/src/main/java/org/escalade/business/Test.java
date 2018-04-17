package org.escalade.business;

import java.util.Set;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.escalade.model.bean.spot.Departement;

@Named
public class Test {
	
	public void test() {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Departement d = new Departement(null,"Nord");
		
		Set<ConstraintViolation<Departement>> param = validator.validate(d);
		for (ConstraintViolation<Departement> constraintViolation : param) {
			System.out.println("constraintViolation : " + constraintViolation.getConstraintDescriptor().getMessageTemplate());
		}
		System.out.println(d.toString());
	}
}
