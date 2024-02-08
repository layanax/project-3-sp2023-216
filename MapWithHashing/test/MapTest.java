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
    /**
     * Test removing key-value pair and checks if key is removed from map.
     */
    @Test
    public void testRemoveAnyAndCheckRemovedKey() {
        Map<String, String> testMap = this.constructorTest();
        testMap.add("one", "1");
        assertNotNull(testMap.removeAny());
        assertFalse(testMap.hasKey("one"));
    }

    /**
     * Test adding key-value pair and checks if map equals reference map.
     */
    @Test
    public void testAddAndCheckEquality() {
        Map<String, String> testMap = this.constructorTest();
        Map<String, String> refMap = this.constructorRef();

        testMap.add("k1", "v1");
        refMap.add("k1", "v1");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing key-value pair and checks if map equals reference map.
     *
     */
    @Test
    public void testRemoveAndCheckEquality() {
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

    /**
     * Test removing key-value pair until map is empty and checks removed
     * key-value pairs.
     *
     */
    @Test
    public void testRemoveToEmptyAndCheckKeyValuePairs() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        Map<String, String> mExpected = this.createFromArgsTest("red", "one");

        Pair<String, String> p = m.remove("red");
        Pair<String, String> pExpected = mExpected.remove("red");

        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Test adding single key-value pair and checks if map equals reference map.
     */
    @Test
    public final void addOneKeyValuePair() {
        Map<String, String> test = this.createFromArgsTest();
        Map<String, String> expect = this.createFromArgsRef("1", "2");
        test.add("1", "2");
        assertEquals(expect, test);
    }

    /**
     * Test adding multiple key-value pairs and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void addMultipleKeyValuePairs() {
        Map<String, String> test = this.createFromArgsTest("1", "2");
        Map<String, String> expect = this.createFromArgsRef("1", "2", "3", "4");
        test.add("3", "4");
        assertEquals(expect, test);
    }

    /**
     * Test removing single key-value pair and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void removeKeyValuePair() {
        Map<String, String> test = this.createFromArgsTest("1", "2");
        Map<String, String> expect = this.createFromArgsRef();
        test.remove("1");
        assertEquals(expect, test);
    }

    /**
     * Test removing multiple key-value pairs and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void removeMultipleKeyValuePairs() {
        Map<String, String> test = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> expect = this.createFromArgsRef("3", "4");
        test.remove("1");
        assertEquals(expect, test);
    }

    /**
     * Test removing key-value pair and checks if map equals reference map.
     */
    @Test
    public final void removeAnyKeyValuePair() {
        Map<String, String> test = this.createFromArgsTest("1", "2");
        Map<String, String> expect = this.createFromArgsRef();
        test.removeAny();
        assertEquals(expect, test);
    }
}
