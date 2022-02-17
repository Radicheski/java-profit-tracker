package me.radicheski.financebackend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "tesouroDireto.titulos")
public class Bond {

    @Id
    private String id;
    private BondType type;
    private LocalDate maturity;

    Bond(BondType type, LocalDate maturity) {
        this.type = type;
        this.maturity = maturity;
        this.id = this.getName();
    }

    public BondType getType() {
        return this.type;
    }

    public LocalDate getMaturity() {
        return this.maturity;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return "%s %td/%<tm/%<tY".formatted(this.type, this.maturity);
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
