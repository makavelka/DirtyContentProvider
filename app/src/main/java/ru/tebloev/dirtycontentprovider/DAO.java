package ru.tebloev.dirtycontentprovider;

/**
 * @author Tebloev Vladimir
 */
public interface DAO {

    long addShop(Shop shop);

    int updateShop(Shop shop);

    int updateShopById(int id, Shop shop);

    void deleteShopById(int id);

    Shop getShopById(int id);
}
