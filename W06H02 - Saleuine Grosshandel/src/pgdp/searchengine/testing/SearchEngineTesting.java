package pgdp.searchengine.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import pgdp.searchengine.pagerepository.Author;
import pgdp.searchengine.pagerepository.Document;
import pgdp.searchengine.pagerepository.DocumentCollection;
import pgdp.searchengine.util.Date;

import java.util.jar.JarOutputStream;

public class SearchEngineTesting {
    public static void main (String[] args){
        Date newDate = new Date(14,10,2002);
        Date newDate2 = new Date(10,10,2002);
        Date newDateafter = new Date( 20,10,2002);

        Author tomy = new Author("tomy","doan","f","e",newDate);
        Document testdocument = new Document("a","b","c",newDate, tomy);
        Document testdocument2 = new Document("a","b","c",newDate, tomy);
        Document testdocumentafter = new Document("a","b","c",newDateafter, tomy);

        DocumentCollection testCollection = new DocumentCollection(2);
        Date releaseTest = new Date(30,10,2002);
        Date lastUpdateDocument = new Date (26,10,2002);
        Date lastUpdateTest = new Date(24,10,2002);
        testCollection.add(testdocument);
        testCollection.add(testdocument2);
        testCollection.add(testdocumentafter);
        testdocument.setLastUpdateDate(lastUpdateDocument);
        testdocument2.setLastUpdateDate(lastUpdateDocument);
        testdocumentafter.setLastUpdateDate(lastUpdateDocument);
        testCollection.toString();
        testCollection.removeOldDocuments(null, lastUpdateTest);
        System.out.println("\n\n");
        testCollection.toString();
    }
}
