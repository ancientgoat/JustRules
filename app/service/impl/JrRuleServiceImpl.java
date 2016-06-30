package service.impl;

import entity.JrRule;
import service.JrRuleService;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 */
@Named
public class JrRuleServiceImpl implements JrRuleService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public JrRule save(JrRule inRule) {
        em.persist(inRule);
        return inRule;
    }

    @Override
    public List<String> getRuleNames() {
        Query query = em.createNativeQuery("SELECT ruleName FROM rule");
        List<String> ruleNames = query.getResultList();
        return ruleNames;
    }
}
