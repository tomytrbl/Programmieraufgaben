package pgdp.searchengine.pagerepository;

public class LinkedDocumentCollection extends DocumentCollection {

    public LinkedDocumentCollection(int numberOfBuckets) {
        super(numberOfBuckets);
    }

    @Override
    public boolean add(Document document) {
        if (!(document instanceof AbstractLinkedDocument)) {
            return false;
        }
        if (document == null) {
            return false;
        }

        int bucketIndex = indexFunction(((AbstractLinkedDocument) document).getAddress());

        return super.getBuckets()[(bucketIndex)].add(document);
    }


    private int indexFunction(String address) {
        if (address == null) {
            return -1;
        }
        int ret = (address.hashCode() % super.getBuckets().length + super.getBuckets().length) % super.getBuckets().length;
        return ret;
    }

    public AbstractLinkedDocument find(String s) {
        if (s == null) {
            return null;
        }
        int bucketIndex = indexFunction(s);
        if (bucketIndex < 0 || bucketIndex >= super.getBuckets().length) {
            return null;
        }

        DocumentListElement foundListElement = null;
        if (super.getBuckets().length > 0) {
            DocumentListElement current = super.getBuckets()[bucketIndex].getHead();

            while (current != null) {
                Document doc = current.getDocument();
                if (doc instanceof AbstractLinkedDocument) {
                    if (((AbstractLinkedDocument) doc).getAddress().equals(s)) {
                        foundListElement = current;
                        return (AbstractLinkedDocument) current.getDocument();
                    }
                }
                current = current.getNext();
            }
        }
        return null;

    }

    public boolean removeDummy(DummyLinkedDocument target) {
        if (target == null) return false;
        int bucketIndex = indexFunction(target.address);

        return getBuckets()[bucketIndex].remove(target);
    }
    private void updateIncomingLinks(LinkedDocument ld, String[] s){}
    private void updateOutgoingLinks(AbstractLinkedDocument a){}
    public boolean addToResultCollection(AbstractLinkedDocument a){
        return false;
    }
}
