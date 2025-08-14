package edus.controllers;

import edus.models.Product;
import edus.services.CartManagementService;
import edus.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartManagementService cartService;

    @Autowired
    private CartService cartService; // Assuming you have this

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestParam String id, HttpServletRequest request) {
        try {
            Product product = cartService.findById(UUID.fromString(id));
            cartService.addToCart(product, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding product to cart");
        }
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<List<Product>> getCartItems(HttpServletRequest request) {
        List<Product> items = cartService.getCartItems(request);
        return ResponseEntity.ok(items);
    }
}
