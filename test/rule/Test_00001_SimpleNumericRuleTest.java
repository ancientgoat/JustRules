package rule;

import static rule.TstFileHelper.buildThings;

import rule.action.SkActions;
import rule.base.SkRuleBase;
import rule.base.SkRuleMaster;
import rule.common.JsonMapperHelper;
import rule.expression.SkExpressionFactory;
import rule.run.SkRuleRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simpler rule tests.
 */
public class Test_00001_SimpleNumericRuleTest {

    private Logger log = LoggerFactory.getLogger(Test_00001_SimpleNumericRuleTest.class);

    public static final String ONE_RULE_FILE_NAME = "NumericOneRuleTest.json";
    public static final String ONE_RULE_FAIL_FILE_NAME = "NumericOneRuleTestFail.json";
    public static final String TWO_RULE_FILE_NAME = "NumericTwoRuleTest.json";
    public static final String ONE_ACTION_FILE_NAME = "Test_Action_Now_001.json";

    @BeforeClass
    public static void beforeClass() {
        SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
    }

    /**
     * Can we read simple JSON without Exception?
     */
    @Test
    public void testSimpleRuleTest() {
        buildThings(ONE_RULE_FILE_NAME, SkRuleBase.class, "rule");
    }

    /**
     * Can we read and run simple JSON without Exception?
     */
    @Test
    public void testOutputJsonSimpleRuleTest() {
        final SkRuleBase rule = buildThings(ONE_RULE_FILE_NAME, SkRuleBase.class, "rule");
        final String json = JsonMapperHelper.beanToJsonPretty(rule);

        log.info("-------------------- JSON ---------------------");
        log.info(json);
        log.info("-------------------- JSON ---------------------");
        final SkRuleRunner runner = new SkRuleRunner();
        rule.run(runner);
    }

    /**
     * Force a fail due to not setting a needed macro value.
     */
    @Test
    public void testSimpleOneRuleFailExecution() {
        final SkRuleBase rule = buildThings(ONE_RULE_FAIL_FILE_NAME, SkRuleBase.class, "rule");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();

        try {
            runner.setValue("THIS_MACRO_DOES_NOT_EXIST", "X");
            rule.run(runner);
            Assert.fail("We should have thrown an error.");
        } catch (Exception e) {
            log.info(String.format("We expected this error : '%s'", e.toString()));
        }
        runner.setValue("MILK.QTY", 2);
        rule.run(runner);
    }

    /**
     * Run the fail rule, but add the needed macro value through an Action.
     */
    @Test
    public void testSimpleOneRuleExecutionWithExternalDataTest() {
        final SkRuleBase rule = buildThings(ONE_RULE_FAIL_FILE_NAME, SkRuleBase.class, "rule");
        final SkActions actions = buildThings(ONE_ACTION_FILE_NAME, SkActions.class, "actions");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
                                                        .addActions(actions)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();
        rule.run(runner);
    }

    /**
     * Two rule test.
     */
    @Test
    public void testSimpleTwoRuleExecutionTest() {
        final SkRuleBase rule = buildThings(TWO_RULE_FILE_NAME, SkRuleBase.class, "rule");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();
        runner.setValue("MILK", 2);
        rule.run(runner);
        runner.setValue("MILK", 9999);
        runner.runRule(rule);
    }
}
