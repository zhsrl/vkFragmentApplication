package com.e.vknew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    RecyclerView likeRecyclerView;
    private List<FeedModel> news;
    private LikeAdapter likeAdapter;
    private LikeAdapter.ItemClickListener itemClickListener;
    private LikeAdapter.FragmentLikeListener fragmentLikeListener;
    boolean isLiked;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        likeRecyclerView = view.findViewById(R.id.favourite_container);
        likeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemClickListener = new LikeAdapter.ItemClickListener() {
            @Override
            public void ItemClick(int position, FeedModel item) {
                Intent detailactivity = new Intent(getActivity(), FeedDetailActivity.class);
                detailactivity.putExtra(FeedDetailActivity.EXTRA_DETAIL,news.get(position));
                detailactivity.putExtra("news", item);
                detailactivity.putExtra("like", item.isLiked());
                startActivity(detailactivity);

            }

            @Override
            public void likeClick(int position, FeedModel item) {
                isLiked = item.isLiked();
                if (isLiked == false) {
                    item.setLiked(true);
                    item.setLikes(String.valueOf(Integer.valueOf(item.getLikes()) + 1));
                    isLiked = true;
                } else {
                    item.setLiked(false);
                    item.setLikes(String.valueOf(Integer.valueOf(item.getLikes()) - 1));
                    isLiked = false;
                }
                likeAdapter.notifyItemChanged(position);

            }
        };


        fragmentLikeListener = new LikeAdapter.FragmentLikeListener() {
            @Override
            public void removeItemLike(FeedModel feed) {
                ((MainActivity) getActivity()).removeItemLike(feed);
            }
        };

        news = new ArrayList<>();
        likeAdapter = new LikeAdapter(getContext(), news, itemClickListener, fragmentLikeListener);
        likeRecyclerView.setAdapter(likeAdapter);

        return view;

    }

    public void saveNews(FeedModel feedModel){
        news.add(feedModel);

        likeRecyclerView.getAdapter().notifyItemInserted( news.size()-1);
    }

    public void removeNews(FeedModel feedModel){
        if (news.indexOf(feedModel)==0){
            news.remove(feedModel);
        } else {
            int position = news.indexOf(feedModel);
            news.remove(feedModel);
            likeRecyclerView.getAdapter().notifyItemRemoved(position);
        }
    }

    public void removeLike(FeedModel feedModel){
        int n = news.indexOf(feedModel);
        this.removeNews(feedModel);
        likeAdapter.notifyItemRemoved(n);
    }

    @Override
    public void  onAttach(@NonNull Context context) {
        super.onAttach(context);
    }



}
