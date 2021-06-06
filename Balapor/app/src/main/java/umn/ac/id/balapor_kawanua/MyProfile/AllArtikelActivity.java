package umn.ac.id.balapor_kawanua.MyProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.ArticlePage.AddArticleActivity;
import umn.ac.id.balapor_kawanua.HomePage.HomePage;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Article;

public class AllArtikelActivity extends AppCompatActivity {

    ImageView ivHomePage, ivAddArticle, ivMyProfile;
    TextView tvNamaUser;

    ImageView ivProfileDetail;

    RecyclerView rvAllArticle;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    ArtikelAdapter mArtikelAdapter;
    ArrayList<Article> mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_artikel);

        ivProfileDetail = findViewById(R.id.ivProfileDetail);
        ivHomePage = findViewById(R.id.ivHomePage);
        ivAddArticle = findViewById(R.id.ivAddArticle);
        ivMyProfile = findViewById(R.id.ivMyProfile);
        tvNamaUser = findViewById(R.id.tvNamaUser);

        rvAllArticle = findViewById(R.id.rvAllArticle);

        ivProfileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileDetail = new Intent(AllArtikelActivity.this, ProfileDetailActivity.class);
                startActivity(ProfileDetail);
            }
        });

        ivHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(AllArtikelActivity.this, HomePage.class);
                startActivity(homePage);
            }
        });

        ivAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArticle = new Intent(AllArtikelActivity.this, AddArticleActivity.class);
                startActivity(addArticle);
            }
        });

        ivMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfile = new Intent(AllArtikelActivity.this, AllArtikelActivity.class);
                startActivity(myProfile);
            }
        });

        rvAllArticle.setLayoutManager(new LinearLayoutManager(this));
        mArticle = new ArrayList<>();
        mArtikelAdapter = new ArtikelAdapter(this, mArticle);
        rvAllArticle.setAdapter(mArtikelAdapter);

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("artikel");

        String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ReadDataFromFirebase(mUid);
    }

    /*
    Read Data With Filtering
    */
    private void ReadDataFromFirebase(String mUid) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Article artikel = dataSnapshot.getValue(Article.class);
                    if (artikel.getPublisher().equals(mUid)){
                        mArticle.add(artikel);
                    }
                    if (artikel == null){
                        Toast.makeText(AllArtikelActivity.this, "You Havent Post Anything", Toast.LENGTH_SHORT).show();
                    }
                }
                mArtikelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setTv(mUid);
    }

    private void setTv(String mUid) {
        dbReference = dbRootNode.getReference("users").child(mUid).child("namaLengkap");
        dbReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    String nama = task.getResult().getValue().toString();
                    tvNamaUser.setText(nama);
                }
            }
        });
    }
}