package org.jeka.demowebinar1no_react.services;

import lombok.SneakyThrows;
import org.jeka.demowebinar1no_react.model.LoadedDocument;
import org.jeka.demowebinar1no_react.repo.DocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class DocumentLoaderService implements CommandLineRunner {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private ResourcePatternResolver resolver;

    @Autowired
    private VectorStore vectorStore;

    @Override
    public void run(String... args) throws Exception {
        loadDocuments();
    }

    @SneakyThrows
    public void loadDocuments() {
        List<Resource> resourceList = Arrays.stream(resolver.getResources("classpath:/knowledgebase/**/*.txt")).toList();

        resourceList.stream()
                .map(resource -> Pair.of(resource, calcContentHash(resource)))
                .filter(pair -> !repository.existsByFilenameAndContentHash(pair.getFirst().getFilename(), pair.getSecond()))
                .forEach(pair -> {
                    List<Document> documents = new TextReader(pair.getFirst()).get();
                    TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder().withChunkSize(500).build();
                    List<Document> chuncks = tokenTextSplitter.apply(documents);
                    vectorStore.accept(chuncks);

                    LoadedDocument loadedDocument = LoadedDocument.builder()
                            .documentType("txt")
                            .chunkCount(chuncks.size())
                            .filename(pair.getFirst().getFilename())
                            .contentHash(pair.getSecond())
                            .build();

                    repository.save(loadedDocument);
                });
    }

    @SneakyThrows
    private String calcContentHash(Resource resource) {
        return DigestUtils.md5DigestAsHex(resource.getInputStream());
    }

}
