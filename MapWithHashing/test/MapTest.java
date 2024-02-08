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
    public void removeAnyTest() {
        Map<String, String> testMap = this.createFromArgsTest("one", "1");
        Map<String, String> refMap = this.createFromArgsRef();

        Pair<String, String> removedPair = testMap.removeAny();

        assertNotNull(removedPair);
        assertFalse(testMap.hasKey(removedPair.key()));
        assertEquals(refMap, testMap);
    }

    /**
     * Test adding key-value pair and checks if map equals reference map.
     */
    @Test
    public void addTest() {
        Map<String, String> testMap = this.createFromArgsTest();
        Map<String, String> refMap = this.createFromArgsRef("k1", "v1");

        testMap.add("k1", "v1");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing key-value pair and checks if map equals reference map.
     *
     */
    @Test
    public void removeTest() {
        Map<String, String> testMap = this.createFromArgsTest("k1", "v1", "k2",
                "v2");
        Map<String, String> refMap = this.createFromArgsRef("k1", "v1", "k2",
                "v2");

        testMap.remove("k1");
        refMap.remove("k1");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing key-value pair until map is empty and checks removed
     * key-value pairs.
     *
     */
    public void removeToEmptyTest() {
        Map<String, String> testMap = this.createFromArgsTest("red", "one");
        Map<String, String> refMap = this.createFromArgsRef();

        Pair<String, String> p = testMap.remove("red");
        Pair<String, String> pExpected = refMap.remove("red");

        assertEquals(pExpected, p);
        assertEquals(refMap, testMap);
    }

    /**
     * test the size of a map that hasn't had anything done to it.
     */
    @Test
    public void testSize1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        int sizeExpected = 1;
        int sizeM = m.size();
        assertEquals(sizeM, sizeExpected);
    }

    /**
     * test the size of a map that has had stuff done to it.
     */
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

    /**
     * test the value finding function on a map with only one pair.
     */
    @Test
    public void testValue1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        String value = m.value("red");
        String expectedValue = "one";

        assertEquals(value, expectedValue);
    }

    /**
     * test the value finding function on a map with more than one pair.
     */
    @Test
    public void testValue2() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        String value = m.value("blue");
        String expectedValue = "two";

        assertEquals(value, expectedValue);

    }

    /**
     * test the has key method when the key is in the size of 1 map.
     */
    @Test
    public void testHasKey1() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        boolean hasKey = m.hasKey("red");
        boolean hasKeyExpected = true;

        assertEquals(hasKey, hasKeyExpected);
    }

    /**
     * test has key when the key is not in the size > 1 map.
     */
    @Test
    public void testHasKey2() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        boolean hasKey = m.hasKey("yellow");
        boolean hasKeyExpected = false;

        assertEquals(hasKey, hasKeyExpected);
    }

    /**
     * test hasKey when it is in the size > 1 map.
     */
    @Test
    public void testHasKey3() {
        Map<String, String> m = this.createFromArgsTest("red", "one");
        m.add("blue", "two");
        boolean hasKey = m.hasKey("blue");
        boolean hasKeyExpected = true;

        assertEquals(hasKey, hasKeyExpected);
    }

    /**
     * Test the constructor for Map4.
     */
    @Test
    public void testConstructor() {
        Map<String, String> m = new Map4<String, String>();
        Map<String, String> ref = this.createFromArgsRef();

        assertEquals(m, ref);
    }

    /**
     * Test adding single key-value pair and checks if map equals reference map.
     */
    @Test
    public final void addOnePairTest() {
        Map<String, String> testMap = this.createFromArgsTest();
        Map<String, String> refMap = this.createFromArgsRef("1", "2");

        testMap.add("1", "2");

        assertEquals(refMap, testMap);
    }

    /**
     * Test adding multiple key-value pairs and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void addMultiplePairsTest() {
        Map<String, String> testMap = this.createFromArgsTest("1", "2");
        Map<String, String> refMap = this.createFromArgsRef("1", "2", "3", "4");

        testMap.add("3", "4");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing single key-value pair and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void removePairTest() {
        Map<String, String> testMap = this.createFromArgsTest("1", "2");
        Map<String, String> refMap = this.createFromArgsRef();

        testMap.remove("1");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing multiple key-value pairs and checks if map equals reference
     * map.
     *
     */
    @Test
    public final void removeMultiplePairsTest() {
        Map<String, String> testMap = this.createFromArgsTest("1", "2", "3",
                "4");
        Map<String, String> refMap = this.createFromArgsRef("3", "4");

        testMap.remove("1");

        assertEquals(refMap, testMap);
    }

    /**
     * Test removing key-value pair and checks if map equals reference map.
     */
    @Test
    public final void removeAnyPairTest() {
        Map<String, String> testMap = this.createFromArgsTest("1", "2");
        Map<String, String> refMap = this.createFromArgsRef();

        testMap.removeAny();

        assertEquals(refMap, testMap);
    }
}
