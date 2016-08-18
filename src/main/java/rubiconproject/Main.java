package rubiconproject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new GenericGroovyApplicationContext("classpath:beans.groovy");
        Worker worker = applicationContext.getBean("mainWorker",  Worker.class);
        worker.start();
    }
}
