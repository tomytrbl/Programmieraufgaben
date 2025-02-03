package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class DocumentCollection {
    private final Bucket[] buckets;


    public DocumentCollection(int length) {
        if (length <= 0) {
            this.buckets = new Bucket[1];
            length = 1;
        } else {
            this.buckets = new Bucket[length];
        }
        for (int i = 0; i < length; i++) {
            buckets[i] = new Bucket();

        }
    }


    private int indexFunction(int documentID) {
        return documentID % buckets.length;
    }

    public boolean add(Document document) {
        if (document != null && document.getDocumentId() < 0 || document == null) {
            return false;
        }

        int index = indexFunction(document.getDocumentId());

        DocumentListElement current = buckets[index].getHead();
        for (int j = 0; j < buckets[index].getSize(); j++) {
            if (document.getDocumentId() == current.getDocument().getDocumentId()) {
                return false;
            }
            current = current.getNext();
        }
        buckets[index].addhelp(document);
        return true;
    }


    public boolean isEmpty() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].getSize() != 0) {
                return false;
            }
        }
        return true;
    }

    public Document find(int documentID) {
        int index = indexFunction(documentID);
        if (index < 0) {
            return null;
        }
        if (buckets[index].getSize() == 0) {
            return null;
        }
        DocumentListElement current = buckets[index].getHead();
        for (int i = 0; i < buckets[index].getSize(); i++) {
            if (current.getNext() == null) // beim tail angekommen
            {
                break;
            } else if ((current.getDocument().getDocumentId() == documentID)) {
                return current.getDocument();
            }
            current = current.getNext();
        }
        if (current.getDocument().getDocumentId() == documentID) {
            return current.getDocument();
        } else return null;
    }

    public boolean removeDocument(int index) {
        Document todelete = find(index);
        if (todelete == null) {
            return false;
        }
        int buckindex = indexFunction(todelete.getDocumentId());
        DocumentListElement current = buckets[buckindex].getHead();

        for (int i = 0; i < buckets[buckindex].getSize(); i++) {
            if (current.getDocument().equals(todelete)) {
                if (buckets[buckindex].getHead() == buckets[buckindex].getTail() && buckets[buckindex].getHead() != null) {
                    buckets[buckindex].setSize(0);
                    buckets[buckindex].setHead(null);
                    buckets[buckindex].setTail(null);
                } else if (buckets[buckindex].getHead().getDocument().equals(todelete)
                ) {
                    buckets[buckindex].setHead(current.getNext());
                    buckets[buckindex].getHead().getPre().setNext(null);
                    buckets[buckindex].getHead().setPre(null);
                    buckets[buckindex].setSize(buckets[buckindex].getSize() - 1);
                    if (size() == 1) {
                        buckets[buckindex].setTail(buckets[buckindex].getHead());
                    }

                } else if (buckets[buckindex].getTail().getDocument().equals(todelete)) {
                    buckets[buckindex].setTail(buckets[buckindex].getTail().getPre());
                    buckets[buckindex].getTail().setNext(null);
                    buckets[buckindex].setSize(buckets[buckindex].getSize() - 1);
                } else {
                    current.getPre().setNext(current.getNext());
                    current.getNext().setPre(current.getPre());
                    buckets[buckindex].setSize(buckets[buckindex].getSize() - 1);
                }
                return true;
            }
            current = current.getNext();

        }
        return false;
    }

    public boolean removeDocumentsFromAuthor(Author author) {
        boolean empty = true;
        for (Bucket bucket : buckets) {
            if (bucket.getSize() != 0) {
                empty = false;
            }
            if (empty) {
                return false;
            }
        }
        boolean found = false;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].getHead() != null) {
                DocumentListElement current = buckets[i].getHead();
                while (current != null) {
                    DocumentListElement next = current.getNext();
                    if (current.getDocument().getAuthor().equals(author)) {
                        removeDocument(current.getDocument().getDocumentId());
                        found = true;
                    }
                    current = next;
                }
            }
        }
        return found;
    }

    public boolean removeAll() {
        boolean empty = true;
        for (Bucket bucket : buckets) {
            if (bucket.getSize() != 0) {
                empty = false;
            }
            if (empty) {
                return false;
            }
        }
        for (int i = 0; i < buckets.length; i++) {
            buckets[i].setHead(null);
            buckets[i].setTail(null);
            buckets[i].setSize(0);
        }

        return true;
    }

    public boolean removeOldDocuments(Date releaseDate, Date lastUpdated) {
        boolean removed = false;
        if (releaseDate == null && lastUpdated == null) {
            return removeAll();
        } else if (lastUpdated == null) {
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i].getHead() != null) {
                    DocumentListElement current = buckets[i].getHead();
                    while (current != null) {
                        DocumentListElement next = current.getNext();
                        if (current.getDocument().getReleaseDate().daysUntil(releaseDate) > 0) {
                            removeDocument(current.getDocument().getDocumentId());
                            removed = true;
                        }
                        current = next;
                    }
                }
            }
        } else if (releaseDate == null) {
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i].getHead() != null) {
                    DocumentListElement current = buckets[i].getHead();
                    while (current != null) {
                        DocumentListElement next = current.getNext();
                        if (current.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0) {
                            removeDocument(current.getDocument().getDocumentId());
                            removed = true;
                        }
                        current = next;
                    }
                }
            }

        } else if (lastUpdated.daysUntil(releaseDate) > 0) {
            return false;
        } else {
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i].getHead() != null) {
                    DocumentListElement current = buckets[i].getHead();
                    while (current != null) {
                        DocumentListElement next = current.getNext();
                        if (current.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0 && current.getDocument().getReleaseDate().daysUntil(releaseDate) > 0) {
                            removeDocument(current.getDocument().getDocumentId());
                            removed = true;
                        }
                        current = next;
                    }
                }
            }
        }
        return removed;
    }


    public boolean contains(Document document) {
        if (document == null) {
            return false;
        }
        if (find(document.getDocumentId()) == null) {
            return false;
        } else if (find(document.getDocumentId()).equals(document)) {
            return true;
        }
        return false;
    }

    public int size() {
        int size = 0;
        for (int i = 0; i < buckets.length; i++) {
            size += buckets[i].getSize();
        }
        return size;
    }

    @Override
    public String toString() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.println("Bucket: " + i);
            DocumentListElement current = buckets[i].getHead();
            for (int j = 0; j < buckets[i].getSize(); j++) {
                System.out.println("Dokument mit der ID: " + current.getDocument().getDocumentId() + "und dem LastUpDate: " + current.getDocument().getLastUpdateDate());
                current = current.getNext();
            }
        }
        return "";
    }

    public DocumentListElement getHead(int bucketIndex) {
        if (bucketIndex < 0 || bucketIndex >= buckets.length) {
            return null;
        }
        if (buckets[bucketIndex].getHead() != null) {
            return buckets[bucketIndex].getHead();
        }
        return null;
    }

    public DocumentListElement getTail(int bucketIndex) {
        if (bucketIndex < 0 || bucketIndex >= buckets.length) {
            return null;
        }
        if (buckets[bucketIndex].getTail() != null) {
            return buckets[bucketIndex].getTail();
        }
        return null;
    }
}
