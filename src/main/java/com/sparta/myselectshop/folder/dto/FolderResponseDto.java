package com.sparta.myselectshop.folder.dto;

import com.sparta.myselectshop.folder.entity.Folder;
import lombok.Getter;

@Getter
public class FolderResponseDto {
    private Long id;
    private String name;

    public FolderResponseDto(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
    }
}