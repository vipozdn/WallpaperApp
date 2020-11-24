package lemon.com.testiw.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import lemon.com.testiw.PhotosAdapter;
import lemon.com.testiw.R;
import lemon.com.testiw.models.Photo;
import lemon.com.testiw.utils.RealmController;

public class FavoriteFragment extends Fragment {

    private TextView textNotification;
    private RecyclerView recyclerView;
    private PhotosAdapter photosAdapter;
    private List<Photo> photos;
    private String TAG = "FavoriteFragment";
    private RealmController realmController;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        textNotification = (TextView)  root.findViewById(R.id.fragment_favorite_notification);
        recyclerView = (RecyclerView) root.findViewById(R.id.fragment_favorite_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        Realm.init(getActivity());
        realmController = new RealmController();

        photos = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);
        getPhotos();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                getPhotos();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    private void getPhotos(){

        photos.addAll(realmController.getPhotos());
        if(photos.size() == 0){
            textNotification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            photosAdapter.notifyDataSetChanged();
        }
    }

}