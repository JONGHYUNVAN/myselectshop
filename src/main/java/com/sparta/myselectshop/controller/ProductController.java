package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.dto.PaginationDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PostMapping()
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.create(productRequestDto, userDetails.getUser());
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductMyPriceRequestDto productMyPriceRequestDto) {
        return service.update(id, productMyPriceRequestDto);
    }

    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @ModelAttribute PaginationDto paginationDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.getProducts(userDetails.getUser(), paginationDto);
    }
}
