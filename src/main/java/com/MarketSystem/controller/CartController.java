package com.MarketSystem.controller;

import com.MarketSystem.models.Cart;
import com.MarketSystem.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> createCart() {
        Cart cart = new Cart();
        cart.setStatus("PENDING");
        return ResponseEntity.ok(cartService.saveCart(cart));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Cart> addItem(@PathVariable String cartId, @PathVariable String productId, @RequestParam int quantity) {
        Cart updatedCart = cartService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable String cartId) {
        return cartService.getCartById(cartId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<Void> checkout(@PathVariable String cartId) {
        try {
            cartService.checkout(cartId);  // Finaliza o pagamento e atualiza o estoque
            return ResponseEntity.noContent().build();  // Retorna resposta de sucesso
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();  // Retorna erro se o pagamento falhou
        }
    }


    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeItem(@PathVariable String cartId, @PathVariable String productId) {
        Cart updatedCart = cartService.removeItemFromCart(cartId, productId);
        if (updatedCart == null) {
            return ResponseEntity.notFound().build(); // Se o carrinho ou produto não for encontrado
        }
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{cartId}/items/{productId}/remove")
    public ResponseEntity<Cart> removeQuantityFromItem(@PathVariable String cartId, @PathVariable String productId, @RequestParam int quantity) {
        Cart updatedCart = cartService.removeQuantityFromCart(cartId, productId, quantity);
        if (updatedCart == null) {
            return ResponseEntity.notFound().build(); // Se o carrinho ou produto não for encontrado
        }
        return ResponseEntity.ok(updatedCart);
    }

}
