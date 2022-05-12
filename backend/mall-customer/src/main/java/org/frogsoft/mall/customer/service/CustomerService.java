package org.frogsoft.mall.customer.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.common.model.cart.CartItem;
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
import org.frogsoft.mall.customer.repository.CustomerRepository;
import org.frogsoft.mall.customer.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    // 索引数据库仓库持久类
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    // 打包Dto类
    private final CustomerDtoMapper customerDtoMapper;
    private final CartDtoMapper cartDtoMapper;
    private final BankCardInfoDtoMapper bankCardInfoDtoMapper;
    private final AddressInfoDtoMapper addressInfoDtoMapper;

    // 跨模块调用访问其他模块的服务类
    private final ProductClient productClient;
    /*带有“client”的，为后端专属服务调用，直接返回相应model类型*/


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
        CartItem newCartItem = new CartItem()
            .setProduct(productInCartItem)
            .setAddTime(LocalDateTime.now())
            .setAmount(addCartItemRequset.getAmount())
            .setRemarks(addCartItemRequset.getRemarks());

        // 购物车项目加入当前Customer的购物车列表
        List<CartItem> currCart = customer.getCartItemList();
        currCart.add(newCartItem);

        // 数据库保存修改
        Customer newCustomer = customerRepository.save(customer.setCartItemList(currCart));

        return cartDtoMapper.toCartDto(newCustomer);
    }

}
