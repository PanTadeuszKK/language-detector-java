import java.io.*;
import java.util.*;

public class Trainer {
    private Map<String, Perceptron> perceptrons = new TreeMap<>();
    private List<String> languageList = new ArrayList<>();

    public void train(File file, double learningRate, int epochs) throws IOException {
        File[] languageDirectory = file.listFiles(File::isDirectory);
        if (languageDirectory == null || languageDirectory.length == 0)
            throw new IOException("Brak katalogów języków: " + file);
        Arrays.sort(languageDirectory, Comparator.comparing(File::getName));
        perceptrons.clear();
        languageList.clear();
        int vectorSize = 26;

        // Tworzenie perceptronow
        for (File langFolder : languageDirectory) {
            perceptrons.put(langFolder.getName(), new Perceptron(vectorSize, learningRate));
            languageList.add(langFolder.getName());
        }

        // Trening perceptronów
        for (int i = 0; i < epochs; i++) {
            for (File langFolder : languageDirectory) {
                String actualLanguage = langFolder.getName();
                File[] files = langFolder.listFiles((d, name) -> name.endsWith(".txt"));
                if (files == null || files.length == 0) throw new IOException("Brak tekstów: " + langFolder);
                Arrays.sort(files, Comparator.comparing(File::getName));
                for (File f : files) {
                    double[] input = Vector.generateProportionsVectorFromFile(f);
                    for (String currentLanguage : perceptrons.keySet()) {
                        int desired = currentLanguage.equals(actualLanguage) ? 1 : -1;

                        List<double[]> inputList = new ArrayList<>();
                        inputList.add(input);

                        List<Integer> wantedList = new ArrayList<>();
                        wantedList.add(desired);

                        perceptrons.get(currentLanguage).train(inputList, wantedList, 1);
                    }
                }
            }
        }
    }

    //Klasyfikacja tekstu
    public String classify(String text) {
        if (perceptrons.isEmpty()) return "Najpierw wytrenuj model";
        double[] input = Vector.generateProportionsVector(text);
        if (Arrays.stream(input).sum() == 0) return "Brak liter do klasyfikacji";
        return perceptrons.entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getValue().score(input)))
                .map(Map.Entry::getKey).orElse("Unknown");
    }

    //Getter listy jezykow ktore mozemy klasyfikować
    public String getLanguageList() {
        return String.join(" | ", languageList);
    }
}
