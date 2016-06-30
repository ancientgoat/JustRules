package rule.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import controllers.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import rule.base.SkRule;
import rule.base.SkRuleBase;
import rule.base.SkRules;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.StringWriter;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

/**
 *
 */
public class JsonMapperHelper {

    private static final Logger log = LoggerFactory.getLogger(JsonMapperHelper.class);

    private JsonMapperHelper() {
    }

    /**
     * Read Actions from file.
     */
    public static SkRules buildThingsFromDirectory(String filePath) {

        String json = "";
        SkRules returnRules = new SkRules();
        try {
            ObjectMapper objectMapper;
            ClassPathResource resource = new ClassPathResource(filePath);
            File topDir = resource.getFile();
            if (!topDir.exists()) {
                throw new IllegalArgumentException(
                                String.format("File '%s' does NOT exist.", topDir.getAbsolutePath()));
            }

            File[] files = topDir.listFiles();
            for (File file : files) {

                SkRuleBase rule = null;
                SkRules rules = null;

                System.out.println("--------------------------------------------------------");
                String fullFilePath = file.getAbsolutePath();
                System.out.println(String.format("File : %s", fullFilePath));
                System.out.println("--------------------------------------------------------");

                if (!fullFilePath.toLowerCase()
                                 .endsWith(".json")) {
                    continue;
                }

                try {
                    rule = buildThings(fullFilePath, SkRuleBase.class, "rule");
                } catch (Exception e) {
                    rules = buildThings(fullFilePath, SkRules.class, "rules");
                }
                if (null != rule) {
                    returnRules.addRule(rule);
                }
                if (null != rules) {
                    returnRules.addRules(rules);
                }
            }
            return returnRules;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Read Actions from file.
     */
    public static <C> C buildThings(String filePath, Class inClazz, String inRootName) {
        String json = "";
        C things = null;
        try {
            ObjectMapper objectMapper;

            File file = null;

            try {
                ClassPathResource resource = new ClassPathResource(filePath);
                file = resource.getFile();
            } catch (Exception e) {
                file = new File(filePath);
            }
            if (null == file && !file.exists()) {
                throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
            }
            objectMapper = JsonMapperHelper.jsonMapper();
            things = (C)objectMapper.readValue(file, inClazz);
            return things;

        } catch (Exception e) {
            String prettyJson = JsonMapperHelper.beanToJsonPretty(things, inRootName);
            log.error(String.format("\n%s\n%s\n%s", e.toString(), prettyJson, json));
            throw new IllegalArgumentException(json, e);
        }
    }

    /**
     * Read Actions from file.
     */
    public static SkRules buildRules(String inJsonContent) {
        SkRules returnRules = new SkRules();
        SkRuleBase rule = null;
        SkRules rules = null;
        try {
            ObjectMapper objectMapper = JsonMapperHelper.jsonMapper();
            try {
                rule = objectMapper.readValue(inJsonContent, SkRuleBase.class);
            } catch (Exception e) {
                rules = objectMapper.readValue(inJsonContent, SkRules.class);
            }
            if (null != rule) {
                returnRules.addRule(rule);
            }
            if (null != rules) {
                returnRules.addRules(rules);
            }
            return returnRules;
        } catch (Exception e) {
            log.error(String.format("\n%s\n%s", e.toString(), inJsonContent));
            throw new IllegalArgumentException(inJsonContent, e);
        }
    }

    public static final ObjectMapper newInstanceJson() {
        ObjectMapper mapper = new CustomMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        // mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
        // mapper.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);

        //mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
        //mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);

        return mapper;
    }

    ///**
    // * Use Jackson's XML Mapper.
    // */
    //public static ObjectMapper xmlMapper() {
    //	final JacksonXmlModule module = new JacksonXmlModule();
    //	module.setDefaultUseWrapper(false);
    //	final XmlMapper xmlMapper = new XmlMapper(module);
    //	return commonProperties(xmlMapper);
    //}

    /**
     * Use Jackson's JSON Mapper.
     */
    public static final ObjectMapper jsonMapper() {
        ObjectMapper mapper = new CustomMapper();
        //mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
        return mapper;
    }

    /**
     * Take any bean and return as JSON String.
     */
    public static String beanToJsonPretty(final Object inObject) {
        return prettyPrint(inObject, jsonMapper());
    }

    public static String beanToJsonPretty(final Object inObject, String inRootName) {
        return prettyPrint(inObject, jsonMapper(), inRootName);
    }

    public static String beanToJson(final Object inObject) {
        return prettyPrint(inObject, jsonMapper());
    }

    /**
     * Pretty Print (output) the input Object, using the input Jackson ObjectMapper.
     */
    public static String prettyPrint(Object inObject, ObjectMapper inObjectMapper) {
        return prettyPrint(inObject, inObjectMapper, null);
    }

    /**
     * Pretty Print (output) the input Object, using the input Jackson ObjectMapper.
     */
    public static String prettyPrint(Object inObject, ObjectMapper inObjectMapper, String inRootName) {
        try {
            //return inObjectMapper.writerWithDefaultPrettyPrinter()
            //		.writeValueAsString(inObject);

            StringWriter w = new StringWriter();
            ObjectWriter writer = inObjectMapper.writerWithDefaultPrettyPrinter();
            if (null != inRootName) {
                writer = writer.withRootName(inRootName);
            }
            writer.writeValue(w, inObject);
            return w.toString();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
     */
    public static String beanToJsonString(Object inObject) {
        return beanToString(inObject, jsonMapper());
    }

    /**
     * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
     */
    public static String beanToJsonString(Object inObject, String inRootName) {
        return beanToString(inObject, inRootName, jsonMapper());
    }

    /**
     * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
     */
    public static String beanToString(Object inObject, ObjectMapper inObjectMapper) {
        return beanToString(inObject, null, inObjectMapper);
    }

    public static String beanToString(Object inObject, String inRootName, ObjectMapper inObjectMapper) {
        try {
            StringWriter w = new StringWriter();
            ObjectWriter writer = inObjectMapper.writer();
            if (null != inRootName) {
                writer = writer.withRootName(inRootName);
            }
            writer.writeValue(w, inObject);
            return w.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * This method ensures that the output String has only
     * valid XML unicode characters as specified by the
     * XML 1.0 standard. For reference, please see
     * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
     * standard</a>. This method will return an empty
     * String if the input is null or empty.
     *
     * @param in The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /**
     * Add some properties to any Jackson ObjectMapper.
     */
    private static ObjectMapper commonProperties(final ObjectMapper inMapper) {
        inMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        inMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        inMapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
        inMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return inMapper;
    }

    private static class CustomMapper extends ObjectMapper {

        public CustomMapper() {
            super();
        }

        public CustomMapper(final JsonFactory inFactory) {
            super(inFactory);
        }

        @PostConstruct
        public void customConfiguration() {
            // Uses Enum.toString() for serialization of an Enum
            this.enable(WRITE_ENUMS_USING_TO_STRING);
            // Uses Enum.toString() for deserialization of an Enum
            this.enable(READ_ENUMS_USING_TO_STRING);

            // this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            // this.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
            // this.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);
            // this.configure(SerializationFeature.INDENT_OUTPUT, true);
        }
    }
}
