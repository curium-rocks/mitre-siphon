package rocks.curium.mitresiphon.tasks.helpers;

import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.HttpModifiedCheck;

@Service
public class HttpModifiedCheckImpl implements HttpModifiedCheck {

  private final WebClient.Builder webClientBuilder;

  public HttpModifiedCheckImpl(WebClient.Builder builder) {
    webClientBuilder = builder;
  }

  /**
   * Determine if the resource located at the URI has been modified since the last fetch time by
   * inspect HTTP headers.
   *
   * @param lastFetchTime time of last change, if null we haven't received a change
   * @param resourceUri {URI} specifies the location of the resource
   * @return {boolean} true if new data is available, false otherwise
   */
  @Override
  public CompletableFuture<Boolean> isNewDataAvailable(
      ZonedDateTime lastFetchTime, URI resourceUri) {
    CompletableFuture<Boolean> fut = new CompletableFuture<>();
    Disposable disposable =
        webClientBuilder
            .build()
            .head()
            .uri(resourceUri)
            .exchangeToFlux(
                clientResponse -> {
                  clientResponse.headers();
                  // TODO: handle case where server may not send this
                  String lastModified = clientResponse.headers().header("last-modified").get(0);
                  ZonedDateTime lastModifiedParsed =
                      ZonedDateTime.parse(lastModified, DateTimeFormatter.RFC_1123_DATE_TIME);
                  if (lastFetchTime == null || lastModifiedParsed.isAfter(lastFetchTime)) {
                    fut.complete(true);
                  } else {
                    fut.complete(false);
                  }
                  return clientResponse.bodyToFlux(String.class);
                })
            .subscribe();
    fut.whenComplete(
        (resp, err) -> {
          disposable.dispose();
        });
    return fut;
  }
}
