package com.e.vknew;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    Context context;
    private List<FeedModel> mData;
    RequestManager glide;
    private ItemClickListener itemClickListener;
    private @Nullable FragmentLikeListener fragmentLikeListener;
    private @Nullable FragmentButtonListener fragmentButtonListener;


    public FeedAdapter(Context context,
                       List<FeedModel> newsList,
                       @Nullable ItemClickListener itemClickListener,
                       @Nullable FragmentLikeListener fragmentLikeListener,
                       @Nullable FragmentButtonListener fragmentButtonListener) {
        this.context = context;
        FeedModel.feedModelList = mData;
        mData = newsList;
        this.glide = Glide.with(context);
        this.itemClickListener = itemClickListener;
        this.fragmentButtonListener=fragmentButtonListener;
        this.fragmentLikeListener=fragmentLikeListener;
    }

//    public FeedAdapter(List<FeedModel> newsGenerator, ItemClickListener itemClickListener, FragmentButtonListener fragmentButtonListener, FragmentLikeListener fragmentLikeListener) {
//        this.itemClickListener = itemClickListener;
//        this.fragmentButtonListener=fragmentButtonListener;
//        this.fragmentLikeListener=fragmentLikeListener;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_model, null, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(params);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.MyViewHolder holder, final int position) {
        final FeedModel feedModel = mData.get(getItemViewType(position));
        glide.load(mData.get(position).getUploaderpic()).into(holder.uploader);
        holder.uploadername.setText(mData.get(position).getName());
        holder.posttime.setText(mData.get(position).getTime());
        holder.likes.setText(mData.get(position).getLikes());
        holder.comments.setText(mData.get(position).getComments());
        holder.shares.setText(mData.get(position).getShares());
        holder.views.setText(mData.get(position).getViews());
        holder.postText.setText(mData.get(position).getPostText());

        if(feedModel.getPostpic() == 0){
            holder.post.setVisibility(View.GONE);
        }else{
            holder.post.setVisibility(View.VISIBLE);
            glide.load(mData.get(position).getPostpic()).into(holder.post);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity;
                detailActivity = new Intent(context,FeedDetailActivity.class);
                detailActivity.putExtra(FeedDetailActivity.EXTRA_DETAIL,mData.get(position));
                detailActivity.putExtra("news",mData.get(position));
                context.startActivity(detailActivity);
                if(itemClickListener == null){
                    itemClickListener.ItemClick(position,feedModel);
                }
            }
        });



        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.likeClick(position,feedModel);
                }

            }
        });

        if(feedModel.isLiked() == true){
            holder.likeBtn.setImageResource(R.drawable.liked);
        }else {

            holder.likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView uploadername,posttime,postText,likes,comments,shares,views;
        CircleImageView uploader;
        ImageView post,likeBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadername = (TextView) itemView.findViewById(R.id.user_name);
            posttime =(TextView) itemView.findViewById(R.id.post_time);
            likes = (TextView) itemView.findViewById(R.id.cnt_like);
            postText = (TextView) itemView.findViewById(R.id.post_text);
            comments = (TextView) itemView.findViewById(R.id.cnt_comment);
            shares = (TextView) itemView.findViewById(R.id.cnt_shares);
            views = (TextView) itemView.findViewById(R.id.cnt_view);
            uploader = (CircleImageView) itemView.findViewById(R.id.user_avatar);
            post = (ImageView) itemView.findViewById(R.id.post_pic);
            likeBtn = (ImageView) itemView.findViewById(R.id.like);
        }
    }

    public int getItemViewType(int position){
        return position;
    }



    interface ItemClickListener{
        void ItemClick(int position, FeedModel item);
        void likeClick(int position, FeedModel item);

    }

    public interface FragmentLikeListener{

        void removeItemLike(FeedModel feed);

    }


    public interface FragmentButtonListener{

        void myClick(FeedModel modelFeed, int option);

    }


    public void removeLike(FeedModel feedModel) {

        int n = mData.indexOf(feedModel);
        feedModel.setLiked(false);
        feedModel.setLikes(String.valueOf(Integer.valueOf(feedModel.getLikes())-1));
        mData.set(n, feedModel);
        mData.set(n,feedModel);
        this.notifyItemChanged(n);

    }
}
