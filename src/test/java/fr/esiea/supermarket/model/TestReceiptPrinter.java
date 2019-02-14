package fr.esiea.supermarket.model;


import fr.esiea.supermarket.ReceiptPrinter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestReceiptPrinter {

    @Test
    public void testReceiptPrinter(){
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product banana = new Product("banana",ProductUnit.Each);
        Product apples = new Product("apples",ProductUnit.Each);
        Product lemon = new Product("lemon",ProductUnit.Each);

        catalog.addProduct(toothbrush,1.00);
        catalog.addProduct(banana,2.00);
        catalog.addProduct(apples,1.50);
        catalog.addProduct(lemon,1.00);


        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(banana,3);
        cart.addItemQuantity(apples,3);
        cart.addItemQuantity(lemon,1);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, banana, 1);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        ReceiptPrinter defaultReceiptPrinter = new ReceiptPrinter();

        String expectedReceiptPrinter ="toothbrush                          2.00\n" +
                                        "  1.00 * 2\n" +
                                        "banana                              6.00\n" +
                                        "  2.00 * 3\n" +
                                        "apples                              4.50\n" +
                                        "  1.50 * 3\n" +
                                        "lemon                               1.00\n"+
                                        "1.0% off(banana)                   -0.06\n"+
                                        "\n" +
                                        "Total:                             13.44";

        Assertions.assertThat(expectedReceiptPrinter).isEqualTo(defaultReceiptPrinter.printReceipt(receipt));
        Assertions.assertThat(receipt.getItems().size()).isEqualTo(4);
        Assertions.assertThat(new ReceiptPrinter().printReceipt(receipt)).isNotBlank();
    }


}
