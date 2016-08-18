package rubiconproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

@Slf4j
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new GenericGroovyApplicationContext("classpath:beans.groovy");
        Worker worker = applicationContext.getBean("mainWorker",  Worker.class);
        log.info("start");
        worker.start();
    }
}
