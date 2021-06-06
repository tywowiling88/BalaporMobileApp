package umn.ac.id.balapor_kawanua.AdminInstansi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import umn.ac.id.balapor_kawanua.Login.LoginActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Instansi;

public class TambahInstansiActivity extends AppCompatActivity {

    TextView tvInstansi;

    EditText etNamaInstansi, etNomorTelepon, etAlamatInstansi,
            etKecamatanInstansi, etKotaInstansi, etProvinsiInstansi,
            etKodePos;

    TextView tvErrorNamaInstansi, tvErrorNomorTelepon, tvErrorAlamatInstansi,
            tvErrorKecamatanInstansi, tvErrorKotaInstansi, tvErrorProvinsiInstansi,
            tvErrorKodePos;

    ProgressBar pbComplete;

    //    Realtime DB
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_instansi);

        tvInstansi = findViewById(R.id.tvInstansi);

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

        pbComplete = findViewById(R.id.pbComplete);

        changeWelcomeMsg();
    }

    /*
    Get Instansi From Extra
    Change Welcome Message Base On Instansi
    */
    private void changeWelcomeMsg() {
        Intent intent = getIntent();
        String keyInstansi = intent.getStringExtra("Instansi");

        if (!keyInstansi.isEmpty()){
            tvInstansi.setText(keyInstansi);
        } else {
            Toast.makeText(this, "Direct Access Not Allowed", Toast.LENGTH_SHORT).show();
//            Intent dAccess = new Intent(this, LoginActivity.class);
//            startActivity(dAccess);
        }
    }

    /*
    Validating Data From Input
    */
    public void tambahData(View view) {
        if (!ValidateNamaInstansi() | !ValidateNomorTelepon() | !ValidateAlamatInstansi() |
                !ValidateKecamatanInstansi() | !ValidateKotaInstansi() | !ValidateProvinsiInstansi() | !ValidateKodePos()){
            return;
        } else {
            pbComplete.setVisibility(View.VISIBLE);
            insertData();
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

        if (valAlamatInstansi.length() == 0 || valAlamatInstansi.length() > 50){
            tvErrorAlamatInstansi.setVisibility(View.VISIBLE);
            tvErrorAlamatInstansi.setText("Alamat Instansi Tidak Boleh 0 atau Lebih Dari 50");
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
    If Validation Success
    This Method Run
    */
    private void insertData() {
        Intent intent = getIntent();
        String instansiKey = intent.getStringExtra("Instansi").toLowerCase();

        /*Connection to firebase with reference*/
        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi");

        /*Generate Random String For ID*/
        String namaInstansi = etNamaInstansi.getText().toString();
        String nomorTelepon = etNomorTelepon.getText().toString();
        String alamatInstansi = etAlamatInstansi.getText().toString();
        String kecamatanInstansi = etKecamatanInstansi.getText().toString();
        String kotaInstansi = etKotaInstansi.getText().toString();
        String provinsiInstansi = etProvinsiInstansi.getText().toString();
        String kodePos = etKodePos.getText().toString();
        String randomKey = UUID.randomUUID().toString();

        Instansi instansi = new Instansi(namaInstansi, nomorTelepon, alamatInstansi,
                kecamatanInstansi, kotaInstansi, provinsiInstansi, kodePos, randomKey);

        dbReference.child(instansiKey).child(randomKey).setValue(instansi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(TambahInstansiActivity.this, "Data Instansi " + instansiKey + "Berhasil Diinput Ke database", Toast.LENGTH_LONG).show();
                pbComplete.setVisibility(View.INVISIBLE);

                Intent intent = getIntent();
                String key = intent.getStringExtra("Instansi");

                Intent success = new Intent(TambahInstansiActivity.this, AllInstansiActivity.class);
                success.putExtra("Instansi", key);
                startActivity(success);

            } else{
                Toast.makeText(TambahInstansiActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                pbComplete.setVisibility(View.INVISIBLE);

                Intent fail = new Intent(TambahInstansiActivity.this, TambahInstansiActivity.class);
                startActivity(fail);
            }
            }
        });

    }

    public void back(View view) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");

        Intent back = new Intent(TambahInstansiActivity.this, AllInstansiActivity.class);
        back.putExtra("Instansi", key);
        startActivity(back);

    }
}