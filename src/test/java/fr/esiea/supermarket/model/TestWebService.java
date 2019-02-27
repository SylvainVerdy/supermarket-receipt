package fr.esiea.supermarket.model;



import fr.esiea.supermarket.web.spring.SpringWebApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestWebService {

    @Test
    public void TestAddProduct(){

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "kilo", "1.0");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(product.getName().equals("banana")).isTrue();
        Assertions.assertThat(product.getUnit().equals("kilo")).isTrue();

    }
    @Test
    public void testListSellableProducts() {
        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addProduct("banana", "kilo", "1.0");
        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(springWebApplication.listSellableProducts().iterator().next()).isEqualTo(product);
    }

    @Test
    public void TestDeleteProduct() {

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.deleteProduct("banana", "kilo", "1.0");

        Product product = springWebApplication.listSellableProducts().iterator().next();
        Assertions.assertThat(product.getName().equals("banana")).isFalse();
        Assertions.assertThat(product.getUnit().equals("kilo")).isFalse();

    }

    @Test
    public void TestAddSpecialOffer() {

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.addSpecialOffer("TenPercent", "Banana", "10");
        Product product = springWebApplication.addSpecialOffer().iterator().next();
        Assertions.assertThat(springWebApplication.addSpecialOffer().iterator().next()).isEqualTo(product);

    }

    @Test
    public void TestDeleteSpecialOffer() {

        SpringWebApplication springWebApplication = new SpringWebApplication();
        springWebApplication.deleteSpecialOffer("TenPercent", "Banana", "10");
        Product product = springWebApplication.deleteSpecialOffer().iterator().next();
        Assertions.assertThat(deleteSpecialOffer().equals("10")).isFalse();



}
