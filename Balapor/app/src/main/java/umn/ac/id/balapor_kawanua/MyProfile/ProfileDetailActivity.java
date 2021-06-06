package umn.ac.id.balapor_kawanua.MyProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.balapor_kawanua.Login.LoginActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.Register.RegisterActivity;
import umn.ac.id.balapor_kawanua.model.User;

public class ProfileDetailActivity extends AppCompatActivity {

    ImageView ivBackToMyProfile;
    EditText etNamaLengkap, etPassword, etJenisKelamin, etNomorHP;
    TextView tvEmail, tvCreatedAt;
    Button btnLogout, btnUpdateProfile, btnDeleteProfile;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    //    Firebase Authentication
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etPassword = findViewById(R.id.etPassword);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);
        etNomorHP = findViewById(R.id.etNomorHP);
        tvEmail = findViewById(R.id.tvEmail);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);


        ivBackToMyProfile = findViewById(R.id.ivBackToMyProfile);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);
        btnLogout = findViewById(R.id.btnLogout);

        etNamaLengkap.setFocusable(false);
        etJenisKelamin.setFocusable(false);
        etNomorHP.setFocusable(false);
        etPassword.setFocusable(false);

        ivBackToMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackToMyProfile = new Intent(ProfileDetailActivity.this, AllArtikelActivity.class);
                startActivity(BackToMyProfile);
            }
        });

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users");

        mAuth = FirebaseAuth.getInstance();

        String userID = mAuth.getCurrentUser().getUid();

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String NamaLengkap = userProfile.getNamaLengkap();
                    String Password = userProfile.getPassword();
                    String JenisKelamin = userProfile.getJenisKelamin();
                    String NomorHP = userProfile.getNomorHP();
                    String Email = userProfile.getEmail();
                    String CreatedAt = userProfile.getCreatedAT();

                    etNamaLengkap.setText(NamaLengkap);
                    etPassword.setText(Password);
                    etJenisKelamin.setText(JenisKelamin);
                    etNomorHP.setText(NomorHP);

                    tvEmail.setText(Email);
                    tvCreatedAt.setText(CreatedAt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        Button Delete Profile Clicked
        */
        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            String notificationID = "Delete Account";
                            String title = "Hapus Akun Berhasil";
                            String message = "Akun telah berhasil dihapus, untuk menggunakan aplikasi lagi harap melakukan regristrasi akun kembali";

                            Intent logout = new Intent(ProfileDetailActivity.this, LoginActivity.class);
                            startActivity(logout);

                            checkBuildVersion(notificationID);
                            notification(notificationID, title, message);

                            Toast.makeText(ProfileDetailActivity.this, "Akun Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /*
        Button Update Profile Click
        */
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaLengkap = etNamaLengkap.getText().toString();
                String nomorHP = etNomorHP.getText().toString();
                String email = tvEmail.getText().toString();
                String password = etPassword.getText().toString();

                Intent updateProfile = new Intent(ProfileDetailActivity.this, UpdateProfileActivity.class);
                updateProfile.putExtra("namaLengkap", namaLengkap);
                updateProfile.putExtra("nomorHP", nomorHP);
                updateProfile.putExtra("email", email);
                updateProfile.putExtra("password", password);

                startActivity(updateProfile);
            }
        });
    }

    /*
    Start Notification Method
    */

    private void checkBuildVersion(String notificationID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationID, "Start Application", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void notification(String notificationID, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfileDetailActivity.this, notificationID);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setStyle(new NotificationCompat.BigTextStyle());

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ProfileDetailActivity.this);
        managerCompat.notify(1, builder.build());
    }

    /*
    End Notification Input
    */

    /*
    Logout Button Clicked
    */
    public void logoutUser(View view) {
        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users");

        mAuth = FirebaseAuth.getInstance();

        dbReference.child(mAuth.getCurrentUser().getUid()).child("isLoggedIn").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    String isLoggedIn = task.getResult().getValue(String.class);
                    if (isLoggedIn.contains("True")){
                        dbReference.child(mAuth.getCurrentUser().getUid()).child("isLoggedIn").setValue("False");
                        mAuth.signOut();

                        String notificationID = "Logout";
                        String title = "Logout Akun Berhasil";
                        String message = "Silahkan melakukan login kembali jika ingin menggunakan aplikasi";

                        Intent logout = new Intent(ProfileDetailActivity.this, LoginActivity.class);
                        startActivity(logout);

                        checkBuildVersion(notificationID);
                        notification(notificationID, title, message);
                    }
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "Something Went Wrong" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}