import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
        String srcNode = e.getSrc();
        boolean srcNodeAdded = g.addNode(srcNode);

        String dstNode = e.getDst();
        boolean dstNodeAdded = g.addNode(dstNode);

        boolean edgeAdded = g.addEdge(srcNode, dstNode);

        // nodes not addded separately from edges so can return any of these to find out if edge was actually added or not
        return edgeAdded || srcNodeAdded || dstNodeAdded;
    }

    public boolean hasNode(String n) {
        return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
        return g.hasEdge(e.getSrc(), e.getDst());
    }

    public boolean removeEdge(Edge e) {
        if (!hasEdge(e)) {
            return false;
        }

        String srcNode = e.getSrc();
        String dstNode = e.getDst();

        g.removeEdge(srcNode, dstNode);

        if (g.succ(srcNode).isEmpty() || g.pred(srcNode).isEmpty()) {
            g.removeNode(srcNode);
        }

        if (g.succ(dstNode).isEmpty() || g.pred(dstNode).isEmpty()) {
            g.removeNode(dstNode);
        }

        return true;
    }

    public List<Edge> outEdges(String n) {
        List<Edge> outEdges = new ArrayList<>();

        List<String> successors = g.succ(n);

        for (String succNode: successors) {
            outEdges.add(new Edge(n, succNode));
        }

        return outEdges;
    }

    public List<Edge> inEdges(String n) {
        List<Edge> inEdges = new ArrayList<>();

        List<String> predecessors = g.pred(n);

        for (String predNode: predecessors) {
            inEdges.add(new Edge(predNode, n));
        }

        return inEdges;
    }

    public List<Edge> edges() {
        List<Edge> allEdges = new ArrayList<>();

        for (String n: g.nodes()) {
            List<Edge> nOutEdges = outEdges(n);

            for (Edge e: nOutEdges) {
                allEdges.add(e);
            }
        }

        return allEdges;
    }

    public EdgeGraph union(EdgeGraph g) {
        EdgeGraphAdapter unionedGraph = new EdgeGraphAdapter(new ListGraph());

        List<Edge> thisGraphEdges = this.edges();
        for (Edge e: thisGraphEdges) {
            unionedGraph.addEdge(e);
        }

        List<Edge> gGraphEdges = g.edges();
        for (Edge e: gGraphEdges) {
            unionedGraph.addEdge(e);
        }

        return unionedGraph;
    }

    public boolean hasPath(List<Edge> e) {
        if (e.isEmpty()) {
            return true;
        }

        for (Edge edge: e) {
            if (!this.hasEdge(edge)) {
                return false;
            }
        }

        for (int i = 0; i < e.size() - 1; i++) {
            Edge currEdge = e.get(i);
            Edge nextEdge = e.get(i + 1);

            if (!currEdge.getDst().equals(nextEdge.getSrc())) {
                throw new BadPath("Given edges do not form a valid path.");
            }
        }

        return true;
    }
}
