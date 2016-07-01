package rule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rule.action.SkActions;
import rule.base.SkRuleMaster;
import rule.base.SkRules;
import rule.common.JsonMapperHelper;
import rule.expression.SkExpressionFactory;
import rule.run.SkRuleRunner;

import static rule.TstFileHelper.buildThings;

/**
 * More complex rule tests.  Some contain rule references to other rules
 */
public class Test_00002_ReferenceTest {

    private Logger log = LoggerFactory.getLogger(Test_00002_ReferenceTest.class);

    public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";
    public static final String SECOND_REF_TEST_FILE_NAME = "StringReferenceRule_4_WithOtherRefs.json";
    public static final String THIRD_REF_TEST_FILE_NAME = "StringReferenceRule_5_MultiAction.json";
    public static final String FORTH_REF_TEST_FILE_NAME = "StringReferenceRule_6_MultiActionWithMacros.json";
    public static final String FIRST_ACTION_FILE_NAME = "Test_Actions_001.json";
    public static final String SECOND_ACTION_FILE_NAME = "Test_Actions_002_With_Macros.json";

    @BeforeClass
    public static void beforeClass() {
        SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
    }

    /**
     * Can we read a several rules simple JSON without Exception?
     */
    @Test
    public void testOutputJsonSimpleRuleTest() {
        final SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
        final String json = JsonMapperHelper.beanToJsonPretty(rules);
        log.info(json);
        log.info("-------------------- xJSON ---------------------");
        log.info("-------------------- xJSON ---------------------");
    }

    /**
     * Three rule reference test.  Just call the 3rd rule, and the other two are referenced.
     */
    @Test
    public void testRefThreeRuleTest() {

        final SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
                                                              .build();
        final SkRuleRunner runner = master.getRuleRunner();
        try {
            // First, force a fail due to missing macro value.  We are missing setting
            //	of the macro 'NAME'
            runner.setValue("THIS_MACRO_DOES_NOT_EXIST", "X");
            runner.runRule(master.getRule("COKE_RULE_00003"));
            Assert.fail("We should have thrown an error.");
        } catch (Exception e) {
            log.info(String.format("We expected this error : '%s'", e.toString()));
        }

        // Note: All macros are turned into UPPER case.
        runner.setValue("nAmE", "Fred");

        // Choose only one rule, from the three, to setValue.
        runner.runRule(master.getRule("COKE_RULE_00003"));
    }

    /**
     * Can we read in an Action file without Exception?
     */
    @Test
    public void testReadActionsTest() {
        buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
    }

    /**
     * Read in actions - setValue with Three rule action-ref test.
     */
    @Test
    public void testActionRefRuleRefTest() {
        final SkActions actions = buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
        final SkRules rules = buildThings(SECOND_REF_TEST_FILE_NAME, SkRules.class, "rules");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
                                                        .addActions(actions)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();

        // Note: All macros are turned into UPPER case.
        runner.setValue("nAmE", "Fred");

        // Choose only one rule, from the three, to setValue.
        runner.runRule(master.getRule("COKE_RULE_00003"));
    }

    /**
     * Read in actions - setValue with Three rule action-ref test.  Just more actions.
     */
    @Test
    public void testMultiActionRefRuleRefTest() {
        final SkActions actions = buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
        final SkRules rules = buildThings(THIRD_REF_TEST_FILE_NAME, SkRules.class, "rules");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
                                                        .addActions(actions)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();

        // Note: All macros are turned into UPPER case.
        runner.setValue("nAmE", "Fred");

        // Choose only one rule, from the three, to setValue.
        runner.runRule(master.getRule("COKE_RULE_00003"));
    }

    /**
     * Read in actions - setValue with Three rule action-ref test.  Just more actions.
     */
    @Test
    public void testMultiActionWithMacrosRefRuleRefTest() {
        final SkActions actions = buildThings(SECOND_ACTION_FILE_NAME, SkActions.class, "actions");
        final SkRules rules = buildThings(FORTH_REF_TEST_FILE_NAME, SkRules.class, "rules");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
                                                        .addActions(actions)
                                                        .build();
        final SkRuleRunner runner = master.getRuleRunner();

        // Note: All macros are turned into UPPER case.
        runner.setValue("nAmE", "Fred");

        // Choose only one rule, from the three, to setValue.
        runner.runRule(master.getRule("COKE_RULE_00003"));
    }
}
