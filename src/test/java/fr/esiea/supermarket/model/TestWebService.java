package fr.esiea.supermarket.model;



import fr.esiea.supermarket.web.spring.SpringWebApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.view.RedirectView;


import javax.validation.constraints.Null;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestWebService {

    private SupermarketCatalog catalog = new BasicCatalog();
    private Map<Integer, ShoppingCart> paniers = new HashMap<>();
    Teller teller = new Teller(catalog);
    ShoppingCart cart = paniers.get(1);

    @Test
    public void TestAddProduct(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "kilo", "1.0");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(product.getName().equals("banana")).isTrue();
        Assertions.assertThat(product.getUnit().equals("kilo")).isFalse();

    }
    @Test
    public void testListSellableProducts() {
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "kilo", "1.0");
        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(springWebApplication.listSellableProducts().iterator().next()).isEqualTo(product);
    }

    @Test
    void testRemoveProduct() {
        SpringWebApplication springWebApplication = new SpringWebApplication();
        final Product bagofrice = new Product("frites", ProductUnit.Each);
        springWebApplication.addProduct("frites","kilo", "2.2");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        springWebApplication.deleteProduct("frites");
        Assertions.assertThat(product.getName().equals("frites")).isTrue();
    }

    @Test
    void testAddClient(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","kilo", "2.2");
        springWebApplication.addClient(1);
        Product panier_produit1 = springWebApplication.addProductToClientCart(1,"banana",2);
        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(springWebApplication.addClient(1)).isEqualTo(-1);
    }
    @Test
    void testAddCartClient(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","kilo", "2.2");
        Product product = springWebApplication.addProductToClientCart(1,"frites",2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(product,3);
        Assertions.assertThat(cart.productQuantities()).isNotEmpty();
    }

}
