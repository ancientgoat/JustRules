package translate;

/**
 *
 */
public class JrRuleTranslate {
    private JrRuleTranslate() {
    }

    public static entity.JrRule toJrRule(model.JrRuleForm inForm) {
        entity.JrRule rule = new entity.JrRule();

        rule.setRule(inForm.getRule());
        rule.setRuleName(inForm.getRuleName());
        return rule;
    }

}
