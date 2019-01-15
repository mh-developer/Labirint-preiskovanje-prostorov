import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    public ArrayList<Integer> currentOrder = new ArrayList<>();
    public ArrayList<Integer> nextOrder = new ArrayList<>();
    private int[][] distances;
    private Random random = new Random();
    public double shortestDistance = 0;

    public SimulatedAnnealing(int[][] distances) {
        this.distances = distances;
        for (int i = 0; i < distances.length; i++) {
            currentOrder.add(i);
        }
    }

    private double GetTotalDistance(ArrayList<Integer> order) {
        double distance = 0;

        for (int i = 0; i < order.size() - 1; i++) {
            distance += distances[order.get(i)][order.get(i + 1)];
        }

        if (order.size() > 0) {
            distance += distances[order.get(order.size() - 1)][0];
        }

        return distance;
    }

    private ArrayList<Integer> GetNextArrangement(ArrayList<Integer> order) {
        ArrayList<Integer> newOrder = new ArrayList<>();

        for (int i = 0; i < order.size(); i++)
            newOrder.add(order.get(i));

        int firstRandomCityIndex = random.nextInt(newOrder.size() - 1) + 1;
        int secondRandomCityIndex = random.nextInt(newOrder.size() - 1) + 1;

        int dummy = newOrder.get(firstRandomCityIndex);
        newOrder.set(firstRandomCityIndex, newOrder.get(secondRandomCityIndex));
        newOrder.set(secondRandomCityIndex, dummy);

        return newOrder;
    }

    public void Anneal() {
        int iteration = -1;

        double temperature = 10000.0;
        double deltaDistance = 0;
        double coolingRate = 0.9999;
        double absoluteTemperature = 0.00001;

        double distance = GetTotalDistance(currentOrder);

        while (temperature > absoluteTemperature) {
            nextOrder = GetNextArrangement(currentOrder);

            deltaDistance = GetTotalDistance(nextOrder) - distance;

            //if the new order has a smaller distance
            //or if the new order has a larger distance but satisfies Boltzman condition then accept the arrangement
            if ((deltaDistance < 0) || (distance > 0 && Math.exp(-deltaDistance / temperature) > random.nextDouble())) {
                for (int i = 0; i < nextOrder.size(); i++)
                    currentOrder.set(i, nextOrder.get(i));

                distance = deltaDistance + distance;
            }

            //cool down the temperature
            temperature *= coolingRate;

            iteration++;
        }

        shortestDistance = distance;
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
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(graph);
        simulatedAnnealing.Anneal();
        System.out.println(simulatedAnnealing.shortestDistance);
        System.out.println(simulatedAnnealing.nextOrder.toString());
        System.out.println(simulatedAnnealing.currentOrder.toString());
    }
}