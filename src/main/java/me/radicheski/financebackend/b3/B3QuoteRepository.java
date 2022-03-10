package me.radicheski.financebackend.b3;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface B3QuoteRepository extends MongoRepository<B3Quote, String> {}
