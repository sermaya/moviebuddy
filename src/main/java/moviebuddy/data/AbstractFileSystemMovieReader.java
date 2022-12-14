package moviebuddy.data;

import moviebuddy.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public abstract class AbstractFileSystemMovieReader {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String metadata;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        URL metadataUrl = ClassLoader.getSystemResource(metadata);
        if (Objects.isNull(metadataUrl)) {
            throw new FileNotFoundException(metadata);
        }

        if (Files.isReadable(Path.of(metadataUrl.toURI())) == false) {
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        log.info("Destroyed bean");
    }
}
