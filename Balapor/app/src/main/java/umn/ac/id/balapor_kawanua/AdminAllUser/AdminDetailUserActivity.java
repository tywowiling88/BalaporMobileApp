package umn.ac.id.balapor_kawanua.AdminAllUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import umn.ac.id.balapor_kawanua.AdminArtikel.AdminArtikelActivity;
import umn.ac.id.balapor_kawanua.AdminInstansi.AdminInstansiActivity;
import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;

public class AdminDetailUserActivity extends AppCompatActivity {

    TextView tvNamaLengkap, tvEmail, tvNomorHP,
            tvJenisKelamin, tvCreatedAt, tvIsLoggedIn, tvRole;

    Button btnDelete, btnUpdate;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    //    Firebase Authentication
    FirebaseAuth mAuth;

    LinearLayout lnAllUserItem, lnLayoutButton;
    LinearLayout lnAllUserItem2, lnLayoutButton2;

    EditText etNamaLengkap, etNomorHP, etEmail, etPassword, etRole;
    TextView tvErrorNamaLengkap, tvErrorNomorHP, tvErrorEmail, tvErrorPassword, tvErrorRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_user);

        tvNamaLengkap = findViewById(R.id.tvNamaLengkap);
        tvEmail = findViewById(R.id.tvEmail);
        tvNomorHP = findViewById(R.id.tvNomorHP);
        tvJenisKelamin = findViewById(R.id.tvJenisKelamin);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvIsLoggedIn = findViewById(R.id.tvIsLoggedIn);
        tvRole = findViewById(R.id.tvRole);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        lnAllUserItem = findViewById(R.id.lnAllUserItem);
        lnLayoutButton = findViewById(R.id.lnLayoutButton);

        lnAllUserItem2 = findViewById(R.id.lnAllUserItem2);
        lnLayoutButton2 = findViewById(R.id.lnLayoutButton2);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNomorHP = findViewById(R.id.etNomorHP);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRole = findViewById(R.id.etRole);

        tvErrorNamaLengkap = findViewById(R.id.tvErrorNamaLengkap);
        tvErrorNomorHP = findViewById(R.id.tvErrorNomorHP);
        tvErrorEmail = findViewById(R.id.tvErrorEmail);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);
        tvErrorRole = findViewById(R.id.tvErrorRole);

        Intent intent = getIntent();
        String role = intent.getStringExtra("Role");

        if (role.equals("Admin")){
            btnDelete.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }

        SetAllData();

    }

    /*
    Start Menu Naviagation
    */

    public void menuHome(View view) {
        Intent homePage = new Intent(AdminDetailUserActivity.this, AdminInstansiActivity.class);
        startActivity(homePage);
    }

    public void menuAllUserData(View view) {
        Intent allAccPage = new Intent(AdminDetailUserActivity.this, AdminAllUserActivity.class);
        startActivity(allAccPage);
    }

    public void menuAdminProfile(View view) {
        Intent myProfilePage = new Intent(AdminDetailUserActivity.this, AdminProfileActivity.class);
        startActivity(myProfilePage);
    }

    public void articlePage(View view) {
        Intent articlePage = new Intent(this, AdminArtikelActivity.class);
        startActivity(articlePage);
    }

    /*
    End Menu Naviagation
    */

    /*
    Start
    Get All Data
    From Extras
    */
    private void SetAllData() {
        Intent intent = getIntent();
        tvNamaLengkap.setText(intent.getStringExtra("NamaLengkap"));
        tvEmail.setText(intent.getStringExtra("Email"));
        tvNomorHP.setText(intent.getStringExtra("NomorHP"));
        tvJenisKelamin.setText(intent.getStringExtra("JenisKelamin"));
        tvCreatedAt.setText(intent.getStringExtra("CreatedAT"));
        tvIsLoggedIn.setText(intent.getStringExtra("IsLoggedIn"));
        tvRole.setText(intent.getStringExtra("Role"));
    }

    /*
    End
    Get All Data
    From Extras
    */

    public void deleteUser(View view) {
        Intent intent = getIntent();
        String uId = intent.getStringExtra("UserID");

        mAuth = FirebaseAuth.getInstance();

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users").child(uId);

                if (dbReference != null) {
                    dbReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent success = new Intent(AdminDetailUserActivity.this, AdminAllUserActivity.class);
                                startActivity(success);
                                Toast.makeText(AdminDetailUserActivity.this, "Data User Berhasil Dihapus", Toast.LENGTH_LONG).show();
                            } else {
                                Intent fail = new Intent(AdminDetailUserActivity.this, AdminAllUserActivity.class);
                                startActivity(fail);
                                Toast.makeText(AdminDetailUserActivity.this, "Data User Tidak Berhasil Dihapus", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Intent fail = new Intent(AdminDetailUserActivity.this, AdminAllUserActivity.class);
                    startActivity(fail);
                    Toast.makeText(AdminDetailUserActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }

    }

    public void back(View view) {
        Intent back = new Intent(AdminDetailUserActivity.this, AdminAllUserActivity.class);
        startActivity(back);
    }



    public void Update(View view) {
        lnAllUserItem.setVisibility(View.INVISIBLE);
        lnLayoutButton.setVisibility(View.INVISIBLE);

        lnAllUserItem2.setVisibility(View.VISIBLE);
        lnLayoutButton2.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        String namaLengkap = intent.getStringExtra("NamaLengkap");
        String email = intent.getStringExtra("Email");
        String nomorHP = intent.getStringExtra("NomorHP");
        String password = intent.getStringExtra("password");
        String role = intent.getStringExtra("Role");

        SetEt(namaLengkap, nomorHP, email, password, role);
    }

    private void SetEt(String namaLengkap, String nomorHP, String email, String password, String role) {
        etNamaLengkap.setText(namaLengkap);
        etNomorHP.setText(nomorHP);
        etEmail.setText(email);
        etPassword.setText(password);
        etRole.setText(role);
    }

    /*
    IF USER CLICK SAVE BUTTON
    START
    */

    public void SaveData(View view) {
        if (!ValidateNamaLengkap() | !ValidateNomorTelepon() |
                !ValidateEmail() | !ValidatePassword() | !ValidateRole()) {
            return;
        }
        else {
            String namaLengkapET = etNamaLengkap.getText().toString();
            String nomorHPET = etNomorHP.getText().toString();
            String emailET = etEmail.getText().toString();
            String passwordET = etPassword.getText().toString();
            String roleET = etRole.getText().toString();

            Intent intent = getIntent();
            String namaLengkap = intent.getStringExtra("NamaLengkap");
            String email = intent.getStringExtra("Email");
            String nomorHP = intent.getStringExtra("NomorHP");
            String password = intent.getStringExtra("password");
            String uid = intent.getStringExtra("UserID");
            String role = intent.getStringExtra("Role");

            String jenisKelamin = intent.getStringExtra("JenisKelamin");
            String createdAt = intent.getStringExtra("CreatedAT");
            String loggedIn = intent.getStringExtra("IsLoggedIn");

            dbRootNode = FirebaseDatabase.getInstance();
            dbReference = dbRootNode.getReference("users").child(uid);

            if (uid != null){
                Intent success = new Intent(this, AdminDetailUserActivity.class);

                if (!namaLengkap.equals(namaLengkapET)){
                    dbReference.child("namaLengkap").setValue(namaLengkapET);
                    success.putExtra("NamaLengkap", namaLengkapET);
                } else {
                    success.putExtra("NamaLengkap", namaLengkap);
                }

                if (!nomorHP.equals(nomorHPET)){
                    dbReference.child("nomorHP").setValue(nomorHPET);
                    success.putExtra("NomorHP", nomorHPET);
                } else {
                    success.putExtra("NomorHP", nomorHP);
                }

                if (!email.equals(emailET)){
                    dbReference.child("email").setValue(emailET);
                    success.putExtra("Email", emailET);
                } else{
                    success.putExtra("Email", email);
                }

                if (!password.equals(passwordET)){
                    dbReference.child("password").setValue(passwordET);
                    success.putExtra("password", passwordET);
                } else{
                    success.putExtra("password", password);
                }

                if (!role.equals(roleET)){
                    dbReference.child("role").setValue(roleET);
                    success.putExtra("Role", roleET);
                } else {
                    success.putExtra("Role", role);
                }

                success.putExtra("JenisKelamin", jenisKelamin);
                success.putExtra("CreatedAT", createdAt);
                success.putExtra("IsLoggedIn", loggedIn);
//                success.putExtra("Role", role);
                success.putExtra("UserID", uid);
                startActivity(success);
            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }



    /*
    IF USER CLICK SAVE BUTTON
    END
    */

    /*
    START USER VALIDATION
    */

    private Boolean ValidateNamaLengkap() {
        String valNamaLengkap = etNamaLengkap.getText().toString();
        String regexNamaLengkap = "[a-zA-Z ]*[a-zA-Z]";
        if (valNamaLengkap.isEmpty()) {
            tvErrorNamaLengkap.setVisibility(View.VISIBLE);
            tvErrorNamaLengkap.setText("Nama Lengkap Tidak Boleh Kosong");
            return false;
        } else if (valNamaLengkap.length() < 6){
            tvErrorNamaLengkap.setVisibility(View.VISIBLE);
            tvErrorNamaLengkap.setText("Nama Lengkap Harus Lebih Dari 6 Huruf");
            return false;
        }
        else if (valNamaLengkap.length() >= 60) {
            tvErrorNamaLengkap.setVisibility(View.VISIBLE);
            tvErrorNamaLengkap.setText("Nama Lengkap Tidak Boleh Lebih Dari 60 Huruf");
            return false;
        } else if (!valNamaLengkap.matches(regexNamaLengkap)){
            tvErrorNamaLengkap.setVisibility(View.VISIBLE);
            tvErrorNamaLengkap.setText("Nama Lengkap Hanya Boleh Huruf Dan Angka");
            return false;
        }
        else {
            tvErrorNamaLengkap.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateNomorTelepon() {
        String valNomorHP = etNomorHP.getText().toString();
        String NomorPattern = "[+62]*[0-9]+"; // [0-9]+ Int Only

        if (valNomorHP.isEmpty()){
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Nomor HP Tidak Boleh Kosong");
            return false;
        }
        else if (valNomorHP.substring(0, 1).contains("0")){
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Tidak Perlu dimulai dengan 0");
            return false;
        }
        else if (!valNomorHP.matches(NomorPattern)){
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Hanya Boleh Angka");
            return false;
        }
        else if (valNomorHP.length() < 9) {
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Nomor Telpon Harus Lebih Dari 8 Digit");
            return false;
        }
        else if (valNomorHP.length() > 16) {
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Nomor Telpon Tidak Boleh Lebih Dari 16 Digit");
            return false;
        }
        else {
            tvErrorNomorHP.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidateEmail() {
        String valEmail = etEmail.getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (valEmail.isEmpty() || valEmail.length() >= 50) {
            tvErrorEmail.setVisibility(View.VISIBLE);
            tvErrorEmail.setText("Masukan Alamat Email Yang Benar");
            return false;
        }
        else if (!valEmail.matches(emailPattern)){
            tvErrorEmail.setVisibility(View.VISIBLE);
            tvErrorEmail.setText("Masukan Alamat Email Yang Benar");
            return false;
        }
        else {
            tvErrorEmail.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private Boolean ValidatePassword(){
        String valPassword = etPassword.getText().toString();
        String regexPassword = "[!@#$%^&*A-Za-z0-9]+";

        if (valPassword.isEmpty()){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Password Tidak Boleh Kosong");
            return false;
        } else if (valPassword.length() < 4){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Password Harus Lebih Dari 4");
            return false;
        }
        else if (valPassword.length() >25){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Panjang Password Tidak Boleh Lebih Dari 25");
            return false;
        }
        else if (!valPassword.matches(regexPassword))
        {
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Password Boleh Berisi Huruf, Angka, Simbol, dan tidak boleh ada spasi");
            return false;
        }
        else {
            tvErrorPassword.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean ValidateRole() {
        String valRole = etRole.getText().toString();

        if (valRole.isEmpty()){
            tvErrorRole.setVisibility(View.VISIBLE);
            tvErrorRole.setText("Role Tidak Boleh Kosong");
            return false;
        }
        else if (valRole.contentEquals("Admin") || valRole.contentEquals("Customer")) {
            tvErrorRole.setVisibility(View.INVISIBLE);
            return true;
        }
        else {
            tvErrorRole.setVisibility(View.VISIBLE);
            tvErrorRole.setText("Role Hanya Boleh Diisi Admin/Customer");
            return false;
        }
    }
    /*
    START USER VALIDATION
    */
}