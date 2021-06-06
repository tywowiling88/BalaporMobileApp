package umn.ac.id.balapor_kawanua.AdminArtikel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Article;

public class AdminArtikelAdapter extends RecyclerView.Adapter<AdminArtikelAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Article> mArtikel;

    //    Firebase Realtime Database
    private FirebaseDatabase dbRootNode;
    private DatabaseReference dbReference;

    public AdminArtikelAdapter(Context mContext, ArrayList<Article> mArtikel) {
        this.mContext = mContext;
        this.mArtikel = mArtikel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(mContext).inflate(R.layout.all_artikel_item_admin, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.tvDescription.setText(mArtikel.get(position).getDescription());
    holder.tvLocation.setText(mArtikel.get(position).getLocation());

        Picasso
                .get()
                .load(mArtikel.get(position).getPostImage())
                .into(holder.ivFoto);

    /*
    Database
    Connection
    */
        String postID = mArtikel.get(position).getPostId();
        dbRootNode = FirebaseDatabase.getInstance();
        dbReference = dbRootNode.getReference("artikel").child(postID);

    holder.btnProses.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbReference.child("status").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(mContext, "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });
    
    holder.btnBerhasil.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbReference.child("status").setValue("2").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(mContext, "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });
    
    holder.btnGagal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbReference.child("status").setValue("3").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(mContext, "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

    }

    @Override
    public int getItemCount() {
        if (mArtikel != null){
            return mArtikel.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvDescription, tvLocation;
        Button btnProses, btnBerhasil, btnGagal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnProses = itemView.findViewById(R.id.btnProses);
            btnBerhasil = itemView.findViewById(R.id.btnBerhasil);
            btnGagal = itemView.findViewById(R.id.btnGagal);
        }
    }
}
