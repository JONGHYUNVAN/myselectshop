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
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    @PostMapping("/products")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.create(productRequestDto, userDetails.getUser());
    }

    @PostMapping("/products/{productId}/folder")
    public void addFolder(@PathVariable Long productId, @RequestParam Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.addFolder(productId,folderId,userDetails.getUser());
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductMyPriceRequestDto productMyPriceRequestDto) {
        return service.update(id, productMyPriceRequestDto);
    }

    @GetMapping("products")
    public Page<ProductResponseDto> getProducts(
            @ModelAttribute PaginationDto paginationDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.getProducts(userDetails.getUser(), paginationDto);
    }

    @GetMapping("/folders/{folderId}/products")
    public Page<ProductResponseDto> getProductsInFolder(
            @ModelAttribute PaginationDto paginationDto,
            @PathVariable Long folderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.getProductsInFolder(userDetails.getUser(),folderId, paginationDto);
    }
}
