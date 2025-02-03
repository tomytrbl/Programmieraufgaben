package pgdp.searchengine.networking;

import java.net.Socket;
import java.util.stream.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class HTTPRequest {
    private String host;
    private String path;

    public HTTPRequest(String host, String path) {
        this.host = host;
        this.path = path;
    }

    public HTTPResponse send(int port){
        Socket socket = null;
        try {
            socket = new Socket(host, port);

            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;

            while((line = rd.readLine()) != null){
                content.append(line);
            }
        return new HTTPResponse(content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }
}
