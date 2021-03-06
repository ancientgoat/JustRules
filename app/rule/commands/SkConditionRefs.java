package rule.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * References to conditions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkConditionRefs {

	private Logger log = LoggerFactory.getLogger(SkConditionRefs.class);

	List<String> conditionRefs = Lists.newArrayList();

	public List<String> getConditionRefs() {
		return conditionRefs;
	}

	public SkConditionRefs setConditionRefs(final List<String> inConditionRefs) {
		conditionRefs = inConditionRefs;
		return this;
	}
}
