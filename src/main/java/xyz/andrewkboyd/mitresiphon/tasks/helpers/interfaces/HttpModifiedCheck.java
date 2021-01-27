package xyz.andrewkboyd.mitresiphon.tasks.helpers.interfaces;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Interface establishing contract for determining if a http resources has
 * been modified since the last fetch
 */
public interface HttpModifiedCheck {

    /**
     * Determine if the resource located at the URI
     * has been modified since the last fetch time by inspect HTTP headers.
     * @param lastFetchTime time of last change, if null we haven't received a change
     * @param resourceUri {URI} specifies the location of the resource
     * @return future that resolves with true if there is new data available or false if the data hasn't changed since last checked
     */
    CompletableFuture<Boolean> isNewDataAvailable(ZonedDateTime lastFetchTime, URI resourceUri) throws IOException;
}
