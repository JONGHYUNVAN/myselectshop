package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.PaginationDto;
import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public static final int MIN_MY_PRICE = 100;

    public ProductResponseDto create(ProductRequestDto productRequestDto, User user) {
        Product product = repository.save(new Product(productRequestDto,user));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductMyPriceRequestDto productMyPriceRequestDto) {
        int myPrice = productMyPriceRequestDto.getMyPrice();
        if(myPrice < MIN_MY_PRICE){
            throw new IllegalArgumentException("myPrice must less than " + MIN_MY_PRICE);
        }
        Product product = repository.findById(id).orElseThrow(()-> new NullPointerException("product not found"));
        product.update(productMyPriceRequestDto);

        return new ProductResponseDto(product);
    }

    public Page<Product> getProducts(User user, PaginationDto paginationDto) {
        Sort.Direction direction = paginationDto.getIsAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, paginationDto.getSortBy());
        Pageable pageable = PageRequest.of(paginationDto.getPage() - 1, paginationDto.getSize(), sort);

        UserRoleEnum userRoleEnum = user.getRole();
        Page<Product> productList;

        if (userRoleEnum == UserRoleEnum.USER) {
            productList = repository.findAllByUser(user, pageable);
        } else {
            productList = repository.findAll(pageable);
        }

        return productList;
    }



    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = repository.findById(id).orElseThrow(()-> new NullPointerException("product not found"));
        product.updateByItemDto(itemDto);
    }

    public List<ProductResponseDto> getAllProducts(User user) {
        List<Product> productList = repository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : productList){
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return productResponseDtoList;
    }
}
