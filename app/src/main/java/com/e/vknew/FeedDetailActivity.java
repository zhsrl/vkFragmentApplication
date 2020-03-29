package com.e.vknew;



import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class FeedDetailActivity extends AppCompatActivity {
    Context context;
    ImageView likeButton;
    boolean isLiked;

    public static final String EXTRA_DETAIL = "extra_detail";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        context = this;

        ImageView userPic = findViewById(R.id.detail_user_pic);
        ImageView postPic = findViewById(R.id.detail_uploader_pic);
        likeButton =(ImageView) findViewById(R.id.detail_like);
        boolean likes;



        TextView userName = (TextView) findViewById(R.id.detail_user_name);
        TextView userTime = (TextView) findViewById(R.id.detail_time);
        TextView postText = (TextView) findViewById(R.id.detail_text);
        final TextView likeCount = (TextView) findViewById(R.id.detail_like_cnt);
        likes = getIntent().getExtras().getBoolean("like");
        TextView shareCount = (TextView) findViewById(R.id.detail_share_cnt);
        TextView viewCount = (TextView) findViewById(R.id.detail_view_cnt);


        final FeedModel feedModel = (FeedModel)getIntent().getParcelableExtra("news");

        Glide.with(this).load(feedModel.getUploaderpic()).into(userPic);
        Glide.with(this).load(feedModel.getPostpic()).into(postPic);
        userName.setText(feedModel.getName());
        userTime.setText(feedModel.getTime());
        postText.setText(feedModel.getPostText());
        likeCount.setText(feedModel.getLikes());
        shareCount.setText(feedModel.getShares());
        viewCount.setText(feedModel.getViews());
        feedModel.setLiked(likes);
        isLiked = likes;




        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLiked == false){
                    feedModel.setLiked(true);
                    feedModel.setLikes(String.valueOf(Integer.valueOf(feedModel.getLikes())+1));
                    likeCount.setText(String.valueOf(feedModel.getLikes()));
                    isLiked=true;
//                    Glide.with(v).load(R.drawable.ic_liked).into(likeButton);
                    likeButton.setImageResource(R.drawable.liked );
                }else {
//                    Glide.with(v).load(R.drawable.ic_favorite_border_black_24dp).into(likeButton);
                    likeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    feedModel.setLiked(false);
                    feedModel.setLikes(String.valueOf(Integer.valueOf(feedModel.getLikes())-1));
                    likeCount.setText(String.valueOf(feedModel.getLikes()));
                    isLiked=false;

                }
            }
        });
        if (isLiked==true){
            Glide.with(this).load(R.drawable.liked  ).into(likeButton);

        }else
            Glide.with(this).load(R.drawable.ic_favorite_border_black_24dp).into(likeButton);
    }
}