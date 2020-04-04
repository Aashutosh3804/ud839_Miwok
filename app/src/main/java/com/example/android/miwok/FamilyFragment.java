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


public class FamilyFragment extends Fragment {
    private MediaPlayer mdp;
    private AudioManager adm;

    private int mPage;
    AudioManager.OnAudioFocusChangeListener focuslist= new AudioManager.OnAudioFocusChangeListener() {
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


    public FamilyFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.activity_numbers,container,false);

        adm =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Words> words = new ArrayList<>();
        ListView lst=(ListView) rootview.findViewById(R.id.list);
        words.add(new Words("father", "әpә", R.drawable.family_father,R.raw.family_father));
        words.add(new Words("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        words.add(new Words("son", "angsi", R.drawable.family_son,R.raw.family_son));
        words.add(new Words("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Words("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Words("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Words("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_younger_sister));
        words.add(new Words("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Words("grandmother ", "ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Words("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter=new WordAdapter(getActivity(),words,R.color.category_family);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                release_media();
                Words word=words.get(position);


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
