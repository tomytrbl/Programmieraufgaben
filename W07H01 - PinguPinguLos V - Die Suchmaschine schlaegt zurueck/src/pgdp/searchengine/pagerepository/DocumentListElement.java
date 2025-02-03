package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.WordCount;

public class DocumentListElement {
    private Document document;
    private DocumentListElement pre;
    private DocumentListElement next;
    private WordCount[] wordCountArray;
    private double similarity;

    public DocumentListElement(Document document) {
        this.document = document;
        this.wordCountArray = document.getWordCountArray();
        this.similarity = 0;
    }

    public WordCount[] getWordCountArray() {
        return wordCountArray;
    }

    public double getSimilarity() {
        return similarity;
    }

    public int getDocumentId() {
        return document.getDocumentId();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentListElement getPre() {
        return pre;
    }

    public void setPre(DocumentListElement pre) {
        this.pre = pre;
    }

    public DocumentListElement getNext() {
        return next;
    }

    public void setNext(DocumentListElement next) {
        this.next = next;
    }

    public void setWordCountArray(WordCount[] wordCountArray) {
        this.wordCountArray = wordCountArray;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

}
