package com.itkee.webmagic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling
public class WebmagicApplication {
	@Autowired
	private MultipartConfigElement multipartConfigElement;
	@Autowired
	private DispatcherServlet dispatcherServlet;

	public static void main(String[] args) {
		SpringApplication.run(WebmagicApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean apiServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
		//注入上传配置到自己注册的ServletRegistrationBean
		bean.addUrlMappings("/api/*");
		bean.setMultipartConfig(multipartConfigElement);
		bean.setName("apiServlet");
		return bean;
	}
}
