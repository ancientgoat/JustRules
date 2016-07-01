import conf.AppConf;
import conf.DataConf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
    private ApplicationContext context;

    @Override
    public void onStart(final Application app) {
        context = new AnnotationConfigApplicationContext(AppConf.class, DataConf.class);
    }

    @Override
    public <A> A getControllerInstance(final Class<A> clazz) {
        return context.getBean(clazz);
    }
}
