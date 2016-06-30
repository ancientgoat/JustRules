package service;

import entity.JrRule;

import java.util.List;

/**
 * All database methods regarding JrRule(s).
 */
public interface JrRuleService {

    /**
     * Fetch 'all' rule names from the database.
     */
    List<String> getRuleNames();

    /**
     * Persist a rule to the database.
     */
    void save(JrRule inRule);
}
