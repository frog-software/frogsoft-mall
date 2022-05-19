package org.frogsoft.mall.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.customer.controller.client.ProductClient;
import org.frogsoft.mall.customer.controller.request.AddCartItemRequset;
import org.frogsoft.mall.customer.dto.AddressInfoDtoMapper;
import org.frogsoft.mall.customer.dto.BankCardInfoDtoMapper;
import org.frogsoft.mall.customer.dto.CartDto;
import org.frogsoft.mall.customer.dto.CartDtoMapper;
import org.frogsoft.mall.customer.dto.CustomerDtoMapper;
import org.frogsoft.mall.customer.repository.*;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    // 索引数据库仓库持久类
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;
    private final BankCardRepository bankCardRepository;
    private final ProductRepository productRepository;

    // 打包Dto类
    private final CustomerDtoMapper customerDtoMapper;
    private final CartDtoMapper cartDtoMapper;
    private final BankCardInfoDtoMapper bankCardInfoDtoMapper;
    private final AddressInfoDtoMapper addressInfoDtoMapper;

    // 跨模块调用访问其他模块的服务类
    private final ProductClient productClient;

    /*带有“client”的，为后端专属服务调用，直接返回相应model类型*/
    public Customer getCustomerClient(String username){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));

        return customerRepository.findByUser(user)
            .orElseThrow(() -> new NotFoundException("Customer not found."));
    }

    public Customer getCustomerClient(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found."));
    }


    /*提供给本模块Controller的服务*/
    // 示例：新建购物车项目
    public CartDto saveNewCartItem(AddCartItemRequset addCartItemRequset, String username){

        // 通过用户名查找User
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
        // 通过User查找相应Customer
        Customer customer = customerRepository.findByUser(user)
            .orElseThrow(() -> new NotFoundException("Customer not found."));

        // 跨模块调用获得Product
        Product productInCartItem = productClient.getProduct(addCartItemRequset.getProductID());

        // 新建购物车项目
        // 购物车项目写入数据库（这一步必须，否则将无法赋予Customer的外键）
        CartItem newCartItem = cartItemRepository.save(new CartItem()
            .setProduct(productInCartItem)
            .setAddTime(LocalDateTime.now())
            .setAmount(addCartItemRequset.getAmount())
            .setRemarks(addCartItemRequset.getRemarks()));

        // 购物车项目加入当前Customer的购物车列表
        Set<CartItem> currCart = customer.getCartItemList();
        currCart.add(newCartItem);

        // 数据库保存修改
        Customer newCustomer = customerRepository.save(customer.setCartItemList(currCart));

        return cartDtoMapper.toCartDto(newCustomer);
    }

    //删除购物车中商品
    public void deleteCartItem(AddCartItemRequset addCartItemRequset, String username,int index){
        // 通过用户名查找User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        // 通过User查找相应Customer
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Customer not found."));
        Product productInCartItem = productClient.getProduct(addCartItemRequset.getProductID());
        try {
            ArrayList<CartItem> cartItemList = cartItemRepository.findAllByUsername(username);
            CartItem targetCartItem = cartItemList.get(index);


            targetCartItem.setProduct(null);
            CartItem savedCartItem = cartItemRepository.save(targetCartItem);
            cartItemRepository.delete(savedCartItem);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("comment not found.");
        }

    }

    //删除购物车
    public void deleteCart(String username, UserDetail authenticateUser){
        CartItem currCart = cartItemRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Cart not Found"));
        currCart.setProduct(null);

        CartItem savedCart = cartItemRepository.save(currCart);
        cartItemRepository.delete(savedCart);
    }

    //修改购物车中商品
    public CartDto modifyCart(AddCartItemRequset addCartItemRequset, String username,int index){
        // 通过用户名查找User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        // 通过User查找相应Customer
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Customer not found."));
        Product productInCartItem = productClient.getProduct(addCartItemRequset.getProductID());
        try {
            ArrayList<CartItem> cartItemList = cartItemRepository.findAllByUsername(username);
            CartItem targetCartItem = cartItemList.get(index);
            // TODO：比对user是否有修改权限
            CartItem savedCartItem = cartItemRepository.save(targetCartItem
                    .setProduct(productInCartItem)
                    .setAddTime(LocalDateTime.now())
                    .setAmount(addCartItemRequset.getAmount())
                    .setRemarks(addCartItemRequset.getRemarks()));
            return cartDtoMapper.toCartDto(savedCartItem);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("cartItem not found.");
        }
    }

    //查找所有购物车商品
    public ArrayList<CartDto> getAllProductsInCartByOwner(String username){

        return cartItemRepository.findAllByUsername(username)
                .stream()
                .map(cartDtoMapper::toCartDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //查找单个购物车商品
    public CartDto getSingleOrder(String username, int index){
        // 通过用户名查找User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        // 通过User查找相应Customer
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Customer not found."));

        try {
            ArrayList<CartItem> cartItemList = cartItemRepository.findAllByUsername(username);
            CartItem targetCartItem = cartItemList.get(index);
            return cartDtoMapper.toCartDto(targetCartItem);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("comment not found.");
        }
    }

}
