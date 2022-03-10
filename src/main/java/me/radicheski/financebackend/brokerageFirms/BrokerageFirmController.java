package me.radicheski.financebackend.brokerageFirms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrokerageFirmController {

    private BrokerageFirmService service;

    @Autowired
    public void setService(BrokerageFirmService service) {
        this.service = service;
    }

    @GetMapping(value = "/brokerage-firms")
    @ResponseBody
    ResponseEntity<List<BrokerageFirm>> getAll() {
        List<BrokerageFirm> firms = this.service.getAll();

        if (firms.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(firms, HttpStatus.OK);
    }

    @GetMapping(value = "/brokerage-firms/query")
    @ResponseBody
    ResponseEntity<List<BrokerageFirm>> query(@RequestParam(value = "s", required = false) String string) {
        List<BrokerageFirm> firms = this.service.getAllContaining(string);

        if (firms.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(firms, HttpStatus.OK);
    }

}
