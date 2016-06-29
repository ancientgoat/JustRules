package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Application extends Controller {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject private service.JrRuleService ruleService;

    public Result index() {
        session().clear(); //reset saved login

        java.util.List<String> ruleNames = ruleService.getRuleNames();

        return ok(index.render(Form.form(model.JrRuleForm.class), ruleNames));
    }

    public Result addRule() {
        play.data.Form<model.JrRuleForm> form = play.data.Form.form(model.JrRuleForm.class).bindFromRequest();
        if (form.hasErrors()) {
            java.util.List<String> ruleNames = ruleService.getRuleNames();
            return badRequest(index.render(form, ruleNames));
        } else {
            model.JrRuleForm ruleForm = form.get();
            entity.JrRule rule = translate.JrRuleTranslate.toJrRule(ruleForm);
            ruleService.save(rule);
            return redirect(routes.Application.index());
        }
    }

//    public Result getRuleNames() {
//        java.util.List<String> ruleNames = ruleService.getRuleNames();
//        return ok(play.libs.Json.toJson(ruleNames));
//    }

//    public Result login() {
//        final Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
//        if (form.hasErrors()) {
//            return badRequest(index.render(form));
//        }
//        final LoginForm loginData = form.get();
//
//        //Login existing users
//        if (userService.userExists(loginData.getUsername())) {
//            log.info("Trying to login user: {}", loginData.getUsername());
//            if (!userService.login(loginData.getUsername(), loginData.getPassword())) {
//                form.reject("Invalid username-password combination");
//                return badRequest(index.render(form));
//            }
//        } else { //And register non-existing users
//            log.info("Registering user: {}", loginData.getUsername());
//            //userService.register(loginData.getUsername(), loginData.getPassword());
//        }
//
//        //keep track of which user is logged in through session cookies
//        session("username", loginData.getUsername());
//        log.info("user session started: {}", loginData.getUsername());
//        return redirect(routes.Application.prepareBet());
//    }
//
//    public Result prepareBet() {
//        if (!isLoggedIn()) {
//            return redirect(routes.Application.index());
//        }
//
//        final String username = session("username");
//        return ok(newBet.render(username,
//                                userService.getBalance(username),
//                                Form.form(BetForm.class),
//                                null)); //no BetOutcome yet
//    }
//
//    public Result decideBet() {
//        if (!isLoggedIn()) {
//            return redirect(routes.Application.index());
//        }
//
//        final String username = session("username");
//        final Form<BetForm> form = Form.form(BetForm.class).bindFromRequest();
//        if (form.hasErrors()) {
//            return badRequest(newBet.render(username,
//                                            userService.getBalance(username),
//                                            form,
//                                            null)); //no BetOutcome
//        }
//
//        final BetForm bet = form.get();
//        final String invalidBetMessage = ruleService.validateBet(username, bet);
//        if (invalidBetMessage != null) {
//            log.debug("user {} could not make a bet", username);
//            form.reject(invalidBetMessage);
//            return badRequest(newBet.render(username,
//                                            userService.getBalance(username),
//                                            form,
//                                            null));
//        }
//
//        final BetOutcome outcome = ruleService.decideBet(username, bet);
//
//        return ok(newBet.render(username,
//                                userService.getBalance(username),
//                                form, //resuse previous bet for convenience
//                                outcome));
//    }
//
//    public Result getHistory(final String username) {
//        if (!userService.userExists(username)) {
//            log.debug("History requested for non-user: {}", username);
//            return badRequest(history.render(username, Collections.emptyList()));
//        }
//        return ok(history.render(username, ruleService.getHistory(username)));
//    }
//
//    private boolean isLoggedIn() {
//        return session("username") != null;
//    }
}
