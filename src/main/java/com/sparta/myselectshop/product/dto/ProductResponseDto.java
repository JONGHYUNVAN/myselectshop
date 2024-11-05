package com.sparta.myselectshop.product.dto;

import com.sparta.myselectshop.folder.dto.FolderResponseDto;
import com.sparta.myselectshop.product.entity.Product;
import com.sparta.myselectshop.product.entity.ProductFolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private String link;
    private String image;
    private Integer lprice;
    private Integer myPrice;

    private List<FolderResponseDto> folderResponseDtoList = new ArrayList<>();

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.link = product.getLink();
        this.image = product.getImage();
        this.lprice = product.getLprice();
        this.myPrice = product.getMyPrice();
        for(ProductFolder productFolder: product.getProductFolderList()){
            folderResponseDtoList.add(new FolderResponseDto(productFolder.getFolder()));
        }
    }
}