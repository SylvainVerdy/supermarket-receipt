package fr.esiea.supermarket.model.Offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;


public class TenPercentDiscount implements Offer {

    private final Product product;
    private final double argument;

    public TenPercentDiscount(Product product, double argument) {
        this.product = product;
        this.argument = argument;
    }

    @Override
    public Discount getDiscount(Product p, double quantity, double unitPrice) {
        return new Discount(p, argument + "% off", quantity * unitPrice * argument / 100.0);
    }
}
