package service;

import com.google.common.collect.Lists;
import conf.AppConf;
import conf.TestDataConf;
import entity.JrRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class JrRuleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private JrRuleService mockedRuleService;
    @Inject private JrRuleService ruleService;

    @Before
    public void beforeTest() {
        mockedRuleService = mock(JrRuleService.class);
    }

    /**
     * Mock fetching some rule names.
     */
    @Test
    public void testGetRuleNames() {
        final List<String> ruleNames = getRuleNames();
        when(mockedRuleService.getRuleNames()).thenReturn(ruleNames);
        final List<String> tempRuleName = mockedRuleService.getRuleNames();
        Assert.assertEquals("Rule name sizes are different.", ruleNames.size(), tempRuleName.size());
    }

    /**
     * Test a simple persist.
     */
    @Test
    public void testSimpleSave() {
        final JrRule initialRule = new JrRule()
                        .setRuleName("A1")
                        .setRule("some rule ges here");
        final JrRule returnRule = initialRule.setId(13L);
        when(mockedRuleService.save(initialRule)).thenReturn(returnRule);
        final JrRule newRule = mockedRuleService.save(initialRule);
        Assert.assertEquals("Ids do not match", returnRule.getId(), newRule.getId());
    }

    /**
     * Test that a duplicate name fails.
     */
    @Test
    public void testCanNotDuplicateRuleName() {
        final JrRule initialRule = new JrRule()
                        .setRuleName("A1")
                        .setRule("some rule ges here");
        final JrRule newRule1 = ruleService.save(initialRule);

        try {
            final JrRule newRule2 = ruleService.save(initialRule);
            fail("Was not suppose to get here - we are not suppose to allow duplicate rule names.");
        } catch (Exception e) {
            //Ignore - this is what we expect
        }
    }

    /**
     * Helper method for faking a list of rule names.
     */
    private List<String> getRuleNames() {
        final List<String> ruleNames = Lists.newArrayList();
        ruleNames.add("A1");
        ruleNames.add("A2");
        return ruleNames;
    }
}
