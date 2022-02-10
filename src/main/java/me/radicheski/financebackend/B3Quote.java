package me.radicheski.financebackend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "cotacoes")
public class B3Quote {

    private int tp_reg;
    private LocalDate dt_pregao;
    private int cd_bdi;
    @Id
    private String cd_acao;
    private int tp_merc;
    private String nm_empresa_rdz;
    private String especi;
    private Integer prazot;
    private String moeda_ref;
    private BigDecimal vl_abertura;
    private BigDecimal vl_maximo;
    private BigDecimal vl_minimo;
    private BigDecimal vl_medio;
    private BigDecimal vl_fechamento;
    private BigDecimal vl_mlh_oft_compra;
    private BigDecimal vl_mlh_oft_venda;
    private int vl_ttl_neg;
    private long qt_tit_neg;
    private BigDecimal vl_volume;
    private BigDecimal vl_exec_opc;
    private int in_opc;
    private LocalDate dt_vnct_opc;
    private int ft_cotacao;
    private BigDecimal vl_exec_moeda_corrente;
    private String cd_isin;
    private int numeroDistribuicao;

    void setTp_reg(int tp_reg) {
        this.tp_reg = tp_reg;
    }

    public int getTp_reg() {
        return this.tp_reg;
    }

    void setDt_pregao(LocalDate dt_pregao) {
        this.dt_pregao = dt_pregao;
    }

    public LocalDate getDt_pregao() {
        return this.dt_pregao;
    }

    void setCd_bdi(int cd_bdi) {
        this.cd_bdi = cd_bdi;
    }

    public int getCd_bdi() {
        return this.cd_bdi;
    }

    void setCd_acao(String cd_acao) {
        this.cd_acao = cd_acao;
    }

    public String getCd_acao() {
        return this.cd_acao;
    }

    void setTp_merc(int tp_merc) {
        this.tp_merc = tp_merc;
    }

    public int getTp_merc() {
        return this.tp_merc;
    }

    void setNm_empresa_rdz(String nm_empresa_rdz) {
        this.nm_empresa_rdz = nm_empresa_rdz;
    }

    public String getNm_empresa_rdz() {
        return this.nm_empresa_rdz;
    }

    void setEspeci(String especi) {
        this.especi = especi;
    }

    public String getEspeci() {
        return this.especi;
    }

    void setPrazot(Integer prazot) {
        this.prazot = prazot;
    }

    public Integer getPrazot() {
        return this.prazot;
    }

    void setMoeda_ref(String referenceCurrency) {
        this.moeda_ref = referenceCurrency;
    }

    public String getMoeda_ref() {
        return this.moeda_ref;
    }

    void setVl_abertura(BigDecimal vl_abertura) {
        this.vl_abertura = vl_abertura;
    }

    public BigDecimal getVl_abertura() {
        return this.vl_abertura;
    }

    void setVl_maximo(BigDecimal vl_maximo) {
        this.vl_maximo = vl_maximo;
    }

    public BigDecimal getVl_maximo() {
        return this.vl_maximo;
    }

    void setVl_minimo(BigDecimal vl_minimo) {
        this.vl_minimo = vl_minimo;
    }

    public BigDecimal getVl_minimo() {
        return this.vl_minimo;
    }

    void setVl_medio(BigDecimal vl_medio) {
        this.vl_medio = vl_medio;
    }

    public BigDecimal getVl_medio() {
        return this.vl_medio;
    }

    void setVl_fechamento(BigDecimal vl_fechamento) {
        this.vl_fechamento = vl_fechamento;
    }

    public BigDecimal getVl_fechamento() {
        return this.vl_fechamento;
    }

    void setVl_mlh_oft_compra(BigDecimal vl_mlh_oft_compra) {
        this.vl_mlh_oft_compra = vl_mlh_oft_compra;
    }

    public BigDecimal getVl_mlh_oft_compra() {
        return this.vl_mlh_oft_compra;
    }

    void setVl_mlh_oft_venda(BigDecimal vl_mlh_oft_venda) {
        this.vl_mlh_oft_venda = vl_mlh_oft_venda;
    }

    public BigDecimal getVl_mlh_oft_venda() {
        return this.vl_mlh_oft_venda;
    }

    void setVl_ttl_neg(int vl_ttl_neg) {
        this.vl_ttl_neg = vl_ttl_neg;
    }

    public int getVl_ttl_neg() {
        return this.vl_ttl_neg;
    }

    void setQt_tit_neg(long qt_tit_neg) {
        this.qt_tit_neg = qt_tit_neg;
    }

    public long getQt_tit_neg() {
        return this.qt_tit_neg;
    }

    void setVl_volume(BigDecimal vl_volume) {
        this.vl_volume = vl_volume;
    }

    public BigDecimal getVl_volume() {
        return this.vl_volume;
    }

    void setVl_exec_opc(BigDecimal vl_exec_opc) {
        this.vl_exec_opc = vl_exec_opc;
    }

    public BigDecimal getVl_exec_opc() {
        return this.vl_exec_opc;
    }

    void setIn_opc(int in_opc) {
        this.in_opc = in_opc;
    }

    public int getIn_opc() {
        return this.in_opc;
    }

    void setDt_vnct_opc(LocalDate dt_vnct_opc) {
        this.dt_vnct_opc = dt_vnct_opc;
    }

    public LocalDate getDt_vnct_opc() {
        return this.dt_vnct_opc;
    }

    void setFt_cotacao(int ft_cotacao) {
        this.ft_cotacao = ft_cotacao;
    }

    public int getFt_cotacao() {
        return this.ft_cotacao;
    }

    void setVl_exec_moeda_corrente(BigDecimal vl_exec_moeda_corrente) {
        this.vl_exec_moeda_corrente = vl_exec_moeda_corrente;
    }

    public BigDecimal getVl_exec_moeda_corrente() {
        return this.vl_exec_moeda_corrente;
    }

    void setCd_isin(String cd_isin) {
        this.cd_isin = cd_isin;
    }

    public String getCd_isin() {
        return this.cd_isin;
    }

    void setNumeroDistribuicao(int numeroDistribuicao) {
        this.numeroDistribuicao = numeroDistribuicao;
    }

    public int getNumeroDistribuicao() {
        return this.numeroDistribuicao;
    }

}
