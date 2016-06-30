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

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
