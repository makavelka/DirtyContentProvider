package ru.tebloev.dirtycontentprovider;

/**
 * @author Tebloev Vladimir
 */
public class DaoImplementation implements DAO {

    private final MyDatabase mDatabase;

    public DaoImplementation(MyDatabase database) {
        mDatabase = database;
    }

    @Override
    public long addShop(Shop shop) {
        return mDatabase.addShop(shop);
    }

    @Override
    public int updateShop(Shop shop) {
        return mDatabase.updateShop(shop);
    }

    @Override
    public int updateShopById(int id, Shop shop) {
        return mDatabase.updateShop(id, shop);
    }

    @Override
    public void deleteShopById(int id) {
        mDatabase.deleteShop(id);
    }

    @Override
    public Shop getShopById(int id) {
        return mDatabase.getShop(id);
    }
}
