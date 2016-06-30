package controllers;

import model.JrRuleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import rule.base.SkRuleMaster;
import rule.base.SkRules;
import rule.common.JsonMapperHelper;
import rule.run.SkRuleRunner;
import service.JrRuleService;
import translate.JrRuleTranslate;
import views.html.index;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject private JrRuleService ruleService;

    public Result index() {
        session().clear(); // reset saved login
        java.util.List<String> ruleNames = ruleService.getRuleNames();
        return ok(index.render(Form.form(model.JrRuleForm.class), ruleNames));
    }

    public Result doRuleAction() {

        String[] postAction = request().body().asFormUrlEncoded().get("action");
        if (postAction == null || postAction.length == 0) {
            return badRequest("You must provide a valid action");
        } else {
            String action = postAction[0];
            switch (action) {
            case "Save":
                return _saveRule();

            case "Run":
                return _runRule();

            default:
                return badRequest("This action is not allowed");
            }
        }
    }

    private Result _saveRule() {
        play.data.Form<model.JrRuleForm> form = play.data.Form.form(model.JrRuleForm.class).bindFromRequest();
        if (form.hasErrors()) {
            List<String> ruleNames = ruleService.getRuleNames();
            return badRequest(index.render(form, ruleNames));
        } else {
            JrRuleForm ruleForm = form.get();
            entity.JrRule rule = JrRuleTranslate.toJrRule(ruleForm);
            ruleService.save(rule);
            return redirect(routes.Application.index());
        }
    }

    private Result _runRule() {
        play.data.Form<model.JrRuleForm> form = play.data.Form.form(model.JrRuleForm.class).bindFromRequest();
        if (form.hasErrors()) {
            List<String> ruleNames = ruleService.getRuleNames();
            return badRequest(index.render(form, ruleNames));
        } else {
            JrRuleForm ruleForm = form.get();
            String ruleJson = ruleForm.getRule();
            SkRules rules = JsonMapperHelper.buildRules(ruleJson);
            SkRuleMaster master = new SkRuleMaster.Builder()
                            .addRules(rules).build();
            master.getRuleRunner().runRuleRef(ruleForm.getRuleName());

            return redirect(routes.Application.index());
        }
    }
}
