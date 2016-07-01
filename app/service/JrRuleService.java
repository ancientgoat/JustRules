package service;

import entity.JrRule;
import java.util.List;

/**
 * All database methods regarding JrRule(s).
 */
public interface JrRuleService {

    /**
     * Fetch 'all' rule names from the database.
     *
     * @return List<String>
     */
    List<String> getRuleNames();

    /**
     * Persist a rule to the database.  Return the saved rules, perhaps now with an 'id'.
     *
     * @param inRule Rule to be saved.
     * @return JrRule
     */
    JrRule save(final JrRule inRule);
}
