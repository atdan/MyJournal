package com.example.android.myjournal.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.myjournal.JournalModel;
import com.example.android.myjournal.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View view;
    TextView textTitle, textTime,textDesCriptiontv;
    Context context;
    public ViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        itemView.setOnClickListener(this);

        view = itemView;
        textTime = view.findViewById(R.id.time);
        textTitle = view.findViewById(R.id.header);
        textDesCriptiontv = view.findViewById(R.id.description);

    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public TextView getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(TextView textTitle) {
        this.textTitle = textTitle;
    }

    public TextView getTextTime() {
        return textTime;
    }

    public void setTextTime(TextView textTime) {
        this.textTime = textTime;
    }

    public TextView getTextDesCription() {
        return textDesCriptiontv;
    }

    public void setTextDesCription(String textDesCription) {
        textDesCriptiontv.setText(textDesCription);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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
        textDesCriptiontv = view.findViewById(R.id.description);


        textTime.setText(noteModel.getTime());
        textTitle.setText(noteModel.getHeader());
        textDesCriptiontv.setText(noteModel.getBody());


    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onClick(View v) {

    }
}
