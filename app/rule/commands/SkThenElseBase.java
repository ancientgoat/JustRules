package rule.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import rule.action.SkAction;
import rule.action.SkActions;
import rule.base.SkRuleBase;
import rule.expression.SkExpressions;
import rule.run.SkRuleRunner;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Base class for a 'SkThen' and a 'SkElse', as they do the exact same thing.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkThenElseBase {

    @JsonProperty("expressions")
    private SkExpressions expressions;

    @JsonProperty("actions")
    private SkActions actions = new SkActions();

    @JsonProperty("postactions")
    private SkActions postActions = new SkActions();

    @JsonProperty("condition")
    private SkCondition condition = null;

    @JsonProperty("rule")
    private SkRuleBase rule = null;

    private String ruleRef;

    @JsonProperty("ruleref")
    public void setRuleRef(final String inRuleRef) {
        ruleRef = inRuleRef;
    }

    public String getRuleRef() {
        return ruleRef;
    }

    public SkActions getActions() {
        return actions;
    }

    public void setPostActions(final SkActions inPostActions) {
        postActions = inPostActions;
    }

    public void setActions(final @NotNull SkActions inActions) {
        actions = inActions;
    }

    @JsonProperty("action")
    public void setAction(final @NotNull SkAction inAction) {
        this.actions.addAction(inAction);
    }

    @JsonProperty("postaction")
    public void setPostAction(final @NotNull SkAction inPostAction) {
        this.actions.addPostAction(inPostAction);
    }

    /**
     *
     */
    @JsonProperty("expressions")
    public void setExpressionList(final List<String> inExpressionList) {
        if (null != inExpressionList) {
            this.expressions = new SkExpressions().addExpressions(inExpressionList);
        }
    }

    public void setCondition(final SkCondition inCondition) {
        condition = inCondition;
    }

    public void setRule(final SkRuleBase inRule) {
        rule = inRule;
    }

    /**
     *
     */
    @JsonProperty("expressions")
    public List<String> getExpressionList() {
        if (null != expressions) {
            return expressions.getExpressions();
        }
        return null;
    }

    public SkCondition getCondition() {
        return condition;
    }

    public SkRuleBase getRule() {
        return rule;
    }

    /**
     * Run this 'Then' or 'Else'.
     */
    public void run(final @NotNull SkRuleRunner inRunner) {
        if (null != expressions) {
            this.expressions.run(inRunner);
        }
        if (null != actions) {
            this.actions.run(inRunner);
        }
        if (null != ruleRef) {
            inRunner.runRuleRef(this.ruleRef);
        }
        if (null != this.rule) {
            this.rule.run(inRunner);
        }
        if (null != this.condition) {
            this.condition.run(inRunner);
        }
        if (null != postActions) {
            this.postActions.run(inRunner);
        }
    }
}
