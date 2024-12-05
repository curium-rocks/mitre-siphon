package rocks.curium.mitresiphon.tasks.helpers;

import io.github.pixee.security.HostValidator;
import io.github.pixee.security.Urls;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.NvdHttpFetch;

@Service
public class NvdHttpFetchImpl implements NvdHttpFetch {

  private final WebClient.Builder webClientBuilder;

  public NvdHttpFetchImpl(WebClient.Builder builder) {
    webClientBuilder = builder;
  }

  private static String readStringFromURL(String requestURL) throws IOException {
    try (Scanner scanner =
        new Scanner(
            new GZIPInputStream(Urls.create(requestURL, Urls.HTTP_PROTOCOLS, HostValidator.DENY_COMMON_INFRASTRUCTURE_TARGETS).openStream()),
            StandardCharsets.UTF_8.toString())) {
      scanner.useDelimiter("\\A");
      return scanner.hasNext() ? scanner.next() : "";
    }
  }

  /**
   * @param resourceId
   * @return
   */
  @Override
  public CompletableFuture<String> fetch(URI resourceId) throws IOException {
    CompletableFuture<String> fut = new CompletableFuture<>();
    String jsonStr = readStringFromURL(resourceId.toString());
    fut.complete(jsonStr);
    return fut;
  }
}
