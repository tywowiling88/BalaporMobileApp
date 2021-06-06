package umn.ac.id.balapor_kawanua.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import umn.ac.id.balapor_kawanua.Login.LoginActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.User;

public class RegisterActivity extends AppCompatActivity {

    ProgressBar pbRegisterComplete;
    TextInputLayout etPassword;
    EditText etNamaLengkap, etJenisKelamin, etNomorHP, etEmail;
    TextView tvErrorNamaLengkap, tvErrorJenisKelamin, tvErrorNomorHP, tvErrorEmail, tvErrorPassword;
    Button btnRegister;

//    Realtime DB
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

//    User Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);
        etNomorHP = findViewById(R.id.etNomorHP);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvErrorNamaLengkap = findViewById(R.id.tvErrorNamaLengkap);
        tvErrorJenisKelamin = findViewById(R.id.tvErrorJenisKelamin);
        tvErrorNomorHP = findViewById(R.id.tvErrorNomorTelepon);
        tvErrorEmail = findViewById(R.id.tvErrorEmail);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);

        pbRegisterComplete = findViewById(R.id.pbRegisterComplete);
        btnRegister = findViewById(R.id.btnRegister);

    }

    /*
    Start Notification Method
    */

    private void checkBuildVersion(String notificationID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationID, "Start Application", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void notification(String notificationID, String title, String message) {
        Intent browseIntent = new Intent(Intent.ACTION_VIEW);
        browseIntent.setData(Uri.parse("http://gmail.com"));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(browseIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this, notificationID);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setContentIntent(resultPendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setStyle(new NotificationCompat.BigTextStyle());


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(RegisterActivity.this);
        managerCompat.notify(1, builder.build());
    }

    /*
    End Notification Method
    */

    /*
    Intent For Other Activity
    */
    public void signUpActivity(View view) {
        Intent signUpPage = new Intent(this, RegisterActivity.class);
        startActivity(signUpPage);
    }

    public void loginActivity(View view) {
        Intent loginPage = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginPage);
    }

    public void buttonRegisterClicked(View view) {
        if (!ValidateNamaLengkap() | !ValidateJenisKelamin() | !ValidateNomorTelepon() |
                !ValidateEmail() | !ValidatePassword()) {
            return;
        }
        else {

            pbRegisterComplete.setVisibility(View.VISIBLE);

            dbRootNode = FirebaseDatabase.getInstance();
            dbReference = dbRootNode.getReference("users");

            mAuth = FirebaseAuth.getInstance();

            //Get Input From Form
            String namaLengkap = etNamaLengkap.getText().toString();
            String jenisKelamin = etJenisKelamin.getText().toString().toLowerCase();
            String nomorHP = etNomorHP.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getEditText().getText().toString();

            /*
            Get Date When Finish Register
            */
            Date todayTime = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(todayTime);

            String role = "Customer";
            String isLoggedIn = "False";
            String indonesiaCode = "+62";
            String HandPhone = indonesiaCode + nomorHP;

            /*
            Create Email And Password User Authentication On Firebase
            */
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String uidAcc = mAuth.getUid();

                        /*Input To FireBase, base on User model that i create*/
                        User user = new User(namaLengkap, jenisKelamin, HandPhone, email, password, formattedDate, role, isLoggedIn, uidAcc);
                        dbReference.child(mAuth.getCurrentUser().getUid()).setValue(user);
                        Toast.makeText(RegisterActivity.this, "Akun Berhasil Dibuat, Verifikasi Menggunakan Email Terlebih Dahulu Sebelum Menggunakan Akun", Toast.LENGTH_LONG).show();

                        FirebaseUser userAccount = mAuth.getCurrentUser();
                        userAccount.sendEmailVerification();

                        pbRegisterComplete.setVisibility(View.INVISIBLE);

                        String notificationID = "Register Success";
                        String title = "Registrasi Akun Berhasil";
                        String message = "Link Verifikasi Telah Dikirim Ke Email Anda, Harap Lakukan Verifikasi Terlebih Dahulu";

                        Intent succesRegister = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(succesRegister);

                        checkBuildVersion(notificationID);
                        notification(notificationID, title, message);
                    } else{
                        Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        tvErrorEmail.setVisibility(View.VISIBLE);
                        tvErrorEmail.setText("Email Sudah Terdaftar, Gunakan Email Yang Lain");
                        pbRegisterComplete.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    /*
    START INPUT VALIDATION
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

    private Boolean ValidateJenisKelamin() {
        String valJenisKelamin = etJenisKelamin.getText().toString().toLowerCase();

        if (valJenisKelamin.isEmpty()){
            tvErrorJenisKelamin.setVisibility(View.VISIBLE);
            tvErrorJenisKelamin.setText("Jenis Kelamin Tidak Boleh Kosong");
            return false;
        }
        else if (valJenisKelamin.contentEquals("pria") || valJenisKelamin.contentEquals("wanita")) {
            tvErrorJenisKelamin.setVisibility(View.INVISIBLE);
            return true;
        }
        else {
            tvErrorJenisKelamin.setVisibility(View.VISIBLE);
            tvErrorJenisKelamin.setText("Jenis Kelamin Hanya Boleh Diisi Pria/Wanita");
            return false;
        }
    }

    private Boolean ValidateNomorTelepon() {
        String valNomorHP = etNomorHP.getText().toString();
        String NomorPattern = "[0-9]+"; // [0-9]+ Int Only

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
        String valPassword = etPassword.getEditText().getText().toString();
        String regexPassword = "[!@#$%^&*A-Za-z0-9]+";

        if (valPassword.isEmpty()){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Kata Sandi Tidak Boleh Kosong");
            return false;
        } else if (valPassword.length() < 4){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Kata Sandi Harus Lebih Dari 4");
            return false;
        }
        else if (valPassword.length() >25){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Panjang Kata Sandi Tidak Boleh Lebih Dari 25");
            return false;
        }
        else if (!valPassword.matches(regexPassword))
        {
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Kata Sandi Boleh Berisi Huruf, Angka, Simbol, dan tidak boleh ada spasi");
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

}