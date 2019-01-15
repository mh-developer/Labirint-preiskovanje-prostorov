import jdk.swing.interop.SwingInterOpUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Labirint {
    private Graf graf;
    public ArrayList<Node> nodes;
    public int startNode;
    public ArrayList<Integer> endNodes;
    public int[][] matrikaSosedov;
    public int[][] matrikaRazdalj;
    public ArrayList<Integer> najdenaPot = new ArrayList<>();
    public ArrayList<Node> najdenaPotZNodi = new ArrayList<>();
    public int cenaPoti;
    public int[] ceneVozlisc;

    public Labirint(String filename) {
        this.graf = new Graf(filename);
        this.nodes = graf.getNodes();
        this.startNode = graf.vrniZacetnoVozlisce();
        this.endNodes = graf.vrniKoncnaVozlisca();
        this.matrikaSosedov = graf.vrniMatrikoSosedov();
        this.matrikaRazdalj = graf.vrniMatrikoRazdalj();
    }

    public void izpisiZacetnoVozlisce() {
        System.out.println("ZAČETNO VOZLIŠČE: " + startNode);
    }

    public void izpisiKoncnaVozlisca() {
        String koncnaVozlisca = "KONČNA VOZLIŠČA: ";
        for (int koncnoVozlisce: endNodes) {
            koncnaVozlisca += koncnoVozlisce + " ";
        }
        System.out.println(koncnaVozlisca);
    }

    public void izpisiMatrikoSosedov() {
        String str = "MATRIKA SOSEDOV: \n";
        for(int i = 0; i < matrikaSosedov.length; i++) {
            for (int j = 0; j < matrikaSosedov[i].length; j++) {
                str += matrikaSosedov[i][j] + " ";
            }
            str += "\n";
        }
        System.out.print(str);
    }

    public void izpisiMatrikoRazdalj() {
        String str = "MATRIKA RAZDALJ: \n";
        for(int i = 0; i < matrikaRazdalj.length; i++) {
            for (int j = 0; j < matrikaRazdalj[i].length; j++) {
                str += matrikaRazdalj[i][j] + " ";
            }
            str += "\n";
        }
        System.out.print(str);
    }

    public void zapisiResitevLabirintaVDatoteko() {
        try {
            PrintWriter pw = new PrintWriter("src/rezultat.txt");
            ArrayList<String[]> zacetniNodi = graf.vrniZacetneNode();
            String zapis = "";
            for (int i = 0; i < zacetniNodi.size(); i++) {
                for (int j = 0; j < zacetniNodi.get(i).length; j++) {
                    if (nodeIsOnPath(zacetniNodi.get(i)[j], j, i))
                        zapis += "-5 ";
                    else
                        zapis += zacetniNodi.get(i)[j] + " ";
                }
                zapis = zapis.trim() + "\n";
            }
            pw.print(zapis);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean nodeIsOnPath(String s, int x, int y) {
        for (Node n: najdenaPotZNodi) {
            if (n.element == Integer.parseInt(s) && n.x == x && n.y == y)
                return true;
        }
        return false;
    }

    public void nastaviCeneVozliscem() {
        ceneVozlisc = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node n = graf.poisciNodePoOznaki(i);
            ceneVozlisc[i] = n.element;
        }
    }

    public void nastaviNajdeneNode() {
        for (int oznaka: najdenaPot) {
            najdenaPotZNodi.add(graf.poisciNodePoOznaki(oznaka));
        }
    }

    public void izpisiUrejeneParePotiInCeno() {
        String str = "KOORDINATE NAJDENE POTI: ";
        this.cenaPoti = 0;
        for (Node n: najdenaPotZNodi) {
            str += "("+ n.x + ", " + n.y + ")  ";
            this.cenaPoti += n.element;
        }
        str = str.trim().replace("  ", " <- ");
        System.out.println(str);
        System.out.println("CENA NAJDENE POTI: " + (this.cenaPoti + 5));
        System.out.println("ŠTEVILO PREMIKOV NA POTI: " + (najdenaPotZNodi.size() - 1));
    }

    public void poisciPot(String preiskovalniAlgoritem) {
        switch (preiskovalniAlgoritem) {
            case "DFS":
                izpisiMatrikoSosedov();
                DFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = DFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + DFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + DFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "BFS":
                izpisiMatrikoSosedov();
                BFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = BFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + BFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + BFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "A*":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                AStar.search(matrikaRazdalj, startNode, endNodes, ceneVozlisc);
                najdenaPot = AStar.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + AStar.steviloObdelanihVozlisc);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "IDA*":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                IDAStar idas = new IDAStar();
                idas.find(matrikaRazdalj, startNode, endNodes, ceneVozlisc);
                najdenaPot = IDAStar.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + IDAStar.steviloObdelanihVozlisc);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "IDDFS":
                izpisiMatrikoSosedov();
                IDDFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = IDDFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + IDDFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + IDDFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "ANNEAL":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(matrikaRazdalj);
                simulatedAnnealing.Anneal();
                System.out.println(simulatedAnnealing.currentOrder);
                System.out.println(simulatedAnnealing.nextOrder);
                break;
            case "DIJKSTRA":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                Dijkstra dijkstra = new Dijkstra();
                dijkstra.dijkstra(matrikaRazdalj, startNode);
                break;
            default:
                System.out.println("Napačna številka labirinta ali pa napačen algoritem!");
        }
    }

    public void poisciPotBrezIzpisa(String preiskovalniAlgoritem) {
        switch (preiskovalniAlgoritem) {
            case "DFS":
                DFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = DFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + DFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + DFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "BFS":
                BFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = BFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + BFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + BFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "A*":
                nastaviCeneVozliscem();
                AStar.search(matrikaRazdalj, startNode, endNodes, ceneVozlisc);
                najdenaPot = AStar.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + AStar.steviloObdelanihVozlisc);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "IDA*":
                nastaviCeneVozliscem();
                IDAStar idas = new IDAStar();
                idas.find(matrikaRazdalj, startNode, endNodes, ceneVozlisc);
                najdenaPot = IDAStar.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + IDAStar.steviloObdelanihVozlisc);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "IDDFS":
                IDDFS.search(matrikaSosedov, startNode, endNodes);
                najdenaPot = IDDFS.najdenaPot;
                nastaviNajdeneNode();
                System.out.println();
                izpisiUrejeneParePotiInCeno();
                System.out.println("ŠTEVILO OBDELANIH VOZLIŠČ: " + IDDFS.steviloObdelanihVozlisc);
                System.out.println("NAJVEČJA PREISKANA GLOBINA: " + IDDFS.najvecjaPreiskanaGlobina);
                System.out.println("ŠTEVILO VSEH VOZLIŠČ: " + nodes.size());
                break;
            case "ANNEAL":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(matrikaRazdalj);
                simulatedAnnealing.Anneal();
                System.out.println(simulatedAnnealing.currentOrder);
                System.out.println(simulatedAnnealing.nextOrder);
                break;
            case "DIJKSTRA":
                izpisiMatrikoRazdalj();
                nastaviCeneVozliscem();
                Dijkstra dijkstra = new Dijkstra();
                dijkstra.dijkstra(matrikaRazdalj, startNode);
                break;
            default:
                System.out.println("Napačna številka labirinta ali pa napačen algoritem!");
        }
    }

    public static void poisciPotZaLabirint() {
        Scanner sc = new Scanner(System.in);
        System.out.print("LABIRINT: ");
        String labyirinthNumber = sc.next();
        String filename = "labyrinth_" + labyirinthNumber + ".txt";
        System.out.print("PREISKOVALNI ALGORITEM: ");
        String preiskovalniAlgoritem = sc.next();
        System.out.print("ZAPIŠEM REŠITEV V DATOTEKO? (DA/NE): ");
        String resitev = sc.next();
        System.out.print("POLN IZPIS? (DA/NE): ");
        String polnIzpis = sc.next();
        Labirint labirint = new Labirint(filename);
        labirint.izpisiZacetnoVozlisce();
        labirint.izpisiKoncnaVozlisca();
        if (polnIzpis.equals("DA"))
            labirint.poisciPot(preiskovalniAlgoritem);
        else
            labirint.poisciPotBrezIzpisa(preiskovalniAlgoritem);
        if (resitev.equals("DA"))
            labirint.zapisiResitevLabirintaVDatoteko();
    }

    public static void main(String[] args) {
        Labirint.poisciPotZaLabirint();
    }
}
