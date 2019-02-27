package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.Offers.*;
import fr.esiea.supermarket.model.Offers.Offer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.esiea.supermarket.model.Offers.SpecialOfferType.*;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        if (offerType == FiveForAmount) {
            this.offers.put(product, new FiveForAmountOffer(product, argument));
        } else if (offerType == ThreeForTwo) {
            this.offers.put(product, new ThreeForTwoOffer(product, argument));
        } else if (offerType == TwoForAmount) {
            this.offers.put(product, new TwoForAmountOffer(product, argument));
        } else if (offerType == TenPercentDiscount) {
            this.offers.put(product, new TenPercentDiscount(product, argument));
        }else if (offerType == TenPercentForEachOneOfThem) {
            this.offers.put(product, new TenPercentForEachOneOfThem(product, argument));
        }
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
