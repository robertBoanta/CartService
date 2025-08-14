package edus.services;


import edus.models.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartService {
    private RestTemplate restTemplate = new RestTemplate();
    private final CartManagementService cartManagementService;

    public CartService(CartManagementService cartManagementService) {
        this.cartManagementService = cartManagementService;
    }


    public Product fetchProduct(UUID id) {
        String url = "http://localhost:8091/api/products/findById?id=" + id;
        return restTemplate.getForObject(url, Product.class);
    }

    public void addToCart(UUID id, HttpServletRequest request) {
       Product fetchedProduct = fetchProduct(id);
        if (fetchedProduct != null) {
            cartManagementService.addToCart(fetchedProduct, request);
        }
    }

    public ArrayList<Product> getCartItems(HttpServletRequest request) {
        return cartManagementService.getCartItems(request);
    }
}
