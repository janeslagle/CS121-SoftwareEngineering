import java.io.StringBufferInputStream;
import java.util.*;

public class Main {

	// Run "java -ea Main" to run with assertions enabled (If you run
	// with assertions disabled, the default, then assert statements
	// will not execute!)

	// test1() - test10() for ListGraph
	// Tests addNode
	public static void test1() {
		Graph g = new ListGraph();  // Create new graph

		assert !g.hasNode("a");  // Make sure graph that should be empty doesn't have node before actually add it

		assert g.addNode("a");   // Add nodes to graph
		assert g.addNode("b");

		assert g.hasNode("a");   // Test that correct nodes are added to graph
		assert g.hasNode("b");
		assert !g.hasNode("e");  // Make sure only have nodes that should be in graph
		assert !g.hasNode("m");
	}

	// Tests addEdge
	public static void test2() {
		Graph g = new ListGraph();    // Create new graph

		assert g.addNode("a");     // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		assert !g.hasEdge("a", "b");  // Make sure don't have any edges before add them

		assert g.addEdge("a", "b");   // Add edges to graph
		assert g.addEdge("b", "e");
		assert g.addEdge("c", "a");
		assert g.addEdge("d", "e");

		assert g.hasEdge("a", "b");   // Make sure have all edges just created
		assert g.hasEdge("b", "e");
		assert g.hasEdge("c", "a");
		assert g.hasEdge("d", "e");

		assert !g.hasEdge("b", "a");  // Make sure don't have edges that did not add to graph
		assert !g.hasEdge("a", "e");
	}

	// Tests removeNode
	public static void test3() {
		Graph g = new ListGraph();    // Create new graph

		assert g.addNode("a");     // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		assert g.hasNode("a");     // Make sure have nodes just added
		assert g.hasNode("b");
		assert g.hasNode("c");
		assert g.hasNode("d");
		assert g.hasNode("e");

		assert g.removeNode("a");  // Remove nodes from graph
		assert g.removeNode("c");
		assert g.removeNode("e");

		assert !g.hasNode("a");    // Make sure graph doesn't have removed nodes anymore
		assert !g.hasNode("c");
		assert !g.hasNode("e");

		assert g.hasNode("b");     // Make sure graph still has nodes didn't remove
		assert g.hasNode("d");
	}

	// Tests removeEdge
	public static void test4() {
		Graph g = new ListGraph();      // Create new graph

		assert g.addNode("a");       // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		assert !g.hasEdge("a", "b");    // Make sure don't have any edges before add them

		assert g.addEdge("a", "b");     // Add edges to graph
		assert g.addEdge("b", "e");
		assert g.addEdge("c", "a");
		assert g.addEdge("d", "e");

		assert g.hasEdge("a", "b");     // Make sure have all edges just created
		assert g.hasEdge("b", "e");
		assert g.hasEdge("c", "a");
		assert g.hasEdge("d", "e");

		assert g.removeEdge("a", "b");  // Remove edges
		assert g.removeEdge("c", "a");

		assert !g.hasEdge("a", "b");    // Make sure removed edges are actually removed
		assert !g.hasEdge("c", "a");

		assert g.hasEdge("b", "e");     // Make sure edges didn't remove are still in graph
		assert g.hasEdge("d", "e");
	}

	// Tests nodes
	public static void test5() {
		Graph g = new ListGraph();      // Create new graph

		assert g.addNode("a");       // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		// Make sure that nodes contains all nodes that just added
		assert g.nodes().contains("a");
		assert g.nodes().contains("b");
		assert g.nodes().contains("c");
		assert g.nodes().contains("d");
		assert g.nodes().contains("e");
	}

	// Tests succ
	public static void test6() {
		Graph g = new ListGraph();      // Create new graph

		assert g.addNode("a");       // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		assert g.addEdge("a", "b");     // Add edge from a to all other nodes in graph
		assert g.addEdge("a", "c");
		assert g.addEdge("a", "d");
		assert g.addEdge("a", "e");

		assert g.addEdge("b", "d");     // Add some other edges too
		assert g.addEdge("e", "a");
		assert g.addEdge("c", "d");

		// Make sure that succ list for each node has all nodes that have edge from them
		assert g.succ("a").contains("b");
		assert g.succ("a").contains("c");
		assert g.succ("a").contains("d");
		assert g.succ("a").contains("e");
		assert g.succ("b").contains("d");
		assert g.succ("e").contains("a");
		assert g.succ("c").contains("d");
	}

	// Tests pred
	public static void test7() {
		Graph g = new ListGraph();      // Create new graph

		assert g.addNode("a");       // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");
		assert g.addNode("e");

		assert g.addEdge("c", "b");     // Add edges to graph to b
		assert g.addEdge("d", "b");
		assert g.addEdge("e", "b");

		// Check that pred with b as input returns c,d,e
		assert g.pred("b").contains("c");
		assert g.pred("b").contains("d");
		assert g.pred("b").contains("e");
	}

	// Tests union
	public static void test8() {
		Graph g1 = new ListGraph();  // Create 1st graph

		assert g1.addNode("a");          // Add nodes to 1st graph
		assert g1.addNode("b");
		assert g1.addNode("c");
		assert g1.addNode("d");
		assert g1.addNode("e");

		assert g1.addEdge("a", "b");        // Add edges to 1st graph
		assert g1.addEdge("b", "c");
		assert g1.addEdge("b", "a");
		assert g1.addEdge("c", "d");
		assert g1.addEdge("d", "c");
		assert g1.addEdge("d", "e");

		Graph g2 = new ListGraph();   // Create 2nd graph

		// Union g1 + g2 and make sure that the union is the same as g1 since g2 is empty
		Graph unionedGraph1 = g1.union(g2);

		assert unionedGraph1.hasNode("a");
		assert unionedGraph1.hasNode("b");
		assert unionedGraph1.hasNode("c");
		assert unionedGraph1.hasNode("d");
		assert unionedGraph1.hasNode("e");

		assert unionedGraph1.hasEdge("a", "b");
		assert unionedGraph1.hasEdge("b", "a");
		assert unionedGraph1.hasEdge("c", "d");
		assert unionedGraph1.hasEdge("d", "c");
		assert unionedGraph1.hasEdge("d", "e");

		Graph g3 = new ListGraph();  // Create 3rd graph

		assert g3.addNode("a");          // Add nodes to 3rd graph, all same nodes as g1, but with some more added
		assert g3.addNode("b");
		assert g3.addNode("c");
		assert g3.addNode("d");
		assert g3.addNode("e");
		assert g3.addNode("f");
		assert g3.addNode("g");
		assert g3.addNode("h");

		assert g3.addEdge("a", "b");        // Add edges to 3rd graph, all same as g1, but with some more added
		assert g3.addEdge("b", "c");
		assert g3.addEdge("b", "a");
		assert g3.addEdge("c", "d");
		assert g3.addEdge("d", "c");
		assert g3.addEdge("d", "e");
		assert g3.addEdge("a", "f");
		assert g3.addEdge("d", "g");
		assert g3.addEdge("c", "h");
		assert g3.addEdge("f", "g");
		assert g3.addEdge("f", "h");
		assert g3.addEdge("g", "a");

		// Union g1 + g3
		Graph unionedGraph2 = g1.union(g3);

		// Make sure unionedGraph2 is actually the union of g1 + g3
		assert unionedGraph2.hasNode("a");
		assert unionedGraph2.hasNode("b");
		assert unionedGraph2.hasNode("c");
		assert unionedGraph2.hasNode("d");
		assert unionedGraph2.hasNode("e");
		assert unionedGraph2.hasNode("f");
		assert unionedGraph2.hasNode("g");
		assert unionedGraph2.hasNode("h");

		assert unionedGraph2.hasEdge("a", "b");
		assert unionedGraph2.hasEdge("b", "a");
		assert unionedGraph2.hasEdge("c", "d");
		assert unionedGraph2.hasEdge("d", "c");
		assert unionedGraph2.hasEdge("d", "e");
		assert unionedGraph2.hasEdge("a", "f");
		assert unionedGraph2.hasEdge("d", "g");
		assert unionedGraph2.hasEdge("c", "h");
		assert unionedGraph2.hasEdge("f", "g");
		assert unionedGraph2.hasEdge("f", "h");
		assert unionedGraph2.hasEdge("g", "a");
	}

	// Tests subGraph
	public static void test9() {
		Graph g = new ListGraph();

		assert g.addNode("a");          // Add nodes to graph
		assert g.addNode("b");
		assert g.addNode("c");
		assert g.addNode("d");

		assert g.addEdge("a", "b");        // Add edges to graph
		assert g.addEdge("a", "c");
		assert g.addEdge("c", "d");

		// Create nodes input to plug into subGraph method
		Set<String> nodes = new HashSet<>();
		assert nodes.add("a");
		assert nodes.add("b");
		assert nodes.add("c");
		assert nodes.add("e");

		Graph subGraph = g.subGraph(nodes);
		assert subGraph.hasNode("a");
		assert subGraph.hasNode("b");
		assert subGraph.hasNode("c");
		assert subGraph.hasEdge("a", "b");
		assert subGraph.hasEdge("a", "c");
	}

	// Tests connected
	public static void test10() {
		Graph g1 = new ListGraph();

		assert g1.addNode("a");
		assert g1.addNode("b");
		assert g1.addNode("c");
		assert g1.addNode("d");
		assert g1.addNode("e");

		assert g1.addEdge("a", "b");
		assert g1.addEdge("b", "c");
		assert g1.addEdge("c", "d");
		assert g1.addEdge("d", "e");
		assert g1.addEdge("d", "c");
		assert g1.addEdge("c", "e");
		assert g1.addEdge("e", "a");

		assert g1.connected("a", "e");
		assert g1.connected("d", "a");
		assert g1.connected("c", "b");

		Graph g2 = new ListGraph();

		assert g2.addNode("a");
		assert g2.addNode("b");
		assert g2.addNode("c");
		assert g2.addNode("d");

		assert g2.addEdge("a", "b");
		assert g2.addEdge("a", "d");
		assert g2.addEdge("c", "d");

		assert g2.connected("a", "b");
		assert g2.connected("a", "d");
		assert !g2.connected("a", "c");
		assert !g2.connected("b", "c");
	}

	// test11() - test18() for EdgeGraphAdapter
	// Tests addEdge
	public static void test11() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "b");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);

		assert eg.hasEdge(e1);                    // Make sure edges were actually added
		assert eg.hasEdge(e2);
	}

	// Tests hasNode
	public static void test12() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "b");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);

		assert eg.hasNode("a");                // Nodes should be added since an edge containing them has been added to eg
		assert eg.hasNode("b");
		assert eg.hasNode("c");
	}

	// Tests removeEdge
	public static void test13() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "b");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);

		assert eg.hasEdge(e1);                    // Make sure edges were actually added
		assert eg.hasEdge(e2);

		assert eg.removeEdge(e1);                 // Make sure edges were actually removed

		// e1 was last edge from a so check that it removed node a
		// e1 was also last edge to b so make sure it removed node b
		// since node b was removed, that means that edge e2 should also not be there anymore...
		assert !eg.hasNode("a");
		assert !eg.hasEdge(e2);
		assert !eg.hasNode("b");
	}

	// Tests outEdges
	public static void test14() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "b");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");
		Edge e3 = new Edge("a", "d");
		Edge e4 = new Edge("a", "e");
		Edge e5 = new Edge("b", "e");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);
		assert eg.addEdge(e3);
		assert eg.addEdge(e4);
		assert eg.addEdge(e5);

		// Check that outEdges is correct for both a, b nodes
		assert eg.outEdges("a").size() == 3;
		assert eg.outEdges("a").contains(e1);
		assert eg.outEdges("a").contains(e3);
		assert eg.outEdges("a").contains(e4);

		// Make sure that outEdges for one node doesn't include any for another node
		assert !eg.outEdges("a").contains(e2);

		assert eg.outEdges("b").size() == 2;
		assert eg.outEdges("b").contains(e2);
		assert eg.outEdges("b").contains(e5);
	}

	// Tests inEdges
	public static void test15() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "c");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");
		Edge e3 = new Edge("d", "c");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);
		assert eg.addEdge(e3);

		// Check that inEdges is correct for c node
		assert eg.inEdges("c").size() == 3;
		assert eg.inEdges("c").contains(e1);
		assert eg.inEdges("c").contains(e2);
		assert eg.inEdges("c").contains(e3);

		// Make sure that inEdges is empty for noes that are not dst node for any edge
		assert eg.inEdges("a").size() == 0;
	}

	// Tests edges
	public static void test16() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "c");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");
		Edge e3 = new Edge("d", "c");
		Edge e4 = new Edge("a", "b");
		Edge e5 = new Edge("a", "e");
		Edge e6 = new Edge("d", "b");
		Edge e7 = new Edge("d", "e");

		assert eg.addEdge(e1);                    // Add edges to eg
		assert eg.addEdge(e2);
		assert eg.addEdge(e3);
		assert eg.addEdge(e4);
		assert eg.addEdge(e5);
		assert eg.addEdge(e6);
		assert eg.addEdge(e7);

		// Make sure edges() list has all of the added edges in it
		assert eg.edges().size() == 7;
		assert eg.edges().contains(e1);
		assert eg.edges().contains(e2);
		assert eg.edges().contains(e3);
		assert eg.edges().contains(e4);
		assert eg.edges().contains(e5);
		assert eg.edges().contains(e6);
		assert eg.edges().contains(e7);
	}

	// Tests union
	public static void test17() {
		Graph g1 = new ListGraph();                // Create new graph
		EdgeGraph eg1 = new EdgeGraphAdapter(g1);   // Use the ListGraph to create new EdgeGraphAdapter case

		Edge e1 = new Edge("a", "c");     // Create some edges to add to eg
		Edge e2 = new Edge("b", "c");
		Edge e3 = new Edge("d", "c");

		assert eg1.addEdge(e1);                    // Add edges to eg
		assert eg1.addEdge(e2);
		assert eg1.addEdge(e3);

		// Test unioning eg1 with another empty eg graph
		Graph g2 = new ListGraph();
		EdgeGraph eg2 = new EdgeGraphAdapter(g2);

		EdgeGraph unionedGraph1 = eg1.union(eg2);
		assert unionedGraph1.hasNode("a");
		assert unionedGraph1.hasNode("b");
		assert unionedGraph1.hasNode("c");
		assert unionedGraph1.hasNode("d");

		assert unionedGraph1.hasEdge(e1);
		assert unionedGraph1.hasEdge(e2);
		assert unionedGraph1.hasEdge(e3);

		// Now test unioning with a non-empty eg graph
		Graph g3 = new ListGraph();
		EdgeGraph eg3 = new EdgeGraphAdapter(g3);

		eg3.addEdge(e1);
		eg3.addEdge(e2);
		eg3.addEdge(e3);

		Edge e4 = new Edge("a", "e");
		Edge e5 = new Edge("f", "g");

		eg3.addEdge(e4);
		eg3.addEdge(e5);

		EdgeGraph unionedGraph2 = eg1.union(eg3);

		assert unionedGraph2.hasNode("a");
		assert unionedGraph2.hasNode("b");
		assert unionedGraph2.hasNode("c");
		assert unionedGraph2.hasNode("d");
		assert unionedGraph2.hasNode("e");
		assert unionedGraph2.hasNode("f");
		assert unionedGraph2.hasNode("g");

		assert unionedGraph2.hasEdge(e1);
		assert unionedGraph2.hasEdge(e2);
		assert unionedGraph2.hasEdge(e3);
		assert unionedGraph2.hasEdge(e4);
		assert unionedGraph2.hasEdge(e5);
	}

	// Tests hasPath
	public static void test18() {
		Graph g = new ListGraph();                // Create new graph
		EdgeGraph eg = new EdgeGraphAdapter(g);   // Use the ListGraph to create new EdgeGraphAdapter case

		List<Edge> emptyList = Collections.<Edge> emptyList();

		assert eg.hasPath(emptyList);             // Empty lists should always return true

		// Now test that not having all edges in graph makes it false
		Edge e1 = new Edge("a", "b");
		Edge e2 = new Edge("b", "c");
		eg.addEdge(e1);

		List<Edge> invalidEdges = Arrays.asList(e1, e2);
		assert !eg.hasPath(invalidEdges);

		// Now test having valid edges
		eg.addEdge(e2);   // invalidEdges list should be valid now!
		assert eg.hasPath(invalidEdges);
	}

    public static void main(String[] args) {
		// Tests for ListGraph
		// test1();
		// test2();
		// test3();
		// test4();
		// test5();
		// test6();
		// test7();
		// test8();
		// test9();
		// test10();

		// Tests for EdgeGraphAdapter
		// test11();
		// test12();
		// test13();
		// test14();
		// test15();
		// test16();
		// test17();
		// test18();
    }
}
