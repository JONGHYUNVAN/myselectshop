package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.PaginationDto;
import com.sparta.myselectshop.dto.ProductMyPriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.*;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductFolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final FolderRepository folderRepository;
    private final ProductFolderRepository productFolderRepository;

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


    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(User user, PaginationDto paginationDto) {
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

        return productList.map(ProductResponseDto::new);
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

    public void addFolder(Long productId, Long folderId, User user) {
        Product product = repository.findById(productId).orElseThrow(()-> new NullPointerException("product not found"));
        Folder folder = folderRepository.findById(folderId).orElseThrow(()-> new NullPointerException("folder not found"));
        if(!product.getUser().getId().equals(user.getId())
        || !folder.getUser().getId().equals(product.getUser().getId())
        ){
            throw new IllegalArgumentException("Not user's product or folder");
        }
        Optional<ProductFolder> overlapFolder = productFolderRepository.findByProductAndFolder(product,folder);
        if(overlapFolder.isPresent()) throw new IllegalArgumentException("Overlapped folder");

        productFolderRepository.save(new ProductFolder(product,folder));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsInFolder(User user, Long folderId, PaginationDto paginationDto) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NullPointerException("folder not found"));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not user's folder");
        }

        Sort.Direction direction = paginationDto.getIsAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, paginationDto.getSortBy());
        Pageable pageable = PageRequest.of(paginationDto.getPage() - 1, paginationDto.getSize(), sort);

        Page<Product> productsInFolder = repository.findAllByUserAndProductFolderList_FolderId(user,folderId, pageable);

        return productsInFolder.map(ProductResponseDto::new);
    }
}
