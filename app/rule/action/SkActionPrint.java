package rule.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rule.run.SkRuleRunner;

public class SkActionPrint extends SkAction {

    private static final Logger log = LoggerFactory.getLogger(SkActionPrint.class);

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String inMessage) {
        message = inMessage;
    }

    @Override
    public void run(SkRuleRunner inRunner) {
        // Is this a macro?
        String newMessage = (String)inRunner.getValue(this.message, this.message);
        // Does this have embedded macros in the format '${macro}'
        newMessage = inRunner.expandMacros(newMessage);
        log.info(newMessage);
    }
}
