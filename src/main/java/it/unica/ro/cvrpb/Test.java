package it.unica.ro.cvrpb;


import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.readers.CVRPBInstanceReader;

import java.io.IOException;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws IOException {
        CVRPBInstance i = new CVRPBInstanceReader(Settings.instancesPath + "A1.txt").read();
        System.out.println(i);
        System.out.println("\n");
        Set<Route> s = new CVRPBSolver().constructSolution(i);
        s.forEach(route ->
                System.out.println(route + "\t" + route.getDeliveryLoad() + "\t" + route.getPickupLoad())
        );
        boolean legal = new CVRPBSolutionChecker(i).check(s);
        System.out.println(legal);
    }
}
