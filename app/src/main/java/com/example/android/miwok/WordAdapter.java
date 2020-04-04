package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class WordAdapter extends ArrayAdapter<Words> {
    private int colorid;

    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Words> words,int colorid) {
        super(context, 0, words);
        this.colorid=colorid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View lstview=convertView;
        if(lstview==null){
            lstview= LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false
            );
        }
        Words word =getItem(position);
        TextView miwok=(TextView)lstview.findViewById(R.id.miwok_text_view);
        TextView eng=(TextView)lstview.findViewById(R.id.default_text_view);
        View textconatier=lstview.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),colorid);
        textconatier.setBackgroundColor(color);
        ImageView img=(ImageView) lstview.findViewById(R.id.image);
        miwok.setText(word.getmMiwokTransaltion());
        eng.setText(word.getmDefaultTransalation());
        img.setImageResource(word.getMimageid());
        return lstview;
    }
}
