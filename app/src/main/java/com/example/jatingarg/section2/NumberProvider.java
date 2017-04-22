package com.example.jatingarg.section2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jatingarg on 22/04/17.
 */

public class NumberProvider extends ContentProvider {
    private static final String PROVIDER_NAME = "com.example.jatingarg.section2.numberprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/numbers" );
    private static final int NUMBERS  = 1;
    private static final UriMatcher mMatcher;
    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(PROVIDER_NAME,"numbers", NUMBERS);
    }

    DBhelper mDBhelper;

    @Override
    public boolean onCreate() {
        mDBhelper = new DBhelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(mMatcher.match(uri) == NUMBERS){
            return mDBhelper.getAllData();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
