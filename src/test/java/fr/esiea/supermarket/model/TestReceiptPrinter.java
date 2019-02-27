package fr.esiea.supermarket.model;


import fr.esiea.supermarket.ReceiptPrinter;
import fr.esiea.supermarket.model.Offers.SpecialOfferType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestReceiptPrinter {

    @Test
    public void testReceiptPrinter() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product banana = new Product("banana", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        Product lemon = new Product("lemon", ProductUnit.Each);
        Product Jelly = new Product("Jelly", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 1.00);
        catalog.addProduct(banana, 2.00);
        catalog.addProduct(apples, 1.50);
        catalog.addProduct(lemon, 1.00);
        catalog.addProduct(Jelly, 1.50);


        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(banana, 3);
        cart.addItemQuantity(apples, 3);
        cart.addItemQuantity(lemon, 1);
        cart.addItemQuantity(Jelly, 3.5);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, banana, 1);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        ReceiptPrinter defaultReceiptPrinter = new ReceiptPrinter();

        String expectedReceiptPrinter = "toothbrush                          2.00\n" +
                "  1.00 * 2\n" +
                "banana                              6.00\n" +
                "  2.00 * 3\n" +
                "apples                              4.50\n" +
                "  1.50 * 3\n" +
                "lemon                               1.00\n" +
                "Jelly                               5.25\n" +
                "  1.50 * 3.500\n" +
                "1.0% off(banana)                   -0.06\n" +
                "\n" +
                "Total:                             18.69";

        Assertions.assertThat(expectedReceiptPrinter).isEqualTo(defaultReceiptPrinter.printReceipt(receipt));
        Assertions.assertThat(receipt.getItems().size()).isEqualTo(5);
        Assertions.assertThat(new ReceiptPrinter().printReceipt(receipt)).isNotBlank();
    }

    @Test
    public void testReceiptItemQuantityGetter() {
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ReceiptItem item = new ReceiptItem(toothbrush, 2, 3, 6);

        double expectedQuantity = 2;
        Assertions.assertThat(expectedQuantity).as("test to check the good product quantity").isEqualTo(item.getQuantity());
    }

    @Test
    public void testReceiptRemoveItem() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush,1);
        ShoppingCart cart = new ShoppingCart();
        Teller teller = new Teller(catalog);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        cart.removeItem(toothbrush);
        Assertions.assertThat(receipt.getTotalPrice()).as("The Cart is empty").isEqualTo(0);

    }
}
