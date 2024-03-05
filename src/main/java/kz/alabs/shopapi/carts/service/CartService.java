package kz.alabs.shopapi.carts.service;

import kz.alabs.shopapi.carts.dto.*;

import java.util.List;


public interface CartService {

    CartEditResponse update(CartUpdate cartUpdate);

    CartEditResponse create(CartCreate cartCreate);

    CartResponse view();

    void delete(Long id);

    void purchase();

    List<CartHistory> viewHistory();

}
