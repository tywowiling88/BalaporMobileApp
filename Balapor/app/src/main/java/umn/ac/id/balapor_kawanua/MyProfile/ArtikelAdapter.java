package umn.ac.id.balapor_kawanua.MyProfile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Article;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Article> mArticle;

    public ArtikelAdapter(Context mContext, ArrayList<Article> mArticle) {
        this.mContext = mContext;
        this.mArticle = mArticle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(mContext).inflate(R.layout.all_article_item, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String status = mArticle.get(position).getStatus();

        Picasso
                .get()
                .load(mArticle.get(position).getPostImage())
                .into(holder.ivFoto);

        holder.tvDescription.setText(mArticle.get(position).getDescription());
        holder.tvLocation.setText(mArticle.get(position).getLocation());

        if (status.contains("0")) {
            holder.tvStatus.setText("Terkirim");
            holder.tvStatus.setTextColor(Color.BLACK);
        } else if (status.contains("1")) {
            holder.tvStatus.setText("Diproses");
            holder.tvStatus.setTextColor(Color.BLUE);
        } else if (status.contains("2")) {
            holder.tvStatus.setText("Diterima");
            holder.tvStatus.setTextColor(Color.GREEN);
        } else {
            holder.tvStatus.setText("Ditolak");
            holder.tvStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        if (mArticle != null) {
            return mArticle.size();
        }
        else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvDescription, tvLocation, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
