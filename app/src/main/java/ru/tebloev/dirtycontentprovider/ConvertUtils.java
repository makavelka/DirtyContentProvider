package ru.tebloev.dirtycontentprovider;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import static ru.tebloev.dirtycontentprovider.MyDatabase.KEY_ID;
import static ru.tebloev.dirtycontentprovider.MyDatabase.KEY_NAME;
import static ru.tebloev.dirtycontentprovider.MyDatabase.KEY_SH_ADDR;

/**
 * @author Tebloev Vladimir
 */
public class ConvertUtils {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";

    public static Shop convertValuesToShop(ContentValues values) {
        Shop shop = new Shop(values.getAsInteger(ID), values.getAsString(NAME), values.getAsString(ADDRESS));
        return shop;
    }

    public static ContentValues convertShopToValues(Shop shop) {
        ContentValues values = new ContentValues();
        values.put(ID, shop.getId());
        values.put(NAME, shop.getName());
        values.put(ADDRESS, shop.getAddress());
        return values;
    }

    public static List<Shop> convertCursorToShop(Cursor cursor) {
        List<Shop> shops = new ArrayList<>();
        while (cursor.moveToNext()) {
            Shop shop = new Shop();
            shop.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            shop.setAddress(cursor.getString(cursor.getColumnIndex(KEY_SH_ADDR)));
            shop.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            shops.add(shop);
        }
        cursor.close();
        return shops;
    }
}
