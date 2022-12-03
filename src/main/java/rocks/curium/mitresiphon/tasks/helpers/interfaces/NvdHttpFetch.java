package rocks.curium.mitresiphon.tasks.helpers.interfaces;


import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 *
 */
public interface NvdHttpFetch {
    /**
     *
     * @param resourceId
     * @return
     */
    CompletableFuture<String> fetch(URI resourceId) throws IOException;

}
