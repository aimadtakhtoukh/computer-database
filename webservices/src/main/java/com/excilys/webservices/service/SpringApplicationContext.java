package com.excilys.webservices.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}
	 
	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> className) {
		return appContext.getBean(className);
	}

}
