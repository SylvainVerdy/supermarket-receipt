package fr.esiea.supermarket.web.spring;


import java.util.Collection;
import fr.esiea.supermarket.model.BasicCatalog;
import fr.esiea.supermarket.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample web application.<br/>
 * Run {@link #main(String[])} to launch.
 */
@SpringBootApplication
@RestController
public class SpringWebApplication {

    private SupermarketCatalog catalog = new BasicCatalog();
    private ShoppingCart cart  = new ShoppingCart();
    Teller teller = new Teller(catalog);


    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class);
    }

    @RequestMapping("/products")
    public Collection<Product> listSellableProducts(){
        return catalog.getProducts();
    }

    @RequestMapping("/products/add")
    public Product addProduct(@RequestParam("name") String name, @RequestParam("unit") String unit, @RequestParam("price") String price){
        ProductUnit unit1 = null;
        Double price1 = Double.parseDouble(price);
        Product product = null;

        if(unit.equals("Kilo"))
            unit1 = ProductUnit.Kilo;
        else
            unit1 = ProductUnit.Each;

        product = new Product(name, unit1);
        catalog.addProduct(product, price1);

        return product;
    }

    @RequestMapping("/products/delete")
    public void deleteProduct(@RequestParam("name") String name){
        catalog.deleteProduct(name);
    }

    @RequestMapping("/specialoffer/add")
    public String addSpecialOffer(@RequestParam("specialoffer") String specialOfferStr, @RequestParam("productname") String productName, @RequestParam("argument") String argumentStr){
        SpecialOfferType specialOffer = null;

        Double argument = Double.parseDouble(argumentStr);

        Product product = catalog.getProductByName(productName);

        if(specialOfferStr.equals("ThreeForTwo"))
            specialOffer = SpecialOfferType.ThreeForTwo;
        else if(specialOfferStr.equals("TenPercentDiscount"))
            specialOffer = SpecialOfferType.TenPercentDiscount;
        else if(specialOfferStr.equals("TwoForAmount"))
            specialOffer = SpecialOfferType.TwoForAmount;
        else if(specialOfferStr.equals("FiveForAmount"))
            specialOffer = SpecialOfferType.FiveForAmount;

        teller.addSpecialOffer(specialOffer, product, argument);

        return specialOfferStr;
    }
}
