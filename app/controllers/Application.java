package controllers;

import model.JrRuleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import rule.base.SkRuleMaster;
import rule.base.SkRules;
import rule.breadcrumbs.SkBreadcrumbs;
import rule.common.JsonMapperHelper;
import rule.run.SkRuleRunner;
import service.JrRuleService;
import translate.JrRuleTranslate;
import views.html.index;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Standard Play main application controller
 */
@Named
public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject private JrRuleService ruleService;

    /**
     * Home page entry point.  Show edit boxes for a new rule, and an associated list of rules.
     */
    public Result index() {
        session().clear(); // reset saved login
        List<String> ruleNames = getRuleNames();
        return ok(index.render(Form.form(model.JrRuleForm.class), ruleNames, null));
    }

    /**
     * Either save a rule or run a rule.
     */
    public Result doRuleAction() {
        String[] postAction = request().body().asFormUrlEncoded().get("action");
        if (postAction == null || postAction.length == 0) {
            return badRequest("You must provide a valid action");
        } else {
            String action = postAction[0];
            if (log.isDebugEnabled()) {
                log.debug("Performing the action '{}'", action);
            }
            switch (action) {
            case "Save":
                return saveRule();

            case "Run":
                return runRule();

            default:
                return badRequest("This action is not allowed");
            }
        }
    }

    /**
     * Persist a rule to the database.
     */
    private Result saveRule() {
        Form<JrRuleForm> form = Form.form(JrRuleForm.class).bindFromRequest();
        if (form.hasErrors()) {
            List<String> ruleNames = getRuleNames();
            if (log.isWarnEnabled()) {
                log.warn("Save failed for some reason : {}", form.globalError());
            }
            return badRequest(index.render(form, ruleNames, null));
        } else {
            JrRuleForm ruleForm = form.get();
            entity.JrRule rule = JrRuleTranslate.toJrRule(ruleForm);
            ruleService.save(rule);
            if (log.isTraceEnabled()) {
                log.trace("Save successful!");
            }
            return redirect(routes.Application.index());
        }
    }

    /**
     * Execute a rule, returning the breadcrumbs as a result.
     */
    private Result runRule() {
        Form<JrRuleForm> form = Form.form(JrRuleForm.class).bindFromRequest();
        if (form.hasErrors()) {
            List<String> ruleNames = ruleService.getRuleNames();
            if (log.isWarnEnabled()) {
                log.warn("Run Rule failed for some reason : {}", form.globalError());
            }
            return badRequest(index.render(form, ruleNames, null));
        } else {
            JrRuleForm ruleForm = form.get();
            String ruleJson = ruleForm.getRule();
            SkRules rules = JsonMapperHelper.buildRules(ruleJson);
            SkRuleMaster master = new SkRuleMaster.Builder()
                            .addRules(rules).build();
            SkRuleRunner runner = master.getRuleRunner();
            runner.runRuleRef(ruleForm.getRuleName());
            SkBreadcrumbs breadcrumbs = runner.getBreadcrumbs();
            String breadcrumbJson = breadcrumbs.toJson();

            List<String> ruleNames = getRuleNames();

            if (log.isTraceEnabled()) {
                log.trace("Run rule successful!");
            }
            return ok(index.render(Form.form(model.JrRuleForm.class), ruleNames, breadcrumbJson));
        }
    }

    /**
     * Used more than once, so 'if you use it twice, use it once.'  Fetch 'all' rule names from
     *  the database.
     */
    private List<String> getRuleNames() {
        List<String> ruleNames = ruleService.getRuleNames();
        if (log.isTraceEnabled()) {
            log.trace("Number of rules read : {}", ruleNames.size());
        }
        return ruleNames;
    }
}
