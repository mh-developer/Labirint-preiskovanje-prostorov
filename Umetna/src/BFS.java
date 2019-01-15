import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    public static ArrayList<Integer> najdenaPot = new ArrayList<>();
    public static int steviloObdelanihVozlisc = 0;
    public static int najvecjaPreiskanaGlobina = 0;

    public static void search(int[][] graph, int startNode, ArrayList<Integer> endNodes) {
        boolean[] marked = new boolean[graph.length];
        int[] from = new int[graph.length];
        steviloObdelanihVozlisc = 0;
        najvecjaPreiskanaGlobina = 0;
        int globina = 0;

        Queue<Integer> queue = new LinkedList<Integer>();

        marked[startNode] = true;
        from[startNode] = -1;

        queue.add(startNode);
        System.out.println("Dajem v vrsto vozlisce " + startNode);
        steviloObdelanihVozlisc++;

        while (!queue.isEmpty()) {
            int curNode = queue.remove();
            System.out.println("Odstranjujem iz vrste vozlisce " + curNode);

            if (endNodes.contains(curNode)) {
                System.out.println("Resitev BFS v vozliscu " + curNode);
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
                if (graph[curNode][nextNode] == 1 && !marked[nextNode]) {
                    marked[nextNode] = true;
                    from[nextNode] = curNode;
                    queue.add(nextNode);

                    System.out.println("Dajem v vrsto vozlisce " + nextNode);
                    steviloObdelanihVozlisc++;
                    globina++;
                }
            }

            if (globina > najvecjaPreiskanaGlobina)
                najvecjaPreiskanaGlobina = globina;
            globina = 0;
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};

        int startNode = 0;
        ArrayList<Integer> endNodes = new ArrayList<Integer>();
        endNodes.add(6);
        endNodes.add(7);

        BFS.search(graph, startNode, endNodes);

    }

}
