package pgdp.searchengine.networking;

import java.util.Arrays;

public class HTTPResponse {
    private HTTPStatus status;
    private String html;

    public HTTPResponse(String responseText) {

        String[] s = responseText.split(" ");
        this.status = HTTPStatus.getByCode(Integer.parseInt(s[1]));

        String[] rest = responseText.split("<");
        StringBuilder response = new StringBuilder();
        response.append("<");

        for (int i = 1; i < rest.length; i++) {
            response.append(rest[i]);
            if(i < rest.length -1){
                response.append("<");
            }
        }
        this.html = response.toString();
    }

    public HTTPStatus getStatus() {
        return status;
    }

    public String getHtml() {
        return html;
    }
    public static void main (String[]args){
    }
}
