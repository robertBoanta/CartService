package edus.controllers;

import edus.models.Product;
import edus.services.CartService;
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
    public CollectionModel<Product> getCartItems() {
        ArrayList<Product> cartItems = cartService.getCartItems();

        return CollectionModel.of(cartItems)
                .add(linkTo(methodOn(CartController.class).getCartItems()).withSelfRel())
                .add(linkTo(CartController.class).slash("addToCart").withRel("addToCart"));
    }

    @PostMapping("/addToCart")
    public CollectionModel<Product> addToCart(@RequestParam(name = "id", required = true) UUID id) {
        cartService.addToCart(id);

        return CollectionModel.of(cartService.getCartItems())
                .add(linkTo(methodOn(CartController.class).addToCart(id)).withSelfRel())
                .add(linkTo (CartController.class).slash("getCartItems").withRel("addToCart"));
    }
}
