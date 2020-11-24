package lemon.com.testiw.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Photo extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private String id;

    @SerializedName("urls")
    private PhotoUrl photoUrl = new PhotoUrl();


    @SerializedName("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhotoUrl getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(PhotoUrl photoUrl) {
        this.photoUrl = photoUrl;
    }

}
