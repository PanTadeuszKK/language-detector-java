# Language Detector — Java

A desktop application that identifies a text's language using letter frequencies and a set of perceptrons implemented in Java. It combines a small machine-learning implementation with a Swing interface.

An individual university project at the Polish-Japanese Academy of Information Technology (PJATK), with a Data Science focus.

## How it works

1. Convert a text to lowercase and retain letters a–z.
2. Build a 26-dimensional vector of relative letter frequencies.
3. Train one binary perceptron per language against all other languages.
4. Choose the language with the highest raw linear score.

The GUI uses a learning rate of 0.1 and 100 training epochs. It supports retraining, and detects empty input and classification before training. Scores are not probabilities.

## Requirements

- JDK 21, with java and javac available in your terminal.
- A graphical desktop environment for Swing.
- A local training corpus prepared as described below.
- No external libraries are required.

## Prepare training data

The standalone repository does not include a training corpus. Create a directory containing one subdirectory per language and .txt training documents in each, for example:

```text
data/
  train/
    ANGIELSKI/
      document1.txt
      document2.txt
    POLSKI/
      document1.txt
      document2.txt
    WLOSKI/
      document1.txt
      document2.txt
```

Folder names become the labels displayed by the application. Every language directory must contain at least one .txt file. Use several substantial documents per language and consistent UTF-8 encoding with Java 21's default file decoding.

Local corpora under data/ are ignored by Git. Only distribute source texts if their terms permit it, and record their sources. No accuracy result is claimed for an unspecified corpus.

## Compile and run

Run these commands from the repository root after preparing data/train:

```text
mkdir out
javac -encoding UTF-8 -d out src/Main.java src/GUI.java src/Trainer.java src/Perceptron.java src/Vector.java
java -Dlanguages.dir=data/train -cp out Main
```

To use a corpus elsewhere, supply its path explicitly. Paths containing spaces need quotes, for example:

```text
java "-Dlanguages.dir=C:/datasets/language texts" -cp out Main
```

The legacy default path points to the original coursework directory layout. Always supply languages.dir when using this standalone repository.

In the window, click **Trenuj** (Train), wait for the completion message, enter text, and click **Klasyfikuj** (Classify). The interface labels are in Polish.

## Code structure

| File | Responsibility |
| --- | --- |
| src/Main.java | Start the GUI on the Swing event-dispatch thread. |
| src/GUI.java | Training and classification controls. |
| src/Vector.java | Text loading and letter-frequency vectors. |
| src/Perceptron.java | Weight updates, binary predictions and raw scores. |
| src/Trainer.java | Load language folders, train models and choose a label. |

## Verification and evaluation status

The source compiled with Java 21. Local regression checks covered choosing the highest raw score when binary predictions tie, rejecting text without a–z letters, and retraining without duplicated language labels. These checks are not a bundled automated test suite.

**Accuracy has not been evaluated on an independent test set.** There is no reported accuracy, macro-F1 or confusion matrix. Before presenting model performance, reserve documents from different sources for testing, exclude them from training, and report the corpus sizes, sources and per-language results. The current GUI does not automate this evaluation.

## Current limitations

- Diacritics and all characters outside a–z are removed; this loses language information.
- Very short texts can be unreliable.
- The model always picks a trained language for non-empty input; it cannot reject an unknown language.
- Equal raw scores are resolved by the existing map iteration order.
- Training runs on the Swing event-dispatch thread and can temporarily freeze the interface for larger corpora.
- Models are kept in memory and must be trained again after restarting the application.
