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
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
   private MediaPlayer mdp;
   private  AudioManager adm;
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
        final ArrayList<Words> words = new ArrayList<>();
        ListView lst=(ListView) findViewById(R.id.list);
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
//        words.add(new Words("father", "әpә",R.drawable.family_father));
//        words.add(new Words("mother", "әṭa",R.drawable.family_mother));
//        words.add(new Words("son", "angsi",R.drawable.family_son));
//        words.add(new Words("daughter", "tune",R.drawable.family_daughter));
//        words.add(new Words("older brother", "taachi",R.drawable.family_older_brother));
//        words.add(new Words("younger brother", "chalitti",R.drawable.family_younger_brother));
//        words.add(new Words("older sister", "teṭe",R.drawable.family_older_sister));
//        words.add(new Words("younger sister", "kolliti",R.drawable.family_younger_sister));
//        words.add(new Words("grandmother ", "ama",R.drawable.family_mother));
//        words.add(new Words("grandfather", "paapa",R.drawable.family_grandfather));
        WordAdapter adapter=new WordAdapter(this,words,R.color.category_family);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                release_media();
                Words word=words.get(position);


                int res=adm.requestAudioFocus(focuslist,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(res==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mdp = MediaPlayer.create(FamilyActivity.this, word.getSoundid());
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
