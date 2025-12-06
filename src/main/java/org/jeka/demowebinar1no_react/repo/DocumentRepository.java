package org.jeka.demowebinar1no_react.repo;

import org.jeka.demowebinar1no_react.model.LoadedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<LoadedDocument, Long> {

    boolean existsByFilenameAndContentHash(String filename, String contentHash);

}