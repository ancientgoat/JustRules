package service;

/**
 *
 */
public interface JrRuleService {

    java.util.List<String> getRuleNames();

    void save(entity.JrRule inRule);
}
