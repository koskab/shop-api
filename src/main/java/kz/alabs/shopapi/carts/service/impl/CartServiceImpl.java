package kz.alabs.shopapi.carts.service.impl;

import kz.alabs.shopapi.carts.dto.*;
import kz.alabs.shopapi.carts.entity.Cart;
import kz.alabs.shopapi.carts.repository.CartRepository;
import kz.alabs.shopapi.carts.service.CartService;
import kz.alabs.shopapi.core.exception.BadRequestException;
import kz.alabs.shopapi.core.exception.NotFoundException;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.items.service.ItemService;
import kz.alabs.shopapi.shops.dto.ShopView;
import kz.alabs.shopapi.shops.entity.Shop;
import kz.alabs.shopapi.shops.mapper.ShopMapper;
import kz.alabs.shopapi.shops.service.ShopService;
import kz.alabs.shopapi.storages.entity.Storage;
import kz.alabs.shopapi.storages.service.StorageService;
import kz.alabs.shopapi.users.entity.User;
import kz.alabs.shopapi.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final ItemService itemService;
    private final UserService userService;
    private final ShopService shopService;
    private final StorageService storageService;

    @Transactional(readOnly = true)
    public CartResponse view(){
        User currentUser = userService.getCurrentUser();
        if(!repository.existsByUser_IdAndIsPaidFalse(currentUser.getId()))
            throw new NotFoundException("Cart is empty");

        List<Long> shopIds = repository.getShopIdsList(userService.getCurrentUser().getId());
        List<CartView> cartViewList = new ArrayList<>();

        ShopView shopView;
        List<CartItem> cartItems;
        List<BigDecimal> cartViewCosts = new ArrayList<>();
        BigDecimal totalCost;

        for(Long shopId : shopIds){
            cartItems = repository.findItemsByUserAndShop(currentUser.getId(), shopId);
            cartViewCosts.add(cartItems.stream().map(CartItem::getCost).reduce(BigDecimal.ZERO, BigDecimal::add));
            shopView = ShopMapper.INSTANCE.toView(shopService.getEntityById(shopId));
            cartViewList.add(new CartView(shopView, cartItems));
        }
        totalCost = cartViewCosts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cartViewList, totalCost);
    }

    @Override
    @Transactional
    public CartEditResponse create(CartCreate cartCreate){
        Shop shop = shopService.getEntityById(cartCreate.getShopId());
        Item item = itemService.getEntityById(cartCreate.getItemId());
        User currentUser = userService.getCurrentUser();
        validateCartCreation(currentUser, shop, item);

        Cart cart = new Cart(currentUser, shop, item, 1, item.getPrice());
        cart = repository.save(cart);

        return new CartEditResponse(cart.getId());
    }

    @Override
    @Transactional
    public CartEditResponse update(CartUpdate cartUpdate){
        if(cartUpdate.getQuantity() < 1)
            throw new BadRequestException("Quantity should be greater than zero");

        Shop shop = shopService.getEntityById(cartUpdate.getShopId());
        Item item = itemService.getEntityById(cartUpdate.getItemId());
        User currentUser = userService.getCurrentUser();
        validateCartUpdate(currentUser, shop, item, cartUpdate.getQuantity());

        Cart cart = repository.findByUser_IdAndShop_IdAndItem_IdAndIsPaidFalse(currentUser.getId(), cartUpdate.getShopId(), cartUpdate.getItemId());
        cart.setQuantity(cartUpdate.getQuantity());
        cart.setCost(cart.getItem().getPrice().multiply(BigDecimal.valueOf(cartUpdate.getQuantity().longValue())));
        cart = repository.save(cart);

        return new CartEditResponse(cart.getId());
    }

    @Override
    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id))
            throw new NotFoundException("Cart not found");

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void purchase(){
        User user = userService.getCurrentUser();
        if(!repository.existsByUser_IdAndIsPaidFalse(user.getId()))
            throw new NotFoundException("Cart is empty");

        List<Cart> carts = repository.findAllByUser_IdAndIsPaidFalse(user.getId());
        BigDecimal cost = carts
                .stream()
                .map(Cart::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if(user.getBalance().compareTo(cost) < 0)
            throw new BadRequestException("Balance is too low");

        user.writeOff(cost);
        Storage storage;
        for(Cart cart : carts){
            storage = storageService.getEntityByShopAndItem(cart.getShop().getId(), cart.getItem().getId());
            if(storage.getCount() < cart.getQuantity())
                throw new BadRequestException("Couldn't purchase, number of items in the storage is too low");

            storage.reduce(cart.getQuantity());
        }

        carts.forEach(Cart::setPurchased);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartHistory> viewHistory(){
        User currentUser = userService.getCurrentUser();
        return repository.findPurchases(currentUser.getId());
    }


    private void validateCartCreation(User user, Shop shop, Item item){
        if(shop == null)
            throw new NotFoundException("Shop not found");

        if(item == null)
            throw new NotFoundException("Item not found");

        if(repository.existsByUserIdAndShopIdAndItemIdAndIsPaidFalse(user.getId(), shop.getId(), item.getId()))
            throw new NotFoundException("Item is already in the cart");

        if(shop.getStorages()
                .stream()
                .anyMatch(storage ->
                        storage.getItem().getId().equals(item.getId()) && storage.getCount() <= 0)) {
            throw new BadRequestException("Item is out of stock in the shop");
        }
    }

    private void validateCartUpdate(User user, Shop shop, Item item, Integer quantity){
        if(!repository.existsByUserIdAndShopIdAndItemIdAndIsPaidFalse(user.getId(), shop.getId(), item.getId()))
            throw new NotFoundException("Cart not found");

        if(shop.getStorages()
                .stream()
                .noneMatch(storage ->
                        storage.getItem().getId().equals(item.getId()) && storage.getCount() >= quantity)){
            throw new BadRequestException("Not enough items in the shop");
        }
    }

}