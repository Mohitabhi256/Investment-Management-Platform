package com.investment;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class Investment-Advisor-ServiceApplication {

	@Autowired
    private ExcelService excelService;
	public static void main(String[] args) {
		SpringApplication.run(Investment-Advisor-ServiceApplication.class, args);
	}
	 @EventListener(ApplicationReadyEvent.class)
	    public void doSomethingAfterStartup() {
	        try {
	            excelService.importData();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
