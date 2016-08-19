package rubiconproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        GenericGroovyApplicationContext applicationContext = new GenericGroovyApplicationContext();
        injectProperties(args, applicationContext);
        Worker worker = applicationContext.getBean("mainWorker",  Worker.class);
        log.info("start");
        worker.start();
    }

    private static void injectProperties(String[] args, GenericGroovyApplicationContext applicationContext){
        if (args.length != 2){
            throw new IllegalArgumentException("Incorrect number of arguments");
        }
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.setProperty("input.directory", args[0]);
        properties.setProperty("output.file", args[1]);
        propertyPlaceholderConfigurer.setProperties(properties);
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        applicationContext.addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);
        applicationContext.load("classpath:beans.groovy");
        applicationContext.refresh();
    }
}
