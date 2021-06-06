package umn.ac.id.balapor_kawanua.AdminAllUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.AdminArtikel.AdminArtikelActivity;
import umn.ac.id.balapor_kawanua.AdminInstansi.AdminInstansiActivity;
import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.User;

public class AdminAllUserActivity extends AppCompatActivity implements AllUserAdapter.OnUserListener, AdapterView.OnItemSelectedListener {

    RecyclerView rvAllUser;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    AllUserAdapter allUserAdapter;
    ArrayList<User> mUser;

    Spinner spinnerOrderbyInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_user);

        rvAllUser = findViewById(R.id.rvAllUser);

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("users");

        rvAllUser.setLayoutManager(new LinearLayoutManager(this));

        mUser = new ArrayList<>();
        allUserAdapter = new AllUserAdapter(this, mUser, this);
        rvAllUser.setAdapter(allUserAdapter);

//        Spinner
        spinnerOrderbyInUser = findViewById(R.id.spinnerOrderbyInUser);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.orderbyInUser, android.R.layout.preference_category);
        spinnerAdapter.setDropDownViewResource(android.R.layout.preference_category);
        spinnerOrderbyInUser.setAdapter(spinnerAdapter);
        spinnerOrderbyInUser.setOnItemSelectedListener(this);

    }

    public void menuHome(View view) {
        Intent homePage = new Intent(this, AdminInstansiActivity.class);
        startActivity(homePage);
    }

    public void menuAllUserData(View view) {
        Intent allAccPage = new Intent(this, AdminAllUserActivity.class);
        startActivity(allAccPage);
    }

    public void menuAdminProfile(View view) {
        Intent myProfilePage = new Intent(this, AdminProfileActivity.class);
        startActivity(myProfilePage);
    }

    public void articlePage(View view) {
        Intent articlePage = new Intent(this, AdminArtikelActivity.class);
        startActivity(articlePage);
    }

    @Override
    public void onUserClick(int position) {
        Intent detailuser = new Intent(this, AdminDetailUserActivity.class);

        String NamaLengkap = mUser.get(position).getNamaLengkap();
        String createdAT = mUser.get(position).getCreatedAT();
        String email = mUser.get(position).getEmail();
        String jenisKelamin = mUser.get(position).getJenisKelamin();
        String nomorHP = mUser.get(position).getNomorHP();
        String isLoggedIn = mUser.get(position).getIsLoggedIn();
        String role = mUser.get(position).getRole();
        String uid = mUser.get(position).getuID();
        String password = mUser.get(position).getPassword();

        detailuser.putExtra("NamaLengkap", NamaLengkap);
        detailuser.putExtra("CreatedAT", createdAT);
        detailuser.putExtra("Email", email);
        detailuser.putExtra("JenisKelamin", jenisKelamin);
        detailuser.putExtra("NomorHP", nomorHP);
        detailuser.putExtra("IsLoggedIn", isLoggedIn);
        detailuser.putExtra("Role", role);
        detailuser.putExtra("UserID", uid);
        detailuser.putExtra("password", password);

        startActivity(detailuser);
    }

    /*
    When User Choose Item From Spinner
    */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String rule = parent.getItemAtPosition(position).toString();

        if (rule.equals("role")){
            Query query = dbReference.orderByChild(rule).startAt("Admin").endAt("Customer");
            getOrderData(query);
        } else {
            Query query = dbReference.orderByChild(rule);
            getOrderData(query);
        }
    }

    private void getOrderData(Query query) {
        if (mUser != null){
            mUser.clear();
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    mUser.add(user);
                }
                allUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}