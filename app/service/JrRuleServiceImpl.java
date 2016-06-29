package service;

import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@javax.inject.Named
public class JrRuleServiceImpl implements service.JrRuleService {

    @javax.persistence.PersistenceContext
    private javax.persistence.EntityManager em;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void save(entity.JrRule inRule) {
        em.persist(inRule);
    }

    @Override
    public List<String> getRuleNames() {
        Query query = em.createNativeQuery("SELECT ruleName FROM rule");
        List<String> ruleNames = query.getResultList();
        return ruleNames;
    }
}
