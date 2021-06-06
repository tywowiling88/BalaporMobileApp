package umn.ac.id.balapor_kawanua.model;

public class Instansi {
    String namaInstansi, nomorTelepon, alamatInstansi,
            kecamatanInstansi, kotaInstansi, provinsiInstansi,
            kodePos, idInstansi;

    public Instansi(){
    }

    public Instansi(String namaInstansi, String nomorTelepon, String alamatInstansi,
                      String kecamatanInstansi, String kotaInstansi, String provinsiInstansi,
                      String kodePos, String idInstansi) {
        this.namaInstansi = namaInstansi;
        this.nomorTelepon = nomorTelepon;
        this.alamatInstansi = alamatInstansi;
        this.kecamatanInstansi = kecamatanInstansi;
        this.kotaInstansi = kotaInstansi;
        this.provinsiInstansi = provinsiInstansi;
        this.kodePos = kodePos;
        this.idInstansi = idInstansi;
    }

    public String getNamaInstansi() {
        return namaInstansi;
    }

    public void setNamaInstansi(String namaInstansi) {
        this.namaInstansi = namaInstansi;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getAlamatInstansi() {
        return alamatInstansi;
    }

    public void setAlamatInstansi(String alamatInstansi) {
        this.alamatInstansi = alamatInstansi;
    }

    public String getKecamatanInstansi() {
        return kecamatanInstansi;
    }

    public void setKecamatanInstansi(String kecamatanInstansi) {
        this.kecamatanInstansi = kecamatanInstansi;
    }

    public String getKotaInstansi() {
        return kotaInstansi;
    }

    public void setKotaInstansi(String kotaInstansi) {
        this.kotaInstansi = kotaInstansi;
    }

    public String getProvinsiInstansi() {
        return provinsiInstansi;
    }

    public void setProvinsiInstansi(String provinsiInstansi) {
        this.provinsiInstansi = provinsiInstansi;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getIdInstansi() {
        return idInstansi;
    }

    public void setIdInstansi(String idInstansi) {
        this.idInstansi = idInstansi;
    }
}
