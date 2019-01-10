/*** In The Name of Allah ***/
package ghaffarian.graphs;

/**
 * A special kind of labeled digraph (directed graph).
 * While the mathematical definition of graphs only allows unique vertices and edges 
 * (a graph consists of a set of vertices and a set of edges), there are some applications
 * which might require to have equal but not identical vertices.
 * For such usecases, this class extends the Digraph implementation to allow
 * equal (but not identical) vertices and edges to be added to the graph.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class IdentityDigraph<V,E> extends Digraph<V,E> {

    /**
     * Construct a new empty IdentityDiraph object.
     */
    public IdentityDigraph() {
        
    }

    /**
     * Copy constructor. 
     * Create a new IdentityDigraph instance by copying the state of the given Digraph object.
     * 
     * @param graph the Graph object to be copied
     */
    public IdentityDigraph(Digraph<V, E> digraph) {
//            // copy all vertices and edges
//            allEdges = new IdentityLinkedHashSet<>(graph.allEdges);
//            allVertices = new IdentityLinkedHashSet<>(graph.allVertices);
//            // copy incoming-edges map
//            inEdges = new IdentityHashMap<>();
//            for (V v : graph.inEdges.keySet())
//                inEdges.put(v, new IdentityLinkedHashSet<>(graph.inEdges.get(v)));
//            // copy outgoing-edges map
//            outEdges = new IdentityHashMap<>();
//            for (V v : graph.outEdges.keySet())
//                outEdges.put(v, new IdentityLinkedHashSet<>(graph.outEdges.get(v)));
    }
    
}
