package umn.ac.id.balapor_kawanua.AdminAllUser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.User;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> myUser;
    private OnUserListener mOnUserListener;

    public AllUserAdapter(Context context, ArrayList<User> myUser, OnUserListener onUserListener) {
        this.context = context;
        this.myUser = myUser;
        this.mOnUserListener = onUserListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.all_user_item, parent, false);
        return new MyViewHolder(myView, mOnUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.tvNamaLengkap.setText(myUser.get(position).getNamaLengkap());
    holder.tvEmail.setText(myUser.get(position).getEmail());
    holder.tvNomorHP.setText(myUser.get(position).getNomorHP());
    }

    @Override
    public int getItemCount() {
        if (myUser != null) {
            return myUser.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNamaLengkap, tvEmail, tvNomorHP;
        OnUserListener onUserListener;

        public MyViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);

            tvNamaLengkap = itemView.findViewById(R.id.tvNamaLengkap);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvNomorHP = itemView.findViewById(R.id.tvNomorHP);

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
