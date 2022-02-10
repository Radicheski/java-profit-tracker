package me.radicheski.financebackend;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class B3Quotes {

    static final Logger LOGGER = LoggerFactory.getLogger(B3Quotes.class);

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

    @RequestMapping(value = "/b3/{ticker}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String ticker(@PathVariable String ticker) {
        Gson gson = new GsonBuilder()
                .setDateFormat("")
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .serializeNulls()
                .create();
        Optional<B3Quote> quote = this.repository.findById(ticker);
        if (quote.isPresent()) {
            return gson.toJson(quote.get());
        } else {
            return gson.toJson(List.of());
        }
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

