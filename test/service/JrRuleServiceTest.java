package service;

import com.google.common.collect.Lists;
import conf.AppConf;
import conf.TestDataConf;
import entity.JrRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class JrRuleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private JrRuleService ruleService;

    @Before
    public void beforeTest() {
        ruleService = Mockito.mock(JrRuleService.class);
    }

    @Test
    public void testGetRuleNames() {
        List<String> ruleNames = getRuleNames();
        when(ruleService.getRuleNames()).thenReturn(ruleNames);
        List<String> tempRuleName = ruleService.getRuleNames();
        Assert.assertEquals("Rule name sizes are different.", ruleNames.size(), tempRuleName.size());
    }

    @Test
    public void testSimpleSave() {
        JrRule initialRule = new JrRule()
                        .setRuleName("A1")
                        .setRule("some rule ges here");
        JrRule returnRule = initialRule.setId(13L);
        when(ruleService.save(initialRule)).thenReturn(returnRule);
        JrRule newRule = ruleService.save(initialRule);
        Assert.assertEquals("Ids do not match", returnRule.getId(), newRule.getId());
    }

    /**
     *
     */
    private List<String> getRuleNames() {
        List<String> ruleNames = Lists.newArrayList();
        ruleNames.add("A1");
        ruleNames.add("A2");
        return ruleNames;
    }
}
