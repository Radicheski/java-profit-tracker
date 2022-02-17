package me.radicheski.financebackend;

public enum BondType {

    LFT(1L, "LFT"),
    LTN(2L, "LTN"),
    NTN_B(3L, "NTN-B"),
    NTN_C(4L, "NTN-C"),
    NTN_B_PRINCIPAL(5L, "NTN-C Principal"),
    NTN_F(6L, "NTN-F");

    private Long id;
    private String value;

    BondType(Long id, String name) {
        this.id = id;
        this.value = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
