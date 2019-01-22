package fr.esiea.supermarket.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class SupermarketTest {

    @Test
    public void testSomething() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double productValue = receipt.getTotalPrice();
        double expectedProductValue = 4.975;

        Assertions.assertThat(catalog).isInstanceOf(FakeCatalog.class);
        Assertions.assertThat(toothbrush).isInstanceOf(Product.class);
        Assertions.assertThat(productValue).isEqualTo(expectedProductValue);
    }

    @Test
    public void testTenPercentDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 3.6;

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test
    public void testCatalogAdd(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 1.00);

        Assertions.assertThat(catalog.getUnitPrice(toothbrush)).isNotNull();
    }

    @Test
    public void testShoppingCartAdd(){
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        Map<Product, Double> items = cart.productQuantities();

        Assertions.assertThat(items.containsKey(toothbrush)).isTrue();
    }
}
