package com.sparta.myselectshop.folder.service;

import com.sparta.myselectshop.folder.dto.FolderResponseDto;
import com.sparta.myselectshop.folder.entity.Folder;
import com.sparta.myselectshop.user.entity.User;
import com.sparta.myselectshop.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository repository;
    public void createByNames(List<String> folderNames, User user) {
        List<Folder> foldersFromDB = repository.findAllByUserAndNameIn(user,folderNames);
        List<Folder> newFolders = new ArrayList<>();

        for(String folderName : folderNames) {
            if(!existInDB(folderName,foldersFromDB)){
                Folder folder = new Folder(folderName,user);
                newFolders.add(folder);
            }
            else{
                throw new IllegalArgumentException("Existing folder name");
            }
        }

        repository.saveAll(newFolders);
    }

    private boolean existInDB(String folderName, List<Folder> foldersFromDB) {
        for (Folder folder : foldersFromDB) {
            if(folder.getName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> foldersByUser = repository.findAllByUser(user);
        List<FolderResponseDto> responseDtoList = new ArrayList<>();
        for (Folder folder : foldersByUser) {
            responseDtoList.add(new FolderResponseDto(folder));
        }

        return responseDtoList;
    }
}

