package rule;

import static rule.TstFileHelper.buildThings;

import com.google.common.collect.Maps;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rule.action.custom.SkActionCustom;
import rule.action.custom.SkActionCustomGlobal;
import rule.base.SkRule;
import rule.base.SkRuleBase;
import rule.base.SkRuleMaster;
import rule.expression.SkExpressionFactory;
import rule.run.SkRuleRunner;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class Test_00010_CustomActionTest {

    private Logger log = LoggerFactory.getLogger(Test_00010_CustomActionTest.class);

    public static final String CUSTOM_ACTION_FILE_NAME = "Test_Custom_Action_001.json";

    @BeforeClass
    public static void beforeClass() {
        SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
    }

    /**
     * Test a custom action.
     */
    @Test
    public void testCustomActionTest() {
        SkActionCustomGlobal.add("TEST_CUSTOM", new TheBastActionEver());
        final SkRule rule = buildThings(CUSTOM_ACTION_FILE_NAME, SkRuleBase.class, "rule");
        final SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
                                                              .build();
        final SkRuleRunner runner = master.getRuleRunner();
        rule.run(runner);
    }

    /**
     * Custom Action
     */
    public static class TheBastActionEver implements SkActionCustom {

        private Logger log = LoggerFactory.getLogger(TheBastActionEver.class);

        private Map<String, Object> map = Maps.newHashMap();

        @Override
        public void setMap(final @NotNull Map inMap) {
            map = inMap;
        }

        @Override
        public void run(final SkRuleRunner inRunner) {

            log.info("--- Custom Map ---");
            map.keySet()
               .stream()
               .sorted()
               .forEach(k -> {
                   log.info(String.format("Key: %s   Value: %s", k, map.get(k)));
               });

            Map<String, String> getenv = System.getenv();
            System.out.println("--- System Env Map ---");
            getenv.keySet()
                  .stream()
                  .sorted()
                  .forEach(k -> {
                      log.info(String.format("Key: %s   Value: %s", k, map.get(k)));
                  });
        }

        @Override
        public SkActionCustom newInstance() {
            return new TheBastActionEver();
        }
    }
}
