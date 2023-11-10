package com.example.techophile1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter{
    Context context ;
    ArrayList<MsgMod> msgModArray ;
    int SENDER_VIEW_TYPE = 1 ;
    int RECEIVER_VIEW_TYPE = 2 ;

    public MsgAdapter(Context context, ArrayList<MsgMod> msgModArray) {
        this.context = context;
        this.msgModArray = msgModArray;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgMod msgMod = msgModArray.get(position);
        if(holder.getClass() == senderViewHolder.class){
            senderViewHolder viewHolder = (senderViewHolder)holder;
            viewHolder.mssg.setText(msgMod.getMsg());
        }else{
            receiverViewHolder viewHolder = (receiverViewHolder)holder;
            viewHolder.mssg.setText(msgMod.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return msgModArray.size();
    }

    @Override
    public int getItemViewType(int position) {
            if(msgModArray.get(position).getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                return SENDER_VIEW_TYPE ;
            }else{
                return RECEIVER_VIEW_TYPE ;
            }
    }

    class senderViewHolder extends RecyclerView.ViewHolder {
        TextView mssg ;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            mssg = itemView.findViewById(R.id.senderTxt);

        }
    }

    class receiverViewHolder extends RecyclerView.ViewHolder {
        TextView mssg ;
        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            mssg = itemView.findViewById(R.id.receiverTxt);
        }
    }


}
