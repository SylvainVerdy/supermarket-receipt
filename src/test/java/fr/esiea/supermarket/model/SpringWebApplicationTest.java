package fr.esiea.supermarket.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringWebApplicationTest {

    @Test
    public void AddProductTest() {
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        Map<Product, Double> items = cart.productQuantities();

        Assertions.assertThat(items.containsKey(toothbrush)).isTrue();

    }

    @Test
    public void DeleteProductTest()  {

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
