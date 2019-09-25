package com.itkee.webmagic;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@Configuration
public class SaticScheduleTask {
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {
        Spider.create(new MyCnblogsSpider()).addUrl("http://gupiao.zbmf.com/match/267/?order=0")
                .addPipeline(new MyPipeline()).run();
    }
}
