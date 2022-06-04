package com.example.hunter_game.utils;

import android.app.Activity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BackGround {
    static final String LINK_BACKGROUND="https://images.pexels.com/photos/10257142/pexels-photo-10257142.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";

    public BackGround(){ }
    public static void setBackGround(Activity activity, ImageView imageView){
        Glide
                .with(activity)
                .load(LINK_BACKGROUND)
                .centerCrop()
                .into(imageView);
    }
}
