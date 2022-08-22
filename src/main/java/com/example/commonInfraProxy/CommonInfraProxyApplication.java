package com.example.commonInfraProxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CommonInfraProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonInfraProxyApplication.class, args);
	}

}
