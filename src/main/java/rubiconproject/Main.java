package rubiconproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class Main {
    // todo support multithreading
    // todo stateless

    /**
     * I have moved progam arguments from the 'args' array to property file.
     * Otherwise, if we would inject the 'args' to application context, then
     * applicationContext.refresh()
     * shoul have been invoked, which would lead to unnecessary prototype bean initialization, will result in
     * exception since some of the prototype beans are defined with the dummy constructor args,
     * which are not supposed to be passed in their constructors
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        Worker worker = applicationContext.getBean("mainWorker",  Worker.class);
        log.info("start");
        worker.start();
    }
}
