package me.radicheski.financebackend.brokerageFirms;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface BrokerageFirmRepository extends MongoRepository<BrokerageFirm, UUID> {

    List<BrokerageFirm> findAllByNameContainingIgnoreCase(String query);

}
