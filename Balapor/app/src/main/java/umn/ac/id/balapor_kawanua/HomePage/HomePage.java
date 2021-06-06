package umn.ac.id.balapor_kawanua.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.ArticlePage.AddArticleActivity;
import umn.ac.id.balapor_kawanua.MyProfile.AllArtikelActivity;
import umn.ac.id.balapor_kawanua.MyProfile.ProfileDetailActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Instansi;

public class HomePage extends AppCompatActivity {

    ImageView ivHomePage, ivAddArticle, ivMyProfile;
    TextView tvNamaUser;

    RecyclerView rvKepolisian, rvRumahSakit, rvDinasPemerintahan, rvPemadamKebakaran;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    AllInstansiHomeAdapter mAllInstansiHomeAdapter;
    InstansiDinasPemerintahanAdapter mAllInstansiDinasPemerintahan;
    InstansiPemadamKebakaranAdapter mAllinstansiPemadamKebakaran;
    InstansiRumahSakitAdapter mAllInstansiRumahSakit;

    ArrayList<Instansi> mInstansiKepolisian, mInstansiDinasPemerintahan,
            mInstansiPemadamKebakaran, mInstansiRumahSakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ivHomePage = findViewById(R.id.ivHomePage);
        ivAddArticle = findViewById(R.id.ivAddArticle);
        ivMyProfile = findViewById(R.id.ivMyProfile);
        tvNamaUser = findViewById(R.id.tvNamaUser);

        rvKepolisian = findViewById(R.id.rvKepolisian);
        rvRumahSakit = findViewById(R.id.rvRumahSakit);
        rvDinasPemerintahan = findViewById(R.id.rvDinasPemerintahan);
        rvPemadamKebakaran = findViewById(R.id.rvPemadamKebakaran);

        ivHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(HomePage.this, HomePage.class);
                startActivity(homePage);
            }
        });

        ivAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArticle = new Intent(HomePage.this, AddArticleActivity.class);
                startActivity(addArticle);
            }
        });

        ivMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfile = new Intent(HomePage.this, AllArtikelActivity.class);
                startActivity(myProfile);
            }
        });

        instansiKepolisian();
        instansiDinasPemerintahan();
        instansiPemadamKebakaran();
        instansiRumahSakit();
    }

/*
Start Recycler View Method
To get All Data From Firebase
*/
    private void instansiKepolisian() {
        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi").child("kepolisian");

        rvKepolisian.setLayoutManager(new LinearLayoutManager(this));

        mInstansiKepolisian = new ArrayList<>();
        mAllInstansiHomeAdapter = new AllInstansiHomeAdapter(this, mInstansiKepolisian);
        rvKepolisian.setAdapter(mAllInstansiHomeAdapter);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Instansi instansi = dataSnapshot.getValue(Instansi.class);
                    mInstansiKepolisian.add(instansi);
                }
                mAllInstansiHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void instansiDinasPemerintahan() {
        dbRootNode =FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi").child("dinas pemerintahan");

        rvDinasPemerintahan.setLayoutManager(new LinearLayoutManager(this));

        mInstansiDinasPemerintahan = new ArrayList<>();
        mAllInstansiDinasPemerintahan = new InstansiDinasPemerintahanAdapter(this, mInstansiDinasPemerintahan);
        rvDinasPemerintahan.setAdapter(mAllInstansiDinasPemerintahan);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Instansi instansi = dataSnapshot.getValue(Instansi.class);
                    mInstansiDinasPemerintahan.add(instansi);
                }
                mAllInstansiDinasPemerintahan.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void instansiPemadamKebakaran() {
        dbRootNode =FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi").child("pemadam kebakaran");

        rvPemadamKebakaran.setLayoutManager(new LinearLayoutManager(this));

        mInstansiPemadamKebakaran = new ArrayList<>();
        mAllinstansiPemadamKebakaran = new InstansiPemadamKebakaranAdapter(this, mInstansiPemadamKebakaran);
        rvPemadamKebakaran.setAdapter(mAllinstansiPemadamKebakaran);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Instansi instansi = dataSnapshot.getValue(Instansi.class);
                    mInstansiPemadamKebakaran.add(instansi);
                }
                mAllinstansiPemadamKebakaran.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void instansiRumahSakit() {
        dbRootNode =FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("instansi").child("rumah sakit");

        rvRumahSakit.setLayoutManager(new LinearLayoutManager(this));

        mInstansiRumahSakit = new ArrayList<>();
        mAllInstansiRumahSakit = new InstansiRumahSakitAdapter(this, mInstansiRumahSakit);
        rvRumahSakit.setAdapter(mAllInstansiRumahSakit);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Instansi instansi = dataSnapshot.getValue(Instansi.class);
                    mInstansiRumahSakit.add(instansi);
                }
                mAllInstansiRumahSakit.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
End Recycler View Method
To get All Data From Firebase
*/



}