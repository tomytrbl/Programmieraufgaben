package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

import static pgdp.searchengine.util.Date.today;

public class Document {
    private final int documentId;
    private static int nextDocumentId = 0;

    private String title;
    private String description;
    private String content;

    private final Date releaseDate;
    private Date lastUpdateDate;

    private final Author author;

    public Document(String title, String description, String content, Date releaseDate, Author author) {
        documentId = nextDocumentId++;
        this.title = title;
        this.description = description;
        this.content = content;
        this.releaseDate = releaseDate;
        this.lastUpdateDate = releaseDate;
        this.author = author;
    }

    public boolean equals(Document other) {
        return documentId == other.documentId;
    }

    @Override
    public String toString() {
        return title + ", by " + author + ", released " + releaseDate;
    }

    public String toPrintText() {
        return "<|==== " + title + " ====|>" + "\nBy " + author + "\n" + description + "\nLast updated at "
                + lastUpdateDate;
    }

    public static int numberOfCreatedDocuments() {
        return nextDocumentId;
    }

    //Getter
    public int getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Author getAuthor() {
        return author;
    }


    //Setter
    public void setTitle(String title) {
        this.title = title;
        setLastUpdateDate(today());
    }

    public void setDescription(String description) {
        this.description = description;
        setLastUpdateDate(today());
    }

    public void setContent(String content) {
        this.content = content;
        setLastUpdateDate(today());
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int yearsSinceRelease()
    {
        return -1 * today().yearsUntil(releaseDate) -1;
    }

    public int daysSinceLastUpdate() {
        return -1 * today().daysUntil(releaseDate);
    }



}
