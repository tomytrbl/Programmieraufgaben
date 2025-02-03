package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import javax.print.Doc;
import java.util.Locale;

public class Document {
    private final int documentId;
    private static int nextDocumentId = 0;

    private String title;
    private String description;
    private String content;

    private final Date releaseDate;
    private Date lastUpdateDate;

    private final Author author;

    public Document(String title, String description, String content, Date releaseDate, Author author) {
        documentId = nextDocumentId++;
        this.title = title;
        this.description = description;
        this.content = content;
        this.releaseDate = releaseDate;
        this.lastUpdateDate = releaseDate;
        this.author = author;
    }

    public int yearsSinceRelease() {
        return releaseDate.yearsUntil(Date.today());
    }

    public int daysSinceLastUpdate() {
        return lastUpdateDate.daysUntil(Date.today());
    }


    //Getter
    public int getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Author getAuthor() {
        return author;
    }


    //Setter
    public void setTitle(String title) {
        lastUpdateDate = Date.today();
        this.title = title;
    }

    public void setDescription(String description) {
        lastUpdateDate = Date.today();
        this.description = description;
    }

    public void setContent(String content) {
        lastUpdateDate = Date.today();
        this.content = content;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public boolean equals(Document other) {
        return documentId == other.documentId;
    }

    @Override
    public String toString() {
        return title + ", by " + author + ", released " + releaseDate;
    }

    public String toPrintText() {
        return "<|==== " + title + " ====|>" + "\nBy " + author + "\n" + description + "\nLast updated at "
                + lastUpdateDate;
    }

    public static int numberOfCreatedDocuments() {
        return nextDocumentId;
    }

    public WordCount[] getWordCountArray() {
        String help = content;
        help = help.replaceAll("[.,!?*();]", "").toLowerCase();
        String[] splitHelp = help.split(" ");
        WordCount[] helpCount = new WordCount[1];
        helpCount[0] = new WordCount("tobedeleted");

        for (String smallString : splitHelp) {
            boolean found = false;
            for (WordCount word : helpCount) {
                if (smallString.equals(word.getWord())) {
                    found = true;
                    word.increment();
                    break;
                }
            }
            if (smallString.equals("")) {
                found = true;
            }
            if (!found) {
                WordCount[] copy = new WordCount[helpCount.length + 1];
                int i = 0;
                for (i = 0; i < helpCount.length; i++) {
                    copy[i] = helpCount[i];
                }
                WordCount newWord = new WordCount(smallString, 1);
                copy[copy.length - 1] = newWord;
                helpCount = copy;
            }
        }

        WordCount[] WordCount = new WordCount[helpCount.length - 1];
        for (int i = 0; i < WordCount.length; i++) {
            WordCount[i] = helpCount[i + 1];
        }
        return WordCount;
    }

    public static WordCount[] equalizeWordCountArray(WordCount[] first, WordCount[] second) {
        WordCount[] help = new WordCount[0];
        for (int j = 0; j < second.length; j++) {
            boolean found = false;
            for (int i = 0; i < first.length; i++) {
                if (second[j].getWord().equals(first[i].getWord())) {
                    found = true;
                    break;
                }
            }
            if (second[j].getWord().equals("")) {
                found = true;
            }
            if (!found) {
                WordCount[] copy = new WordCount[help.length + 1];
                for (int i = 0; i < help.length; i++) {
                    copy[i] = help[i];
                }
                copy[copy.length - 1] = new WordCount(second[j].getWord());
                help = copy;
            }
        }

        for (int i = 0; i < help.length; i++) {
            System.out.println(help[i].getWord());

        }

        WordCount[] ret = new WordCount[first.length + help.length];
        int x = first.length + help.length - 1;
        System.out.println("länge ist: " + x);
        int offset = 0;

        for (int i = 0; i < first.length; i++) {
            ret[i] = first[i];
            offset += 1;
        }
        for (int i = offset; i < ret.length; i++) {
            ret[i] = help[i - offset];
            System.out.println(i - offset);
        }

        for (WordCount word : ret) {
            System.out.println("NACH EQUAL: " + word.getWord());
        }

        return ret;
    }

    public static void sort(WordCount[] word) {
        for (int i = 0; i < word.length; i++) {
            for (int j = 1; j < word.length; j++) {
                String first = word[j - 1].getWord();
                String second = word[j].getWord();
                int shorterlen = Math.min(first.length(), second.length());
                for (int k = 0; k < shorterlen; k++) {
                    int a = first.charAt(k) - 'a';
                    int b = second.charAt(k) - 'a';
                    if (a > b) {
                        WordCount help = word[j - 1];
                        word[j - 1] = word[j];
                        word[j] = help;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < word.length; i++) {
            System.out.println("Nach Sortierung" + word[i].getWord());

        }
    }

    public static double similarity(WordCount[] first, WordCount[] second) {
        if (first.length != second.length) {
            return -1;
        }
        if (first.length * second.length == 0)
            return 0;
        double resultat = 0;
        for (int i = 0; i < first.length; i++) {
            resultat += first[i].getCount() * second[i].getCount();
        }
        resultat = resultat / (first.length * second.length);

        return resultat;
    }

    public double computeSimilarity(Document document) {
        WordCount[] thisDocument = getWordCountArray();
        WordCount[] otherDocument = document.getWordCountArray();
        thisDocument = equalizeWordCountArray(thisDocument, otherDocument);
        otherDocument = equalizeWordCountArray(otherDocument, thisDocument);
        sort(thisDocument);
        sort(otherDocument);

        return similarity(thisDocument, otherDocument);
    }

    public static void main(String[] args) {
    }
}
