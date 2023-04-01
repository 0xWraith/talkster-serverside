package com.server.talkster.repositories;

import com.server.talkster.models.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileReferenceRepository extends JpaRepository<FileReference, Long> {
    FileReference findFirstByName(String name);
    FileReference findFirstByID(long id);
    void removeAllByID(long id);
}
