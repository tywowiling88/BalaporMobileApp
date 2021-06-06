package umn.ac.id.balapor_kawanua.AdminInstansi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.balapor_kawanua.AdminAllUser.AdminAllUserActivity;
import umn.ac.id.balapor_kawanua.AdminArtikel.AdminArtikelActivity;
import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;

public class DetailInstansiActivity extends AppCompatActivity {
    TextView tvNamaInstansi, tvNomorTelepon, tvAlamatInstansi,
            tvKecamatanInstansi, tvKotaInstansi, tvProvinsiInstansi, tvKodePos;

    LinearLayout lnAllInstansiItem, lnAllInstansiItem2, lnLayoutButton, lnLayoutButton2;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    EditText etNamaInstansi, etNomorTelepon, etAlamatInstansi,
            etKecamatanInstansi, etKotaInstansi, etProvinsiInstansi, etKodePos;

    TextView tvErrorNamaInstansi, tvErrorNomorTelepon, tvErrorAlamatInstansi,
            tvErrorKecamatanInstansi, tvErrorKotaInstansi, tvErrorProvinsiInstansi,
            tvErrorKodePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instansi);

        tvNamaInstansi = findViewById(R.id.tvNamaInstansi);
        tvNomorTelepon = findViewById(R.id.tvNomorTelepon);
        tvAlamatInstansi = findViewById(R.id.tvAlamatInstansi);
        tvKecamatanInstansi = findViewById(R.id.tvKecamatanInstansi);
        tvKotaInstansi = findViewById(R.id.tvKotaInstansi);
        tvProvinsiInstansi = findViewById(R.id.tvProvinsiInstansi);
        tvKodePos = findViewById(R.id.tvKodePos);

        lnAllInstansiItem = findViewById(R.id.lnAllInstansiItem);
        lnAllInstansiItem2 = findViewById(R.id.lnAllInstansiItem2);
        lnLayoutButton = findViewById(R.id.lnLayoutButton);
        lnLayoutButton2 = findViewById(R.id.lnLayoutButton2);

        etNamaInstansi = findViewById(R.id.etNamaInstansi);
        etNomorTelepon = findViewById(R.id.etNomorTelepon);
        etAlamatInstansi = findViewById(R.id.etAlamatInstansi);
        etKecamatanInstansi = findViewById(R.id.etKecamatanInstansi);
        etKotaInstansi = findViewById(R.id.etKotaInstansi);
        etProvinsiInstansi = findViewById(R.id.etProvinsiInstansi);
        etKodePos = findViewById(R.id.etKodePos);

        tvErrorNamaInstansi = findViewById(R.id.tvErrorNamaInstansi);
        tvErrorNomorTelepon = findViewById(R.id.tvErrorNomorTelepon);
        tvErrorAlamatInstansi = findViewById(R.id.tvErrorAlamatInstansi);
        tvErrorKecamatanInstansi = findViewById(R.id.tvErrorKecamatanInstansi);
        tvErrorKotaInstansi = findViewById(R.id.tvErrorKotaInstansi);
        tvErrorProvinsiInstansi = findViewById(R.id.tvErrorProvinsiInstansi);
        tvErrorKodePos = findViewById(R.id.tvErrorKodePos);

//        Get data from Extra
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");
        String namaInstansi = intent.getStringExtra("nama");
        String nomorTelepon = intent.getStringExtra("nomor");
        String alamatInstansi = intent.getStringExtra("alamat");
        String kecamatanInstansi = intent.getStringExtra("kecamatan");
        String kotaInstansi = intent.getStringExtra("kota");
        String provinsiInstasi = intent.getStringExtra("provinsi");
        String kodePos = intent.getStringExtra("kodePos");

        setTv(namaInstansi, nomorTelepon, alamatInstansi,
                kecamatanInstansi, kotaInstansi, provinsiInstasi, kodePos);
    }

    /*
    Method For Set Text View
    */
    private void setTv(String nama, String nomor, String alamat,
                          String kecamatan, String kota, String provinsi, String kodePos) {
        tvNamaInstansi.setText(nama);
        tvNomorTelepon.setText(nomor);
        tvAlamatInstansi.setText(alamat);
        tvKecamatanInstansi.setText(kecamatan);
        tvKotaInstansi.setText(kota);
        tvProvinsiInstansi.setText(provinsi);
        tvKodePos.setText(kodePos);
    }

    /*
    Method For Set Edit View
    */
    private void setEt(String nama, String nomor, String alamat,
                       String kecamatan, String kota, String provinsi, String kodePos) {
        etNamaInstansi.setText(nama);
        etNomorTelepon.setText(nomor);
        etAlamatInstansi.setText(alamat);
        etKecamatanInstansi.setText(kecamatan);
        etKotaInstansi.setText(kota);
        etProvinsiInstansi.setText(provinsi);
        etKodePos.setText(kodePos);
    }

    /*
    Start Menu Naviagation
    */
    public void menuHome(View view) {
        Intent homePage = new Intent(DetailInstansiActivity.this, AdminInstansiActivity.class);
        startActivity(homePage);
    }

    public void menuAllUserData(View view) {
        Intent allAccPage = new Intent(DetailInstansiActivity.this, AdminAllUserActivity.class);
        startActivity(allAccPage);
    }

    public void menuAdminProfile(View view) {
        Intent myProfilePage = new Intent(DetailInstansiActivity.this, AdminProfileActivity.class);
        startActivity(myProfilePage);
    }

    public void articlePage(View view) {
        Intent articlePage = new Intent(this, AdminArtikelActivity.class);
        startActivity(articlePage);
    }
    /*
    End Menu Naviagation
    */

    public void back(View view) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");

        Intent back = new Intent(this, AllInstansiActivity.class);
        back.putExtra("Instansi", key);
        startActivity(back);
    }

    /*
    End Intent Change Activity
    */

    /*
    Start Delete Instansi
    */
    public void deleteInstansi(View view) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");
        String id = intent.getStringExtra("id");

    dbRootNode = FirebaseDatabase.getInstance();
    dbReference = dbRootNode.getReference("instansi").child(key);

//    check if data from the reference is exist
    if (dbReference.child(id) != null){
        dbReference.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DetailInstansiActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                    Intent success = new Intent(DetailInstansiActivity.this, AllInstansiActivity.class);
                    success.putExtra("Instansi", key);
                    startActivity(success);
                } else {
                    Intent fail = new Intent(DetailInstansiActivity.this, AdminInstansiActivity.class);
                    startActivity(fail);
                    Toast.makeText(DetailInstansiActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } else{
        Intent fail = new Intent(DetailInstansiActivity.this, AdminInstansiActivity.class);
        startActivity(fail);
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }
    }

    /*
    End Delete Instansi
    */

    /*
    When Button Update Click
    */
    public void Update(View view) {
    lnAllInstansiItem.setVisibility(View.INVISIBLE);
    lnAllInstansiItem2.setVisibility(View.VISIBLE);

    lnLayoutButton.setVisibility(View.INVISIBLE);
    lnLayoutButton2.setVisibility(View.VISIBLE);

        //        Get data from Extra
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");
        String namaInstansi = intent.getStringExtra("nama");
        String nomorTelepon = intent.getStringExtra("nomor");
        String alamatInstansi = intent.getStringExtra("alamat");
        String kecamatanInstansi = intent.getStringExtra("kecamatan");
        String kotaInstansi = intent.getStringExtra("kota");
        String provinsiInstasi = intent.getStringExtra("provinsi");
        String kodePos = intent.getStringExtra("kodePos");

        setEt(namaInstansi, nomorTelepon, alamatInstansi,
                kecamatanInstansi, kotaInstansi, provinsiInstasi, kodePos);
    }

    /*
    Validating Data From Input
    */

    public void SaveData(View view) {
        if (!ValidateNamaInstansi() | !ValidateNomorTelepon() | !ValidateAlamatInstansi() |
                !ValidateKecamatanInstansi() | !ValidateKotaInstansi() | !ValidateProvinsiInstansi() | !ValidateKodePos()){
            return;
        } else{
            InsertToFirebase();
        }
    }

    /*
    Start Input Validation
    */

    private Boolean ValidateNamaInstansi(){
        String valNamaInstansi = etNamaInstansi.getText().toString();
        String regexNamaInstansi = "[a-zA-Z ]*[a-zA-Z]";
        if (valNamaInstansi.length() == 0 || valNamaInstansi.length() >= 50){
            tvErrorNamaInstansi.setVisibility(View.VISIBLE);
            tvErrorNamaInstansi.setText("Nama Instansi Tidak Boleh 0 atau Lebih Dari 50");
            return false;
        } else if (!valNamaInstansi.matches(regexNamaInstansi)){
            tvErrorNamaInstansi.setVisibility(View.VISIBLE);
            tvErrorNamaInstansi.setText("Nama Instansi Hanya Boleh Huruf");
            return false;
        }
        else {
            tvErrorNamaInstansi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateNomorTelepon() {
        String valNomorHP = etNomorTelepon.getText().toString();
        String NomorPattern = "[0-9]+"; // [0-9]+ Int Only // Regular Expression

        if (valNomorHP.isEmpty()){
            tvErrorNomorTelepon.setVisibility(View.VISIBLE);
            tvErrorNomorTelepon.setText("Nomor Telpon Tidak Boleh Kosong");
            return false;
        }
        else if (!valNomorHP.matches(NomorPattern)){
            tvErrorNomorTelepon.setVisibility(View.VISIBLE);
            tvErrorNomorTelepon.setText("Nomor Telpon Hanya Boleh Angka");
            return false;
        }
        else if (valNomorHP.length() > 13) {
            tvErrorNomorTelepon.setVisibility(View.VISIBLE);
            tvErrorNomorTelepon.setText("Nomor Telpon Tidak Boleh Lebih Dari 13 Digit");
            return false;
        }
        else {
            tvErrorNomorTelepon.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateAlamatInstansi() {
        String valAlamatInstansi = etAlamatInstansi.getText().toString();
        String regexAlamatInstansi = "[.A-Za-z0-9 ]*[.A-Za-z0-9]" ;

        if (valAlamatInstansi.length() == 0 || valAlamatInstansi.length() > 40){
            tvErrorAlamatInstansi.setVisibility(View.VISIBLE);
            tvErrorAlamatInstansi.setText("Alamat Instansi Tidak Boleh 0 atau Lebih Dari 40");
            return false;
        } else if (!valAlamatInstansi.matches(regexAlamatInstansi)){
            tvErrorAlamatInstansi.setVisibility(View.VISIBLE);
            tvErrorAlamatInstansi.setText("Alamat Instansi Hanya boleh huruf dan angka");
            return false;
        } else {
            tvErrorAlamatInstansi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateKecamatanInstansi() {
        String valKecamatanInstansi = etKecamatanInstansi.getText().toString();
        String regexKecamtanInstansi = "[a-zA-Z ]*[a-zA-Z]*";
        if (valKecamatanInstansi.length() == 0 || valKecamatanInstansi.length() >= 50){
            tvErrorKecamatanInstansi.setVisibility(View.VISIBLE);
            tvErrorKecamatanInstansi.setText("Kecamatan Instansi Tidak Boleh 0 atau Lebih Dari 50");
            return false;
        } else if (!valKecamatanInstansi.matches(regexKecamtanInstansi)){
            tvErrorKecamatanInstansi.setVisibility(View.VISIBLE);
            tvErrorKecamatanInstansi.setText("Kecamtan Instansi Hanya Boleh Huruf");
            return false;
        }
        else {
            tvErrorKecamatanInstansi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateKodePos() {
        String valKodePos = etKodePos.getText().toString();
        String regexKodePos = "[0-9]+"; // [0-9]+ Int Only // Regular Expression

        if (valKodePos.isEmpty()){
            tvErrorKodePos.setVisibility(View.VISIBLE);
            tvErrorKodePos.setText("Kode Pos Tidak Boleh Kosong");
            return false;
        }
        else if (!valKodePos.matches(regexKodePos)){
            tvErrorKodePos.setVisibility(View.VISIBLE);
            tvErrorKodePos.setText("Kode Pos Hanya Boleh Angka");
            return false;
        }
        else if (valKodePos.length() != 5) {
            tvErrorKodePos.setVisibility(View.VISIBLE);
            tvErrorKodePos.setText("Kode Pos Hanya Boleh 5 digit");
            return false;
        }
        else {
            tvErrorKodePos.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateKotaInstansi() {
        String valKotaInstansi = etKotaInstansi.getText().toString();
        String regexKotaInstansi = "[a-zA-Z ]*[a-zA-Z]*";
        if (valKotaInstansi.length() == 0 || valKotaInstansi.length() >= 50){
            tvErrorKotaInstansi.setVisibility(View.VISIBLE);
            tvErrorKotaInstansi.setText("Provinsi Instansi Tidak Boleh 0 atau Lebih Dari 50");
            return false;
        } else if (!valKotaInstansi.matches(regexKotaInstansi)){
            tvErrorKotaInstansi.setVisibility(View.VISIBLE);
            tvErrorKotaInstansi.setText("Provinsi Instansi Hanya Boleh Huruf");
            return false;
        }
        else {
            tvErrorKotaInstansi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateProvinsiInstansi() {
        String valProvinsiInstansi = etProvinsiInstansi.getText().toString();
        String regexProvinsiInstansi = "[a-zA-Z ]*[a-zA-Z]*";
        if (valProvinsiInstansi.length() == 0 || valProvinsiInstansi.length() >= 50){
            tvErrorProvinsiInstansi.setVisibility(View.VISIBLE);
            tvErrorProvinsiInstansi.setText("Provinsi Instansi Tidak Boleh 0 atau Lebih Dari 50");
            return false;
        } else if (!valProvinsiInstansi.matches(regexProvinsiInstansi)){
            tvErrorProvinsiInstansi.setVisibility(View.VISIBLE);
            tvErrorProvinsiInstansi.setText("Provinsi Instansi Hanya Boleh Huruf");
            return false;
        }
        else {
            tvErrorProvinsiInstansi.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    /*
    End Input Validation
    */

    /*
    Start Insert To Firebase
    but First Check If The Value Is The Same
    */

    private void InsertToFirebase() {

        //        Get data from Extra
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");
        String namaInstansi = intent.getStringExtra("nama");
        String nomorTelepon = intent.getStringExtra("nomor");
        String alamatInstansi = intent.getStringExtra("alamat");
        String kecamatanInstansi = intent.getStringExtra("kecamatan");
        String kotaInstansi = intent.getStringExtra("kota");
        String provinsiInstasi = intent.getStringExtra("provinsi");
        String kodePos = intent.getStringExtra("kodePos");
        String idInstansi = intent.getStringExtra("id");

        String inputnamaInstansi = etNamaInstansi.getText().toString();
        String inputnomorTelepon = etNomorTelepon.getText().toString();
        String inputalamatInstansi = etAlamatInstansi.getText().toString();
        String inputkecamatanInstansi = etKecamatanInstansi.getText().toString();
        String inputkotaInstansi = etKotaInstansi.getText().toString();
        String inputprovinsiInstasi = etProvinsiInstansi.getText().toString();
        String inputkodePos = etKodePos.getText().toString();

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi").child(key).child(idInstansi);

        Intent success = new Intent(this, DetailInstansiActivity.class);

        if (!namaInstansi.equals(inputnamaInstansi)){
            dbReference.child("namaInstansi").setValue(inputnamaInstansi);
            success.putExtra("nama", inputnamaInstansi);
        } else {
            success.putExtra("nama", namaInstansi);
        }

        if (!nomorTelepon.equals(inputnomorTelepon)){
            dbReference.child("nomorTelepon").setValue(inputnomorTelepon);
            success.putExtra("nomor", inputnomorTelepon);
        } else {
            success.putExtra("nomor", nomorTelepon);
        }

        if (!alamatInstansi.equals(inputalamatInstansi)){
            dbReference.child("alamatInstansi").setValue(inputalamatInstansi);
            success.putExtra("alamat", inputalamatInstansi);
        } else {
            success.putExtra("alamat", alamatInstansi);
        }

        if (!kecamatanInstansi.equals(inputkecamatanInstansi)){
            dbReference.child("kecamatanInstansi").setValue(inputkecamatanInstansi);
            success.putExtra("kecamatan", inputkecamatanInstansi);
        } else {
            success.putExtra("kecamatan", kecamatanInstansi);
        }

        if (!kotaInstansi.equals(inputkotaInstansi)){
            dbReference.child("kotaInstansi").setValue(inputkotaInstansi);
            success.putExtra("kota", inputkotaInstansi);
        } else {
            success.putExtra("kota", kotaInstansi);
        }

        if (!provinsiInstasi.equals(inputprovinsiInstasi)){
            dbReference.child("provinsiInstansi").setValue(inputprovinsiInstasi);
            success.putExtra("provinsi", inputprovinsiInstasi);
        } else {
            success.putExtra("provinsi", provinsiInstasi);
        }

        if (!kodePos.equals(inputkodePos)){
            dbReference.child("kodePos").setValue(inputkodePos);
            success.putExtra("kodePos", inputkodePos);
        } else {
            success.putExtra("kodePos", kodePos);
        }

        success.putExtra("Instansi", key);
        success.putExtra("id", idInstansi);
        startActivity(success);

    }


}