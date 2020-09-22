package sentenceparser;

import sentenceparser.domain.Sentence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Miloslav Vavra
 *
 * This class loads the given file, parses it and converts it into the chosen output format (CSV or XML).
 */
public class Demo {

    public static final String CHARS_TO_REMOVE = "[-,.!?:()]";
    // sentence counter for CSV
    private static int sentenceCount = 1;
    // a flag telling us which output format are we processing
    private static boolean xml = false;

    /**
     * Main method.
     *
     * @param args first argument is the output format - XML ("xml") or CSV ("csv"), second argument is the filepath
     */
    public static void main(String[] args) {

        xml = false;
        String filePath = "";

        // check the first argument
        if (args.length > 0 && args[0] != null && args[0].equals("xml")) {
            xml = true;
        } else if (args.length == 0 || args[0] == null || !args[0].equals("csv")) {
            System.err.println("Invalid arguments! Usage - first argument must be \"csv\" or \"xml\".");
            System.exit(1);
        }

        // check and get the file path from the second argument
        if (args.length < 2 || args[1].equals("")) {
            System.err.println("Invalid arguments! Second argument must be a path to the file.");
            System.exit(1);
        } else {
            filePath = args[1];
        }

        // try to load the file
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("File was not found!");
            System.exit(1);
        }

        // print the header
        if (xml) {
            printXmlHeader();
        } else {
            printCsvHeader();
        }

        // load the file and process every sentence
        loadAndProcessFile(inputStream);

        // print the XML footer
        if (xml) {
            printXmlFooter();
        }
    }

    /**
     * Load the given file and process it.
     *
     * @param inputStream stream with the file
     */
    private static void loadAndProcessFile(FileInputStream inputStream) {
        try (Scanner sc = new Scanner(inputStream, "UTF-8")) {

            Sentence sentence = new Sentence();

            // loop line after line
            while (sc.hasNextLine()) {

                // split every line by white characters
                String[] lineWords = sc.nextLine().split("[,\\s\\-:]");

                // check if the word is the last one in the sentence - in that case sort the whole sentence and print it to the output
                // otherwise just add the word to the current sentence
                for (String word : lineWords) {
                    if (word.equals("Mr.") || word.equals("Ms.") || word.equals("Mrs.")) {
                        sentence.addWord(word);
                    } else if (!word.endsWith(".") && !word.endsWith("!") && !word.endsWith("?")) {
                        String trimmedWord = word.replaceAll(CHARS_TO_REMOVE, "");
                        if (trimmedWord.length() > 0) {
                            sentence.addWord(trimmedWord);
                        }
                    } else {
                        String trimmedWord = word.substring(0, word.length() - 1).replaceAll(CHARS_TO_REMOVE, "");
                        if (trimmedWord.length() > 0) {
                            sentence.addWord(trimmedWord);
                        }
                        Collections.sort(sentence.getWords(), String.CASE_INSENSITIVE_ORDER);
                        if (xml) {
                            printXmlSentence(sentence);
                        } else {
                            printCsvSentence(sentence);
                        }
                        // we are done with the current sentence, start a new one
                        sentence = new Sentence();
                    }
                }
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file");
        }
    }

    /**
     * Print the header for the XML output.
     */
    private static void printXmlHeader() {
        System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        System.out.print("<text>");
    }

    /**
     * Print the footer for the XML output.
     */
    private static void printXmlFooter() {
        System.out.print("\n</text>");
    }

    /**
     * Print the sentence for the XML output.
     *
     * @param sentence one whole sentence
     */
    private static void printXmlSentence(Sentence sentence) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\t<sentence>");
        for (String word : sentence.getWords()) {
            buffer.append("\n\t\t<word>").append(word).append("</word>");
        }
        buffer.append("\n\t</sentence>");
        System.out.print(buffer);
    }

    /**
     * Print the header for the CSV output.
     */
    private static void printCsvHeader() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < 51; i++) {
            buffer.append(", Word ").append(i);
        }
        System.out.print(buffer);
    }

    /**
     * Print the sentence for the CSV output.
     *
     * @param sentence one whole sentence
     */
    private static void printCsvSentence(Sentence sentence) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\nSentence ").append(sentenceCount++);
        for (String word : sentence.getWords()) {
            buffer.append(", ").append(word);
        }
        System.out.print(buffer);
    }
}
