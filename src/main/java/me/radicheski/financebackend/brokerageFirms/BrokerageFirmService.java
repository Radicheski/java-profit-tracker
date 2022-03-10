package me.radicheski.financebackend.brokerageFirms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerageFirmService {

    private BrokerageFirmRepository repository;

    @Autowired
    public void setRepository(BrokerageFirmRepository repository) {
        this.repository = repository;
    }

    List<BrokerageFirm> getAll() {
        return this.repository.findAll();
    }

    List<BrokerageFirm> getAllContaining(String query) {
        return this.repository.findAllByNameContainingIgnoreCase(query);
    }

}
