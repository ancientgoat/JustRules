package rule.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import rule.exception.SkRuleNotFoundException;
import rule.expression.SkExpression;
import rule.expression.SkExpressionFactory;
import rule.run.SkRuleRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 * Representation of an 'if' condition
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkIf {

	private Logger log = LoggerFactory.getLogger(SkIf.class);

	private String inputExpression;
	private SkExpression skExpression;
	private String ruleRef;

	public SkIf() {
	}

	public SkIf(@NotNull String inIfExpression) {
		setExpression(inIfExpression);
	}

	@JsonProperty("ruleref")
	public void setRuleRef(final String inRuleRef) {
		ruleRef = inRuleRef;
	}

	public String getRuleRef() {
		return ruleRef;
	}

	@JsonProperty("expression")
	public void setExpression(final String inExpression) {
		if (null != inExpression) {
			this.inputExpression = inExpression;
			validateInputExpression();
			this.skExpression = SkExpressionFactory.parseExpression(this.inputExpression);
		}
	}

	@JsonIgnore
	public SkExpression getSkExpression() {
		return skExpression;
	}

	private void validateInputExpression() {
		if (null == this.inputExpression && null == ruleRef) {
			throw new IllegalArgumentException("Input expression AND ruleRef can not be null.");
		}
		if (null != this.inputExpression) {
			this.inputExpression = this.inputExpression.trim();

			if (0 == this.inputExpression.length()) {
				throw new IllegalArgumentException("Input expression can not be empty.");
			}
		}
	}

	/**
	 * Run this 'if', or run another rule reference.
	 */
	public Boolean run(SkRuleRunner inRunner) {
		Boolean returnValue = null;
		if (null != ruleRef) {
			returnValue = inRunner.runRuleRef(this.ruleRef);
			inRunner.addDebugCrumb(this.ruleRef, returnValue);
		} else {
			returnValue = runThis(inRunner);
		}
		return returnValue;
	}

	private Boolean runThis(final SkRuleRunner inRunner) {

		Object objectAnswer = inRunner.getValue(this.skExpression);

		if (objectAnswer instanceof Boolean) {
			Boolean answer = (Boolean) objectAnswer;
			return answer;
		}

		if (null == objectAnswer) {
			// Maybe this is a rule reference, and the author used 'if' instead of 'ruleref'.
			try {
				objectAnswer = inRunner.getRule(this.skExpression.getOriginalString())
						.run(inRunner);
			} catch (SkRuleNotFoundException e) {
				// Will fall to exception below
				objectAnswer = null;
			}

			if (null != objectAnswer && objectAnswer instanceof Boolean) {
				// Fix the rule for the author, it is not an 'if', it is an 'ruleref'.
				this.ruleRef = this.skExpression.getOriginalString();
				this.inputExpression = null;
				this.skExpression = null;
				Boolean answer = (Boolean) objectAnswer;
				inRunner.addDebugCrumb(this.ruleRef, answer);
				return answer;
			}
		}

		// Only the error path found below
		if (log.isDebugEnabled() && null != objectAnswer) {
			log.debug(String.format("IF Class : %s", objectAnswer.getClass()
					.getName()));
			log.debug(String.format("IF Value : %s", objectAnswer.toString()));
		}

		final String expString = null != this.skExpression ? this.skExpression.getOriginalString() : "";
		final String tempAnswer = (null != objectAnswer ?
				objectAnswer.getClass()
						.getName() :
				"null");

		final String warning =
				String.format("IF condition mast result in a Boolean, not a " + "%s\nOriginal: %s\nSpEL Exp: %s",
						tempAnswer, expString, expString);

		IllegalArgumentException e = new IllegalArgumentException(warning);
		inRunner.addErrorCrumb(this.skExpression, e);
		throw e;
	}
}
