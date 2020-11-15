package nk.gk.wyl.doc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动时间："+new Date());
        System.out.println("doc-to-txt 项目启动成功");
    }
}
