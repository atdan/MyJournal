package com.example.android.myjournal.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.myjournal.JournalModel;
import com.example.android.myjournal.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View view;
    TextView textTitle, textTime,textDesCription;
    Context context;
    public ViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        itemView.setOnClickListener(this);

        view = itemView;
        textTime = view.findViewById(R.id.time);
        textTitle = view.findViewById(R.id.header);
        textDesCription = view.findViewById(R.id.description);

    }
    public void setNoteTitle(String title){

        textTitle.setText(title);
    }
    public void setNoteTime(String time){

        textTime.setText(time);
    }

    public void bindNotes(JournalModel noteModel){

        textTime = view.findViewById(R.id.time);
        textTitle = view.findViewById(R.id.header);
        textDesCription = view.findViewById(R.id.description);


        textTime.setText(noteModel.getTime());
        textTitle.setText(noteModel.getHeader());
        textDesCription.setText(noteModel.getBody());


    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onClick(View v) {

    }
}
