package umn.ac.id.balapor_kawanua.AdminArtikel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.AdminAllUser.AdminAllUserActivity;
import umn.ac.id.balapor_kawanua.AdminAllUser.AdminDetailUserActivity;
import umn.ac.id.balapor_kawanua.AdminInstansi.AdminInstansiActivity;
import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.MyProfile.ArtikelAdapter;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Article;

public class AdminArtikelActivity extends AppCompatActivity {

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    StorageReference mStorage;

    RecyclerView rvAllArticleAdmin;

    AdminArtikelAdapter mArtikelAdapter;
    ArrayList<Article> mArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_artikel);

        rvAllArticleAdmin = findViewById(R.id.rvAllArticleAdmin);
        rvAllArticleAdmin.setLayoutManager(new LinearLayoutManager(this));

        mArtikel = new ArrayList<>();
        mArtikelAdapter = new AdminArtikelAdapter(this, mArtikel);
        rvAllArticleAdmin.setAdapter(mArtikelAdapter);

        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("artikel");

        Query query = dbReference.orderByChild("publisher");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Article article = dataSnapshot.getValue(Article.class);
                    mArtikel.add(article);
                }
                mArtikelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

}