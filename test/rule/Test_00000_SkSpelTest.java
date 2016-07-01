package rule;

import controllers.Application;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import rule.utils.SkTimer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Spring SpEL tests.
 */
public class Test_00000_SkSpelTest {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Test
    public void simpleTest() {
        final GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        // The constructor arguments are name, birthday, and nationality.
        final Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
        final Map<String, Object> internalMap = new HashMap<String, Object>() {{
            put("name", "Nikola Tesla");
            put("birthdate", c.getTime());
            put("nationality", "Serbian");
        }};

        final SpelExpressionParser parser = new SpelExpressionParser();
        final Expression exp = parser.parseExpression("name");
        String name = (String)exp.getValue(tesla);
        log.info("********** Name : " + name);

        final Expression exp2 = parser.parseExpression("['name']");
        name = (String)exp2.getValue(internalMap);
        log.info("********** Name : " + name);
    }

    @Test
    public void simpleExpressionTest() {
        // The constructor arguments are name, birthday, and nationality.
        final Map<String, Object> internalMap = new HashMap<String, Object>() {{
            put("a", 3);
            put("b", 100);
            put("c", 17L);
        }};

        final SpelExpressionParser parser = new SpelExpressionParser();
        final Expression exp = parser.parseExpression("['a'] + ['b'] + ['c']");
        final BigDecimal answer = exp.getValue(internalMap, BigDecimal.class);
        log.info("********** Answer : {}", answer);
        Assert.assertEquals("Should be 120", answer, BigDecimal.valueOf(120));
    }

    @Test
    public void nextExpressionTest() {
        final List<Integer> intList = new ArrayList<Integer>() {{
            add(10);
            add(100);
            add(1000);
            add(10000);
        }};
        startNextExpressionTest(intList, "--- OFF ---", SpelCompilerMode.OFF);
        startNextExpressionTest(intList, "--- MIXED ---", SpelCompilerMode.MIXED);
        startNextExpressionTest(intList, "--- IMMEDIATE ---", SpelCompilerMode.IMMEDIATE);
    }

    /**
     * @param intList
     * @param inDescription
     * @param inSpelCompilerMode
     */
    private void startNextExpressionTest(final List<Integer> intList, final String inDescription,
                                         final SpelCompilerMode inSpelCompilerMode) {
        final SpelParserConfiguration config = new SpelParserConfiguration(inSpelCompilerMode, this.getClass()
                                                                                                   .getClassLoader());
        log.info(inDescription);
        intList.forEach(i -> runNextExpressionTest(config, i));
    }

    /**
     * @param inConfig
     * @param loopNumber
     */
    private void runNextExpressionTest(final SpelParserConfiguration inConfig, final int loopNumber) {
        final IntStream range = IntStream.range(1, loopNumber);
        final SkTimer timer = new SkTimer();
        range.forEach(i -> {
            // The constructor arguments are name, birthday, and nationality.
            final Map<String, Object> internalMap = new HashMap<String, Object>() {{
                put("a", 3);
                put("b", 100);
                put("c", 17L);
            }};
            final SpelExpressionParser parser = new SpelExpressionParser(inConfig);
            parser.parseExpression("['MILK']")
                  .setValue(internalMap, 80);

            final Expression exp = parser.parseExpression("['a'] + ['b'] + ['c'] + ['MILK']");
            final BigDecimal answer = exp.getValue(internalMap, BigDecimal.class);
            // System.out.println("********** Answer : " + answer);
            Assert.assertEquals("Should be 200", answer, BigDecimal.valueOf(200));
        });
        log.info(String.format("(%10d) Timer : %d", loopNumber, timer.stopAndDiff()));
    }

    /**
     *
     */
    public static class Inventor {

        private String name;
        private String nationality;
        private String[] inventions;
        private Date birthdate;
        private PlaceOfBirth placeOfBirth;

        public Inventor(final String name, final String nationality) {
            GregorianCalendar c = new GregorianCalendar();
            this.name = name;
            this.nationality = nationality;
            this.birthdate = c.getTime();
        }

        public Inventor(final String name, final Date birthdate, final String nationality) {
            this.name = name;
            this.nationality = nationality;
            this.birthdate = birthdate;
        }

        public Inventor() {
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(final String nationality) {
            this.nationality = nationality;
        }

        public Date getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(final Date birthdate) {
            this.birthdate = birthdate;
        }

        public PlaceOfBirth getPlaceOfBirth() {
            return placeOfBirth;
        }

        public void setPlaceOfBirth(final PlaceOfBirth placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
        }

        public void setInventions(final String[] inventions) {
            this.inventions = inventions;
        }

        public String[] getInventions() {
            return inventions;
        }
    }

    public static class PlaceOfBirth {

        private String city;
        private String country;

        public PlaceOfBirth(final String city) {
            this.city = city;
        }

        public PlaceOfBirth(final String city, final String country) {
            this(city);
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(final String s) {
            this.city = s;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(final String country) {
            this.country = country;
        }

    }
}
