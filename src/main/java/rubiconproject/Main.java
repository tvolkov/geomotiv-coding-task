package rubiconproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  *I decided to give up the idea of using groovy config, since there're a few things which groovy config
 * is missing, comparing to xml.
 * For instance, I didn't find a way to declare bean aliases in groovy.
 * Also, intellij idea doesn't recognize bean definitions written in groovy, which is inconvenient.
 *
 * Java/Annotation config is something  don't like, because I think it's good to completely separate
 * code and configuration - that's where groovy/xml config fits perfectly
 *
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        Worker worker = applicationContext.getBean("mainWorker",  Worker.class);
        validateArguments(args);
        log.info("start");
        worker.start(args[0], args[1]);
    }

    private static void validateArguments(String[] args) {
        if (args.length != 2){
            throw new IllegalArgumentException("Incorrect number of arguments");
        }
    }
}
