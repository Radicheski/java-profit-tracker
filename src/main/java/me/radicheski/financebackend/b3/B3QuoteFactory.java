package me.radicheski.financebackend.b3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class B3QuoteFactory {

    B3QuoteRepository repository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public B3Quote getObject(String value) {
        B3Quote quote = new B3Quote();

        quote.setTp_reg(Integer.parseInt(value.substring(0, 2).trim()));
        quote.setDt_pregao(LocalDate.parse(value.substring(2, 10).trim(), B3QuoteFactory.DATE_TIME_FORMATTER));
        quote.setCd_bdi(Integer.parseInt(value.substring(10, 12).trim()));
        quote.setCd_acao(value.substring(12, 24).trim());
        quote.setTp_merc(Integer.parseInt(value.substring(24, 27).trim()));
        quote.setNm_empresa_rdz(value.substring(27, 39).trim());
        quote.setEspeci(value.substring(39, 49).trim());
        try {
            quote.setPrazot(Integer.parseInt(value.substring(49, 52).trim()));
        } catch (NumberFormatException e) {
            //TODO Log exception
        }
        quote.setMoeda_ref(value.substring(52, 56).trim());
        quote.setVl_abertura(new BigDecimal(value.substring(56, 69).trim()).scaleByPowerOfTen(-2));
        quote.setVl_maximo(new BigDecimal(value.substring(69, 82).trim()).scaleByPowerOfTen(-2));
        quote.setVl_minimo(new BigDecimal(value.substring(82, 95).trim()).scaleByPowerOfTen(-2));
        quote.setVl_medio(new BigDecimal(value.substring(95, 108).trim()).scaleByPowerOfTen(-2));
        quote.setVl_fechamento(new BigDecimal(value.substring(108, 121).trim()).scaleByPowerOfTen(-2));
        quote.setVl_mlh_oft_compra(new BigDecimal(value.substring(121, 134).trim()).scaleByPowerOfTen(-2));
        quote.setVl_mlh_oft_venda(new BigDecimal(value.substring(134, 147).trim()).scaleByPowerOfTen(-2));
        quote.setVl_ttl_neg(Integer.parseInt(value.substring(147, 152).trim()));
        quote.setQt_tit_neg(Long.parseLong(value.substring(152, 170).trim()));
        quote.setVl_volume(new BigDecimal(value.substring(170, 188).trim()).scaleByPowerOfTen(-2));
        quote.setVl_exec_opc(new BigDecimal(value.substring(188, 201).trim()).scaleByPowerOfTen(-2));
        quote.setIn_opc(Integer.parseInt(value.substring(201, 202).trim()));
        quote.setDt_vnct_opc(LocalDate.parse(value.substring(202, 210).trim(), B3QuoteFactory.DATE_TIME_FORMATTER));
        quote.setFt_cotacao(Integer.parseInt(value.substring(210, 217).trim()));
        quote.setVl_exec_moeda_corrente(new BigDecimal(value.substring(217, 230).trim()).scaleByPowerOfTen(-6));
        quote.setCd_isin(value.substring(230, 242).trim());
        quote.setNumeroDistribuicao(Integer.parseInt(value.substring(242, 245).trim()));

        this.repository.save(quote);

        return quote;
    }

    @Autowired
    public void setRepository(B3QuoteRepository repository) {
        this.repository = repository;
    }

}
