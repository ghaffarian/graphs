/*** In The Name of Allah ***/
package ghaffarian.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A generic class for labeled digraphs (directed graphs).
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Digraph<V, E> extends AbstractGraph<V, E> {
    
    /**
     * Construct a new empty Digraph object.
     */
    public Digraph() {
        allEdges = new LinkedHashSet<>(32);
        allVertices = new LinkedHashSet<>();
        inEdges = new LinkedHashMap<>();
        outEdges = new LinkedHashMap<>();
    }
    
    /**
     * Copy constructor.
     * Create a new Digraph instance by copying the state of the given graph object.
     * 
     * @param graph the Graph object to be copied
     */
    public Digraph(AbstractGraph<V,E> graph) {
        // copy all vertices and edges
        allEdges = new LinkedHashSet<>(graph.allEdges);
        allVertices = new LinkedHashSet<>(graph.allVertices);
        // copy incoming-edges map
        inEdges = new LinkedHashMap<>();
        for (V v: graph.inEdges.keySet())
            inEdges.put(v, new LinkedHashSet<>(graph.inEdges.get(v)));
        // copy outgoing-edges map
        outEdges = new LinkedHashMap<>();
        for (V v: graph.outEdges.keySet())
            outEdges.put(v, new LinkedHashSet<>(graph.outEdges.get(v)));
    }
    
    @Override
    public boolean isDirected() {
        return true;
    }
    
    @Override
    public boolean addVertex(V v) {
        if (allVertices.add(v)) {
            inEdges.put(v, new LinkedHashSet<>());
            outEdges.put(v, new LinkedHashSet<>());
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeVertex(V v) {
        if (allVertices.remove(v)) {
            allEdges.removeAll(inEdges.remove(v));
            allEdges.removeAll(outEdges.remove(v));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addEdge(Edge<V,E> e) {
        if (!allVertices.contains(e.source))
            throw new IllegalArgumentException("No such source-vertex in this graph!");
        if (!allVertices.contains(e.target))
            throw new IllegalArgumentException("No such target-vertex in this graph!");
        if (allEdges.add(e)) {
            inEdges.get(e.target).add(e);
            outEdges.get(e.source).add(e);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addEdge(V src, V trgt) {
        return addEdge(new Edge<>(src, null, trgt));
    }
    
    @Override
    public boolean removeEdge(Edge<V,E> e) {
        if (allEdges.remove(e)) {
            inEdges.get(e.target).remove(e);
            outEdges.get(e.source).remove(e);
            return true;
        }
        return false;
    }
    
    @Override
    public Set<Edge<V,E>> removeEdges(V src, V trgt) {
        if (!allVertices.contains(src))
            throw new IllegalArgumentException("No such source-vertex in this graph!");
        if (!allVertices.contains(trgt))
            throw new IllegalArgumentException("No such target-vertex in this graph!");
        Set<Edge<V,E>> iterSet;
        Set<Edge<V,E>> removed = new LinkedHashSet<>();
        if (inEdges.get(trgt).size() > outEdges.get(src).size()) {
            iterSet = outEdges.get(src);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.target.equals(trgt)) {
                    it.remove();
                    allEdges.remove(next);
                    inEdges.get(trgt).remove(next);
                    removed.add(next);
                }
            }
        } else {
            iterSet = inEdges.get(trgt);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.source.equals(src)) {
                    it.remove();
                    allEdges.remove(next);
                    outEdges.get(src).remove(next);
                    removed.add(next);
                }
            }
        }
        return removed;
    }
    
    @Override
    public boolean addGraph(AbstractGraph<V,E> graph) {
        boolean modified = false;
        for (V vrtx: graph.allVertices)
            modified |= addVertex(vrtx);
        for (Edge<V,E> edge: graph.allEdges)
            modified |= addEdge(edge);
        return modified;
    }
    
    @Override
    public Set<Edge<V,E>> copyEdgeSet() {
        return new LinkedHashSet<>(allEdges);
    }
    
    @Override
    public Set<V> copyVertexSet() {
        return new LinkedHashSet<>(allVertices);
    }
    
    @Override
    public Set<Edge<V,E>> copyIncomingEdges(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return new LinkedHashSet<>(inEdges.get(v));
    }
    
    @Override
    public Set<Edge<V,E>> copyOutgoingEdges(V v) {
        if (!allVertices.contains(v))
            throw new IllegalArgumentException("No such vertex in this graph!");
        return new LinkedHashSet<>(outEdges.get(v));
    }
    
    @Override
    public Set<Edge<V,E>> getEdgesWithLabel(E label) {
        Set<Edge<V,E>> edges = new LinkedHashSet<>();
        for (Edge e: allEdges) {
            if (label.equals(e.label))
                edges.add(e);
        }
        return edges;
    }
    
    @Override
    public boolean containsEdge(Edge<V,E> e) {
        return allEdges.contains(e);
    }
    
    @Override
    public boolean containsEdge(V src, V trg) {
        for (Edge<V,E> edge: outEdges.get(src)) {
            if (edge.target.equals(trg))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean containsVertex(V v) {
        return allVertices.contains(v);
    }
    
    @Override
    public boolean isConnected() {
        Set<V> visited = new HashSet<>();
        Deque<V> visiting = new ArrayDeque<>();
        visiting.add(allVertices.iterator().next());
        while (!visiting.isEmpty()) {
            V next = visiting.remove();
            visited.add(next);
            for (Edge<V,E> out: outEdges.get(next)) {
                if (!visited.contains(out.target))
                    visiting.add(out.target);
            }
            for (Edge<V,E> in: inEdges.get(next)) {
                if (!visited.contains(in.source))
                    visiting.add(in.source);
            }
        }
        return visited.size() == allVertices.size();
        // return visited.containsAll(allVertices);
    }
}
