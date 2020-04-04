/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mdp;
    private AudioManager adm;
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
    protected void onStop() {
        super.onStop();
        release_media();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        adm =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
//        words.add(new Words("one", "lutti",R.drawable.number_one));
//        words.add(new Words("two", "otiiko",R.drawable.number_two));
//        words.add(new Words("three", "tolooKosu",R.drawable.number_three));
//        words.add(new Words("four", "oyyisa",R.drawable.number_four));
//        words.add(new Words("five", "masokka",R.drawable.number_five));
//        words.add(new Words("six", "temmoka",R.drawable.number_six));
//        words.add(new Words("seven", "kenekaku",R.drawable.number_seven));
//        words.add(new Words("eight", "kawinta",R.drawable.number_eight));
//        words.add(new Words("nine", "wo'e",R.drawable.number_nine));
//        words.add(new Words("ten", "na'accha",R.drawable.number_ten));
        ListView lst=(ListView)findViewById(R.id.list);
        WordAdapter iteamadapter = new WordAdapter(this,words,R.color.category_numbers);
        lst.setAdapter(iteamadapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word=words.get(position);
                release_media();
                int res=adm.requestAudioFocus(focuslist, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(res==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mdp = MediaPlayer.create(NumbersActivity.this, word.getSoundid());
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
    }
    public void  release_media(){
        if(mdp!=null){
            mdp.release();;
            mdp=null;
            adm.abandonAudioFocus(focuslist);
        }
    }
}
