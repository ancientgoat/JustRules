package translate;

import entity.JrRule;
import model.JrRuleForm;

/**
 * Translate class for rules.
 */
public class JrRuleTranslate {

    /**
     * Translate a JrRuleForm to a JrRule.
     */
    public static JrRule toJrRule(JrRuleForm inForm) {
        return new JrRule()
                        .setRule(inForm.getRule())
                        .setRuleName(inForm.getRuleName());
    }
}
