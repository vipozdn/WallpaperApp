package lemon.com.testiw.utils;

import java.util.List;

import io.realm.Realm;
import lemon.com.testiw.models.Photo;

public class RealmController {
    private final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo) {
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    // Asynchronously update objects on a background thread
    public void deletePhoto(final Photo photo) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo photoToFind = realm.where(Photo.class)
                        .equalTo("id", photo.getId())
                        .findFirst();
                photoToFind.deleteFromRealm();
            }
        });
    }

    public boolean isPhotoExists(String id) {
        Photo photoToFind = realm.where(Photo.class)
                .equalTo("id", id)
                .findFirst();

        if (photoToFind == null)
            return false;
        else return true;
    }


    public List<Photo> getPhotos() {
        return realm.where(Photo.class).findAll();
    }
}
