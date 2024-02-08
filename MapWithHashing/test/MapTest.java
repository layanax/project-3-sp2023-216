import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Layan Abdallah & Oak Hodous
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size
    @Test
    public void testRemoveAny() {
        Map<String, String> testMap = this.constructorTest();
        testMap.add("one", "1");
        assertNotNull(testMap.removeAny());
        assertFalse(testMap.hasKey("one"));
    }

    @Test
    public void testAdd() {
        Map<String, String> testMap = this.constructorTest();
        Map<String, String> refMap = this.constructorRef();

        testMap.add("k1", "v1");
        refMap.add("k1", "v1");

        assertEquals(refMap, testMap);
    }

    @Test
    public void testRemove() {
        Map<String, String> testMap = this.constructorTest();
        Map<String, String> refMap = this.constructorRef();

        testMap.add("k1", "v1");
        testMap.add("k2", "v2");
        refMap.add("k1", "v1");
        refMap.add("k2", "v2");

        testMap.remove("k1");
        refMap.remove("k1");

        assertEquals(refMap, testMap);
    }

    @Test
    public void testRemoveToEmpty() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        Map<String, String> mExpected = this.createFromArgsRef("red", "one");

        Pair<String, String> p = m.remove("red");
        Pair<String, String> pExpected = mExpected.remove("red");

        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testSize1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        int sizeExpected = 1;
        int sizeM = m.size();
        assertEquals(sizeM, sizeExpected);
    }

    @Test
    public void testSize2() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        m.add("green", "three");
        m.removeAny();
        m.add("yellow", "four");
        m.remove("yellow");
        int sizeExpected = 2;
        int sizeOfM = m.size();
        assertEquals(sizeOfM, sizeExpected);
    }

    @Test
    public void testValue1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        String value = m.value("red");
        String expectedValue = "one";

        assertEquals(value, expectedValue);
    }

    @Test
    public void testValue2() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        String value = m.value("blue");
        String expectedValue = "two";

        assertEquals(value, expectedValue);

    }

    @Test
    public void testHasKey1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        boolean hasKey = m.hasKey("red");
        boolean hasKeyExpected = true;

        assertEquals(hasKey, hasKeyExpected);
    }

    @Test
    public void testHasKey2() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        boolean hasKey = m.hasKey("yellow");
        boolean hasKeyExpected = false;

        assertEquals(hasKey, hasKeyExpected);
    }

    @Test
    public void testHasKey3() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        boolean hasKey = m.hasKey("blue");
        boolean hasKeyExpected = true;

        assertEquals(hasKey, hasKeyExpected);
    }

    @Test
    public void testConstructor() {
        Map<String, String> m = new Map4<String, String>();
        Map<String, String> ref = this.createFromArgsRef();

        assertEquals(m, ref);
    }
}
