package com.server.talkster.services;

import com.server.talkster.models.FileReference;
import com.server.talkster.repositories.FileReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@Transactional
public class FileService {

    private final String FOLDER_PATH;
    private final FileReferenceRepository fileReferenceRepository;

    @Autowired
    public FileService(FileReferenceRepository fileReferenceRepository)
    {
        FOLDER_PATH = System.getenv("FOLDER_PATH");
        this.fileReferenceRepository = fileReferenceRepository;
    }

    public FileReference saveFile(FileReference fileReference){
        return fileReferenceRepository.save(fileReference);
    }

    public FileReference findFileByID(long id){
        return fileReferenceRepository.findFirstByID(id);
    }
    public FileReference findFileByName(String name) {return fileReferenceRepository.findFirstByName(name);}

    public void removeFileByID(long id){
        fileReferenceRepository.removeAllByID(id);
    }

    public void deleteFileFromPath(String filename){
        File file = new File(FOLDER_PATH+"\\"+filename);
        file.delete();
    }

    public FileReference uploadFile(MultipartFile file) throws IllegalStateException, IOException {
        String extension = file.getOriginalFilename().split("\\.")[1].toLowerCase();
        String filename = UUID.randomUUID()+"."+extension;
        file.transferTo(new File(FOLDER_PATH +"\\"+ filename));
        FileReference fileReference = new FileReference(filename, file.getContentType());
        saveFile(fileReference);
        return fileReference;
    }

    public byte[] downloadFile(String filename) throws IllegalStateException, IOException {
        byte[] file = Files.readAllBytes( new File(FOLDER_PATH+"\\"+filename).toPath());
        return file;
    }
}
