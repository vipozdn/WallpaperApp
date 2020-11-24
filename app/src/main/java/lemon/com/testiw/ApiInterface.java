package lemon.com.testiw;

import java.util.List;

import lemon.com.testiw.models.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers("Authorization: Client-ID YOUR_UNSPLASH_ACCESS_KEY_HERE")
    @GET("photos?page=1&per_page=50&order_by=latest")
    Call<List<Photo>> getPhotos();

    @Headers("Authorization: Client-ID YOUR_UNSPLASH_ACCESS_KEY_HERE")
    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);

}
