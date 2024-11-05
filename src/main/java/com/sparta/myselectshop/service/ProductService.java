package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    public List<ProductResponseDto> getProducts(User user) {
        List<Product> productList = repository.findAllByUser(user);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : productList){
            productResponseDtoList.add(new ProductResponseDto(product));
        }

        return productResponseDtoList;
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
