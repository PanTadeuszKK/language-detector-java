import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI {
    private Trainer trainer = new Trainer();

    public void startProgram() {
        JFrame frame = new JFrame("Klasyfikacja jezykow ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea inputArea = new JTextArea();
        JButton classifyButton = new JButton("Klasyfikuj");
        JLabel resultLabel = new JLabel("Wynik : ");

        JButton trainButton = new JButton("Trenuj");

        trainButton.addActionListener(e -> {
            try {
                File folder = new File(System.getProperty("languages.dir", "../../../../NAI/JEZYKI"));
                trainer.train(folder, 0.1, 100);
                frame.setTitle("Klasyfikacja jezykow : " + trainer.getLanguageList());
                JOptionPane.showMessageDialog(frame, "Ukończono trening");
            } catch (Exception e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Bład podczas treningu : " + e2.getMessage());
            }
        });

        classifyButton.addActionListener(e -> {
            String text = inputArea.getText();
            String lang = trainer.classify(text);
            resultLabel.setText("Wynik : " + lang);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(classifyButton);
        bottomPanel.add(trainButton);
        bottomPanel.add(resultLabel);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
