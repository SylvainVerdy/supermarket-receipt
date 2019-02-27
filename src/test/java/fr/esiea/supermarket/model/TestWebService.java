package fr.esiea.supermarket.model;



import fr.esiea.supermarket.web.spring.SpringWebApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.view.RedirectView;


import javax.validation.constraints.Null;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestWebService {

    private SupermarketCatalog catalog = new BasicCatalog();
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

}
