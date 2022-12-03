package rocks.curium.mitresiphon.tasks.helpers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.NvdHttpFetch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPOutputStream;

@RunWith(SpringRunner.class)
class NvdHttpFetchImplTests {

    public static MockWebServer mockServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();

    }

    @AfterAll
    static void tearDown() throws IOException {
        mockServer.shutdown();
    }

    @Test
    void providesUncompressedString() throws IOException, ExecutionException, InterruptedException {
        NvdHttpFetch fetch = new NvdHttpFetchImpl(WebClient.builder());
        String testStr = "test test test test test test test test";
        byte[] result;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(testStr.length())) {
            GZIPOutputStream gzipOS = new GZIPOutputStream(bos);
            gzipOS.write(testStr.getBytes(StandardCharsets.UTF_8));
            gzipOS.flush();
            gzipOS.close();
            result = bos.toByteArray();
        }


        mockServer.enqueue(new MockResponse()
                .setBody(new Buffer().write(result)));

        String url = String.format("http://localhost:%s/fetchStream",
                mockServer.getPort());

        String response = fetch.fetch(URI.create(url)).get();
        Assertions.assertEquals(testStr, response);
    }
}
