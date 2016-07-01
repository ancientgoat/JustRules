package model;

import play.data.validation.Constraints.Required;

public class JrRuleForm {

    @Required
    private String ruleName;

    @Required
    private String rule;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(final String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(final String rule) {
        this.rule = rule;
    }

}
