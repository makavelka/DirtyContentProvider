package ru.tebloev.dirtycontentprovider;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static ru.tebloev.dirtycontentprovider.provider.CustomProvider.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private ContentObserver mContentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentObserver = new MyObserver(new Handler(Looper.getMainLooper()));
        insertData();
//        readData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getContentResolver().registerContentObserver(Uri.EMPTY, true, mContentObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        getContentResolver().unregisterContentObserver(mContentObserver);
    }

    private void changeUi() {
        // some changes
    }

    private class MyObserver extends ContentObserver {
        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            changeUi();
        }
    }

    private void insertData() {
        for (int i = 1; i < 11; i++) {
            getContentResolver().insert(CONTENT_URI, ConvertUtils.convertShopToValues(new Shop(i, "shop" + i, "address" + i)));
        }
    }

    private List<Shop> readData() {
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        List<Shop> list = ConvertUtils.convertCursorToShop(cursor);
        return list;
    }
}
