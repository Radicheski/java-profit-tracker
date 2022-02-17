package me.radicheski.financebackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Controller
public class QuoteService {

    private Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);

    private QuoteRepository repository;
    private BondFactory factory;

    @Scheduled(cron = "0 0 4 * * MON-FRI", zone = "America/Sao_Paulo")
    public void loadData() {
        LOGGER.info("Start downloading new data");

        List<Downloader> downloaders = getDownloaders();

        downloaders.forEach(downloader -> {
            try {
                Workbook workbook = downloader.getWorkbook();
                if (Objects.nonNull(workbook)) {
                    this.factory.create(downloader.getType(), workbook);
                }
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        });

        LOGGER.info("Finished downloading new data");
    }

    private List<Downloader> getDownloaders() {
        List<Downloader> result = new ArrayList<>();

        List<BondType> types = List.of(BondType.values());
        LocalDate date = LocalDate.now();

        for (BondType type: types) {
            result.add(new Downloader(type, date));
        }

        return result;
    }

    @RequestMapping(value = "/tesouroDireto/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update() {
        ForkJoinPool.commonPool().submit(() -> this.loadData());
    }

    @Autowired
    public void setRepository(QuoteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setFactory(BondFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(value = "/tesouroDireto",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String getAssets() {
        Gson gson = new GsonBuilder()
                .setDateFormat("")
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .serializeNulls()
                .create();
        List<Quote> list1 = this.repository.findAll();
        List<String> list = list1.stream()
                .map(Quote::getBond)
                .map(Bond::getId)
                .collect(Collectors.toList());
        return gson.toJson(list);
    }

}

class Downloader {

    private final String url;

    private BondType type;

    Downloader(BondType type, LocalDate date) {
        this.type = type;
        String baseUrl = "";

        switch (type) {
            case LFT -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/LFT_%<d.xls";
            case LTN -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/LTN_%<d.xls";
            case NTN_B -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/NTN-B_%<d.xls";
            case NTN_C -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/NTN-C_%<d.xls";
            case NTN_B_PRINCIPAL -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/NTN-B_Principal_%<d.xls";
            case NTN_F -> baseUrl = "https://cdn.tesouro.gov.br/sistemas-internos/apex/producao/sistemas/sistd/%d/NTN-F_%<d.xls";
        }

        this.url = String.format(baseUrl, date.getYear());
    }

    public Workbook getWorkbook() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI(this.url)).build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() == 200) {
            return new HSSFWorkbook(response.body());
        }
        return null;
    }

    public BondType getType() {
        return this.type;
    }
}
