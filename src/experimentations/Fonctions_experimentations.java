package experimentations;

import arbre_de_decision_et_compression.Digraph;
import arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression;
import echauffement.Fonctions_echauffement;
import robdd.Fonctions_ROBDD;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Fonctions_experimentations {
    public static int findROBDDSize(int nVariables, BigInteger boolFunction) {
        boolean[] truthTable = Fonctions_echauffement.table(boolFunction, (int) Math.pow(2, nVariables));
        Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDD = Fonctions_arbre_de_decision_et_compression.cons_arbre(truthTable);
        Fonctions_ROBDD.compression_bdd(BDD);
        Digraph dotBDD = new Digraph("ROBDD(" + nVariables + "," + boolFunction + ")");
        dotBDD.addNode("" + BDD.getRoot().toString().substring(81), BDD.getRoot().getContent());
        Fonctions_arbre_de_decision_et_compression.dot(BDD.getRoot(), Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(truthTable.length), dotBDD);
        return dotBDD.getNodeCount();
    }

    public static ArrayList<BigInteger> findROBDDSizeDistribution(int nVariables) {
        ArrayList<BigInteger> results = new ArrayList<>(Collections.nCopies((int) Math.pow(2, nVariables) + 1, BigInteger.ZERO));
        BigInteger totalBoolFunctions = BigInteger.TWO.pow(BigInteger.TWO.pow(nVariables).intValueExact());
        int currBDDSize;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(totalBoolFunctions) < 0; i = i.add(BigInteger.ONE)) {
            currBDDSize = findROBDDSize(nVariables, i);
            results.set(currBDDSize - 1, results.get(currBDDSize - 1).add(BigInteger.ONE));
        }
        return shorten(results);
    }

    public static ArrayList<BigInteger> randomizedROBDDSizeDistribution(int nVariables, BigInteger nRandomSamples) {
        ArrayList<BigInteger> randResults = new ArrayList<>(Collections.nCopies((int) Math.pow(2, nVariables) + 1, BigInteger.ZERO));
        BigInteger totalBoolFunctions = BigInteger.TWO.pow(BigInteger.TWO.pow(nVariables).intValueExact());
        BigInteger randomSample;
        int currBDDSize;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(nRandomSamples) < 0; i = i.add(BigInteger.ONE)) {
            randomSample = new BigInteger(BigInteger.TWO.pow(nVariables).intValueExact(), new Random());
            currBDDSize = findROBDDSize(nVariables, randomSample);
            randResults.set(currBDDSize - 1, randResults.get(currBDDSize - 1).add(BigInteger.ONE));
        }
        randResults.replaceAll(bigint -> (bigint.multiply(totalBoolFunctions).divide(nRandomSamples)));
        return shorten(randResults);
    }

    private static ArrayList<BigInteger> shorten(ArrayList<BigInteger> list) {
        int rm = list.size();
        for (int i = 0; i < rm; i++) {
            if (list.get(rm - i - 1).equals(BigInteger.ZERO)) {
                list.remove(rm - i - 1);
            } else {
                return list;
            }
        }
        return list;
    }

    /*public static ArrayList<Integer> findAllROBDDSizes(int nVariables) { //Unused, deprecated
        int totalBoolFunctions = (int) Math.pow(2, (int) Math.pow(2, nVariables));
        ArrayList<Integer> results = new ArrayList<>(totalBoolFunctions);
        int currBDDSize;
        for (int i = 0; i < totalBoolFunctions; i++) {
            results.add(findROBDDSize(nVariables, i));
        }
        return results;
    }*/

}
