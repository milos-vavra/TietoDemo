package sentenceparser.domain;

import java.util.ArrayList;

public class Sentence {

    private ArrayList<String> words = new ArrayList<>();

    public ArrayList<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        words.add(word);
    }
}
