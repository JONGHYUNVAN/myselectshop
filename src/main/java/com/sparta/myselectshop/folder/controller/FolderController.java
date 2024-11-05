package com.sparta.myselectshop.folder.controller;

import com.sparta.myselectshop.folder.dto.FolderRequestDto;
import com.sparta.myselectshop.folder.dto.FolderResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService service;

    @PostMapping()
    public void create(@RequestBody FolderRequestDto folderRequestDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = folderRequestDto.getFolderNames();
        service.createByNames(folderNames, userDetails.getUser());
    }

    @GetMapping()
    public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return service.getFolders(userDetails.getUser());
    }
}
