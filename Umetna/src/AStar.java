import java.util.ArrayList;
import java.util.LinkedList;

public class AStar {

    public static ArrayList<Integer> najdenaPot = new ArrayList<>();
    public static int steviloObdelanihVozlisc = 0;

    public static void search(int[][] graph, int startNode, ArrayList<Integer> endNodes, int[] hCost) {
        LinkedList<Integer> open = new LinkedList<Integer>();
        boolean[] closed = new boolean[graph.length];
        int[] from = new int[graph.length];

        int[] gScore = new int[graph.length];
        int[] fScore = new int[graph.length];
        steviloObdelanihVozlisc = 0;

        for (int i = 0; i < gScore.length; i++) {
            gScore[i] = Integer.MAX_VALUE;
            fScore[i] = Integer.MAX_VALUE;
        }

        gScore[startNode] = 0;
        fScore[startNode] = hCost[startNode];
        from[startNode] = -1;

        open.add(startNode);
        System.out.println("Odpiram vozlisce " + startNode);

        while (!open.isEmpty()) {
            int minVal = Integer.MAX_VALUE;
            int minPos = 0;
            int curNode = 0;

            for (int i = 0; i < open.size(); i++) {
                int node = open.get(i);
                if (fScore[node] < minVal) {
                    minVal = fScore[node];
                    minPos = i;
                    curNode = node;
                }
            }

            open.remove(minPos);
            closed[curNode] = true;
            System.out.println("Zapiram vozlisce " + curNode);
            steviloObdelanihVozlisc++;

            if (endNodes.contains(curNode)) {
                System.out.println("Resitev A* v vozliscu " + curNode);
                System.out.print("Pot: " + curNode);
                najdenaPot.add(curNode);

                while (true) {
                    curNode = from[curNode];
                    if (curNode != -1) {
                        System.out.print(" <-- " + curNode);
                        najdenaPot.add(curNode);
                    }
                    else {
                        break;
                    }
                }

                return;
            }

            for (int nextNode = 0; nextNode < graph[curNode].length; nextNode++) {
                if (graph[curNode][nextNode] > 0 && !closed[nextNode]) {
                    if (!open.contains(nextNode))
                        System.out.println("Odpiram vozlisce " + nextNode);

                    open.add(nextNode);
                    int dist = gScore[curNode] + graph[curNode][nextNode];

                    if (dist < gScore[nextNode]) {
                        from[nextNode] = curNode;
                        gScore[nextNode] = dist;
                        fScore[nextNode] = gScore[nextNode] + hCost[nextNode];
                        System.out.println("Posodabljam vozlisce " + nextNode);
                    }
                }
            }
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

        AStar.search(graph, startNode, endNodes, hCost);

    }

}