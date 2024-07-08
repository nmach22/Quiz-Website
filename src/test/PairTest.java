package test;

import junit.framework.TestCase;
import main.Manager.Pair;

public class PairTest extends TestCase {

    public PairTest(String name) {
        super(name);
    }

    public void testPairConstructor() {
        Pair pair = new Pair("firstValue", "secondValue");
        assertEquals("firstValue", pair.getFirst());
        assertEquals("secondValue", pair.getSecond());
    }

    public void testGettersAndSetters() {
        Pair pair = new Pair("firstValue", "secondValue");
        pair.setFirst("newFirst");
        pair.setSecond("newSecond");
        assertEquals("newFirst", pair.getFirst());
        assertEquals("newSecond", pair.getSecond());
    }

    public void testToString() {
        Pair pair = new Pair("firstValue", "secondValue");
        assertEquals("(firstValue, secondValue)", pair.toString());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PairTest.class);
    }
}
