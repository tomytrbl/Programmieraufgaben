package pgdp.searchengine.networking;

import javax.swing.text.html.HTML;
import java.sql.Array;
import java.util.*;
import java.util.stream.IntStream;

public final class HTMLProcessing {

    private HTMLProcessing() {
    }

    public static List<HTMLToken> tokenize(String rawHTML) {
        List<HTMLToken> result = new ArrayList<>();

        int lineindex = 0;

        List<String> lines = rawHTML.lines().toList();

        boolean insideTag = false;
        boolean insideText = false;
        StringBuilder text = new StringBuilder();

        boolean StringTag = false;
        StringBuilder quote = new StringBuilder();
        boolean end = false;

        String current = lines.get(0);
        while(!end && lineindex < lines.size()) {
            lineindex++;

            String analyse = current;
            analyse = analyse.trim();

            if(lineindex < lines.size()){current = lines.get(lineindex);}
            for (int i = 0; i < analyse.length(); i++) {
                if (text.toString().contains("/html") || text.toString().contains("/HTML")) {
                    end = true;
                    HTMLToken endToken = new HTMLToken(HTMLToken.TokenType.TAG);
                    String endContent = "/html";
                    IntStream.range(0, endContent.length()).forEach(j -> endToken.addCharacter(endContent.charAt(j)));
                    result.add(endToken);
                    break;
                }

                char c = analyse.charAt(i);

                if (!insideText && !insideText && c == '<' && !StringTag) {
                    insideTag = true;
                    continue;
                }

                if (!insideTag && !insideText && c != '<') {
                    insideText = true;
                    text.append(c);
                    continue;
                }
                if (insideText && c != '<') {
                    text.append(c);
                    continue;
                } else if (insideText && c == '<') {
                    insideText = false;
                    insideTag = true;
                    HTMLToken newText = new HTMLToken(HTMLToken.TokenType.TEXT);
                    String textContent = text.toString().toLowerCase();
                    if (!(textContent.contains("\"") || textContent.contains("\'"))) {
                        textContent = textContent.toLowerCase();
                    }
                    String insert = textContent;
                    IntStream.range(0, textContent.length()).forEach(j -> newText.addCharacter(insert.charAt(j)));
                    text.delete(0, text.length());
                    result.add(newText);
                    continue;
                }
                if (insideTag) {
                    if (c == '>' && !StringTag) {

                        insideTag = false;
                        HTMLToken newTag = new HTMLToken(HTMLToken.TokenType.TAG);
                        String TagContent = text.toString();
                        IntStream.range(0, text.length()).forEach(j -> newTag.addCharacter(TagContent.charAt(j)));
                        text.delete(0, text.length());
                        result.add(newTag);
                        continue;
                    } else {
                        if (!StringTag && (c == '"' || c == '\'')) {
                            StringTag = true;
                            quote.append(c);
                        } else if (StringTag && quote.toString().charAt(0) == c) {
                            StringTag = false;
                            quote.delete(0, quote.length());
                        }
                        if(StringTag){
                        text.append(c);}
                        else text.append(Character.toLowerCase(c));
                        continue;
                    }
                }
            }

        if(lineindex < lines.size()) current = lines.get(lineindex);
    }
        return result;
}

    private enum TokenizingState {
        INITIAL, TAG, TEXT, SINGLE_QUOTE_STRING, DOUBLE_QUOTE_STRING
    }

    public static String[] filterLinks(List<HTMLToken> tokens, String host) {
        String[] result = tokens.stream().filter(token -> token.getTokenType() == HTMLToken.TokenType.TAG)
                .map(token -> token.getContentAsString())
                .filter(contentAsString -> contentAsString.contains("href"))
                .map(s -> s.substring(8, s.length()-1))
                .filter(s -> s.startsWith("h") || s.startsWith("/"))
                .map(s -> { return
                    s.startsWith("http://") ? s.substring(7, s.length()) : s.startsWith("/") ? host + s : "";}).filter(s -> !s.isEmpty())
                .toArray(String[]::new);
       return result;
    }

    public static String filterText(List<HTMLToken> tokens) {
        String [] result = tokens.stream()
                .filter(token -> token.getTokenType() == HTMLToken.TokenType.TEXT)
                .map(token -> token.getContentAsString())
                .toArray(String[]::new);

        return String.join(" ", result);
    }

    public static String filterTitle(List<HTMLToken> tokens) {
        int headindex = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getContentAsString().equals("head")){
                headindex = i;
                break;
            };
        }
        return tokens.get(headindex+2).getContentAsString();
    }

}
