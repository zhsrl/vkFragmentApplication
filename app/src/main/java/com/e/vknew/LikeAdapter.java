package com.e.vknew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class LikeAdapter  extends RecyclerView.Adapter<LikeAdapter.MyViewHolder> {
    Context context;
    private List<FeedModel> mData;
    private @Nullable ItemClickListener listener;
    private @Nullable FragmentLikeListener fragmentLikeListener;

    RequestManager glide;

    public LikeAdapter(Context context,
                        List<FeedModel> mData,
                       ItemClickListener listener,
                       @Nullable FragmentLikeListener fragmentLikeListener){
        this.context = context;
        this.mData = mData;
        this.listener = listener;
        this.fragmentLikeListener = fragmentLikeListener;
        this.glide = Glide.with(context);
    }


    @NonNull
    @Override
    public LikeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_model,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.MyViewHolder holder,final int position) {
        final FeedModel feedModel = mData.get(getItemViewType(position));
        glide.load(mData.get(position).getUploaderpic()).into(holder.uploader);
        glide.load(mData.get(position).getPostpic()).into(holder.post);
        holder.uploadername.setText(feedModel.getName());
        holder.posttime.setText(feedModel.getTime());
        holder.likes.setText(feedModel.getLikes());
        holder.comments.setText(feedModel.getComments());
        holder.shares.setText(feedModel.getShares());
        holder.views.setText(feedModel.getViews());
        holder.postText.setText(feedModel.getPostText());
        holder.like.setImageResource(R.drawable.liked);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentLikeListener != null)
                    fragmentLikeListener.removeItemLike(feedModel);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.ItemClick(position,feedModel);
            }
        });

        if(feedModel.isLiked() == true){
            holder.like.setImageResource(R.drawable.liked);
        }else
            holder.like.setImageResource(R.drawable.ic_favorite_border_black_24dp);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ItemClickListener {

        void ItemClick(int position, FeedModel item);
        void likeClick(int position, FeedModel item);
    }

    public interface FragmentLikeListener {

        void removeItemLike(FeedModel feedModel);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView uploadername,posttime,postText,likes,comments,shares,views;
        CircleImageView uploader;
        ImageView post,like;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadername = itemView.findViewById(R.id.user_name);
            posttime = itemView.findViewById(R.id.post_time);
            likes = itemView.findViewById(R.id.cnt_like);
            postText = itemView.findViewById(R.id.post_text);
            comments = itemView.findViewById(R.id.cnt_comment);
            shares = itemView.findViewById(R.id.cnt_shares);
            views = itemView.findViewById(R.id.cnt_view);

            like = itemView.findViewById(R.id.like);
            uploader =(CircleImageView) itemView.findViewById(R.id.user_avatar);
            post = itemView.findViewById(R.id.post_pic);

        }

        public int getItemViewType(int position) {
            return position;
        }




    }
}
