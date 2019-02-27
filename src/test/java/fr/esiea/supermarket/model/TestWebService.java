package fr.esiea.supermarket.model;



import fr.esiea.supermarket.web.spring.SpringWebApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.view.RedirectView;
import sun.rmi.runtime.NewThreadAction;


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
        springWebApplication.addProduct("banana", "Kilo", "1.0");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(product.getName().equals("banana")).isTrue();
        Assertions.assertThat(product.getUnit().equals("kilo")).isFalse();

    }
    @Test
    public void testListSellableProducts() {
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(springWebApplication.listSellableProducts().iterator().next()).isEqualTo(product);
    }

    @Test
    void testRemoveProduct() {
        SpringWebApplication springWebApplication = new SpringWebApplication();

        springWebApplication.addProduct("frites","Kilo", "2.2");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        springWebApplication.deleteProduct("frites");
        Assertions.assertThat(product.getName().equals("frites")).isTrue();
    }

    @Test
    void testAddClient(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","Kilo", "2.2");
        springWebApplication.addClient(1);
        Product panier_produit1 = springWebApplication.addProductToClientCart(1,"banana",2);
        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(springWebApplication.addClient(1)).isEqualTo(-1);
    }
    @Test
    void testAddCartClient(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","Kilo", "2.2");
        Product product = springWebApplication.addProductToClientCart(1,"frites",2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(product,3);
        Assertions.assertThat(cart.productQuantities()).isNotEmpty();
        Assertions.assertThat(cart.productQuantities()).isNotNull();

    }

    @Test
    void testPassageEnCaisse(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","Kilo", "2.2");
        springWebApplication.addClient(1);
        Product product = springWebApplication.addProductToClientCart(1,"frites",2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(product,3);
        double prixtotal = springWebApplication.passerEnCaisse(1);
        double prix_calcul = 4.4;
        Assertions.assertThat(prixtotal).isEqualTo(prix_calcul);
    }

    @Test
    public void testSpecialOfferTenPercentDiscount(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        springWebApplication.addClient(1);

        springWebApplication.addProductToClientCart(1,"banana",10);
        springWebApplication.addSpecialOffer("TenPercentDiscount", "banana", "1");

        Assertions.assertThat(springWebApplication.passerEnCaisse(1)).isEqualTo(9.9);
    }



    @Test
    public void testSpecialOfferThreeForTwo(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        springWebApplication.addClient(1);

        springWebApplication.addProductToClientCart(1,"banana",10);
        springWebApplication.addSpecialOffer("ThreeForTwo", "banana", "1");

        Assertions.assertThat(springWebApplication.passerEnCaisse(1)).isEqualTo(7.0);
    }

    @Test
    public void testSpecialOfferFiveForAmount(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        springWebApplication.addClient(1);

        springWebApplication.addProductToClientCart(1,"banana",10);
        springWebApplication.addSpecialOffer("FiveForAmount", "banana", "1");

        Assertions.assertThat(springWebApplication.passerEnCaisse(1)).isEqualTo(2.0);
    }

    @Test
    public void testSpecialOfferTwoForAmount(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        springWebApplication.addClient(1);

        springWebApplication.addProductToClientCart(1,"banana",10);
        springWebApplication.addSpecialOffer("TwoForAmount", "banana", "1");

        Assertions.assertThat(springWebApplication.passerEnCaisse(1)).isEqualTo(5.0);
    }

    @Test
    void testDeleteCartClient(){
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("frites","Kilo", "2.2");
        springWebApplication.addClient(1);
        Product product = springWebApplication.addProductToClientCart(1,"frites",2);

        Product p = springWebApplication.deleteProductFromClientCart(1,"frites");

        Assertions.assertThat(springWebApplication.deleteProductFromClientCart(1,"npk")).isNull();

        Assertions.assertThat(springWebApplication.passerEnCaisse(1)).isEqualTo(0);
    }
    
    @Test
    void testProductByName(){
        SupermarketCatalog catalog = new BasicCatalog();
        catalog.addProduct(new Product("frites", ProductUnit.Each), 12);

        Assertions.assertThat(catalog.getProductByName("npk")).isNull();
        Assertions.assertThat(catalog.getProductByName("frites")).isInstanceOf(Product.class);
    }

    @Test
    public void testPasserEnCaisse2(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "Kilo", "1.0");
        springWebApplication.addClient(1);

        springWebApplication.addProductToClientCart(1,"banana",8);

        Assertions.assertThat(springWebApplication.passerEnCaisse2(1)).isEqualTo("banana                              8.00\n" +
                "  1.00 * 8.000\n" +
                "\n" +
                "Total:                              8.00");
    }



}
