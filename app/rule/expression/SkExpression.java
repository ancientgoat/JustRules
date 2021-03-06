package rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rule.run.SkRuleRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Class to hold a Stringed expression to be run under Spring SpEL.
 */
public class SkExpression {

	private Logger log = LoggerFactory.getLogger(SkExpression.class);

	@JsonProperty("expression")
	private String originalString;

	@JsonIgnore
	private String expressionString;

	/**
	 * We keep a list on macros contained inside each expression.
	 */
	private Map<String, String> macroMap = Maps.newHashMap();

	public SkExpression() {
	}

	public SkExpression(final List<String> inMacroList, final String inOriginalExpression, final String inExpressionString) {
		this.expressionString = inExpressionString;
		this.originalString = inOriginalExpression;
		inMacroList.forEach(m -> macroMap.put(m, null));
	}

	@JsonProperty("expression")
	public void setExpression(final String inExpressionsString) {
		SkExpression newExpression = SkExpressionFactory.parseExpression(inExpressionsString);
		this.expressionString = newExpression.expressionString;
		this.originalString = newExpression.originalString;
		this.macroMap.clear();
		this.macroMap.putAll(newExpression.macroMap);
	}

	@JsonProperty("expression")
	public String getOriginalString() {
		return originalString;
	}

	public String getExpressionString() {
		return expressionString;
	}

	@JsonIgnore
	public List<String> getMacroList() {
		return Lists.newArrayList(this.macroMap.keySet());
	}

	/**
	 * This method actually will set a value in the local context, based on this expression.
	 */
	public void setValue(final @NotNull SkRuleRunner inRunner) {
		if (!this.expressionString.startsWith("//"))
			inRunner.setValue(this);
	}

	/**
	 * This method actually will return a value from the local context, based on this expression.
	 * Most often used to get a true or false for an 'if' condition.
	 */
	public Object getValue(final @NotNull SkRuleRunner inRunner) {
		if (!this.expressionString.startsWith("//"))
			return inRunner.getValue(this);
		return true;
	}

	public String dumpToString() {

		if (null != originalString && null != expressionString && originalString.equals(expressionString)) {
			return new StringBuilder()//
					.append("\n")
					.append(String.format("expressionString : %s \n", expressionString))
					.toString();
		} else {
			return new StringBuilder()//
					.append("\n")
					.append(String.format("originalString   : %s \n", originalString))
					.append(String.format("expressionString : %s \n", expressionString))
					.toString();
		}
	}
}
