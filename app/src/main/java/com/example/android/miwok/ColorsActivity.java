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

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
MediaPlayer mdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

       final ArrayList<Words> words = new ArrayList<>();
        ListView lst=(ListView)findViewById(R.id.list) ;
    words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red,R.raw.color_red));
        words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Words("green", "chokokki", R.drawable.color_green,R.raw.color_green));
        words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown,R.raw.color_brown));
        words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray,R.raw.color_gray));
        words.add(new Words("black", "kululli", R.drawable.color_black,R.raw.color_black));
        words.add(new Words("white", "kelelli", R.drawable.color_white,R.raw.color_white));
//        words.add(new Words("red", "weṭeṭṭi",R.drawable.color_red));
//        words.add(new Words("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow));
//        words.add(new Words("dusty yellow", "ṭopiisә",R.drawable.color_dusty_yellow));
//        words.add(new Words("green", "chokokki",R.drawable.color_green));
//        words.add(new Words("brown", "ṭakaakki",R.drawable.color_brown));
//        words.add(new Words("gray", "ṭopoppi",R.drawable.color_gray));
//        words.add(new Words("black", "kululli",R.drawable.color_black));
//        words.add(new Words("white", "kelelli",R.drawable.color_white));
        WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word=words.get(position);
                mdp= MediaPlayer.create(ColorsActivity.this,word.getSoundid());
                mdp.start();
            }
        });

    }

}
