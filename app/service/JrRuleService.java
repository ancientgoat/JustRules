package service;

import entity.JrRule;

import java.util.List;

/**
 * All database methods regarding JrRule(s).
 */
public interface JrRuleService {

    /**
     * @return List<String>  Fetch 'all' rule names from the database.
     */
    List<String> getRuleNames();

    /**
     * Persist a rule to the database.
     *
     * @param inRule Rule to be saved.
     * @return JrRule Return the saved rules, perhaps now with an 'id'.
     */
    JrRule save(JrRule inRule);
}
