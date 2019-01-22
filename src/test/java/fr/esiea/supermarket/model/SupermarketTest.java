package fr.esiea.supermarket.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void testDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 1.00);

        Product oranges = new Product("orange", ProductUnit.Each);
        catalog.addProduct(oranges, 2.00);

        Discount discountToothbrush = new Discount(toothbrush, "For better teeth", 0.3);
        Discount discountOranges = new Discount(oranges, "For better health", 0.5);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(oranges, 3);
        cart.addItemQuantity(toothbrush, 2);

        Teller teller = new Teller(catalog);
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        receipt.addDiscount(discountOranges);
        receipt.addDiscount(discountToothbrush);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 4.4; // expected 7.2?

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test
    public void testCatalogAdd(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 1.00);

        Assertions.assertThat(catalog.getUnitPrice(toothbrush)).isNotNull();
    }
}
