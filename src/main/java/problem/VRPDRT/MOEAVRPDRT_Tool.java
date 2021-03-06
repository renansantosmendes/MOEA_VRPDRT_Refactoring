/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.VRPDRT;

import org.moeaframework.core.variable.PermutationMOEA;
import InstanceReader.*;
import ProblemRepresentation.*;
import VRPDRT.VRPDRT;
import java.util.*;
import org.moeaframework.core.*;
import org.moeaframework.core.variable.*;

/**
 *
 * @author renansantos
 */
public class MOEAVRPDRT_Tool implements Problem {

    private Instance instance = new Instance();
    private String path = "/home/renansantos/Área de Trabalho/Excel Instances/";
    private VRPDRT problem;
    private RankedList rankedList;
    private int numberOfVariables = 1;
    private int numberOfObjectives = 9;
    private int numberOfConstraints = 0;

    public MOEAVRPDRT_Tool() {
        instance.setNumberOfRequests(50)
                .setRequestTimeWindows(10)
                .setInstanceSize("s")
                .setNumberOfNodes(12)
                .setNumberOfVehicles(250)
                .setVehicleCapacity(4);

        RankedList rankedList = new RankedList(instance.getNumberOfNodes());
        rankedList.setAlphaD(0.20)
                .setAlphaP(0.15)
                .setAlphaT(0.10)
                .setAlphaV(0.55);

        problem = new VRPDRT(instance, path, rankedList);
    }

    public MOEAVRPDRT_Tool setNumberOfObjectives(int numberOfObjectives){
        this.numberOfObjectives = numberOfObjectives;
        return this;
    }
    
    public MOEAVRPDRT_Tool setNumberOfVariables(int numberOfVariables){
        this.numberOfVariables = numberOfVariables;
        return this;
    }
    
    public MOEAVRPDRT_Tool setNumberOfConstraints(int numberOfConstraints){
        this.numberOfConstraints = numberOfConstraints;
        return this;
    }
    
    @Override
    public String getName() {
        return "MOEAVRPDRT";
    }

    @Override
    public int getNumberOfVariables() {
        return this.numberOfVariables;
    }

    @Override
    public int getNumberOfObjectives() {
        return this.numberOfObjectives;
    }

    @Override
    public int getNumberOfConstraints() {
        return this.numberOfConstraints;
    }

    @Override
    public void evaluate(Solution solution) {
        int[] array = EncodingUtils.getPermutation(solution.getVariable(0));
        List<Integer> solutionRepresentation = copyArrayToListInteger(array);
//        System.out.println(solutionRepresentation);

        problem = new VRPDRT(instance, path);
//        System.out.println("tamanho requests = " + problem.getData().getRequests().size());
       
        ProblemSolution ps = problem.rebuildSolution(solutionRepresentation, problem.getData().getRequests());
        System.out.println("number of Objectives = " + ps.getObjectives());
        solution.setObjectives(copyListToArrayDouble(ps.getObjectives()));
    }

    @Override
    public Solution newSolution() {
        RankedList rankedList = new RankedList(instance.getNumberOfNodes());
        rankedList.setAlphaD(0.20)
                .setAlphaP(0.15)
                .setAlphaT(0.10)
                .setAlphaV(0.55);

        problem = new VRPDRT(instance, path, rankedList);

        ProblemSolution ps = problem.buildRandomSolution();
        //System.out.println(ps);
        int arraySize = ps.getLinkedRouteList().size();
        int[] array = copyListToArrayInteger(ps.getLinkedRouteList());
        PermutationMOEA permutation = new PermutationMOEA(array);

        Solution solution = new Solution(1, this.numberOfObjectives, this.numberOfConstraints);
        solution.setVariable(0, permutation);
        return solution;
    }

    private int[] copyListToArrayInteger(List<Integer> list) {
        int size = list.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    private double[] copyListToArrayDouble(List<Double> list) {
        int size = list.size();
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private List<Integer> copyArrayToListInteger(int[] array){
        List<Integer> list = new ArrayList<>();
        int size = array.length;
        
        for (int i = 0; i < size; i++) {
            list.add(array[i]);
        }
        return list;
    }
    @Override
    public void close() {

    }

}

