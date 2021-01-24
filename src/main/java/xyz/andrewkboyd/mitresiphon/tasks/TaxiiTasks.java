package xyz.andrewkboyd.mitresiphon.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


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
    public void Fetch(){
        LOG.debug("starting fetch");
        LOG.debug("finished fetch");
    }
}
