package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.Offers.Offer;
import fr.esiea.supermarket.model.Offers.SpecialOfferType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
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


        ReceiptItem Item = new ReceiptItem(toothbrush,2,0.99,0.99*2);
        Product expectedResult = toothbrush;
        Assertions.assertThat(expectedResult).isEqualTo(Item.getProduct());
    }

    @Test
    public void testTenPercentDiscount(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product soap = new Product("soap", ProductUnit.Each);
        catalog.addProduct(toothbrush, 2.00);
        catalog.addProduct(soap, 3.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(soap, 4);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, soap, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 14.4;

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
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 4.0;

        Assertions.assertThat(perceivedValue).isEqualTo(expectedValue);
    }

    @Test

    public void testThreeForTwoDiscountSuperiorTo2(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 3);
        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 10.0);
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
        cart.addItemQuantity(apples, 2);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 2.0;


        Assertions.assertThat(expectedValue).isEqualTo(perceivedValue);
    }

    @Test
    public void testTwoForAmountDiscountSuperiorTo2 () {
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 4);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 3);
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 6.0;

        Assertions.assertThat(expectedValue).isEqualTo(perceivedValue);
    }

    @Test
    public void testFiveForAmountDiscountSuperiorTo5 (){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(apples, 2.00);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 6);
        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double perceivedValue = receipt.getTotalPrice();
        double expectedValue = 3.0;

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

    @Test
    public void testListItemNotEmpty(){
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush);
        List<ProductQuantity> listItems = cart.getItems();

        Assertions.assertThat(listItems).isNotNull();
    }

    @Test
    public void testAddItemQuantityWhileAlreadyAnItem () {
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush);
        cart.addItem(toothbrush);
        Map<Product, Double> productQuantities = cart.productQuantities();
        double itemQuantity = productQuantities.get(toothbrush);
        double expectedItemQuantity = 2.0;
        Assertions.assertThat(expectedItemQuantity).isEqualTo(itemQuantity);
    }

    @Test
    public void testOneItemQuantity () {
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush);
        Map<Product, Double> productQuantities = cart.productQuantities();
        double productQuantity = productQuantities.get(toothbrush);

        double expectedProductQuantity = 1.0;
        Assertions.assertThat(expectedProductQuantity).isEqualTo(productQuantity);
    }

}
