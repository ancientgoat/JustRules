package rule.base;

import rule.action.SkAction;
import rule.action.SkActions;
import rule.action.enums.SkActionContext;
import rule.commands.SkIf;
import rule.run.SkRuleRunner;

import java.util.List;

/**
 * TODO: Maybe I don't really need this, maybe SkRuleRunner can just interact with SkRuleMasterStats.
 *
 * This class was to be a Rule helper, to make convenience methods, and to separate
 * 	some work away from SkRuleRunner.
 */
public class SkRuleMaster {

	private SkMasterStats stats;
	private SkRuleRunner runner;

	private SkRuleMaster() {
	}

	public SkRuleRunner getRuleRunner() {
		if (null == this.runner) {
			runner = new SkRuleRunner();
			runner.setMaster(this);
		}
		return runner;
	}

	public List<SkRule> getRuleList() {
		return this.stats.getRuleList();
	}

	public SkIf findIfFromReference(final String inRuleName) {
		return this.stats.findIfFromReference(inRuleName);
	}

	public SkRule getRule(final String inRuleName) {
		return this.stats.getRule(inRuleName);
	}

	public SkAction getAction(final String inActionName) {
		return this.stats.getAction(inActionName);
	}

	public static class Builder {

		private SkRuleMaster master = new SkRuleMaster();
		private SkMasterStats.Builder statBuilder = new SkMasterStats.Builder();

		public Builder addRules(final SkRules inRules) {
			this.statBuilder.addRules(inRules);
			return this;
		}

		public Builder addRule(final SkRule inRule) {
			this.statBuilder.addRule(inRule);
			return this;
		}

		public Builder addActions(final SkActions inActions) {
			this.statBuilder.addActions(inActions);
			return this;
		}

		public SkRuleMaster build() {
			final SkMasterStats localStats = statBuilder.build();
			this.master.stats = localStats;

			// See if any actions are to be run now?
			final SkRuleRunner ruleRunner = this.master.getRuleRunner();
			final List<SkAction> actionList = this.master.stats.getActionList();
			if (null != actionList && 0 < actionList.size()) {
				actionList.forEach(a -> {
					if (SkActionContext.NOW == a.getActionContext()) {
						a.run(ruleRunner);
					}
				});
			}
			return this.master;
		}
	}
}
