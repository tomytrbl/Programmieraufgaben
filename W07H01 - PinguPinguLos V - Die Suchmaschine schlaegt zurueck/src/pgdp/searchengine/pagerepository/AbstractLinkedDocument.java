package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public abstract class AbstractLinkedDocument extends Document {
    final String address;
    private final LinkedDocumentCollection incomingLinks;

    public AbstractLinkedDocument(String title, String description, String content, Date releaseDate, Author author, String address, int i) {
        super(title,description,content, releaseDate,author);
        this.address = address;
        incomingLinks = new LinkedDocumentCollection(i);

    }

    public LinkedDocumentCollection getIncomingLinks() {
        return incomingLinks;
    }

    public String getAddress() {
        return address;
    }
    public boolean addIncomingLink(AbstractLinkedDocument document) {
        if(document.getAddress().equals(this.getAddress())){return false;}
        if(incomingLinks.find(document.getAddress()) != null){
            return false;}
        return incomingLinks.add(document);
    }
}
