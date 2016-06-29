package rule.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import rule.expression.SkExpression;
import rule.expression.SkExpressions;
import rule.run.SkRuleRunner;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@JsonRootName("actions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkActions {

	@JsonProperty("action")
	private List<SkAction> actionList;

	@JsonProperty("postaction")
	private List<SkAction> postActionList;

	@JsonProperty("expressions")
	private SkExpressions expressions;

	/**
	 *
	 */
	@JsonProperty("expression")
	public void addExpression(@NotNull SkExpression inExpression) {
		if (null == this.expressions) {
			this.expressions = new SkExpressions();
		}
		this.expressions.addExpression(inExpression);
	}

	/**
	 *
	 */
	public List<SkAction> getActionList() {
		return actionList;
	}

	/**
	 *
	 */
	public List<SkAction> getPostActionList() {
		return postActionList;
	}

	/**
	 *
	 */
	public void setActionList(@NotNull List<SkAction> inActionList) {
		actionList = inActionList;
	}

	/**
	 *
	 */
	public void setPostActionList(@NotNull List<SkAction> inPostActionList) {
		postActionList = inPostActionList;
	}

	/**
	 *
	 */
	public void addAction(@NotNull SkAction inAction) {
		initList();
		this.actionList.add(inAction);
	}

	/**
	 *
	 */
	public void addPostAction(@NotNull SkAction inPostAction) {
		initPostList();
		this.postActionList.add(inPostAction);
	}

	/**
	 *
	 */
	private void initList() {
		if (null == actionList) {
			this.actionList = Lists.newArrayList();
		}
	}

	/**
	 *
	 */
	private void initPostList() {
		if (null == postActionList) {
			this.postActionList = Lists.newArrayList();
		}
	}

	/**
	 *
	 */
	public SkExpressions getExpressions() {
		return expressions;
	}

	/**
	 *
	 */
	public void setExpressions(@NotNull SkExpressions inExpressions) {
		expressions = inExpressions;
	}

	/**
	 *
	 */
	public void run(final SkRuleRunner inRunner) {
		// Actions first - then Expressions.
		if (null != this.actionList) {
			for (SkAction localAction : this.actionList) {
				if (null != localAction) {
					localAction.run(inRunner);
				}
			}
		}
		// Expressions.
		if (null != this.expressions) {
			this.expressions.run(inRunner);
		}
	}
}
