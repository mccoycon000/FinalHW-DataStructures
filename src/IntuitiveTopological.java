import java.util.LinkedList;
import java.util.HashMap;

public class IntuitiveTopological implements TopologicalSort{

    private BetterDiGraph graph;

    public IntuitiveTopological(BetterDiGraph graph){
        this.graph = graph;
    }
    @Override
    public Iterable<Integer> order() {
        LinkedList<Integer> temp = (LinkedList<Integer>) graph.vertices();
        LinkedList<Integer> keys = new LinkedList<Integer>();
        LinkedList<Integer> topological = new LinkedList<Integer>();

        for (int i = 0; i < temp.size(); i++){ //Deep copy for list of vertices
            keys.add(temp.get(i));
        }

        while(!graph.isEmpty()){
            int inDegree;
            for(int i = 0; i < keys.size(); i++){
                inDegree = graph.getIndegree(keys.get(i));
                if(inDegree == 0){
                    topological.add(keys.get(i));
                    graph.removeVertex(keys.get(i));
                    keys.remove(i);
                    break;
                }
            }
        }
        return topological;
    }

    @Override
    public boolean isDAG() {
        if (graph.isEmpty()){
            return true;
        }

        LinkedList<Integer> temp = (LinkedList<Integer>) graph.vertices();
        LinkedList<Integer> tempAdj;
        BetterDiGraph tempGraph = new BetterDiGraph();

        for (int i = 0; i < temp.size(); i++){  //Create a deep copy
            tempAdj = (LinkedList<Integer>) graph.getAdj(temp.get(i));
            tempGraph.addVertex(temp.get(i));
            for(int j = 0; j < tempAdj.size(); j++){
                tempGraph.addEdge(temp.get(i), tempAdj.get(j));
            }
        }

        int inDegree;
        int count = 0;
        LinkedList<Integer> tempTwo = (LinkedList<Integer>) tempGraph.vertices();
        LinkedList<Integer> keys = new LinkedList<Integer>();

        for (int i = 0; i < tempTwo.size(); i++){ //Deep copy for list of vertices
            keys.add(tempTwo.get(i));
        }

        while(!tempGraph.isEmpty()){ //Loop for finding cycle
            if (count == 2){ //If for loop-completes without finding an in-Degree zero on non-Empty graph vert not DAG
                return false;
            }
            for(int i = 0; i < keys.size(); i++){
                inDegree = tempGraph.getIndegree(keys.get(i));
                if(inDegree == 0){
                    tempGraph.removeVertex(keys.get(i));
                    keys.remove(i);
                    count = 1;
                    break;
                }
                count = 2; //Represent no in-Degree zero vertices having been found yet.
            }
        }
        return true;
    }
}
