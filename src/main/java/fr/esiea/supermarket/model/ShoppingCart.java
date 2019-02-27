package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.Offers.Offer;
import java.util.*;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    public void removeItem(Product product){
        if (productQuantities.containsKey(product)) {
            // we had to remove the element like this
            Iterator<ProductQuantity> it = items.iterator();
            while(it.hasNext())
            {
                ProductQuantity pq = it.next();
                if(pq.getProduct().getName().equals(product.getName()))
                {
                    it.remove();
                    break;
                }
            }

            productQuantities.remove(product);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                Discount discount = offer.getDiscount(p,quantity,unitPrice);

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
