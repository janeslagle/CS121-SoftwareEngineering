import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if (nodes.containsKey(n)) {
            return false;
        }

        nodes.put(n, new LinkedList<>());
        return true;
    }

    public boolean addEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException("One or both nodes were not previously added as nodes.");
        }

        LinkedList<String> n1AdjacencyList = nodes.get(n1);
        if (n1AdjacencyList.contains(n2)) {
            return false;
        }

        n1AdjacencyList.add(n2);
        return true;
    }

    public boolean hasNode(String n) {
        return nodes.containsKey(n);
    }

    public boolean hasEdge(String n1, String n2) {
        if (!nodes.containsKey(n1)) {
            return false;
        }

        LinkedList<String> n1AdjacencyList = nodes.get(n1);
        return n1AdjacencyList.contains(n2);
    }

    public boolean removeNode(String n) {
        if (!nodes.containsKey(n)) {
            return false;
        }

        nodes.remove(n);  // Remove n as key from nodes (also removes it values, so removes all edges from n)

        // Loop through all values in nodes hashmap and remove all edges to n
        for (LinkedList<String> successors : nodes.values()) {
            successors.remove(n);
        }

        return true;
    }

    public boolean removeEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException("One or both nodes were not previously added as nodes.");
        }

        LinkedList<String> n1AdjacencyList = nodes.get(n1);
        return n1AdjacencyList.remove(n2);
    }

    public List<String> nodes() {
        Set<String> nodeKeys = nodes.keySet();
        List<String> allNodes = new ArrayList<>(nodeKeys);

        return allNodes;
    }

    public List<String> succ(String n) {
        if (!nodes.containsKey(n)) {
            throw new NoSuchElementException("Node not previously added as node.");
        }

        LinkedList<String> nAdjacencyList = nodes.get(n);
        List<String> successors = new ArrayList<>(nAdjacencyList);

        return successors;
    }

    public List<String> pred(String n) {
        List<String> predecessors = new ArrayList<>();
        Set<String> allNodes = nodes.keySet();

        // Loop through all nodes
        for (String node: allNodes) {
            // Get new node each time loop through
            LinkedList<String> nodeAdjacencyList = nodes.get(node);

            // Check if the adjacency list for current node has n in it (if there is an edge from current node to n)
            if (nodeAdjacencyList.contains(n)) {
                predecessors.add(node);
            }
        }

        return predecessors;
    }

    public Graph union(Graph g) {
        ListGraph unionedGraph = new ListGraph();

        // First add all nodes from current graph into new unioned graph
        // Then add all edges from current graph, use succ with nodes to add all edges for each node
        for (String n: this.nodes()) {
            unionedGraph.addNode(n);
        }

        for (String n: this.nodes()) {
            for (String succNode: this.succ(n)) {
                unionedGraph.addEdge(n, succNode);
            }
        }

        // Now add all nodes + edges from new graph inputted
        for (String n: g.nodes()) {
            unionedGraph.addNode(n);
        }

        for (String n: g.nodes()) {
            for (String succNode: g.succ(n)) {
                unionedGraph.addEdge(n, succNode);
            }
        }

        return unionedGraph;
    }

    public Graph subGraph(Set<String> nodes) {
        Graph subGraph = new ListGraph();

        // Want add nodes to subGraph that are in both current graph + nodes in inputted set
        for (String validKey: this.nodes.keySet()) {
            if (nodes.contains(validKey)) {
                subGraph.addNode(validKey);
            }
        }

        // Now add all corresponding edges from current graph for all valid nodes just added in subGraph
        // only add edge from current graph if both nodes in it are in nodes set
        for (String validKey: this.nodes.keySet()) {
            if (nodes.contains(validKey)) {
                for (String succ : this.succ(validKey)) {
                    if (nodes.contains(succ)) {
                        subGraph.addEdge(validKey, succ);
                    }
                }
            }
        }

        return subGraph;
    }

    public boolean connected(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException("One or both nodes were not previously added as nodes.");
        }

        HashSet<String> visitedNodes = new HashSet<>();  // Keep track of nodes have visited when trying to figure out paths so that don't revisit any

        // Initialize queue for BFS, start it from n1
        Queue<String> queue = new LinkedList<>();
        queue.add(n1);

        // BFS to see if can reach n2 from n1 or not
        while (!queue.isEmpty()) {
            String currentNode = queue.poll();  // Move on to next node

            // Check if have reached target node or not, if have then there is a path from n1 to n2!
            if (currentNode.equals(n2)) {
                return true;
            }

            visitedNodes.add(currentNode);

            // Add unvisited nodes to queue so that can loop through them too
            for (String succNodes : this.succ(currentNode)) {
                if (!visitedNodes.contains(succNodes)) {
                    queue.add(succNodes);
                }
            }
        }

        return false;  // If have exited while loop without returning true, then means no valid path
    }
}
