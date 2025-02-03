package pgdp.searchengine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pgdp.searchengine.pagerepository.Author;
import pgdp.searchengine.pagerepository.Bucket;
import pgdp.searchengine.pagerepository.Document;
import pgdp.searchengine.pagerepository.DocumentCollection;
import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import static pgdp.searchengine.pagerepository.Document.equalizeWordCountArray;

public class SearchEngineTesting {

    @BeforeEach
    void setUp() {
        Date testDate1 = new Date(14, 10, 2002);
        Author testAuthor = new Author("Tomy", "Doan","Adress","Email", testDate1);
        Document testDocument = new Document("Title", "Descp", "HiFromOne HelloFromOne", testDate1, testAuthor);
        Document testDocument2 = new Document("Title", "Descp", "ClassFromTwo MethodFromTwo", testDate1, testAuthor);
        Document testDocument3 = new Document("Title", "Descp", "AhriFromThree AmumuFfromThree", testDate1, testAuthor);
        //String title, String description, String content, Date releaseDate, Author author
        Bucket testBucket = new Bucket();
    }
    @Test
    void getCompleteWordCountArrayTest() {
    }

    public static void print(WordCount[] array,String from){
        System.out.println("WCA from: "+from);
        for (int i = 0; i <array.length; i++) {
            System.out.println(array[i].getWord());
            System.out.println(array[i].getCount());
        }
    }

    public static void main(String[] args) {
        Date testDate1 = new Date(14, 10, 2002);
        Author testAuthor = new Author("Tomy", "Doan", "Adress", "Email", testDate1);
        Document testDocument = new Document("Title", "Descp", "HiFromOne HelloFromOne", testDate1, testAuthor);
        Document testDocument2 = new Document("Title", "Descp", "classfromtwo mnethodfromtwo", testDate1, testAuthor);
        Document testDocument3 = new Document("Title", "Descp", "AhriFromThree AmumuFromThree", testDate1, testAuthor);

        DocumentCollection testCollection = new DocumentCollection(2);
        testCollection.add(testDocument);
        testCollection.add(testDocument2);
        testCollection.add(testDocument3);

        print(testDocument.getWordCountArray(), "Document1");
        print(testDocument2.getWordCountArray(), "Document2");
        print(testDocument3.getWordCountArray(), "Document3");

        testCollection.equalizeAllWordCountArrays();
        print(testDocument.getWordCountArray(), "Document1");
        print(testDocument2.getWordCountArray(), "Document2");
        print(testDocument3.getWordCountArray(), "Document3");
        print(testCollection.getHead(0).getWordCountArray(), "Bucket 0");
    }
}
