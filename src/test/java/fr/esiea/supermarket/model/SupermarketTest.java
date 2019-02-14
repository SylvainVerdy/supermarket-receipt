package fr.esiea.supermarket.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.assertj.core.api.Assertions.within;

public class SupermarketTest {

    @Test
    public void testOneProduct() {
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

        double expectedProductValue = 4.975;
        double productValue = receipt.getTotalPrice();
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
    public void testThreeForTwoDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 3);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 4.0;

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test
    public void testTwoForAmountDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 4);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 4.0;

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test
    public void testFiveForAmountDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 10);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 10.0;

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test
    public void testTenPercentForEachOfThem(){
        //TODO complete the test
        SupermarketCatalog catalog = new FakeCatalog();
        Teller teller = new Teller(catalog);

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product banana = new Product("banana",ProductUnit.Each);
        catalog.addProduct(banana,2.00);
        catalog.addProduct(toothbrush,1.00);
        teller.addSpecialOffer(SpecialOfferType.TenPercentForEachOneOfThem, toothbrush, 2);
        teller.addSpecialOffer(SpecialOfferType.TenPercentForEachOneOfThem,banana,3);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(banana,3);



        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 7.70;



        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue , within(0.001));

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
