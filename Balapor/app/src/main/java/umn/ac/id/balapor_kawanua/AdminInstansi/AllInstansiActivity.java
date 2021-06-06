package umn.ac.id.balapor_kawanua.AdminInstansi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Instansi;

public class AllInstansiActivity extends AppCompatActivity implements AllInstansiKepolisianAdapter.OnUserListener {

    RecyclerView rvInstansi;
    TextView tvJudul;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    ArrayList<Instansi> mInstansi;
    AllInstansiKepolisianAdapter mAllInstansiKepolisianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_instansi);

        rvInstansi = findViewById(R.id.rvInstansi);
        tvJudul = findViewById(R.id.tvJudul);

        dbRootNode = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");

        tvJudul.setText("Instansi " + key);

        dbReference = dbRootNode.getReference("instansi").child(key);

        rvInstansi.setLayoutManager(new LinearLayoutManager(this));

        mInstansi = new ArrayList<>();
        mAllInstansiKepolisianAdapter = new AllInstansiKepolisianAdapter(this, mInstansi, this);
        rvInstansi.setAdapter(mAllInstansiKepolisianAdapter);

        Query query = dbReference.orderByChild("namaInstansi");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Instansi instansi = dataSnapshot.getValue(Instansi.class);
                    mInstansi.add(instansi);
                }
                mAllInstansiKepolisianAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void back(View view) {
        Intent back = new Intent(this, AdminInstansiActivity.class);
        startActivity(back);
    }

    public void tambahInstansi(View view) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");

        Intent tambahInstansi = new Intent(this, TambahInstansiActivity.class);

        tambahInstansi.putExtra("Instansi", key);
        startActivity(tambahInstansi);
    }

    @Override
    public void onUserClick(int position) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("Instansi");

        String alamatInstansi = mInstansi.get(position).getAlamatInstansi();
        String idInstansi = mInstansi.get(position).getIdInstansi();
        String kecamatanInstansi = mInstansi.get(position).getKecamatanInstansi();
        String kodePos = mInstansi.get(position).getKodePos();
        String kotaInstansi = mInstansi.get(position).getKotaInstansi();
        String provinsiInstasi = mInstansi.get(position).getProvinsiInstansi();
        String namaInstansi = mInstansi.get(position).getNamaInstansi();
        String nomorTelepon = mInstansi.get(position).getNomorTelepon();

        Intent detailInstansi = new Intent(AllInstansiActivity.this, DetailInstansiActivity.class);
        detailInstansi.putExtra("alamat", alamatInstansi);
        detailInstansi.putExtra("id", idInstansi);
        detailInstansi.putExtra("kecamatan", kecamatanInstansi);
        detailInstansi.putExtra("kodePos", kodePos);
        detailInstansi.putExtra("kota", kotaInstansi);
        detailInstansi.putExtra("provinsi", provinsiInstasi);
        detailInstansi.putExtra("nama", namaInstansi);
        detailInstansi.putExtra("nomor", nomorTelepon);
        detailInstansi.putExtra("Instansi", key);

        startActivity(detailInstansi);
    }
}