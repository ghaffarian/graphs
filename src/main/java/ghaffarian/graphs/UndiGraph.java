/*** In The Name of Allah ***/
package ghaffarian.graphs;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A generic class for labeled undirected graphs.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class UndiGraph<V, E> extends AbstractGraph<V, E> {

    /**
     * Construct a new empty UndiGraph object.
     */
    public UndiGraph() {
        allEdges = new LinkedHashSet<>(32);
        allVertices = new LinkedHashSet<>();
        inEdges = new LinkedHashMap<>();
        outEdges = new LinkedHashMap<>();
    }
    
    /**
     * Copy constructor.
     * Create a new UndiGraph instance by copying the state of the given graph object.
     * 
     * @param graph the Graph object to be copied
     */
    public UndiGraph(AbstractGraph<V,E> graph) {
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
        return false;
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
    public boolean removeEdge(Edge<V, E> e) {
        if (allEdges.remove(e)) {
            inEdges.get(e.target).remove(e);
            outEdges.get(e.source).remove(e);
            return true;
        } else {
            Edge<V, E> reverse = e.reverse();
            if (allEdges.remove(reverse)) {
                inEdges.get(reverse.target).remove(reverse);
                outEdges.get(reverse.source).remove(reverse);
                return true;
            }
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
        // First remove all edges from src to trgt
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
        // Also remove any reverse edges
        V srcRev = trgt, trgtRev = src;
        if (inEdges.get(trgtRev).size() > outEdges.get(srcRev).size()) {
            iterSet = outEdges.get(srcRev);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.target.equals(trgtRev)) {
                    it.remove();
                    allEdges.remove(next);
                    inEdges.get(trgtRev).remove(next);
                    removed.add(next);
                }
            }
        } else {
            iterSet = inEdges.get(trgtRev);
            Iterator<Edge<V,E>> it = iterSet.iterator();
            while (it.hasNext()) {
                Edge<V,E> next = it.next();
                if (next.source.equals(srcRev)) {
                    it.remove();
                    allEdges.remove(next);
                    outEdges.get(srcRev).remove(next);
                    removed.add(next);
                }
            }
        }
        return removed;
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
    public boolean containsEdge(Edge<V,E> e) {
        return allEdges.contains(e) || allEdges.contains(e.reverse());
    }
    
    @Override
    public boolean containsEdge(V src, V trg) {
        for (Edge<V,E> edge: outEdges.get(src)) {
            if (edge.target.equals(trg))
                return true;
        }
        for (Edge<V,E> edge: outEdges.get(trg)) {
            if (edge.target.equals(src))
                return true;
        }
        return false;
    }
}
