package fr.esiea.supermarket.model;

import fr.esiea.supermarket.ReceiptPrinter;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class testReceiptPrinter {

    public Receipt createAnReceipt(){
        //TODO complete the test
        SupermarketCatalog catalog = new FakeCatalog();
        Teller teller = new Teller(catalog);

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product banana = new Product("banana",ProductUnit.Each);
        Product apples = new Product("apples",ProductUnit.Each);
        catalog.addProduct(banana,2.00);
        catalog.addProduct(toothbrush,1.00);
        catalog.addProduct(apples,1.50);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(banana,3);
        cart.addItemQuantity(apples,3);
        Receipt receipt = teller.checksOutArticlesFrom(cart);


        return receipt;
    }
    @Test
    public void testReceiptPrinter(){
        Receipt receipt = createAnReceipt();
        assertThat(new ReceiptPrinter().printReceipt(receipt)).isNotBlank();
    }
}
