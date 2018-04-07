package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Node;

import java.util.Comparator;
import java.util.List;

/**
 * The CostTable class is used to pre-compute the distance between each pair of nodes.
 */
public class CostTable {
    private final int n;
    private final double[] costs;

    /**
     * Creates a new table of costs for the given vehicle routing problem, by computing
     * the distance between every pair of nodes.
     *
     * @param instance an instance of a vehicle routing problem
     */
    public CostTable(CVRPBProblem instance) {
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

    /**
     * Retrieve the distance between two nodes
     * @param a a node of a vehicle routing problem
     * @param b a node of a vehicle routing problem
     * @return the cost associated with nodes a and b
     */
    public double get(Node a, Node b) {
        return get(a.getLabel(), b.getLabel());
    }

    /**
     * Retrieve the distance between the nodes with labeled as i and j
     * @param i the label of a node
     * @param j the label of a node
     * @return the cost associated with nodes labeled as i and j
     */
    public double get(int i, int j) {
        if (i == j) {
            return 0.0;
        }

        if (i > j) {
            // swap i and j
            int tmp = i;
            i = j;
            j = tmp;
        }

        // retrieve the index for the given pair of nodes
        int k = n * i - (i * (i + 1)) / 2 + (j - i - 1);
        return costs[k];
    }

    /**
     * Computes the euclidean distance between two nodes
     * @param a a node of a vehicle routing problem
     * @param b a node of a vehicle routing problem
     * @return the distance between a and b
     */
    private double euclideanDistance(Node a, Node b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
