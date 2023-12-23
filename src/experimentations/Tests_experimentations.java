package experimentations;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import static experimentations.Fonctions_experimentations.findROBDDSizeDistribution;
import static experimentations.Fonctions_experimentations.randomizedROBDDSizeDistribution;

public class Tests_experimentations {

    public static void test_plt(int nVariables, int nRandomSamples) {
        BigInteger nSamples_BigInt = BigInteger.valueOf(nRandomSamples);
        ArrayList<BigInteger> distro;
        long startTime;
        long endTime;
        double execTime;
        if (nVariables < 5) {
            startTime = System.nanoTime();
            distro = findROBDDSizeDistribution(nVariables);
            endTime = System.nanoTime();
        } else {
            startTime = System.nanoTime();
            distro = randomizedROBDDSizeDistribution(nVariables, nSamples_BigInt);
            endTime = System.nanoTime();
        }
        execTime = (double) (endTime - startTime) / 1000000000.;
        ArrayList<Integer> nv = new ArrayList<>();
        for (int i = 0; i < distro.size(); i++) {
            nv.add(i + 1);
        }
        Plot plt = Plot.create();
        plt.plot().add(nv, distro).label("Nombre d'occurences");
        plt.title("Nombre de noeuds du ROBDD pour " + nVariables + " variables");
        plt.ylabel("Nombre d'occurences");
        plt.savefig("plot_" + nVariables + "vars.png").dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Temps d'exécution pour n = " + nVariables + ", avec " + nRandomSamples + " échantillons : " + execTime + " s.");
    }

    public static void main(String[] args) {
        test_plt(10, 5000);
    }
}
