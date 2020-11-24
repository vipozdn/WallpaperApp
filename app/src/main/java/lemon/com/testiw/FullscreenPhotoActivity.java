package lemon.com.testiw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

import io.realm.Realm;
import lemon.com.testiw.models.Photo;
import lemon.com.testiw.utils.RealmController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenPhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "FullscreenPhotoActivity";

    private ImageView fullscreenPhoto;
    private FloatingActionButton fabFavorite;

    private RealmController realmController;

    private Photo photo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullscreenPhoto = (ImageView) findViewById(R.id.activity_fullscreen_photo_photo);
        fabFavorite = (FloatingActionButton) findViewById(R.id.activity_fullscreen_photo_fab_favorite);
        fabFavorite.setOnClickListener(this);

        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        Realm.init(this);
        realmController = new RealmController();
        if (realmController.isPhotoExists(photoId)) {
            fabFavorite.setImageResource(R.drawable.favorited);
        }

        if(!realmController.isPhotoExists(photoId)){
            fabFavorite.setImageResource(R.drawable.not_favorite);
        }
    }

    private void getPhoto(String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()) {
                    photo = response.body();
                    new DownloadImageTask(fullscreenPhoto)
                            .execute(photo.getPhotoUrl().getRegular());
                    Log.d(TAG, "photo.getPhotoUrl().getRegular(): " +
                            photo.getPhotoUrl().getRegular());
                } else {
                    Log.e(TAG,  "response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.e(TAG, "onFailure ", t);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: ",  e);
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onClick(View view) {
        if (realmController.isPhotoExists(photo.getId())) {
            realmController.deletePhoto(photo);
            fabFavorite.setImageResource(R.drawable.not_favorite);
            Toast.makeText(this, getResources()
                    .getString(R.string.favorite_removed), Toast.LENGTH_SHORT).show();
        } else {
            realmController.savePhoto(photo);
            fabFavorite.setImageResource(R.drawable.favorited);
            Toast.makeText(this, getResources()
                    .getString(R.string.favorite_added), Toast.LENGTH_SHORT).show();
        }
    }





}
