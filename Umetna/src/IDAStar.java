import java.util.ArrayList;
import java.util.LinkedList;

public class IDAStar {

    public static ArrayList<Integer> najdenaPot = new ArrayList<>();
    public static int steviloObdelanihVozlisc = 0;

    int[][] searchGraph;
    ArrayList<Integer> searchEndNodes;
    int[] searchHeurCost;

    LinkedList<Integer> path;
    boolean found;

    private int search(int gScore, int bound) {
        int curNode = path.get(0);

        int fScore = gScore + searchHeurCost[curNode];

        if (fScore > bound) {
            System.out.println("Vozlisce " + curNode + " je zunaj trenutne meje (razdalja " + fScore + ")");
            return fScore;
        }

        System.out.println("Vozlisce " + curNode + " je znotraj trenutne meje");

        if (searchEndNodes.contains(curNode)) {
            found = true;
            return fScore;
        }

        int min = Integer.MAX_VALUE;

        for (int nextNode = 0; nextNode < searchGraph[curNode].length; nextNode++) {
            if (searchGraph[curNode][nextNode] > 0) {
                if (!(path.contains(nextNode))) {
                    path.add(0, nextNode);
                    int res = search(gScore + searchGraph[curNode][nextNode], bound);

                    if (found)
                        return res;

                    if (res < min)
                        min = res;

                    path.remove(0);
                }
            }
        }

        return min;
    }

    public void find(int[][] graph, int startNode, ArrayList<Integer> endNodes, int[] hCost) {
        searchGraph = graph;
        searchEndNodes = endNodes;
        searchHeurCost = hCost;

        path = new LinkedList<Integer>();
        path.add(startNode);
        found = false;

        steviloObdelanihVozlisc = 0;

        int bound = searchHeurCost[startNode];

        while (true) {
            System.out.println("Meja iskanja je nastavljena na " + bound);

            int res = search(0, bound);
            steviloObdelanihVozlisc++;

            if (found) {
                System.out.println("Resitev IDA* je v vozliscu: " + path.get(0));
                System.out.println("Najdena resitev je na razdalji: " + res);
                System.out.print("Najdena pot: ");
                for (int i = 0; i < path.size(); i++) {
                    if (i > 0)
                        System.out.print(" <-- ");
                    System.out.print(path.get(i));
                    najdenaPot.add(path.get(i));
                }
                break;
            }

            if (res == Integer.MAX_VALUE) {
                System.out.println("Iz zacetnega vozlisca ni mozno priti do nobenega ciljnega vozlisca!");
                break;
            }

            //postavi novo mejo iskanja
            bound = res;
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 3, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 8, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 2, 0},
                {0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        int startNode = 0;
        ArrayList<Integer> endNodes = new ArrayList<Integer>();
        endNodes.add(7);
        endNodes.add(11);
        endNodes.add(12);

        int[] hCost = {6, 5, 8, 4, 10, 2, 8, 0, 1, 12, 12, 0, 0};

        IDAStar idas = new IDAStar();
        idas.find(graph, startNode, endNodes, hCost);
    }

}
