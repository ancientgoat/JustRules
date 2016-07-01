package rule;

import rule.action.SkActions;
import rule.base.SkRuleMaster;
import rule.expression.SkExpressionFactory;
import rule.run.SkGlobalContext;
import rule.run.SkRuleRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static rule.TstFileHelper.buildThings;

/**
 * Test reading a property file.
 */
public class Test_00003_PropertyFileTest {

    private Logger log = LoggerFactory.getLogger(Test_00003_PropertyFileTest.class);

    public static final String PROPERTY_FILE_TEST_NAME = "PropertyFile_Test.json";

    @BeforeClass
    public static void beforeClass() {
        SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
    }

    /**
     * Property File Test
     */
    @Test
    public void testPropertyFileTest() {
        final SkActions actions = buildThings(PROPERTY_FILE_TEST_NAME, SkActions.class, "actions");
        final SkRuleMaster master = new SkRuleMaster.Builder().addActions(actions)
                                                              .build();
        final SkRuleRunner runner = master.getRuleRunner();
        final Map<String, Object> internalMap = SkGlobalContext.getGlobalMap();

        log.info(internalMap.toString());
        log.info(internalMap.toString());
        Assert.assertEquals("We Expected three items in the property file.", 3, internalMap.size());
    }
}
