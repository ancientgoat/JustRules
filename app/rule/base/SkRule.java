package rule.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import rule.action.SkActions;
import rule.commands.SkCondition;
import rule.expression.SkExpressions;
import rule.run.SkRuleRunner;

/**
 * Basic methods for interacting with Rules.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SkRule {

	String getName();
	String getDescription();
	SkCondition getCondition();
	SkActions getActions();
	SkExpressions getExpressions();
	Boolean run(final SkRuleRunner inSkRuleRunner);
}
