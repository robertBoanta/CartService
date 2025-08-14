package edus.services;

import edus.models.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartManagementService {

    @SuppressWarnings("unchecked")
    public List<Product> getCartItems(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute("cartItems", cartItems);
        }
        return cartItems;
    }

    public void addToCart(Product product, HttpServletRequest request) {
        List<Product> cartItems = getCartItems(request);
        cartItems.add(product);
        // No need to explicitly save - session automatically persists the reference
    }

    public void removeFromCart(Product product, HttpServletRequest request) {
        List<Product> cartItems = getCartItems(request);
        cartItems.remove(product);
    }
}
