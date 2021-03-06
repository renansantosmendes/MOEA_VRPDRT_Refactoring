package VRPDRT;

import InstanceReader.DataOutput;
import InstanceReader.Instance;
import InstanceReader.ScriptGenerator;
import ProblemRepresentation.ProblemSolution;
import ProblemRepresentation.RankedList;
import VRPDRT.MOEAVRPDRT;
import VRPDRT.VRPDRT;
import java.util.ArrayList;
import java.util.List;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.PermutationMOEA;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author renansantos
 */
public class Main {

    private static MOEAVRPDRT problem;
    private static VRPDRT subProblem;
    private static String path;
    private static RankedList rankedList;
    private static Instance instance = new Instance();

    public Main() {

    }

    private static void initializeData() {
        RankedList rankedList = new RankedList(instance.getNumberOfNodes());
        rankedList.setAlphaD(0.20)
                .setAlphaP(0.15)
                .setAlphaT(0.10)
                .setAlphaV(0.55);

        instance.setNumberOfRequests(50)
                .setRequestTimeWindows(10)
                .setInstanceSize("s")
                .setNumberOfNodes(12)
                .setNumberOfVehicles(250)
                .setVehicleCapacity(4);

        subProblem = new VRPDRT(instance, path, rankedList);

        problem = new MOEAVRPDRT(path)
                .setNumberOfObjectives(9)
                .setNumberOfVariables(1)
                .setNumberOfConstraints(0);
    }

    private static List<Integer> copyArrayToListInteger(int[] array) {
        List<Integer> list = new ArrayList<>();
        int size = array.length;

        for (int i = 0; i < size; i++) {
            list.add(array[i]);
        }
        return list;
    }

    private static List<Double> copyArrayToListDouble(double[] array) {
        List<Double> list = new ArrayList<>();
        int size = array.length;

        for (int i = 0; i < size; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public static ProblemSolution convertSolution(Solution solution) {
        initializeData();
        ProblemSolution ps = subProblem
                .rebuildSolution(copyArrayToListInteger(EncodingUtils.getPermutation(solution.getVariable(0))),
                        subProblem.getData().getRequests());
        return ps;
    }

    public static void main(String[] args) {
        path = "/home/renansantos/Área de Trabalho/Excel Instances/";
//        path = "/home/rmendes/VRPDRT/"; 
        int reducedDimensionality = 6;

        String composedName = "OffCLMOEAD" + "_R" + reducedDimensionality;
        System.out.println("Algorithm = " + composedName);
        new ScriptGenerator(composedName, composedName)
                .generate("2d", "large");

        initializeData();

        Instance instance = new Instance();
        instance.setNumberOfRequests(50)
                .setRequestTimeWindows(10)
                .setInstanceSize("s")
                .setNumberOfNodes(12)
                .setNumberOfVehicles(250)
                .setVehicleCapacity(4);

        List<NondominatedPopulation> result = new Executor()
                .withProblemClass(MOEAVRPDRT.class, path)
                .withAlgorithm("OffCLMOEAD")
                .withMaxEvaluations(2000)
                .withProperty("operator", "2x+swap")
                .withProperty("populationSize", 20)
                .withProperty("swap.rate", 0.1)
                .withProperty("2x.rate", 0.7)
                .withProperty("instance", instance.getFullInstanceName())
                .withProperty("clusters", reducedDimensionality)
                .withProperty("filePath", path)
                .runSeeds(30);

        DataOutput dataOutput = new DataOutput(composedName, instance.getInstanceName(), path);
        NondominatedPopulation combinedPareto = new NondominatedPopulation();
        List<ProblemSolution> solutionPopulation = new ArrayList<>();

        for (NondominatedPopulation population : result) {
            for (Solution solution : population) {
                combinedPareto.add(solution);
            }
        }

        System.out.println("combined pareto");
        for (Solution solution : combinedPareto) {
            System.out.println(copyArrayToListDouble(solution.getObjectives()));
            solutionPopulation.add(convertSolution(solution));
        }
        for (Solution solution : combinedPareto) {
            int[] array = EncodingUtils.getPermutation(solution.getVariable(0));
            List<Integer> solutionRepresentation = copyArrayToListInteger(array);
//            ProblemSolution ps = problem.getProblem().rebuildSolution(solutionRepresentation, problem.getProblem().getRequestListCopy());
//            System.out.println("solution = " + ps);
        }
        dataOutput.savePopulation(solutionPopulation);
        dataOutput.saveListOfNondominatedPopulation(result);
    }
}
