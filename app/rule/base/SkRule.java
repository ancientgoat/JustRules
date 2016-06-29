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

	public String getName();
	public String getDescription();
	public SkCondition getCondition();
	public SkActions getActions();
	public SkExpressions getExpressions();
	Boolean run(SkRuleRunner inSkRuleRunner);
}
