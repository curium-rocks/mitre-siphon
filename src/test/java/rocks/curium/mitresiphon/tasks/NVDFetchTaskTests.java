package rocks.curium.mitresiphon.tasks;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import rocks.curium.mitresiphon.dao.ResourceStatDAOImpl;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.HttpModifiedCheck;
import rocks.curium.mitresiphon.tasks.helpers.interfaces.NvdHttpFetch;

import java.io.IOException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
class NVDFetchTaskTests {
    NvdHttpFetch fetch;
    HttpModifiedCheck modifiedCheck;
    KafkaTemplate<String, String> kafkaTemplate;
    JobExecutionContext context;
    ResourceStatDAO resourceDAO;

    public NVDFetchTaskTests() {
        this.fetch = Mockito.mock(NvdHttpFetch.class);
        this.modifiedCheck = Mockito.mock(HttpModifiedCheck.class);
        this.kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        this.context = Mockito.mock(JobExecutionContext.class);
        this.resourceDAO = Mockito.mock(ResourceStatDAOImpl.class);
    }

    @Test
    void doesNotCallFetchWhenNotModified() throws JobExecutionException, IOException {
        URI resourceUri = URI.create("http://localhost/");
        ZonedDateTime dateTime = ZonedDateTime.now();
        Mockito.when(modifiedCheck.isNewDataAvailable(Mockito.any(), Mockito.any()))
                .thenReturn(CompletableFuture.completedFuture(Boolean.FALSE));
        JobDataMap testMap = new JobDataMap();
        testMap.put(NVDFetchTask.URL_PARM_NAME, resourceUri.toString());
        testMap.put(NVDFetchTask.KAFKA_TOPIC_PARM_NAME, "TEST");
        Mockito.when(context.getMergedJobDataMap()).thenReturn(new JobDataMap(testMap));

        NVDFetchTask fetchTask = new NVDFetchTask(kafkaTemplate,modifiedCheck, fetch, resourceDAO);
        fetchTask.executeInternal(context);
        Mockito.verify(fetch, never()).fetch(resourceUri);

    }

    @Test
    void fetchesWhenAvailable() throws IOException, JobExecutionException, InterruptedException {
        URI resourceUri = URI.create("http://localhost/");
        Mockito.when(modifiedCheck.isNewDataAvailable(Mockito.any(), Mockito.any()))
                .thenReturn(CompletableFuture.completedFuture(Boolean.TRUE));
        JobDataMap testMap = new JobDataMap();
        testMap.put(NVDFetchTask.URL_PARM_NAME, resourceUri.toString());
        testMap.put(NVDFetchTask.KAFKA_TOPIC_PARM_NAME, "TEST");
        Mockito.when(context.getMergedJobDataMap()).thenReturn(new JobDataMap(testMap));
        Mockito.when(fetch.fetch(any())).thenReturn(CompletableFuture.completedFuture("TEST"));
        NVDFetchTask fetchTask = new NVDFetchTask(kafkaTemplate,modifiedCheck, fetch, resourceDAO);
        fetchTask.executeInternal(context);
        //TODO: refactor to use awaitility to wait condition with timeout, execute doesn't return future and code behind leverages futures
        Thread.sleep(5000);
        Mockito.verify(fetch, times(1)).fetch(any());

    }
}
