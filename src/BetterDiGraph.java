import javax.swing.*;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.*;

public class BetterDiGraph implements EditableDiGraph{

    private int V;
    private int E;
    private HashMap<Integer, LinkedList<Integer>> list;

    public BetterDiGraph(){
        int V = 0;
        int E = 0;
        list = new HashMap<Integer, LinkedList<Integer>>();

    }
    @Override
    public void addEdge(int v, int w) {
        LinkedList<Integer> adj;
        if (!list.containsKey(v)) {
            addVertex(v);
        }
        adj = list.get(v);
        adj.add(w);
        list.put(v, adj);
        E++;
    }

    @Override
    public void addVertex(int v) {
        LinkedList<Integer> adj;
        adj = new LinkedList<Integer>();
        list.put(v,adj);
        V++;

    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        return list.get(v);
    }

    @Override
    public int getEdgeCount() {
        return E;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {

        if(!list.containsKey(v)) throw new NoSuchElementException();

        LinkedList<Integer> keys = new LinkedList<>();
        list.forEach((k,y) -> keys.add(k));
        int inDegree = 0;
        for (int i = 0; i < keys.size(); i++){
            LinkedList temp = list.get(keys.get(i));
            for(int j = 0; j < temp.size(); j++)
                if (temp.get(j).equals(v)) inDegree++;
        }
        return inDegree;
    }

    @Override
    public int getVertexCount() {
        return V;
    }

    @Override
    public void removeEdge(int v, int w) {
        if (!list.containsKey(v)){
            return;
        }
        LinkedList<Integer> temp = list.get(v);
        int x = 0;
        for (int i = 0; i < temp.size(); i++){
            x = temp.get(i);
            if (x == w){
                temp.remove(i);
                E--;
                return;
            }
        }
    }

    @Override
    public void removeVertex(int v) {
        LinkedList tempAdj = (LinkedList) getAdj(v);
        int adjCount = tempAdj.size();
        list.remove((v));
        V--;
        E = E - adjCount;

    }

    @Override
    public Iterable<Integer> vertices() {
        LinkedList<Integer> vertices = new LinkedList<>();
        list.forEach((k,v) -> vertices.add(k));
        return vertices;
    }

    @Override
    public boolean isEmpty() {
        if(V > 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean containsVertex(int v) {
        return list.containsKey(v);
    }
}
