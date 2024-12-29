package com.MarketSystem.services;

import com.MarketSystem.models.Cart;
import com.MarketSystem.models.CartItem;
import com.MarketSystem.models.Product;

import com.MarketSystem.repository.CartRepository;
import com.MarketSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart addItemToCart(String cartId, String productId, int quantity) {
        // Verifique se o carrinho existe
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Verifique se o produto existe
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Adiciona o item ao carrinho
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.calculateSubtotal();

        // Verifica se o item já existe no carrinho
        boolean itemExists = false;
        for (CartItem existingItem : cart.getItems()) {
            if (existingItem.getProduct().getId().equals(productId)) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                existingItem.calculateSubtotal();
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cart.getItems().add(cartItem);
        }

        // Recalcula o total do carrinho
        cart.calculateTotal();

        // Salva o carrinho atualizado
        return cartRepository.save(cart);
    }

    public void checkout(String cartId) {
        // Recupera o carrinho
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Valida o estoque antes de finalizar a compra
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }
        }

        // Simula o processo de pagamento
        boolean paymentSuccessful = processPayment(cart);
        if (!paymentSuccessful) {
            throw new RuntimeException("Pagamento falhou.");
        }

        // Atualiza o estoque dos produtos
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            int newStock = product.getStock() - cartItem.getQuantity();
            product.setStock(newStock);
            productRepository.save(product); // Atualiza o produto no estoque
        }

        // Marca o carrinho como pago e finaliza
        cart.setStatus("COMPLETED");
        cartRepository.save(cart); // Salva o carrinho finalizado
    }

    private boolean processPayment(Cart cart) {
        // Simulação de pagamento bem-sucedido
        return true;
    }


    public Cart removeItemFromCart(String cartId, String productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return null; // Se o carrinho não for encontrado
        }

        // Encontre o item no carrinho
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (itemToRemove != null) {
            cart.getItems().remove(itemToRemove); // Remove o item
            cart.calculateTotal(); // Recalcula o total do carrinho
            cartRepository.save(cart); // Salva o carrinho atualizado
        }
        return cart;
    }

    public Cart removeQuantityFromCart(String cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return null; // Se o carrinho não for encontrado
        }

        // Encontre o item no carrinho
        CartItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (itemToUpdate != null) {
            int newQuantity = itemToUpdate.getQuantity() - quantity;

            if (newQuantity <= 0) {
                cart.getItems().remove(itemToUpdate); // Remove o item se a quantidade for 0 ou negativa
            } else {
                itemToUpdate.setQuantity(newQuantity); // Atualiza a quantidade do item
                itemToUpdate.calculateSubtotal(); // Recalcula o subtotal
            }

            cart.calculateTotal(); // Recalcula o total do carrinho
            cartRepository.save(cart); // Salva o carrinho atualizado
        }
        return cart;
    }



}
