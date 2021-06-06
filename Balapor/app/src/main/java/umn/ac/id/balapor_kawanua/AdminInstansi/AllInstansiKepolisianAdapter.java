package umn.ac.id.balapor_kawanua.AdminInstansi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Instansi;

public class AllInstansiKepolisianAdapter extends RecyclerView.Adapter<AllInstansiKepolisianAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Instansi> mInstansi;
    private OnUserListener mOnUserListener;

    public AllInstansiKepolisianAdapter(Context mContext, ArrayList<Instansi> mInstansi, OnUserListener mOnUserListener) {
        this.mContext = mContext;
        this.mInstansi = mInstansi;
        this.mOnUserListener = mOnUserListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(mContext).inflate(R.layout.all_instansi_item, parent, false);
        return new MyViewHolder(myView, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String alamat = mInstansi.get(position).getAlamatInstansi();
        String kecamatan = mInstansi.get(position).getKecamatanInstansi();
        String kota = mInstansi.get(position).getKotaInstansi();
        String provinsi = mInstansi.get(position).getProvinsiInstansi();

        String fullAlamat = alamat + ", " + kecamatan + ", " + kota + ", " + provinsi;

    holder.tvNamaInstansi.setText(mInstansi.get(position).getNamaInstansi());
    holder.tvNomorTelepon.setText(mInstansi.get(position).getNomorTelepon());
    holder.tvAlamatInstansi.setText(fullAlamat);

    }

    @Override
    public int getItemCount() {
        if (mInstansi != null){
            return mInstansi.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNamaInstansi, tvNomorTelepon, tvAlamatInstansi;
        OnUserListener onUserListener;

        public MyViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            tvNamaInstansi = itemView.findViewById(R.id.tvNamaInstansi);
            tvNomorTelepon = itemView.findViewById(R.id.tvNomorTelepon);
            tvAlamatInstansi = itemView.findViewById(R.id.tvAlamatInstansi);

            this.onUserListener = onUserListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }

    public interface OnUserListener{
       void onUserClick(int position);
    }
}
