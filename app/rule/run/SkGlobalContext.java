package rule.run;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

/**
 * A global context shared among all SpEL contexts.
 */
public class SkGlobalContext {

    private Logger log = LoggerFactory.getLogger(SkGlobalContext.class);

    private static Map<String, Object> globalMap = Collections.synchronizedMap(Maps.newHashMap());

    private SkGlobalContext() {
    }

    public static Object getValue(final @NotNull String inKey) {
        final String upperKey = inKey.toUpperCase();
        final Object o = globalMap.get(upperKey);
        if (null == o) {
            throw new IllegalArgumentException(String.format("No global value with name/key '%s'.", upperKey));
        }
        return o;
    }

    public static Object getValue(final @NotNull String inKey, final Object inDefautValue) {
        final String upperKey = inKey.toUpperCase();
        final Object o = globalMap.get(upperKey);
        if (null == o) {
            return inDefautValue;
        }
        return o;
    }

    public static void setValues(final Map<String, Object> inMap) {
        globalMap.putAll(inMap);
    }

    public static void setValue(final String inKey, final Object inValue) {
        globalMap.put(inKey, inValue);
    }

    public static Map<String, Object> getGlobalMap() {
        return globalMap;
    }

    public static Boolean containsMacroKey(final String inKey) {
        return globalMap.containsKey(inKey);
    }
}
