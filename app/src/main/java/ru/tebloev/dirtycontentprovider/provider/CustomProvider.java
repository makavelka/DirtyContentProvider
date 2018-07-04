package ru.tebloev.dirtycontentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.tebloev.dirtycontentprovider.ConvertUtils;
import ru.tebloev.dirtycontentprovider.DAO;
import ru.tebloev.dirtycontentprovider.DaoImplementation;
import ru.tebloev.dirtycontentprovider.MyDatabase;

/**
 * @author Tebloev Vladimir
 */
public class CustomProvider extends ContentProvider {

    private static final String AUTHORITY =
            "ru.tebloev.dirtycontentprovider.provider";
    private static final String SHOPS_TABLE = "shops";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + SHOPS_TABLE);

    public static final int SHOPS = 1;
    public static final int SHOP_ID = 2;

    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, SHOPS_TABLE, SHOPS);
        sURIMatcher.addURI(AUTHORITY, SHOPS_TABLE + "/#",
                SHOP_ID);
    }

    private DAO mDAO;
    private MyDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = new MyDatabase(getContext());
        mDAO = new DaoImplementation(mDatabase);
        return mDatabase != null ? true : false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SHOPS_TABLE);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case SHOP_ID:
                queryBuilder.appendWhere(MyDatabase.KEY_ID + "="
                        + uri.getLastPathSegment());
                break;
            case SHOPS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(mDatabase.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = 0;
        switch (uriType) {
            case SHOPS:
                id = mDAO.addShop(ConvertUtils.convertValuesToShop(values));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(SHOPS_TABLE + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsDeleted = 0;
        switch (uriType) {
            case SHOPS:
                rowsDeleted = mDatabase.delete(selection, selectionArgs);
                break;
            case SHOP_ID:
                String id = uri.getLastPathSegment();
                mDAO.deleteShopById(Integer.valueOf(id));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " +
                        uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsUpdated = 0;
        switch (uriType) {
            case SHOPS:
                rowsUpdated = mDAO.updateShop(ConvertUtils.convertValuesToShop(values));
                break;
            case SHOP_ID:
                String id = uri.getLastPathSegment();
                mDAO.updateShopById(Integer.valueOf(id), ConvertUtils.convertValuesToShop(values));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " +
                        uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;
    }
}
