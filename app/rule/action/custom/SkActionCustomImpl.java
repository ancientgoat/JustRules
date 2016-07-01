package rule.action.custom;

import com.google.common.collect.Maps;
import rule.action.SkAction;
import rule.run.SkRuleRunner;

import java.util.Map;

/**
 * Any custom Action must extend this class.
 */
public class SkActionCustomImpl extends SkAction {

	private Map<String, Object> map = Maps.newHashMap();
	private String customname;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(final Map<String, Object> inMap) {
		map = inMap;
	}

	public String getCustomname() {
		return customname;
	}

	public void setCustomname(final String inCustomname) {
		customname = inCustomname;
	}

	@Override
	public void run(final SkRuleRunner inRunner) {
		if (null == this.customname) {
			throw new IllegalArgumentException("Can not have a customname of null.");
		}
		final SkActionCustom action = SkActionCustomGlobal.get(customname);
		action.setMap(map);
		action.run(inRunner);
	}
}
