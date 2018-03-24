package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Node;

import java.util.Comparator;
import java.util.List;

public class CostTable {
    private final int n;
    private final double[] costs;

    public CostTable(CVRPBInstance instance) {
        List<Node> nodes = instance.getNodes();
        nodes.sort(Comparator.comparingInt(Node::getLabel));

        n = nodes.size();
        int size = n * (n - 1) / 2;
        costs = new double[size];

        int k = 0;
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                costs[k] = euclideanDistance(nodes.get(i), nodes.get(j));
                k++;
            }
        }
    }

    public double get(Node a, Node b) {
        return get(a.getLabel(), b.getLabel());
    }

    public double get(int i, int j) {
        if (i == j) {
            return 0.0;
        }

        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
        }

        int k = n * i - (i * (i + 1)) / 2 + (j - i - 1);
        return costs[k];
    }

    private double euclideanDistance(Node a, Node b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
