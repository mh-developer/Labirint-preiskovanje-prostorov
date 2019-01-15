import java.util.ArrayList;

public class Graf {
    private ArrayList<Node> nodes;
    private String filename;

    public Graf(String filename) {
        ArrayList<String[]> arrayList = Node.readNodes("src/" + filename);
        ArrayList<Node> nodes = Node.ustvariNode(arrayList);
        Node.poisciSosede(nodes);
        this.nodes = Node.vrniPrehodneNode(nodes);
        this.filename = "src/" + filename;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node poisciNodePoOznaki(int oznaka) {
        return Node.findNodeByOznaka(nodes, oznaka);
    }

    public int[][] vrniMatrikoSosedov() {
        return Node.ustvariMatrikoSosedov(nodes);
    }

    public int[][] vrniMatrikoRazdalj() {
        return Node.ustvariMatrikoRazdalj1(nodes);
    }

    public int vrniZacetnoVozlisce() {
        for (Node node : nodes) {
            if (node.element == -2)
                return node.oznaka;
        }
        return -1;
    }

    public ArrayList<Integer> vrniKoncnaVozlisca() {
        ArrayList<Integer> koncaVozlisca = new ArrayList<>();
        for (Node node : nodes) {
            if (node.element == -3)
                koncaVozlisca.add(node.oznaka);
        }
        return koncaVozlisca;
    }

    public ArrayList<String[]> vrniZacetneNode() {
        return Node.readNodes(this.filename);
    }
}
