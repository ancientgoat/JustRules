package rule.run;

import rule.action.SkAction;
import rule.base.SkRule;
import rule.base.SkRuleBase;
import rule.base.SkRuleMaster;
import rule.breadcrumbs.SkBreadcrumb;
import rule.breadcrumbs.SkBreadcrumbs;
import rule.expression.SkExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.SpelCompilerMode;

import java.util.List;

/**
 * The bean that actually, finally, gets to executing rules and such.
 */
public class SkRuleRunner {

    private Logger log = LoggerFactory.getLogger(SkRuleRunner.class);

    private SkRuleContext ruleContext;
    private SkRuleMaster ruleMaster;
    private SkBreadcrumbs breadcrumbs = new SkBreadcrumbs();

    public SkRuleRunner() {
        init(null);
    }

    public SkRuleRunner(final SpelCompilerMode inSpelCompilerMode) {
        init(inSpelCompilerMode);
    }

    private void init(final SpelCompilerMode inSpelCompilerMode) {
        SpelCompilerMode mode = (null != inSpelCompilerMode ? inSpelCompilerMode : SpelCompilerMode.MIXED);
        this.ruleContext = new SkRuleContext(mode);
        this.ruleContext.setRunner(this);
    }

    public SkRuleContext getRuleContext() {
        return ruleContext;
    }

    public SkRuleMaster getRuleMaster() {
        return ruleMaster;
    }

    public void runRules(final List<SkRule> inRuleList) {
        for (SkRule rule : inRuleList) {
            runRule(rule);
        }
    }

    public Boolean runRule(final SkRule inRule) {
        return inRule.run(this);
    }

    public SkAction getAction(final String inActionName) {
        return this.ruleMaster.getAction(inActionName);
    }

    public void setValue(final String inKey, final Object inValue) {
        if (null != inKey) {
            this.ruleContext.setValue(inKey.toUpperCase(), inValue);
        }
    }

    public void setGlobalValue(final String inKey, final Object inValue) {
        if (null != inKey) {
            SkGlobalContext.setValue(inKey.toUpperCase(), inValue);
        }
    }

    public void setValue(final SkExpression inExpression) {
        this.ruleContext.setValue(inExpression);
    }

    public Object getValue(final String inKey) {
        Object value = this.ruleContext.getValue(inKey);
        if (null == value && SkGlobalContext.containsMacroKey(inKey)) {
            value = SkGlobalContext.getValue(inKey);
        }
        return value;
    }

    public Object getValue(final String inKey, final Object inDefaultValue) {
        Object value = this.ruleContext.getValue(inKey);
        if (null == value && SkGlobalContext.containsMacroKey(inKey)) {
            value = SkGlobalContext.getValue(inKey);
        }
        if (null != value) {
            return value;
        }
        return inDefaultValue;
    }

    public Object getValue(final SkExpression inExpression) {
        return this.ruleContext.getValue(inExpression);
    }

    public boolean containsMacroKey(final String inKey) {
        return this.ruleContext.containsMacroKey(inKey);
    }

    public void setMaster(final SkRuleMaster inMaster) {
        this.ruleMaster = inMaster;
    }

    public void setupMaster(final SkRuleBase inRule) {
        if (null == this.ruleMaster) {
            this.ruleMaster = new SkRuleMaster.Builder().addRule(inRule)
                                                        .build();
        }
    }

    public SkRule getRule(final String inRuleName) {
        return this.ruleMaster.getRule(inRuleName);
    }

    public void executeAction(final String inActionName) {
        final SkAction action = this.ruleMaster.getAction(inActionName);
        action.run(this);
    }

    public String expandMacros(final String inMessage) {
        return this.ruleContext.expandMacros(inMessage);
    }

    public Boolean runRuleRef(final String inRuleRef) {
        SkRule rule = this.ruleMaster.getRule(inRuleRef);
        return runRule(rule);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public SkBreadcrumb addAlertCrumb(final String inError, final Exception inException) {
        return this.breadcrumbs.addAlertCrumb(inError, inException);
    }

    public SkBreadcrumb addErrorCrumb(final String inError, final Exception inException) {
        return this.breadcrumbs.addErrorCrumb(inError, inException);
    }

    public SkBreadcrumb addWarnCrumb(final String inError, final Exception inException) {
        return this.breadcrumbs.addWarnCrumb(inError, inException);
    }

    public SkBreadcrumb addDebugCrumb(final String inError, final Exception inException) {
        return this.breadcrumbs.addDebugCrumb(inError, inException);
    }

    public SkBreadcrumb addTraceCrumb(final String inError, final Exception inException) {
        return this.breadcrumbs.addTraceCrumb(inError, inException);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public SkBreadcrumb addAlertCrumb(SkExpression inExpression, final Exception inException) {
        return this.breadcrumbs.addAlertCrumb(inExpression, inException);
    }

    public SkBreadcrumb addErrorCrumb(SkExpression inExpression, final Exception inException) {
        return this.breadcrumbs.addErrorCrumb(inExpression, inException);
    }

    public SkBreadcrumb addWarnCrumb(SkExpression inExpression, final Exception inException) {
        return this.breadcrumbs.addWarnCrumb(inExpression, inException);
    }

    public SkBreadcrumb addDebugCrumb(SkExpression inExpression, final Exception inException) {
        return this.breadcrumbs.addDebugCrumb(inExpression, inException);
    }

    public SkBreadcrumb addTraceCrumb(SkExpression inExpression, final Exception inException) {
        return this.breadcrumbs.addTraceCrumb(inExpression, inException);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public SkBreadcrumb addAlertCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addAlertCrumb(inDescription, inResult);
    }

    public SkBreadcrumb addErrorCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addErrorCrumb(inDescription, inResult);
    }

    public SkBreadcrumb addWarnCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addWarnCrumb(inDescription, inResult);
    }

    public SkBreadcrumb addInfoCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addInfoCrumb(inDescription, inResult);
    }

    public SkBreadcrumb addDebugCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addDebugCrumb(inDescription, inResult);
    }

    public SkBreadcrumb addTraceCrumb(final String inDescription, final Object inResult) {
        return this.breadcrumbs.addTraceCrumb(inDescription, inResult);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public SkBreadcrumb addAlertCrumb(final String inDescription) {
        return this.breadcrumbs.addAlertCrumb(inDescription, (Object)null);
    }

    public SkBreadcrumb addErrorCrumb(final String inDescription) {
        return this.breadcrumbs.addErrorCrumb(inDescription, (Object)null);
    }

    public SkBreadcrumb addWarnCrumb(final String inDescription) {
        return this.breadcrumbs.addWarnCrumb(inDescription, (Object)null);
    }

    public SkBreadcrumb addInfoCrumb(final String inDescription) {
        return this.breadcrumbs.addInfoCrumb(inDescription, (Object)null);
    }

    public SkBreadcrumb addDebugCrumb(final String inDescription) {
        return this.breadcrumbs.addDebugCrumb(inDescription, (Object)null);
    }

    public SkBreadcrumb addTraceCrumb(final String inDescription) {
        return this.breadcrumbs.addTraceCrumb(inDescription, (Object)null);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public SkBreadcrumb addAlertCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addAlertCrumb(inExpression, (Object)null);
    }

    public SkBreadcrumb addErrorCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addErrorCrumb(inExpression, (Object)null);
    }

    public SkBreadcrumb addWarnCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addWarnCrumb(inExpression, (Object)null);
    }

    public SkBreadcrumb addInfoCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addInfoCrumb(inExpression, (Object)null);
    }

    public SkBreadcrumb addDebugCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addDebugCrumb(inExpression, (Object)null);
    }

    public SkBreadcrumb addTraceCrumb(String inDesc, final SkExpression inExpression) {
        return this.breadcrumbs.addTraceCrumb(inExpression, (Object)null);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public SkBreadcrumb addAlertCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addAlertCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumb addErrorCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addErrorCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumb addWarnCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addWarnCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumb addInfoCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addInfoCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumb addDebugCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addDebugCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumb addTraceCrumb(String inDesc, final SkExpression inExpression, final Object inResult) {
        return this.breadcrumbs.addTraceCrumb(inDesc, inExpression, inResult);
    }

    public SkBreadcrumbs getBreadcrumbs() {
        return breadcrumbs;
    }
}
