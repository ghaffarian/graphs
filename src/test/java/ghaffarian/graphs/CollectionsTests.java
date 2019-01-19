/*** In The Name of Allah ***/
package ghaffarian.graphs;

import ghaffarian.collections.IdentityLinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testing collections.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class CollectionsTests {
    
    @Test
    public void identitySetTest()  {
        Atom hydrogen1 = new Atom("Hydrogen", "H");
        Atom hydrogen2 = new Atom("Hydrogen", "H");
        Atom oxygen = new Atom("Oxygen", "O");
        
        Set<Atom> identitySet = new IdentityLinkedHashSet<>(); 
        //java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
        
        assertTrue(identitySet.add(oxygen));
        assertFalse(identitySet.add(oxygen));
        assertTrue(identitySet.contains(oxygen));
        //
        assertTrue(identitySet.add(hydrogen1));
        assertFalse(identitySet.add(hydrogen1));
        assertTrue(identitySet.contains(hydrogen1));
        assertFalse(identitySet.contains(hydrogen2));
        //
        assertTrue(identitySet.add(hydrogen2));
        assertFalse(identitySet.add(hydrogen2));
        assertTrue(identitySet.contains(hydrogen2));
        assertEquals(3, identitySet.size());
    }

}
