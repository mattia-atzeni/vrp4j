## CVRPB Solver
Solving Vehicle Routing Problems with Backhauls

### Build
The project is built with maven.
```bash
$ mvn package
```

### Run
To run the project just type
```bash
$ ./cvrpb-solver
```
or
```bash
java -jar target/CVRPBSolver-0.1-jar-with-dependencies.jar
```
Instance files must be placed in:
./RPA_VRPB_Instances_and_Solutions/Instances/.

### Strategies
The project applies a local search and allows the user to choose between the following strategies:
* Best Exchange
* Best Relocate
* Combination of Best Exchange and Best Relocate

The project allows also to use a Multi-Start method in order to further improve the results.
