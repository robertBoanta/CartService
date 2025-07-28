package edus.services;

import edus.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;

@Service
@SessionScope
public class CartManagementService {
   private ArrayList<Product> cartItems;

    public ArrayList<Product> getCartItems() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        return cartItems;
    }

    public void addToCart(Product product) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(product);
    }

    public void removeFromCart(Product product) {
        if (cartItems != null) {
            cartItems.remove(product);
        }
    }
}
