package fr.esiea.supermarket.web.spring;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.esiea.supermarket.ReceiptPrinter;
import fr.esiea.supermarket.model.BasicCatalog;
import fr.esiea.supermarket.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
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
    private Map<Integer, ShoppingCart> paniers = new HashMap<>();
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

    @RequestMapping("/{id}/shoppingcart/add")
    public Product addProductToClientCart(@PathVariable("id") int clientID, @RequestParam("pname") String productName
            , @RequestParam("pquantity") double quantity){

        ShoppingCart cart = paniers.get(clientID);
        ProductUnit unit1 = null;
        Product product = catalog.getProductByName(productName);

        if(cart == null || productName == null)
            return null;

        cart.addItemQuantity(product, quantity);

        return product;
    }

    @RequestMapping("/{id}/shoppingcart/delete")
    public Product deleteProductFromClientCart(@PathVariable("id") int clientID, @RequestParam("pname") String productName){

        ShoppingCart cart = paniers.get(clientID);
        Product p = catalog.getProductByName(productName);

        if(p == null)
            return null;

        cart.removeItem(p);
        return p;
    }


    @RequestMapping("/{id}/passerencaisse")
    public double passerEnCaisse(@PathVariable("id") int clientID){

        ShoppingCart cart = paniers.get(clientID);
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        return receipt.getTotalPrice();
    }

    @RequestMapping("/{id}/passerencaisse2")
    public String passerEnCaisse2(@PathVariable("id") int clientID){

        ShoppingCart cart = paniers.get(clientID);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        ReceiptPrinter printer = new ReceiptPrinter();

        return printer.printReceipt(receipt);
    }

    // to be able to create clients for tests
    @RequestMapping("/client/add/{id}")
    public int addClient(@PathVariable("id") int clientID){
        if(paniers.containsKey(clientID))
            return -1;

        paniers.put(clientID, new ShoppingCart());

        return clientID;
    }
}
