package me.radicheski.financebackend;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QuoteFactory {

    final private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

//    private QuoteConsumer consumer = new QuoteConsumer();

    public List<Quote> create(Bond bond, Sheet sheet) throws Exception {

        List<Quote> quotes = new ArrayList<>();

        Iterator<Row> iterator = sheet.rowIterator();

        while (iterator.hasNext()) {
            Row row = iterator.next();
            quotes.add(create(bond, row));
        }

        quotes.removeIf(quote -> quote.getBuyRate() == null);

//        quotes.forEach(this.consumer::accept);

        return quotes;
    }

    public void flush() throws Exception {
//        this.consumer.flush();
    }

    Quote create(Bond bond, Row row) {

        Quote quote = new Quote(bond);

        try {

            row.cellIterator().forEachRemaining(cell -> {

                switch (cell.getCellType()) {

                    case STRING:
                        quote.setDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMAT));
                        break;

                    case NUMERIC:

                        switch (cell.getColumnIndex()) {

                            case 0:
                                quote.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
                                break;

                            case 1:
                                quote.setBuyRate(new BigDecimal(cell.getNumericCellValue()).setScale(4, RoundingMode.HALF_UP));
                                break;

                            case 2:
                                quote.setSellRate(new BigDecimal(cell.getNumericCellValue()).setScale(4, RoundingMode.HALF_UP));
                                break;

                            case 3:
                                quote.setBuyPrice(new BigDecimal(cell.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                                break;

                            case 4:
                                quote.setSellPrice(new BigDecimal(cell.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                                break;

                            case 5:
                                quote.setBasePrice(new BigDecimal(cell.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                                break;
                        }

                }

            });

        }catch (Exception e) {
//            e.printStackTrace();
        }

        return quote;
    }

}