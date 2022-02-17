package me.radicheski.financebackend;

import org.springframework.data.mongodb.repository.MongoRepository;

interface BondRepository extends MongoRepository<Bond, String> {
}
