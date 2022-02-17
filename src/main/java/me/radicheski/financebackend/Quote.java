package me.radicheski.financebackend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "tesouroDireto.cotacoes")
public class Quote {

    @Id
    private String id;

    private Bond bond;
    private LocalDate date;

    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal basePrice;

    Quote(Bond bond) {
        this.bond = bond;
        this.id = this.bond.getId();
    }

    public Bond getBond() {
        return this.bond;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BigDecimal getBuyRate() {
        return this.buyRate;
    }

    public BigDecimal getSellRate() {
        return this.sellRate;
    }

    public BigDecimal getBuyPrice() {
        return this.buyPrice;
    }

    public BigDecimal getSellPrice() {
        return this.sellPrice;
    }

    public BigDecimal getBasePrice() {
        return this.basePrice;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }

    void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "bond=" + bond +
                ", date=" + date +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", basePrice=" + basePrice +
                '}';
    }
}