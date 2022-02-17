package me.radicheski.financebackend;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BondFactory {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");

    private QuoteRepository quoteRepository;
    private BondRepository bondRepository;

    void create(BondType type, Workbook workbook) throws Exception {
        QuoteFactory factory = new QuoteFactory();

        Iterator<Sheet> iterator = workbook.sheetIterator();

        while (iterator.hasNext()) {
            Sheet sheet = iterator.next();
            List<Quote> quotes = new ArrayList<>();
            Bond bond = getBond(type, sheet.getSheetName());
            this.selectBond(bond);
            quotes.addAll(factory.create(bond, sheet));
            LocalDate lastDate = quotes.stream()
                    .map(Quote::getDate)
                    .max(LocalDate::compareTo)
                    .get();
            quotes.removeIf(quote -> quote.getDate().isBefore(lastDate));
            this.quoteRepository.insert(quotes);
        }
    }

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Autowired
    public void setBondRepository(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    private Bond getBond(BondType type, String sheetName) {
        String expiryString = sheetName.substring(sheetName.length() - 6);
        LocalDate expiryDate = LocalDate.parse(expiryString, DATE_FORMAT);

        return new Bond(type, expiryDate);
    }

    private void selectBond(Bond bond) {
        this.bondRepository.insert(bond);
    }

}

