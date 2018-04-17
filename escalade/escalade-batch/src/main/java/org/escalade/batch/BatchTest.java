package org.escalade.batch;

import org.escalade.business.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchTest {
	
	public static void main(String[] args) {
		@SuppressWarnings({ "resource" })
		ApplicationContext vApplicationContext = new ClassPathXmlApplicationContext("classpath:/bootstrapContext.xml");
		
		Test test = (Test) vApplicationContext.getBean("test");
		test.test();
	}

}
