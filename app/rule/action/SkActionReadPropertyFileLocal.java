package rule.action;

import controllers.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rule.action.enums.SkActionContext;
import rule.run.SkRuleRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Read a property file into the GLOBAL Context.
 */
public class SkActionReadPropertyFileLocal extends SkAction {

    private static final Logger log = LoggerFactory.getLogger(SkActionReadPropertyFileLocal.class);

    private String filename;

    @Override
    public void run(final SkRuleRunner inRunner) {
        try {
            filename = (String)inRunner.getValue(filename, filename);

            log.info("=========================================");
            log.info("filename : " + filename);
            log.info("=========================================");
            final ClassPathResource resource = new ClassPathResource(filename);
            final File file = resource.getFile();
            final Properties properties = new Properties();
            properties.load(new FileReader(file));
            properties.stringPropertyNames()
                      .forEach(name -> {
                          final String value = properties.getProperty(name);
                          inRunner.setValue(name.toUpperCase(), value);
                      });
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String inFilename) {
        filename = inFilename;
    }

    public SkActionContext getActionContext() {
        return SkActionContext.NOW;
    }
}
