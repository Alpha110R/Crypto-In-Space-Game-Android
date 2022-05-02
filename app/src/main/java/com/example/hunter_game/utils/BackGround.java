package com.example.hunter_game.utils;

import android.app.Activity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BackGround {
    static final String LINK_BACKGROUND="https://images.pexels.com/photos/10257142/pexels-photo-10257142.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
    private ImageView imageView;
    private Activity activity;

    public BackGround(Activity activity, ImageView imageView){
        this.activity = activity;
        this.imageView = imageView;
    }
    public void setBackGround(){
        Glide
                .with(activity)
                .load(LINK_BACKGROUND)
                .into(imageView);
    }
}
