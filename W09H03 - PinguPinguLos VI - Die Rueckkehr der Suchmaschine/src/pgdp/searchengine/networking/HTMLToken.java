package pgdp.searchengine.networking;

public class HTMLToken {

    private TokenType tokenType;
    private StringBuilder content;

    public HTMLToken(TokenType tokenType) {
        this.tokenType = tokenType;
        this.content = new StringBuilder();
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getContentAsString() {
        if(content == null){
            return "";
        } else return content.toString();
    }

    public void addCharacter(char c) {
        content.append(c);
    }

    public int getStatus() {
        throw new UnsupportedOperationException("Not implemented!");
    }



    @Override
    public String toString() {
        if(tokenType == TokenType.TAG){
            return "Tag: "+content.toString();}
        else return "Text: "+content.toString();
    }

    public enum TokenType {
        TAG, TEXT
    }
}
