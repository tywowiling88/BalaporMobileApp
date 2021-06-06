package umn.ac.id.balapor_kawanua.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.ArticlePage.AddArticleActivity;
import umn.ac.id.balapor_kawanua.MyProfile.AllArtikelActivity;
import umn.ac.id.balapor_kawanua.MyProfile.ProfileDetailActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.Register.RegisterActivity;
import umn.ac.id.balapor_kawanua.walkthroughpage.WalkThrough;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    TextInputLayout etPassword;
    TextView tvErrorEmail, tvErrorPassword;
    Button btnLogin;
    ProgressBar pbRegisterComplete;

//    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

//    Firebase Authentication
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextView tvTitle, tvMessage;

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +
                    ".{4,}"+
                    "$");

    @Override
    protected void onStart() {
        super.onStart();

        if (!isConnectedInternet(LoginActivity.this)){
            customDialogInternet(LoginActivity.this);
        } else {
            mUser = FirebaseAuth.getInstance().getCurrentUser();

            if (mUser != null && mUser.isEmailVerified()){

                String mUid = mUser.getUid();

                dbRootNode = FirebaseDatabase.getInstance();
                dbReference = dbRootNode.getReference("users").child(mUid).child("role");

                dbReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){

                            String role = task.getResult().getValue(String.class);

                            /*
                            If Login As Admin
                            */
                            if (role.contains("Admin")){
                                String notificationID = "Already Login";
                                String title = "Selamat Datang Kembali";
                                String message = "Harap tetap ramah dan sopan selama menggunakan applikasi";

                                Intent admin = new Intent(LoginActivity.this, AdminProfileActivity.class);
                                startActivity(admin);

                                checkBuildVersion(notificationID);
                                notification(notificationID, title, message);
                            }
                            /*
                            If Login As Customer
                            */
                            else {
                                String notificationID = "Already Login";
                                String title = "Selamat Datang Kembali";
                                String message = "Harap tetap ramah dan sopan selama menggunakan applikasi";

                                Intent customer = new Intent(LoginActivity.this, AllArtikelActivity.class);
                                startActivity(customer);

                                checkBuildVersion(notificationID);
                                notification(notificationID, title, message);
                            }
                        }
                    }
                });
            }
        }
    }

    /*
    Start Notification Method
    */

    private void checkBuildVersion(String notificationID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationID, "Start Application", NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void notification(String notificationID, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(LoginActivity.this, notificationID);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setStyle(new NotificationCompat.BigTextStyle());

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(LoginActivity.this);
        managerCompat.notify(1, builder.build());
    }

    /*
    End Notification Method
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvErrorEmail = findViewById(R.id.tvErrorEmail);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);

        pbRegisterComplete = findViewById(R.id.pbRegisterComplete);

        btnLogin = findViewById(R.id.btnLogin);

    }

    /*
    Intent For Other Activity
    */

    public void signUpActivity(View view) {
        Intent signUpPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(signUpPage);
    }

    public void loginActivity(View view) {
        Intent loginPage = new Intent(this, LoginActivity.class);
        startActivity(loginPage);
    }

    public void forgetPassword(View view) {
        Intent forgetPassword = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(forgetPassword);
    }

    /*
    Start Input Validation Using Regular Expression
    */

    public void buttonLoginClicked(View view) {
        if (!ValidateEmail() | !ValidatePassword()) {
            return;
        } else {
            isUser();
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

        if (valPassword.isEmpty()){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Kata Sandi Tidak Boleh Kosong");
            return false;
        } else if (valPassword.length() >25){
            tvErrorPassword.setVisibility(View.VISIBLE);
            tvErrorPassword.setText("Panjang Kata Sandi Tidak Boleh Lebih Dari 25");
            return false;
        } else {
            tvErrorPassword.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    /*
    End Input Validation Using Regular Expression
    */

    /*
    Start Check Internet Connection
    */
    private boolean isConnectedInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //        Check and Set Connection Value
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileDataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileDataConn != null && mobileDataConn.isConnected())){
            return true;
        } else{
            return false;
        }
    }
    /*
    End Check Internet Connection
    */

    /*
    Custom dialog if user is not connected to Internet
    */
    private void customDialogInternet(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_messages, null);
        dialog.setView(dialogView);

        dialog.setCancelable(false)
                .setPositiveButton("Hubungkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog.show();
    }

    /*
    Check User If Exist
    Check Email Verification
    Get Email and Password
    */
    private void isUser() {

        pbRegisterComplete.setVisibility(View.VISIBLE);

        String emailEntered = etEmail.getText().toString().trim();
        String passwordEntered = etPassword.getEditText().getText().toString().trim();

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(emailEntered, passwordEntered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user.isEmailVerified()){
                        dbReference.child(mAuth.getCurrentUser().getUid()).child("isLoggedIn").setValue("True");

                        dbReference.child(mAuth.getCurrentUser().getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){

                                    String role = task.getResult().getValue(String.class);

                                    if (role.contains("Admin")){
                                        pbRegisterComplete.setVisibility(View.INVISIBLE);

                                        String notificationID = "Login";
                                        String title = "Selamat Datang " + role;
                                        String message = "Silahkan lakukan sesusai dengan tugas anda";

                                        Intent successLogin = new Intent(LoginActivity.this, AdminProfileActivity.class);
                                        startActivity(successLogin);

                                        checkBuildVersion(notificationID);
                                        notification(notificationID, title, message);
                                    } else {
                                        pbRegisterComplete.setVisibility(View.INVISIBLE);

                                        String notificationID = "Login";
                                        String title = "Selamat Datang";
                                        String message = "Merasa Bebas Untuk Menggunakan Aplikasi Ini, selama tetap sopan dan tidak menyinggung orang lain";

                                        Intent successLogin = new Intent(LoginActivity.this, WalkThrough.class);
                                        startActivity(successLogin);

                                        checkBuildVersion(notificationID);
                                        notification(notificationID, title, message);
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                    pbRegisterComplete.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Harap Verifikasi Email Terlebih dahulu, Link Verifikasi Telah diberikan Di Email", Toast.LENGTH_LONG).show();
                        pbRegisterComplete.setVisibility(View.INVISIBLE);
                    }

                } else {
                    tvErrorEmail.setVisibility(View.VISIBLE);
                    tvErrorEmail.setText("Email Atau Kata Sandi Salah");
                    tvErrorPassword.setVisibility(View.VISIBLE);
                    tvErrorPassword.setText("Email Atau Kata Sandi Salah");
                    pbRegisterComplete.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void DeveloperPage(View view) {
        Intent developerPage = new Intent(this, DeveloperTeamActivity.class);
        startActivity(developerPage);
    }
}