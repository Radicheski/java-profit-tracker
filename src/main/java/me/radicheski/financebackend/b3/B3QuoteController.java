package me.radicheski.financebackend.b3;

import com.google.gson.*;
import me.radicheski.financebackend.LocalDateAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Controller
public class B3QuoteController {

    static final Logger LOGGER = LoggerFactory.getLogger(B3QuoteController.class);

    B3Visitor visitor;
    B3QuoteRepository repository;

    @Scheduled(cron = "0 0 4 * * TUE-SAT", zone = "America/Sao_Paulo")
    public void loadData() {
        LOGGER.info("Start downloading new data");
        String urlString = String.format("https://bvmf.bmfbovespa.com.br/InstDados/SerHist/COTAHIST_D%td%<tm%<tY.ZIP", LocalDate.now().minusDays(1));
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI(urlString)).build();
            Path path = Files.createTempFile(Path.of("."), null, null);
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(path));
            if (response.statusCode() == 200) {
                try (FileSystem fs = FileSystems.newFileSystem(response.body())) {
                    for (Path root : fs.getRootDirectories()) {
                        Files.walkFileTree(root, visitor);
                    }
                }
            }
            Files.deleteIfExists(path);
        } catch(Exception e) {
            LOGGER.error("", e);
        }
        LOGGER.info("Finished downloading new data");
    }

    @GetMapping(value = "/b3",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<String>> getAssets() {
        List<String> asset = this.repository.findAll().stream()
                .map(B3Quote::getCd_acao)
                .collect(Collectors.toList());

        if (asset.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @RequestMapping(value = "/b3/update",
            method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update() {
        ForkJoinPool.commonPool().submit(this::loadData);
    }

    @GetMapping(value = "/b3/{ticker}",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<B3Quote> ticker(@PathVariable String ticker) {
        Optional<B3Quote> quote = this.repository.findById(ticker);

        if (quote.isPresent()) return new ResponseEntity<>(quote.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Autowired
    public void setRepository(B3QuoteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setVisitor(B3Visitor visitor) {
        this.visitor = visitor;
    }

}

