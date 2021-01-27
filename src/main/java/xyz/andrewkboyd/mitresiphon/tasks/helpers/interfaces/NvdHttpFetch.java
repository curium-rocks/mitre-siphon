package xyz.andrewkboyd.mitresiphon.tasks.helpers.interfaces;

import xyz.andrewkboyd.mitresiphon.generated.models.Nvd_cve_feed_json_1_1;

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
