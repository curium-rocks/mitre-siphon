package rocks.curium.mitresiphon.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rocks.curium.mitresiphon.tasks.NVDFetchTask;

@Configuration
public class QuartzConfig {
  private static final String NVD_CVE_RECENT_JOB_ID = "NVDCVERecent";
  private static final String NVD_CVE_MODIFIED_JOB_ID = "NVDCVEModified";
  private static final String NVD_CVE_COMPLETE_JOB_ID = "NVDCVEComplete";

  @Bean
  public JobDetail fetchNVDCVERecent() {
    return JobBuilder.newJob(NVDFetchTask.class)
        .withIdentity(NVD_CVE_RECENT_JOB_ID)
        .usingJobData(NVDFetchTask.URL_PARM_NAME, NVDFetchTask.RECENT_URL)
        .usingJobData(NVDFetchTask.KAFKA_TOPIC_PARM_NAME, NVDFetchTask.RECENT_KAFKA_TOPIC)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger fetchNVDCVERecentTrigger(@Qualifier("fetchNVDCVERecent") JobDetail jobDetails) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetails)
        .withIdentity(NVD_CVE_RECENT_JOB_ID)
        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
        .startNow()
        .build();
  }

  @Bean
  public JobDetail fetchNVDCVEModified() {
    return JobBuilder.newJob(NVDFetchTask.class)
        .withIdentity(NVD_CVE_MODIFIED_JOB_ID)
        .usingJobData(NVDFetchTask.URL_PARM_NAME, NVDFetchTask.MODIFIED_URL)
        .usingJobData(NVDFetchTask.KAFKA_TOPIC_PARM_NAME, NVDFetchTask.MODIFIED_KAFKA_TOPIC)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger fetchNVDCVEModifiedTrigger(
      @Qualifier("fetchNVDCVEModified") JobDetail jobDetails) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetails)
        .withIdentity(NVD_CVE_MODIFIED_JOB_ID)
        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
        .startNow()
        .build();
  }

  @Bean
  public JobDetail fetchNVDCVEComplete() {
    return JobBuilder.newJob(NVDFetchTask.class)
        .withIdentity(NVD_CVE_COMPLETE_JOB_ID)
        .usingJobData(NVDFetchTask.URL_PARM_NAME, NVDFetchTask.COMPLETE_URL)
        .usingJobData(NVDFetchTask.KAFKA_TOPIC_PARM_NAME, NVDFetchTask.COMPLETE_KAFKA_TOPIC)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger fetchNVDCVECompleteTrigger(
      @Qualifier("fetchNVDCVEComplete") JobDetail jobDetails) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetails)
        .withIdentity(NVD_CVE_COMPLETE_JOB_ID)
        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
        .startNow()
        .build();
  }
}
