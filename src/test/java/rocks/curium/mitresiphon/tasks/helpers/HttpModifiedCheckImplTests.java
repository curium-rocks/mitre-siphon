package rocks.curium.mitresiphon.tasks.helpers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.HttpModifiedCheck;

import java.io.IOException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
class HttpModifiedCheckImplTests {

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

    //TODO: refactor tests to use parameters

    @Test
    void returnsTrueWhenProvidedNull() throws ExecutionException, InterruptedException, IOException {
        mockServer.enqueue(new MockResponse()
                .setBody("")
                .addHeader("last-modified", "Wed, 21 Oct 2015 07:28:00 GMT"));
        String url = String.format("http://localhost:%s/returnsTrueWhenProvided",
                mockServer.getPort());
        HttpModifiedCheck modifiedCheck = new HttpModifiedCheckImpl(WebClient.builder());
        Assertions.assertTrue(modifiedCheck.isNewDataAvailable(null, URI.create(url)).get());
    }

    @Test
    void returnsFalseIfEqual() throws IOException, ExecutionException, InterruptedException {
        mockServer.enqueue(new MockResponse()
                .setBody("")
                .addHeader("last-modified", "Wed, 21 Oct 2015 07:28:00 GMT"));
        String url = String.format("http://localhost:%s/returnsFalseIfEqual",
                mockServer.getPort());
        HttpModifiedCheck modifiedCheck = new HttpModifiedCheckImpl(WebClient.builder());
        ZonedDateTime lastCheck = ZonedDateTime.parse("Wed, 21 Oct 2015 07:28:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME);
        Assertions.assertFalse(modifiedCheck.isNewDataAvailable(lastCheck, URI.create(url)).get());
    }

    @Test
    void returnsFalseIfOlder() throws IOException, ExecutionException, InterruptedException {
        mockServer.enqueue(new MockResponse()
                .setBody("")
                .addHeader("last-modified", "Wed, 21 Oct 2015 07:28:00 GMT"));
        String url = String.format("http://localhost:%s/returnsFalseIfEqual",
                mockServer.getPort());
        HttpModifiedCheck modifiedCheck = new HttpModifiedCheckImpl(WebClient.builder());
        ZonedDateTime lastCheck = ZonedDateTime.parse("Wed, 21 Oct 2020 07:28:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME);
        Assertions.assertFalse(modifiedCheck.isNewDataAvailable(lastCheck, URI.create(url)).get());
    }

    @Test
    void returnsTrueIfNewer() throws IOException, ExecutionException, InterruptedException {
        mockServer.enqueue(new MockResponse()
                .setBody("")
                .addHeader("last-modified", "Wed, 21 Oct 2015 07:28:00 GMT"));
        String url = String.format("http://localhost:%s/returnsFalseIfEqual",
                mockServer.getPort());
        HttpModifiedCheck modifiedCheck = new HttpModifiedCheckImpl(WebClient.builder());
        ZonedDateTime lastCheck = ZonedDateTime.parse("Wed, 21 Oct 2020 07:28:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME);
        Assertions.assertFalse(modifiedCheck.isNewDataAvailable(lastCheck, URI.create(url)).get());
    }
}
