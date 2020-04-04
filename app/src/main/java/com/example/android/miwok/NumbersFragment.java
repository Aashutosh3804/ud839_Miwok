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


/**
 * A
 */
public class NumbersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
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


    public NumbersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);
        adm =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Words> words=new ArrayList<>();
        words.add(new Words("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("three", "tolooKosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("five", "masokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("six", "temmoka", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("ten", "na'accha", R.drawable.number_ten, R.raw.number_ten));

        ListView lst=(ListView)rootView.findViewById(R.id.list);
        WordAdapter iteamadapter = new WordAdapter(getActivity(),words,R.color.category_numbers);
        lst.setAdapter(iteamadapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word=words.get(position);
                release_media();
                int res=adm.requestAudioFocus(focuslist, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

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

        return rootView;
    }
    public void  release_media(){
        if(mdp!=null){
            mdp.release();;
            mdp=null;
            adm.abandonAudioFocus(focuslist);
        }
    }
}
