import java.util.List;

public class Perceptron {
    private double[] weights;
    private double bias;
    private double learningRate;

    public Perceptron(int vectorSize, double learningRate) {
        this.learningRate = learningRate;
        this.weights = new double[vectorSize];
        this.bias = 0;
    }

    public void train(List<double[]> X, List<Integer> y, int cycleSize) {
        for (int k = 0; k < cycleSize; k++) {
            for (int i = 0; i < X.size(); i++) {
                int y_pred = predict(X.get(i));
                int error = y.get(i) - y_pred;
                if (error != 0) {
                    for (int j = 0; j < weights.length; j++) {
                        weights[j] += learningRate * error * X.get(i)[j];
                    }
                    bias += error * learningRate;
                }
            }
        }
    }

    public double score(double[] x) {
        if (x.length != weights.length) throw new IllegalArgumentException("Niepoprawny wymiar wektora");
        for (double value : x) if (!Double.isFinite(value)) throw new IllegalArgumentException("Wymagane skończone liczby");
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * x[i];
        }
        return sum;
    }

    public int predict(double[] x) {
        return score(x) >= 0 ? 1 : -1;
    }
}
