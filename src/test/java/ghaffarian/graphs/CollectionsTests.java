/*** In The Name of Allah ***/
package ghaffarian.graphs;

import ghaffarian.collections.IdentityLinkedHashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testing graph operations.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class CollectionsTests {
    
    @Test
    public void identitySetTest()  {

        Set<String> idSet = new IdentityLinkedHashSet<>();
        //java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
        String str1 = new String("test-1");
        String str2 = new String("test-2");
        String str3 = new String("test-2");

        assertTrue(idSet.add(str1));
        assertFalse(idSet.add(str1));
        assertTrue(idSet.contains(str1));
        //
        assertTrue(idSet.add(str2));
        assertFalse(idSet.add(str2));
        assertTrue(idSet.contains(str2));
        assertFalse(idSet.contains(str3));
        //
        assertTrue(idSet.add(str3));
        assertFalse(idSet.add(str3));
        assertTrue(idSet.contains(str3));
        assertEquals(3, idSet.size());
        
        Atom hydrogen1 = new Atom("Hydrogen", "H");
        Atom hydrogen2 = new Atom("Hydrogen", "H");
        Atom oxygen = new Atom("Oxygen", "O");
        
        Set<Atom> normalSet = new LinkedHashSet<>();
        
        assertTrue(normalSet.add(oxygen));
        assertTrue(normalSet.contains(oxygen));
        assertTrue(normalSet.add(hydrogen1));
        assertTrue(normalSet.contains(hydrogen1));
        assertTrue(normalSet.contains(hydrogen2));
        assertFalse(normalSet.add(hydrogen2));
        assertEquals(2, normalSet.size());
        
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
