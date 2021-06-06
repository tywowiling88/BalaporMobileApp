package umn.ac.id.balapor_kawanua.MyProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import umn.ac.id.balapor_kawanua.R;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText etNamaLengkap, etNomorHP, etEmail, etPassword;
    TextView tvErrorNamaLengkap, tvErrorNomorHP, tvErrorEmail, tvErrorPassword;

//    Firebase Database Realtime
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

//  Firebase Authentication
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNomorHP = findViewById(R.id.etNomorHP);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvErrorNamaLengkap = findViewById(R.id.tvErrorNamaLengkap);
        tvErrorNomorHP = findViewById(R.id.tvErrorNomorHP);
        tvErrorEmail = findViewById(R.id.tvErrorEmail);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);

        Intent intent = getIntent();
        String namaLengkap = intent.getStringExtra("namaLengkap");
        String nomorHP = intent.getStringExtra("nomorHP");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        etNamaLengkap.setText(namaLengkap);
        etNomorHP.setText(nomorHP);
        etEmail.setText(email);
        etPassword.setText(password);

        etEmail.setFocusable(false);
        etPassword.setFocusable(false);
    }

    public void back(View view) {
        Intent back = new Intent(this, ProfileDetailActivity.class);
        startActivity(back);
    }

    public void SaveData(View view) {
        if (!ValidateNamaLengkap() | !ValidateNomorTelepon() |
                !ValidateEmail() | !ValidatePassword()) {
            return;
        } else{
            Intent intent = getIntent();
            String namaLengkap = intent.getStringExtra("namaLengkap");
            String nomorHP = intent.getStringExtra("nomorHP");
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");

            InsertToDatabase(namaLengkap, nomorHP, email, password);
        }
    }

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
        else if (valNomorHP.length() > 13) {
            tvErrorNomorHP.setVisibility(View.VISIBLE);
            tvErrorNomorHP.setText("Nomor Telpon Tidak Boleh Lebih Dari 13 Digit");
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

    /*
    END USER VALIDATION
    */

    /*
    WHEN USER CLICK SAVE
    */
    private void InsertToDatabase(String namaLengkap, String nomorHP, String email, String password) {
        String inputNamaLengkap = etNamaLengkap.getText().toString();
        String inputNomorHP = etNomorHP.getText().toString();
        String inputEmail = etEmail.getText().toString();
        String inputPassword = etPassword.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = mAuth.getCurrentUser().getUid();

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users").child(uid);

        if (!namaLengkap.equals(inputNamaLengkap)){
            dbReference.child("namaLengkap").setValue(inputNamaLengkap);
        }
        if (!nomorHP.equals(inputNomorHP)){
            dbReference.child("nomorHP").setValue(inputNomorHP);
        }

        if (!email.equals(inputEmail)){
//            firebaseUser.updateEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()){
//                        dbReference.child("email").setValue(inputEmail);
//                        firebaseUser.sendEmailVerification();
//                    } else {
//                        Toast.makeText(UpdateProfileActivity.this, "Something Went Wrong, Your Email Havent Changed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    firebaseUser.updateEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dbReference.child("email").setValue(inputEmail);
                            Toast.makeText(UpdateProfileActivity.this, "Email Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateProfileActivity.this, "Email Tidak Berhasil Diubah", Toast.LENGTH_SHORT).show();;
                        }
                        }
                    });
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();;
                    }
                }
            });
        }

        if (!password.equals(inputPassword)){
            firebaseUser.updatePassword(inputPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        dbReference.child("password").setValue(inputPassword);
                        Toast.makeText(UpdateProfileActivity.this, "Update Password Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "Update Password Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (namaLengkap.equals(inputNamaLengkap) && nomorHP.equals(inputNomorHP) && email.equals(inputEmail) && password.equals(inputPassword)){
            Toast.makeText(this, "You Havent Change Any Data", Toast.LENGTH_SHORT).show();
            Intent success = new Intent(this, ProfileDetailActivity.class);
            startActivity(success);
        } else {
            Intent success = new Intent(this, ProfileDetailActivity.class);
            startActivity(success);
        }

    }
}