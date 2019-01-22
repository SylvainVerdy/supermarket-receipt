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
}
