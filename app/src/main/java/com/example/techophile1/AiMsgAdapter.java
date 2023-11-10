package com.example.techophile1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AiMsgAdapter extends RecyclerView.Adapter<AiMsgAdapter.ViewHolder>{
    ArrayList<AiMsgMod> msgModArray ;
    public AiMsgAdapter(ArrayList<AiMsgMod> msgModArray) {
        this.msgModArray = msgModArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ai_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AiMsgMod msgMod = msgModArray.get(position);
        if(msgMod.getSentBy().equals(AiMsgMod.AI)){
            holder.me_rec.setVisibility(View.GONE);
            holder.ai_rec.setVisibility(View.VISIBLE);
            holder.ai_text.setText(msgMod.getMsg());
        }else{
            holder.me_rec.setVisibility(View.VISIBLE);
            holder.ai_rec.setVisibility(View.GONE);
            holder.me_text.setText(msgMod.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return msgModArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout me_rec , ai_rec;
        TextView me_text , ai_text ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            me_rec = itemView.findViewById(R.id.me_rec);
            ai_rec = itemView.findViewById(R.id.ai_rec);
            me_text = itemView.findViewById(R.id.me_text);
            ai_text = itemView.findViewById(R.id.ai_text);

        }
    }
}
