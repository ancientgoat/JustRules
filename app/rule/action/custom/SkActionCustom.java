package rule.action.custom;

import rule.run.SkRuleRunner;

import java.util.Map;

/**
 * Any custom Action must implement this interface.
 */
public interface SkActionCustom<A extends SkActionCustom> {

    /**
     * Set a map of values to pass to the Custom Action.
     *
     * @param inMap
     */
    void setMap(Map<String, Object> inMap);

    /**
     * Run this action.
     *
     * @param inRunner
     */
    void run(SkRuleRunner inRunner);

    /**
     * Make a new instance of this class.
     *
     * @return
     */
    A newInstance();
}
