package fr.esiea.supermarket.model.Offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;


public class TenPercentForEachOneOfThem implements Offer {

    private final Product product;
    private final double argument;

    public TenPercentForEachOneOfThem(Product product, double argument) {
        this.product = product;
        this.argument = argument;
    }

    @Override
    public Discount getDiscount(Product p, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        double discountValue = ((unitPrice*0.9)+(quantityAsInt-1)*unitPrice);
        return new Discount(p, argument + "% off", unitPrice*quantity - discountValue);
    }
}
