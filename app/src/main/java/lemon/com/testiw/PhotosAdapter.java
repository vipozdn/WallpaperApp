package lemon.com.testiw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

import lemon.com.testiw.models.Photo;
import lemon.com.testiw.utils.SquareImage;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private static final String TAG = "PhotosAdapter";
    private List<Photo> photos;
    private Context context;

    public PhotosAdapter(Context context, List<Photo> photos) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photos.get(position);

        new DownloadImageTask(holder.squareImage)
                .execute(photo.getPhotoUrl().getRegular());
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout itemPhotoLayout;
        SquareImage squareImage;


        public ViewHolder(View view) {
            super(view);
            itemPhotoLayout = (FrameLayout) view.findViewById(R.id.item_photo_layout);
            squareImage = (SquareImage) view.findViewById(R.id.item_photo_photo);

            itemPhotoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    setFullScreenActivity(position);
                }
            });
        }


    }

    public void setFullScreenActivity(int position) {
        String photoId = photos.get(position).getId();
        Intent intent = new Intent(context, FullscreenPhotoActivity.class);
        intent.putExtra("photoId", photoId);
        context.startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        SquareImage bmImage;

        public DownloadImageTask(SquareImage bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
