package model;

import play.data.validation.Constraints;

/**
 *
 */

public class JrRuleForm {

    @Constraints.Required
    private String ruleName;

    @Constraints.Required
    private String rule;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}
