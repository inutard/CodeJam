package codejam.y2008.round_emea.rainbow_trees;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import codejam.utils.datastructures.GraphInt;
import codejam.utils.datastructures.TreeInt;
import codejam.utils.main.DefaultInputFiles;
import codejam.utils.main.Runner.TestCaseInputScanner;
import codejam.utils.multithread.Consumer.TestCaseHandler;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;

public class Main implements TestCaseHandler<InputData>,
        TestCaseInputScanner<InputData>, DefaultInputFiles {

    @Override
    public String[] getDefaultInputFiles() {
 //       return new String[] {"sample.in"};
        return new String[] {"C-small-practice.in", };
       // return new String[] {"C-large-practice.in"};
     //   return new String[] {"A-small-practice.in", "A-large-practice.in"};
    }

    @Override
    public InputData readInput(Scanner scanner, int testCase) {
        InputData i = new InputData(testCase);
        i.n = scanner.nextInt();
        i.k = scanner.nextInt();
        i.edges = new ArrayList<>(i.n-1);
        
        for(int ii = 0; ii < i.n - 1; ++ii) {
            i.edges.add(new ImmutablePair<>(scanner.nextInt(),
                    scanner.nextInt()));
        }
        return i;
    }
   
    public Pair<Integer, Integer> createEdge(int n1, int n2) {
        return new ImmutablePair<>(Math.min(n1,n2), Math.max(n1,n2));
    }
  
    public long perm(int n, int k) {
        Preconditions.checkArgument(k >= 1);
        long r = 1;
        for(int i = 0; i < k; ++i) {
            r *= (n-i);
            r %= MOD;
        }
        return r;
    }
    
    static class NodeData {
        BitSet vertices;
        long chromNum;
    }
    
    private static final int MOD = 1000000009;
    private static final BigInteger MOD_BI = BigInteger.valueOf(MOD);
  
    @Override
    public String handleCase(InputData input) {
        
        //First create the graph of vertices
        GraphInt vertexGraph = new GraphInt();
        List<Pair<Integer,Integer>>  edges = new ArrayList<>();
        BiMap<Pair<Integer,Integer>, Integer> edgeIntMap = HashBiMap.create();
        
        for(Pair<Integer, Integer> edge : input.edges ) {
            edges.add(createEdge(edge.getLeft(), edge.getRight()));
            edgeIntMap.put(edges.get(edges.size()-1), edges.size()-1);
            
            vertexGraph.addConnection(edge.getLeft(), edge.getRight());
        }
               
        TreeInt<NodeData> tree = vertexGraph.convertToTree(1);
        
        Stack<TreeInt<NodeData>.Node> toVisit = new Stack<>();
        Set<TreeInt<NodeData>.Node> visited = new HashSet<>();
        
        toVisit.add(tree.getRoot());
        tree.getRoot().setData(null);
        
        while(!toVisit.isEmpty()) {
            TreeInt<NodeData>.Node node = toVisit.peek();
            
            //If node has height <= 2, then we can process it directly
            //Add all edges connected to the edge connecting the 
            //subtree rooted at node to the tree
            if (node.getHeight() <= 2) {
                BitSet vertexSet = BitSet.valueOf(node.getChildrenBits().toLongArray());
                vertexSet.set(node.getId());
                if (node.getParent() != null) {
                    vertexSet.or(node.getParent().getChildrenBits());
                    vertexSet.set(node.getParent().getId());
                    if (node.getParent().getParent() != null) {
                        vertexSet.set(node.getParent().getParent().getId());
                    }
                }
            
                NodeData data = new NodeData();
                data.chromNum = perm(input.k, vertexSet.cardinality() - 1);
                data.vertices = vertexSet;
                
                node.setData(data);
                visited.add(node);
                toVisit.pop();
                continue;
            }
            
            //Have children been visited yet?
            Iterator<TreeInt<NodeData>.Node> childIt = node.getChildren().iterator(); 
            TreeInt<NodeData>.Node child = childIt.next(); 
            if (!visited.contains(child)) {
                //Add all children to stack
                toVisit.add(child);
                while(childIt.hasNext()) {
                    child = childIt.next();
                    toVisit.add(child);
                }
                continue;
            }
            
            //All children have been visited
            /*
            Set<Integer> vertexSet = node.getNextLevel();
            
            if (node.getParent() != null) {
                vertexSet.addAll(node.getParent().getNextLevel());
                
                if (node.getParent().getParent() != null) {
                    vertexSet.add(node.getParent().getParent().getId());
                }
            }*/
            
            BitSet vertexSet = BitSet.valueOf(node.getChildrenBits().toLongArray());
            
            vertexSet.set(node.getId());
            
            if (node.getParent() != null) {
                vertexSet.or(node.getParent().getChildrenBits());
                vertexSet.set(node.getParent().getId());
                
                if (node.getParent().getParent() != null) {
                    vertexSet.set(node.getParent().getParent().getId());
                }
            }
            
            //Combine this sub graph with children
            long cn = perm(input.k, vertexSet.cardinality() - 1);
            
            childIt = node.getChildren().iterator();
            while(childIt.hasNext()) {
                child = childIt.next();
                
                //It was already included by the vertices set
                if (child.getHeight() <= 1)
                    continue;
                
                BitSet intersection = BitSet.valueOf(vertexSet.toLongArray());
                //Set<Integer> intersection = Sets.intersection(vertexSet,child.getData().vertices);
                intersection.and(child.getData().vertices);
                long interSecCn = perm(input.k, intersection.cardinality()-1);
                cn *= child.getData().chromNum;
                cn %= MOD;
                if (interSecCn == 0) {
                    Preconditions.checkState(cn == 0);
                } else {
                    cn = BigInteger.valueOf(cn).
                            multiply(
                                    BigInteger.valueOf(interSecCn).modInverse(MOD_BI))
                                    .mod(MOD_BI).longValue();
                    
                }
                
                //vertexSet.addAll(child.getData().vertices);
                vertexSet.or(child.getData().vertices);
            }
            
            NodeData data = new NodeData();
            data.chromNum = cn;
            data.vertices = vertexSet;
            node.setData(data);
            
            toVisit.pop();
            visited.add(node);
        }
        
        long ans = tree.getRoot().getData().chromNum;
        
        
        
        return String.format("Case #%d: %d", input.testCase, ans);
    
       
    }
    
    public String handleCaseBruteForce(InputData input) {
        //First create the graph of vertices
        GraphInt vertexGraph = new GraphInt();
        List<Pair<Integer,Integer>>  edges = new ArrayList<>();
        BiMap<Pair<Integer,Integer>, Integer> edgeIntMap = HashBiMap.create();
        
        for(Pair<Integer, Integer> edge : input.edges ) {
            edges.add(createEdge(edge.getLeft(), edge.getRight()));
            edgeIntMap.put(edges.get(edges.size()-1), edges.size()-1);
            
            vertexGraph.addConnection(edge.getLeft(), edge.getRight());
        }
        
        
        //Create edge graph
        GraphInt edgeGraph = new GraphInt();
        
        for(Pair<Integer,Integer> edge : edges) {
            Set<Integer> leftNeighbors = vertexGraph.getNeighbors(edge.getLeft());
            Set<Integer> rightNeighbors = vertexGraph.getNeighbors(edge.getRight());
            int edgeNum = edgeIntMap.get(edge);
            
            edgeGraph.addNode(edgeNum);
            
            for(Integer neigh : leftNeighbors) {                
                Pair<Integer,Integer> adjEdge = createEdge(neigh, edge.getLeft());                
                int adjEdgeNum = edgeIntMap.get(adjEdge);                
                edgeGraph.addConnection(edgeNum,adjEdgeNum);
            }
            
            for(Integer neigh : rightNeighbors) {
                Pair<Integer,Integer> adjEdge = createEdge(neigh, edge.getRight());                
                int adjEdgeNum = edgeIntMap.get(adjEdge);                
                edgeGraph.addConnection(edgeNum,adjEdgeNum);
            }
        }
        
      //Same as edgeGraph with edges added between neighbors of neighbors
        GraphInt rainbowGraph = new GraphInt();
        for(int edgeNum = 0; edgeNum < input.n - 1; ++edgeNum) {
            Set<Integer> adjEdges = edgeGraph.getNeighbors(edgeNum);
            
            rainbowGraph.addNode(edgeNum);
            
            for(Integer adjEdge : adjEdges) {
                rainbowGraph.addConnection(adjEdge,edgeNum);
                
                Set<Integer> adjRainEdges = edgeGraph.getNeighbors(adjEdge);
                for(Integer rainEdge : adjRainEdges) {
                    rainbowGraph.addConnection(rainEdge,edgeNum);
                }
            }
        }
        
        
        Map<GraphInt, Integer> memoize = Maps.newHashMap();
        
         int r = getChromaticPolynomial(rainbowGraph, input.k, memoize);
        return String.format("Case #%d: %d", input.testCase, r);
    }
    
    int getChromaticPolynomial(GraphInt graph, int k, Map<GraphInt, Integer> memoize) {
        
        Integer ret = memoize.get(graph);
        
        if (ret != null)
            return ret;
        
        //Get an edge
        Integer v = null;
        Integer u = null;
        Iterator<Integer> it = graph.getNodes().iterator();
        while(it.hasNext() ) {
            u = it.next();
            Set<Integer> adjNodes = graph.getNeighbors(u);
            
            if (!adjNodes.isEmpty()) {
                v = adjNodes.iterator().next();
                break;
            }
        }
        
        //If there is no edge, then we can return k ^ |v|
        if (v==null) {
            if (graph.getNodes().size() == 0)
                return 0;
            
            return IntMath.pow(k, graph.getNodes().size());
        }
        
        GraphInt withoutEdgeUV = new GraphInt(graph);
        withoutEdgeUV.removeConnection(u,v);
        int r = getChromaticPolynomial(withoutEdgeUV, k, memoize);
        
        
        //Now contract edge uv
        GraphInt withContraction = new GraphInt(graph);
        withContraction.contractEdge(u,v);
        Preconditions.checkState(withContraction.getNodes().size() + 1== graph.getNodes().size());
        r-=getChromaticPolynomial(withContraction, k, memoize);
        
        memoize.put(graph, r);
        return r;
    }

}