package com.example.android.myjournal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
    ArrayList<JournalModel> journalModels = new ArrayList<>();
    Context mContext;

    JournalAdapter (Context context, ArrayList<JournalModel> journals) {
        mContext = context;
        journalModels = journals;
    }


    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.each_list_item,parent,false);
        return new JournalViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalModel model = journalModels.get(position);
        if (model != null) {
            holder.textHeader.setText(model.getHeader());
            holder.body.setText(model.getBody());
            holder.imageView.setImageResource(model.getImageResourceId());     }
    }

    @Override
    public int getItemCount() {
        return journalModels.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView textHeader;
        TextView body;
        ImageView imageView;

        JournalViewHolder(View itemView) {
            super(itemView);
            textHeader =  itemView.findViewById(R.id.header);
            body=  itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.daily_mood);

        }
    }

}
