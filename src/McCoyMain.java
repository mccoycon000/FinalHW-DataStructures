/**
 * Program for generating kanji component dependency order via topological sort.
 * 
 * @author (your name), Acuna
 * @version (version)
 */

import java.util.LinkedList;
import java.util.HashMap;
import java.io.*;


public class McCoyMain {
    
    /**
     * Entry point for testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        HashMap<Integer, Character> kanjiInfo = new HashMap<>();

        BetterDiGraph graph = new BetterDiGraph();

        try { //Creates hashmap of kanji characters

            BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Connor\\IdeaProjects\\FinalHW\\src\\data-kanji.txt")), "UTF8"));

            Integer i;
            Character j;


            String c = indexReader.readLine();

            while ((c = indexReader.readLine()) != null) {
                boolean skip = false;
                if (c.charAt(0) == '#'){ //If the first char in line is #. it skips the line and reads the next
                    skip = true;
                    if(c == null) break;
                }
                if (skip == false) {
                    int length = c.length();
                    j = c.charAt(length - 1); //Get the character out of the line.
                    i = Integer.parseInt(c.replaceAll("[\\D]", ""));//Get integer out of the line by changing all none int characters to blanks.
                    kanjiInfo.put(i, j); //Add to hashmap storing symbol with key
                    graph.addVertex(i); //Add vertex to BetterDiGraph Object
                    //System.out.println(kanjiInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<Integer, Integer> compMap = new HashMap<>(); //Mostly used in testing

        try { //Create hashmap with dependencies. And add edges to BetterDiMap
            BufferedReader componentReader =  new BufferedReader(new FileReader("C:\\Users\\Connor\\IdeaProjects\\FinalHW\\src\\data-components.txt"));


            Integer first;
            Integer second;
            String x = componentReader.readLine();

            while((x = componentReader.readLine()) != null){

                //System.out.println(x);

                String s = x;
                int index = -1;
                for(int i = 0; i < s.length(); i++ ){  //Find the index in string of tab
                    Character y =x.charAt(i);
                    if(y.equals('\t')){
                        index = i;
                        break;
                    }
                }

                first = Integer.parseInt(x.substring(0, index));
                second = Integer.parseInt(x.substring(index + 1, x.length()));
                compMap.put(first,second);//for testing
                graph.addEdge(first, second);//Adding an edge for each relationship found

                //System.out.println(compMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(compMap);
       // System.out.println(kanjiInfo);
       // System.out.println(graph);

        IntuitiveTopological sortedGraph = new IntuitiveTopological(graph);
        if(sortedGraph.isDAG()) {
            System.out.println("Original: ");

            LinkedList<Integer> sorted = (LinkedList<Integer>) graph.vertices();
            for (int i = 0; i < sorted.size(); i++) {
                System.out.print(kanjiInfo.get(sorted.get(i)));
            }
            System.out.println();

            sorted = (LinkedList<Integer>) sortedGraph.order();

            System.out.println("Sorted: ");

            for (int i = 0; i < sorted.size(); i++) {
                System.out.print(kanjiInfo.get(sorted.get(i)));
            }
        }else {
            System.out.print("Graph has a cycle!");
        }


    }
}