package fr.esiea.supermarket.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BasicCatalog implements SupermarketCatalog {
    private Map<String, Product> products = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();


    @Override
    public void addProduct(Product product, double price) {
        this.products.put(product.getName(), product);
        this.prices.put(product.getName(), price);
    }

    @Override
    public double getUnitPrice(Product p) {
        return this.prices.get(p.getName());
    }

    @Override
    public void deleteProduct(String name) {
        this.products.remove(name);
        this.prices.remove(name);
    }

    @Override
    public Collection<Product> getProducts() {
        return products.values();
    }

    @Override
    public Product getProductByName(String productName) {
        return products.get(productName);
    }

}
