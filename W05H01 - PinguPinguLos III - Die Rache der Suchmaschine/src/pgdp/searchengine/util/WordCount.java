package pgdp.searchengine.util;

public class WordCount {
    private String word;
    private int count;

    public WordCount(String word, int count) {
        this.word = word;
        if (count < 0){
            this.count = 0;
        }
        else this.count = count;

    }
    public WordCount(String word){
        count = 0;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void increment(){
        count += 1;
    }
}
