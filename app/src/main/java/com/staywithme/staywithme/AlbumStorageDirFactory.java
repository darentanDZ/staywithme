package com.staywithme.staywithme;

/**
 * Created by daren on 10/12/2016.
 */
import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
