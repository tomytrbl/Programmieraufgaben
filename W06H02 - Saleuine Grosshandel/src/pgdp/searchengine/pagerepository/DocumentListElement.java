package pgdp.searchengine.pagerepository;

import javax.print.Doc;

public class DocumentListElement {
    private Document document;
    private DocumentListElement pre;
    private DocumentListElement next;

    public DocumentListElement(Document document) {
        this.document = document;
        pre = null;
        next = null;
    }

    public DocumentListElement(Document document, DocumentListElement pre, DocumentListElement next) {
        this.document = document;
        this.pre = pre;
        this.next = next;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentListElement getPre() {
        return pre;
    }

    public DocumentListElement getNext() {
        return next;
    }

    public void setPre(DocumentListElement pre) {
        this.pre = pre;
    }

    public void setNext(DocumentListElement next) {
        this.next = next;
    }

    public Document getDocument() {
        return document;
    }
}
