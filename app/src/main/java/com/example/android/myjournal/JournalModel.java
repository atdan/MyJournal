package com.example.android.myjournal;

public class JournalModel {
    private String header, time;
    private int imageResourceId;
    private String body;
    public JournalModel(){
        //Empty constructor
    }
    public JournalModel(String mheader, int mImageResourceId, String mBody, String time){
        header = mheader;
        imageResourceId = mImageResourceId;
        body = mBody;
        this.time = time;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getBody() {
        return body;
    }
}
