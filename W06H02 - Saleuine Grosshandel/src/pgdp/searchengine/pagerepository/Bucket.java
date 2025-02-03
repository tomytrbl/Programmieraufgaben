package pgdp.searchengine.pagerepository;

public class Bucket {
    private DocumentListElement head;
    private DocumentListElement tail;
    private int size;

    public Bucket() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void isEmpty(){
        if (size == 0){
            head = null;
            tail = null;
        }
    }

    public void addhelp(Document document){
        DocumentListElement toInsert = new DocumentListElement(document);
        if (head == null){
            head = toInsert;
            tail = toInsert;
        }
        else if (size == 1){
            if (document.getDocumentId() < head.getDocument().getDocumentId()){
                toInsert.setNext(head);
                head.setPre(toInsert);
                head = toInsert;
            }
            else{
                head.setNext(toInsert);
                toInsert.setPre(head);
                tail = toInsert;
            }
        } else {
            DocumentListElement current = head;
            for (int i = 0; i < size; i++) {
                if (current.getNext() == null) // beim tail angekommen
                {break;}
                else if (!(current.getDocument().getDocumentId() < document.getDocumentId())){
                    break;}
                current = current.getNext();
                //wert von next holen und vergleichen
            }
            if (current == head) {
                toInsert.setNext(head);
                head.setPre(toInsert);
                head = toInsert;
            } else if (current.getNext() == null && document.getDocumentId() > current.getDocument().getDocumentId()) {
                tail.setNext(toInsert);
                toInsert.setPre(tail);
                tail = toInsert;
            } else {
                DocumentListElement vorg = current.getPre();
                vorg.setNext(toInsert);
                toInsert.setPre(vorg);
                toInsert.setNext(current);
                current.setPre(toInsert);
            }
        }
        size++;
    }
    public int getSize() {
        return size;
    }

    public DocumentListElement getHead() {
        return head;
    }

    public DocumentListElement getTail() {
        return tail;
    }

    public void setHead(DocumentListElement head) {
        this.head = head;
    }

    public void setTail(DocumentListElement tail) {
        this.tail = tail;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
