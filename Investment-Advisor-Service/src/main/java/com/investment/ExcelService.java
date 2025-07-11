package com.investment;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Transactional
    public void importExcelData(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        // Read header row to get dates
        Row headerRow = sheet.getRow(0);
        List<Date> dates = new ArrayList<>();
        for (int i = 5; i < headerRow.getLastCellNum() - 5; i++) { // Exclude the last 4 columns
            Cell dateCell = headerRow.getCell(i);
            if (dateCell != null) {
                CellValue cellValue = evaluator.evaluate(dateCell);
                if (cellValue != null) {
                    switch (cellValue.getCellType()) {
                        case STRING:
                            try {
                                dates.add(dateFormat.parse(cellValue.getStringValue()));
                            } catch (Exception e) {
                                System.out.println("Error parsing date: " + cellValue.getStringValue());
                            }
                            break;
                        case NUMERIC:
                            dates.add(DateUtil.getJavaDate(cellValue.getNumberValue()));
                            break;
                        default:
                            System.out.println("Unsupported cell type: " + cellValue.getCellType());
                    }
                }
            }
        }

        System.out.println("Dates list size: " + dates.size());
        for (Date date : dates) {
            System.out.println("Date: " + date);
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) {
                continue; // Skip header row
            }
            if (currentRow.getRowNum() > 51) {
                break;
            }

            // **Updated Section**
            // Find stock by symbol
            Stock stock = stockRepository.findBySymbol(currentRow.getCell(2).getStringCellValue());
            
            if (stock == null) {
                stock = new Stock(); // Create a new stock if it doesn't exist
            }

            // Update stock details
            if (currentRow.getCell(0) != null) {
                stock.setCompanyName(currentRow.getCell(0).getStringCellValue());
            }
            if (currentRow.getCell(1) != null) {
                stock.setIndustry(currentRow.getCell(1).getStringCellValue());
            }
            if (currentRow.getCell(2) != null) {
                stock.setSymbol(currentRow.getCell(2).getStringCellValue());
            }
            if (currentRow.getCell(3) != null) {
                stock.setSeries(currentRow.getCell(3).getStringCellValue());
            }
            if (currentRow.getCell(4) != null) {
                stock.setIsinCode(currentRow.getCell(4).getStringCellValue());
            }

            // Update the closing price
         // Extracting the closing price
         // Extracting the closing price
            if (currentRow.getCell(currentRow.getLastCellNum() - 6) != null) {
                Cell closingPriceCell = currentRow.getCell(currentRow.getLastCellNum() - 6);
                
                if (closingPriceCell.getCellType() == CellType.NUMERIC) {
                    // Directly set the closing price if it's a numeric cell
                    stock.setClosingPrice(BigDecimal.valueOf(closingPriceCell.getNumericCellValue()));
                    
                } else if (closingPriceCell.getCellType() == CellType.FORMULA) {
                    // If it's a formula cell, try to retrieve the numeric value
                    try {
                        stock.setClosingPrice(BigDecimal.valueOf(closingPriceCell.getNumericCellValue()));
                    } catch (NotImplementedException e) {
                        System.out.println("Function not implemented: " + e.getMessage());
                        stock.setClosingPrice(BigDecimal.ZERO); // Fallback in case formula is not supported
                    }
                    
                } else {
                    // If the cell is neither numeric nor formula, set the default value
                    System.out.println("Unsupported cell type for closing price: " + closingPriceCell.getCellType());
                    stock.setClosingPrice(BigDecimal.ZERO);
                }
            }



            // Read highest, lowest, average prices, and performance from the last 4 columns
            if (currentRow.getCell(currentRow.getLastCellNum() - 4) != null) {
                stock.setHighestPrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 4).getNumericCellValue()));
            }
            if (currentRow.getCell(currentRow.getLastCellNum() - 3) != null) {
                stock.setLowestPrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 3).getNumericCellValue()));
            }
            if (currentRow.getCell(currentRow.getLastCellNum() - 2) != null) {
                stock.setAveragePrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 2).getNumericCellValue()));
            }
            if (currentRow.getCell(currentRow.getLastCellNum() - 1) != null) {
                stock.setPercentageChange(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 1).getNumericCellValue()));
            }

            // Save the updated stock
            stockRepository.save(stock);

            // **End of Updated Section**

            // Now process stock prices as usual
            for (int i = 5; i < currentRow.getLastCellNum() - 5; i++) { // Exclude the last 4 columns
                Cell cell = currentRow.getCell(i);
                if (cell != null) {
                    StockPrice stockPrice = stockPriceRepository.findBySymbolAndSdate(stock.getSymbol(), dates.get(i - 5))
                            .orElse(new StockPrice());  // Retrieve existing or create new

                    stockPrice.setSymbol(stock.getSymbol());
                    if (i - 5 < dates.size()) {
                        stockPrice.setSdate(dates.get(i - 5)); // Adjust index for dates list
                    } else {
                        System.out.println("Index out of bounds for dates list: " + (i - 5));
                    }

                    try {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            stockPrice.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        } else if (cell.getCellType() == CellType.ERROR) {
                            stockPrice.setPrice(BigDecimal.ZERO); // Handle error cell
                        } else if (cell.getCellType() == CellType.FORMULA) {
                            try {
                                stockPrice.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                            } catch (NotImplementedException e) {
                                System.out.println("Function not implemented: " + e.getMessage());
                                stockPrice.setPrice(BigDecimal.ZERO);
                            }
                        } else {
                            System.out.println("Unexpected cell type: " + cell.getCellType());
                            stockPrice.setPrice(BigDecimal.ZERO); // Default for unexpected cell types
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing cell: " + e.getMessage());
                        stockPrice.setPrice(BigDecimal.ZERO); // Default value
                    }

                    // Debug statements
                    System.out.println("Symbol: " + stockPrice.getSymbol());
                    System.out.println("Date: " + stockPrice.getSdate());
                    System.out.println("Price: " + stockPrice.getPrice());

                    // Save or update stock price
                    stockPriceRepository.save(stockPrice);
                }
            }
        }

        workbook.close();
        excelFile.close();
    }

    public void importData() throws IOException {
        String filePath = "C:\\Users\\vanreddy\\Downloads\\ind_nifty50list_50.xlsx";
        importExcelData(filePath);
    }
}

//package com.investment;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Service
//public class ExcelService {
//
//    @Autowired
//    private StockRepository stockRepository;
//
//    public void importExcelData(String filePath) throws IOException {
//        FileInputStream excelFile = new FileInputStream(filePath);
//        Workbook workbook = new XSSFWorkbook(excelFile);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rows = sheet.iterator();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//
//        // Read header row to get dates
//        Row headerRow = sheet.getRow(0);
//        List<Date> dates = new ArrayList<>();
//        for (int i = 5; i < headerRow.getLastCellNum() - 4; i++) { // Exclude the last 4 columns
//            Cell dateCell = headerRow.getCell(i);
//            if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
//                try {
//                    dates.add(dateFormat.parse(dateCell.getStringCellValue()));
//                } catch (Exception e) {
//                    // Handle parsing error
//                }
//            }
//        }
//
//        while (rows.hasNext()) {
//            Row currentRow = rows.next();
//            if (currentRow.getRowNum() == 0) {
//                continue; // Skip header row
//            }
//
//            Stock stock = new Stock();
//            stock.setCompanyName(currentRow.getCell(0).getStringCellValue());
//            stock.setIndustry(currentRow.getCell(1).getStringCellValue());
//            stock.setSymbol(currentRow.getCell(2).getStringCellValue());
//            stock.setSeries(currentRow.getCell(3).getStringCellValue());
//            stock.setIsinCode(currentRow.getCell(4).getStringCellValue());
//
//            // Read prices
//            List<BigDecimal> prices = new ArrayList<>();
//            for (int i = 5; i < currentRow.getLastCellNum() - 4; i++) { // Exclude the last 4 columns
//                Cell cell = currentRow.getCell(i);
//                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
//                    prices.add(BigDecimal.valueOf(cell.getNumericCellValue()));
//                }
//            }
//
//            // Read highest, lowest, average prices, and performance from the last 4 columns
//            stock.setHighestPrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 4).getNumericCellValue()));
//            stock.setLowestPrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 3).getNumericCellValue()));
//            stock.setAveragePrice(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 2).getNumericCellValue()));
//            stock.setPercentageChange(BigDecimal.valueOf(currentRow.getCell(currentRow.getLastCellNum() - 1).getNumericCellValue()));
//
//            // Set the closing price as the last price in the prices list
//            if (!prices.isEmpty()) {
//                stock.setClosingPrice(prices.get(prices.size() - 1));
//            }
//
//            // Set the date (assuming the last date is the closing date)
//            stock.setDate(dates.get(dates.size() - 1));
//
//            stockRepository.save(stock);
//        }
//
//        workbook.close();
//        excelFile.close();
//    }
//}
//
