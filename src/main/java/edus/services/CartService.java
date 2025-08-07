package edus.services;


import edus.models.Product;
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
        String url = "http://product-service-nodeport:8091/api/products/findById?id=" + id;
        return restTemplate.getForObject(url, Product.class);
    }

    public void addToCart(UUID id) {
       Product fetchedProduct = fetchProduct(id);
        if (fetchedProduct != null) {
            cartManagementService.addToCart(fetchedProduct);
        }
    }

    public ArrayList<Product> getCartItems() {
        return cartManagementService.getCartItems();
    }
}
