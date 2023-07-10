package com.example.foodorderapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.model.Messages;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{
    private List<CentreSP> listCentreSP;
    private Context context;
    private MessagesAdapter.ICLickItemMesseageListener messeageListener;
    public interface ICLickItemMesseageListener{
        void onClickItemMesseage(int position);
    }

    public MessagesAdapter(List<CentreSP> listCentreSP, Context context, MessagesAdapter.ICLickItemMesseageListener messeageListener) {
        this.listCentreSP = listCentreSP;
        this.context = context;
        this.messeageListener = messeageListener;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        MessagesAdapter.MessagesViewHolder viewHolder = new MessagesAdapter.MessagesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CentreSP centreSP = listCentreSP.get(position);
        if(centreSP == null){
            return;
        }

        if(centreSP.getEmailReceiver() == null || centreSP.getEmailReceiver().isEmpty() || centreSP.getEmailReceiver() =="null"){
            holder.tvNameReceiver.setText("Trung tâm hỗ trợ");
        }else {
            holder.tvNameReceiver.setText(centreSP.getEmailReceiver());
        }

        holder.tvMesReceiver.setText("Aa");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messeageListener.onClickItemMesseage(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(listCentreSP != null){
            return listCentreSP.size();
        }
        return 0;
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgReceiver;
        private TextView tvNameReceiver, tvMesReceiver;
        private LinearLayout linearLayout;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgReceiver = itemView.findViewById(R.id.imgRecriver);
            tvMesReceiver = itemView.findViewById(R.id.messageReciver);
            tvNameReceiver = itemView.findViewById(R.id.nameReceiver);
            linearLayout = itemView.findViewById(R.id.lncentresp);
        }
    }
    public CentreSP GetCentreSP(int position) {
        List<CentreSP> list = this.GetListCentreSP();
        return list.get(position);
    }

    private List<CentreSP> GetListCentreSP() {
        return this.listCentreSP;
    }
}
