package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

public class LinkedDocument extends AbstractLinkedDocument {
    private final LinkedDocumentCollection outgoingLinks;

    public LinkedDocument(String title, String description, String content, Date releaseDate, Author author, String s, int i) {
        super(title,description,content, releaseDate,author, s, i);
        outgoingLinks = new LinkedDocumentCollection(i);
    }

    public LinkedDocumentCollection getOutgoingLinks() {
        return outgoingLinks;
    }
    public String[] getOutgoingAddresses(){
        String [] cont = super.getContent().split(" ");
        String [] addresses = new String[cont.length];

        int index = 0;
        for (String link: cont) {
            if (link.length() < 7) continue;
            if (link.substring(0,6).equals("link::")){
                addresses[index] = link.substring(6);
                index++;
            }
        }
        if(index == 0){return new String[0];}
        if (index < addresses.length){
            String[] tmp = new String[index];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = addresses[i];
            }
            addresses = tmp;
        }

        return addresses;
    }


    @Override
    public WordCount[] getWordCountArray() {
        WordCount[] wcarray = super.getWordCountArray();
        String[] s = getOutgoingAddresses();
        int howoften = 0;
        for (int i = 0; i < wcarray.length; i++) {
            for (int j = 0; j < s.length; j++) {
                if (wcarray[i].getWord().equals(s[j])) {
                    howoften++;
                    break;
                }
            }
        }
        WordCount[] ret = new WordCount[wcarray.length - howoften];
        int index = 0;
        for (int i = 0; i < wcarray.length; i++) {
            boolean notthere = true;
            for (int j = 0; j < s.length; j++) {
                if (wcarray[i].getWord().equals(s[j])) {
                    notthere = false;
                    break;
                }
            }
            if (notthere) {
                ret[index] = wcarray[i];
                index++;
            }
        }
        return ret;
    }

    public boolean addOutgoingLink(AbstractLinkedDocument document) {
       AbstractLinkedDocument foundElement = outgoingLinks.find(document.getAddress());

       if(document.getAddress().equals(address) || foundElement instanceof LinkedDocument) return false;

       if(foundElement instanceof  DummyLinkedDocument)
           outgoingLinks.removeDummy((DummyLinkedDocument) foundElement);
       outgoingLinks.add(document);
       return true;
    }



}
