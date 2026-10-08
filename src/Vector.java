import java.io.*;

public class Vector {

    // Tworzenie wektora proporcji liter
    public static double[] generateProportionsVector (String text) {
        text = text.toLowerCase().replaceAll("[^a-z]", "");
        double[] counts = new double[26];
        for (char c : text.toCharArray()) counts[c - 'a']++;
        double total = text.length();
        for (int i = 0; i < 26; i++) counts[i] = total > 0 ? counts[i] / total : 0;
        return counts;
    }

    // Wczytywanie tekstu
    public static double[] generateProportionsVectorFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line).append(" ");
        reader.close();
        return generateProportionsVector(sb.toString());
    }
}
