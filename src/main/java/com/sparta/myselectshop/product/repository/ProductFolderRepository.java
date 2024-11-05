package com.sparta.myselectshop.product.repository;

import com.sparta.myselectshop.folder.entity.Folder;
import com.sparta.myselectshop.product.entity.Product;
import com.sparta.myselectshop.product.entity.ProductFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
    Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);

}
