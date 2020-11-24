package lemon.com.testiw.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lemon.com.testiw.ApiInterface;
import lemon.com.testiw.PhotosAdapter;
import lemon.com.testiw.R;
import lemon.com.testiw.ServiceGenerator;
import lemon.com.testiw.models.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoFragment extends Fragment {

    private RecyclerView recyclerView;
    private PhotosAdapter photosAdapter;
    private List<Photo> photoArrayList = new ArrayList<>();
    private String TAG = "PhotoFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = root.findViewById(R.id.fragment_photo_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photosAdapter = new PhotosAdapter(getContext(), photoArrayList);
        recyclerView.setAdapter(photosAdapter);
        getPhotos();
        return root;
    }

    private void getPhotos() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        Call<List<Photo>> photos = apiInterface.getPhotos();

        photos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {

                if (response.isSuccessful()) {
                    // Чтобы добраться до данных, необходимо вызвать метод body()
                    //Log.d(TAG, "response " + response.body().size());
                    photoArrayList.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });
    }
}