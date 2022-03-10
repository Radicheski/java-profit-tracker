package me.radicheski.financebackend.brokerageFirms;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("brokerageFirms")
public class BrokerageFirm {

    @Id
    private UUID id;
    @Indexed(unique = true)
    private String name;

    public UUID getId() {
        return this.id;
    }

    public BrokerageFirm() {}

    public BrokerageFirm(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
