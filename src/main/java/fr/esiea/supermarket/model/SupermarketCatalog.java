package fr.esiea.supermarket.model;

import java.util.Collection;
import java.util.List;

public interface SupermarketCatalog {
    void addProduct(Product product, double price);

    double getUnitPrice(Product product);

    void deleteProduct(String name);

    Collection<Product> getProducts();

    Product getProductByName(String productName);
}
