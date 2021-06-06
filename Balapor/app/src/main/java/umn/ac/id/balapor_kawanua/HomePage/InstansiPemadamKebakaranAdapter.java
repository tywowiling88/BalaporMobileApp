package umn.ac.id.balapor_kawanua.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Instansi;

public class InstansiPemadamKebakaranAdapter extends RecyclerView.Adapter<InstansiPemadamKebakaranAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Instansi> mInstansi;

    public InstansiPemadamKebakaranAdapter(Context mContext, ArrayList<Instansi> mInstansi) {
        this.mContext = mContext;
        this.mInstansi = mInstansi;
    }

    @NonNull
    @Override
    public InstansiPemadamKebakaranAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(mContext).inflate(R.layout.all_instansi_item_homepage, parent, false);
        return new InstansiPemadamKebakaranAdapter.MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstansiPemadamKebakaranAdapter.MyViewHolder holder, int position) {
        holder.tvNamaInstansi.setText(mInstansi.get(position).getNamaInstansi());
        holder.tvNomorTelepon.setText(mInstansi.get(position).getNomorTelepon());
        holder.tvAalamatInstansi.setText(mInstansi.get(position).getAlamatInstansi());
    }

    @Override
    public int getItemCount() {
        if (mInstansi != null){
            return mInstansi.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaInstansi, tvNomorTelepon, tvAalamatInstansi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaInstansi = itemView.findViewById(R.id.tvNamaInstansi);
            tvNomorTelepon = itemView.findViewById(R.id.tvNomorTelepon);
            tvAalamatInstansi = itemView.findViewById(R.id.tvAlamatInstansi);
        }
    }

}
