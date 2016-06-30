package translate;

import entity.JrRule;
import model.JrRuleForm;

/**
 * Translate class for rules.
 */
public class JrRuleTranslate {
    private JrRuleTranslate() {
    }

    /**
     * Translate a JrRuleForm to a JrRule.
     */
    public static JrRule toJrRule(JrRuleForm inForm) {
        JrRule rule = new JrRule();
        rule.setRule(inForm.getRule());
        rule.setRuleName(inForm.getRuleName());
        return rule;
    }

}
