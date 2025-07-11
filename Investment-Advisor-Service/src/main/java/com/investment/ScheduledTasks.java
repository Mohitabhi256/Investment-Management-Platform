package com.investment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {

    @Autowired
    private ExcelService excelService;

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void importExcelDataDaily() {
        try {
            excelService.importExcelData("C:\\Users\\vanreddy\\Downloads\\ind_nifty50list_50.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private BasketService basketService;

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight every day
    public void updateBasketPrices() {
        basketService.updateBasketPrices();
    }
}
