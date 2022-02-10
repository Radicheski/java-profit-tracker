package me.radicheski.financebackend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface B3QuoteRepository extends MongoRepository<B3Quote, String> {}
