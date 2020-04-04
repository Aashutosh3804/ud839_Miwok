package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

    private MediaPlayer mdp;
    private AudioManager adm;
    private AudioManager.OnAudioFocusChangeListener focuslist= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mdp.pause();
                mdp.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mdp.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                release_media();
            }
        }
    };
    @Override
    public void onStop() {
        super.onStop();
        release_media();
    }

    public ColorsFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.activity_numbers,container,false);

        adm =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Words> words = new ArrayList<>();
        ListView lst=(ListView)rootview.findViewById(R.id.list) ;
        words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red,R.raw.color_red));
        words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Words("green", "chokokki", R.drawable.color_green,R.raw.color_green));
        words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown,R.raw.color_brown));
        words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray,R.raw.color_gray));
        words.add(new Words("black", "kululli", R.drawable.color_black,R.raw.color_black));
        words.add(new Words("white", "kelelli", R.drawable.color_white,R.raw.color_white));

        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_colors);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word=words.get(position);
                release_media();
                int res=adm.requestAudioFocus(focuslist,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(res==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mdp = MediaPlayer.create(getActivity(), word.getSoundid());
                    mdp.start();
                    mdp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            release_media();
                        }
                    });
                }
            }
        });



        return rootview;
    }

    public void  release_media(){
        if(mdp!=null){
            mdp.release();;
            mdp=null;
            adm.abandonAudioFocus(focuslist);
        }
    }

}
