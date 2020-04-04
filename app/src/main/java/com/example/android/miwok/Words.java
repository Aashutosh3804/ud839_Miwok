package com.example.android.miwok;

public class Words {
    private String mDefaultTransalation;
    private String mMiwokTransaltion;
    private int mimageid;
    private int soundid;

    public int getMimageid() {
        return mimageid;
    }

    public Words(String mDefaultTransalation, String mMiwokTransaltion) {
        this.mDefaultTransalation = mDefaultTransalation;
        this.mMiwokTransaltion = mMiwokTransaltion;
    }

    public int getSoundid() {
        return soundid;
    }

    public Words(String mDefaultTransalation, String mMiwokTransaltion, int mimageid, int soundid) {
        this.mDefaultTransalation = mDefaultTransalation;
        this.mMiwokTransaltion = mMiwokTransaltion;
        this.mimageid = mimageid;
        this.soundid=soundid;
    }

    public String getmDefaultTransalation() {
        return mDefaultTransalation;
    }

    public String getmMiwokTransaltion() {
        return mMiwokTransaltion;
    }
}
