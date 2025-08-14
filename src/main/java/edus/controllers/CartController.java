package edus.controllers;

import edus.models.Product;
import edus.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

   private final CartService cartService;

   public CartController(CartService cartService) {
         this.cartService = cartService;
   }

    @GetMapping("/getCartItems")
    public CollectionModel<Product> getCartItems(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        ArrayList<Product> cartItems = cartService.getCartItems();

        return CollectionModel.of(cartItems)
                .add(linkTo(methodOn(CartController.class).getCartItems(null)).withSelfRel())
                .add(linkTo(CartController.class).slash("addToCart").withRel("addToCart"));
    }

    @PostMapping("/addToCart")
    public CollectionModel<Product> addToCart(@RequestParam(name = "id", required = true) UUID id
            , HttpServletRequest request) {

       HttpSession session = request.getSession(true);

        cartService.addToCart(id);

        return CollectionModel.of(cartService.getCartItems())
                .add(linkTo(methodOn(CartController.class).addToCart(id, null)).withSelfRel())
                .add(linkTo (CartController.class).slash("getCartItems").withRel("getCartItems"));
    }
}
