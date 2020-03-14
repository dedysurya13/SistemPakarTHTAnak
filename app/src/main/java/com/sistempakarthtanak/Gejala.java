package com.sistempakarthtanak;

//objek gejala digunakan pada saat diagnosa
public class Gejala {

    private String namaGejala = null; //nama gejala
    private boolean selected = false; //gejala diceklis / tidak

    public Gejala(String namaGejala, boolean selected) {
        super();
        this.namaGejala = namaGejala;
        this.selected = selected;
    }

    public String getNamaGejala() {
        return namaGejala;
    }
    public void setNamaGejala(String namaGejala) {
        this.namaGejala = namaGejala;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
