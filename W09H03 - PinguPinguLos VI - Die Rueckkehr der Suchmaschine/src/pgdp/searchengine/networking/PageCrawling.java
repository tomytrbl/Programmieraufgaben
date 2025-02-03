package pgdp.searchengine.networking;

import pgdp.searchengine.pagerepository.Author;
import pgdp.searchengine.pagerepository.LinkedDocument;
import pgdp.searchengine.pagerepository.LinkedDocumentCollection;
import pgdp.searchengine.util.Date;

import java.util.List;

public final class PageCrawling {
    private PageCrawling() {

    }

    public static void crawlPages(LinkedDocumentCollection collection, int number) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    public static void crawlPages(LinkedDocumentCollection collection, int number, String startingAddress) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    public static boolean crawlPage(LinkedDocumentCollection collection, String address) {
        int indexHostPath = 0;
        for (int i = 0; i < address.length(); i++) {
            char c = address.charAt(i);
            if(c == '/'){
                indexHostPath = i;
                break;
            }
        }
        String host = "localhost";
        String path = address.substring(indexHostPath+1);
        HTTPRequest request = new HTTPRequest("localhost", path);
        HTTPResponse response = request.send(8000);

        if(response.getStatus() != HTTPStatus.OK){
            return false;
        }

        List<HTMLToken> tokens = HTMLProcessing.tokenize(response.getHtml());
        LinkedDocument ldtoAdd = new LinkedDocument(
                HTMLProcessing.filterTitle(tokens)
                ,""
                ,HTMLProcessing.filterText(tokens)
                , Date.today()
                ,new Author("", "", "", "", Date.today())
                ,address
                ,HTMLProcessing.filterLinks(tokens, host).length);
        String[] links = HTMLProcessing.filterLinks(tokens, host );
        for (String link : links) {
            if(collection.find(link) != null){
            collection.find(link).getIncomingLinks().add(ldtoAdd);}
        }

        collection.add(ldtoAdd);
        return true;
    }

    public static void main(String... args) {

    }

}
