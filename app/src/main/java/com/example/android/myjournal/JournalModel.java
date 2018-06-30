package com.example.android.myjournal;

public class JournalModel {
    private String header;
    private int imageResourceId;
    private String body;
    public JournalModel(String mheader, int mImageResourceId, String mBody){
        header = mheader;
        imageResourceId = mImageResourceId;
        body = mBody;
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
