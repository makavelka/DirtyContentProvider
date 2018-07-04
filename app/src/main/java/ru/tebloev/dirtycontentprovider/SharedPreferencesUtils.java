package ru.tebloev.dirtycontentprovider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Tebloev Vladimir
 */
public class SharedPreferencesUtils {

    public static final String TAG = "my sp";
    public static final String ID = "id";

    public static void insert(Context context, String data) {
        getEditor(context).putString(ID, data).apply();
    }

    public static void delete(Context context, String id) {
        getEditor(context).remove(id).apply();
    }

    public static String get(Context context, String id) {
        return getSP(context).getString(id, "");
    }


    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return preferences.edit();
    }

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }
}
