package xyz.andrewkboyd.mitresiphon.tasks;

import org.mitre.taxii.client.HttpClient;
import org.mitre.taxii.messages.xml10.DiscoveryRequest;
import org.mitre.taxii.messages.xml11.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Scheduled tasks for fetching information from TAXII API end points
 */
@Component
public class TaxiiTasks {
    private static final Logger LOG = LoggerFactory.getLogger(TaxiiTasks.class);

    /**
     * Fetch the TAXII information from the MITRE end point
     */
    @Scheduled(fixedRate = 5000)
    public void Fetch() {
        LOG.debug("starting mitre taxii fetch");

        LOG.debug("finished mitre taxii fetch");
    }
}
