package umn.ac.id.balapor_kawanua.AdminProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.balapor_kawanua.AdminAllUser.AdminAllUserActivity;
import umn.ac.id.balapor_kawanua.AdminArtikel.AdminArtikelActivity;
import umn.ac.id.balapor_kawanua.AdminInstansi.AdminInstansiActivity;
import umn.ac.id.balapor_kawanua.Login.LoginActivity;
import umn.ac.id.balapor_kawanua.MyProfile.ProfileDetailActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.User;

public class AdminProfileActivity extends AppCompatActivity {

    EditText etNamaLengkap, etPassword, etJenisKelamin, etNomorHP;
    TextView tvEmail, tvCreatedAt;
    Button btnLogout;
    ImageView ivHome, ivMyAccount, ivAllAccount;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    //    Firebase Authentication
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etPassword = findViewById(R.id.etPassword);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);
        etNomorHP = findViewById(R.id.etNomorHP);
        tvEmail = findViewById(R.id.tvEmail);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);

        btnLogout = findViewById(R.id.btnLogout);

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users");

        ivHome = findViewById(R.id.ivHome);
        ivAllAccount = findViewById(R.id.ivAllAccount);
        ivMyAccount = findViewById(R.id.ivMyAccount);

        mAuth = FirebaseAuth.getInstance();

        String userID = mAuth.getUid();

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

    }

    public void menuHome(View view) {
        Intent homePage = new Intent(AdminProfileActivity.this, AdminInstansiActivity.class);
        startActivity(homePage);
    }

    public void menuAllUserData(View view) {
        Intent allAccPage = new Intent(AdminProfileActivity.this, AdminAllUserActivity.class);
        startActivity(allAccPage);
    }

    public void menuAdminProfile(View view) {
        Intent myProfilePage = new Intent(AdminProfileActivity.this, AdminProfileActivity.class);
        startActivity(myProfilePage);
    }

    public void articlePage(View view) {
        Intent articlePage = new Intent(this, AdminArtikelActivity.class);
        startActivity(articlePage);
    }

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

                        Intent logout = new Intent(AdminProfileActivity.this, LoginActivity.class);
                        startActivity(logout);
                    }
                } else {
                    Toast.makeText(AdminProfileActivity.this, "Something Went Wrong" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}