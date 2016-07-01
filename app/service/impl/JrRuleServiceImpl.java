package service.impl;

import entity.JrRule;
import service.JrRuleService;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static javafx.scene.input.KeyCode.T;

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
        String ruleName = inRule.getRuleName();

        List<String> resultList = em.createQuery("SELECT ruleName FROM entity.JrRule R WHERE R.ruleName = :ruleName", String.class)
                                 .setParameter("ruleName", ruleName)
                                 .getResultList();
        if (resultList.size() > 0) {
            throw new IllegalArgumentException(String.format("Rule name '%s' already exists.", resultList));
        }
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
