import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Node {
    public Node LEVI = null;
    public Node DESNI = null;
    public Node ZGORNJI = null;
    public Node SPODNJI = null;
    public int x;
    public int y;
    public int element;
    public int oznaka;

    public Node(int element, int oznaka, int x, int y) {
        this.element = element;
        this.oznaka = oznaka;
        this.x = x;
        this.y = y;
    }

    public static ArrayList<String[]> readNodes(String filename) {
        ArrayList<String[]> seznamVozlisc = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            String data = "";
            while (sc.hasNextLine()) {
                data += sc.nextLine() + "\n";
            }
            String[] vrstice = data.split("\n");
            for (String vrstica: vrstice) {
                seznamVozlisc.add(vrstica.split(","));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Napačna številka labirinta ali pa napačen algoritem!");
            System.exit(1);
        }
        return seznamVozlisc;
    }

    public static ArrayList<Node> ustvariNode(ArrayList<String[]> seznamVozlisc) {
        ArrayList<Node> nodes = new ArrayList<>();
        int oznaka = 0;
        for (int i = 0; i < seznamVozlisc.size(); i++) {
            for (int j = 0; j < seznamVozlisc.get(i).length; j++) {
                nodes.add(new Node(Integer.parseInt(seznamVozlisc.get(i)[j]), oznaka++, j, i));
            }
        }
        return nodes;
    }

    public static Node findNodeByPosition(ArrayList<Node> nodes, int x, int y) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }

    public static Node findNodeByOznaka(ArrayList<Node> nodes, int oznaka) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.oznaka == oznaka) {
                return node;
            }
        }
        return null;
    }

    public static ArrayList<Node> poisciSosede(ArrayList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (!(node.element == -1 && node.y == 0)) {
                node.ZGORNJI = findNodeByPosition(nodes, node.x, node.y - 1);
            }
            if (!(node.element == -1 && node.y == nodes.size() - 1)) {
                node.SPODNJI = findNodeByPosition(nodes, node.x, node.y + 1);
            }
            if (!(node.element == -1 && node.x == 0)) {
                node.LEVI = findNodeByPosition(nodes, node.x - 1, node.y);
            }
            if (!(node.element == -1 && node.x == nodes.size() - 1)) {
                node.DESNI = findNodeByPosition(nodes, node.x + 1, node.y);
            }
        }
        return nodes;
    }

    public static ArrayList<Node> vrniPrehodneNode(ArrayList<Node> nodes) {
        ArrayList<Node> prehodniNodi = new ArrayList<>();
        int oznaka = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.element != -1) {
                node.oznaka = oznaka++;
                prehodniNodi.add(node);
            }
        }
        return prehodniNodi;
    }

    public static int[][] ustvariMatrikoSosedov(ArrayList<Node> nodes) {
        int[][] matrikaSosedov = new int[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Node levi = node.LEVI;
            if (levi != null && levi.element != -1)
                matrikaSosedov[i][levi.oznaka] = 1;
            Node desni = node.DESNI;
            if (desni != null && desni.element != -1)
                matrikaSosedov[i][desni.oznaka] = 1;
            Node zgornji = node.ZGORNJI;
            if (zgornji != null && zgornji.element != -1)
                matrikaSosedov[i][zgornji.oznaka] = 1;
            Node spodnji = node.SPODNJI;
            if (spodnji != null && spodnji.element != -1)
                matrikaSosedov[i][spodnji.oznaka] = 1;
        }
        return matrikaSosedov;
    }

    public static int[][] ustvariMatrikoRazdalj(ArrayList<Node> nodes) {
        int[][] matrikaRazdalj = new int[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Node levi = node.LEVI;
            if (levi != null && levi.element != -1) {
                if (levi.element == -2 || levi.element == -3)
                    matrikaRazdalj[i][levi.oznaka] = 0;
                else
                    matrikaRazdalj[i][levi.oznaka] = levi.element;
            }
            Node desni = node.DESNI;
            if (desni != null && desni.element != -1) {
                if (desni.element == -2 || desni.element == -3)
                    matrikaRazdalj[i][desni.oznaka] = 0;
                else
                    matrikaRazdalj[i][desni.oznaka] = desni.element;
            }
            Node zgornji = node.ZGORNJI;
            if (zgornji != null && zgornji.element != -1) {
                if (zgornji.element == -2 || zgornji.element == -3)
                    matrikaRazdalj[i][zgornji.oznaka] = 0;
                else
                    matrikaRazdalj[i][zgornji.oznaka] = zgornji.element;
            }
            Node spodnji = node.SPODNJI;
            if (spodnji != null && spodnji.element != -1) {
                if (spodnji.element == -2 || spodnji.element == -3)
                    matrikaRazdalj[i][spodnji.oznaka] = 0;
                else
                    matrikaRazdalj[i][spodnji.oznaka] = spodnji.element;
            }
        }
        return matrikaRazdalj;
    }

    public static int[][] ustvariMatrikoRazdalj1(ArrayList<Node> nodes) {
        int[][] matrikaRazdalj = new int[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Node levi = node.LEVI;
            if (levi != null && levi.element != -1) {
                if (levi.element == -2 || levi.element == -3)
                    matrikaRazdalj[i][levi.oznaka] = 1;
                else
                    matrikaRazdalj[i][levi.oznaka] = levi.element;
            }
            Node desni = node.DESNI;
            if (desni != null && desni.element != -1) {
                if (desni.element == -2 || desni.element == -3)
                    matrikaRazdalj[i][desni.oznaka] = 1;
                else
                    matrikaRazdalj[i][desni.oznaka] = desni.element;
            }
            Node zgornji = node.ZGORNJI;
            if (zgornji != null && zgornji.element != -1) {
                if (zgornji.element == -2 || zgornji.element == -3)
                    matrikaRazdalj[i][zgornji.oznaka] = 1;
                else
                    matrikaRazdalj[i][zgornji.oznaka] = zgornji.element;
            }
            Node spodnji = node.SPODNJI;
            if (spodnji != null && spodnji.element != -1) {
                if (spodnji.element == -2 || spodnji.element == -3)
                    matrikaRazdalj[i][spodnji.oznaka] = 1;
                else
                    matrikaRazdalj[i][spodnji.oznaka] = spodnji.element;
            }
        }
        return matrikaRazdalj;
    }
}
