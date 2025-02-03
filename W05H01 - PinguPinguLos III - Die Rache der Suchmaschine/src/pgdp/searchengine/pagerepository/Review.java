package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class Review {
    private final int postId;
    private static int nextPostId = 0;

    private String title;
    private String content;
    private final Date postDate;
    private final Author reviewer;
    private final Document reviewedDocument;

    private int rating;

    public Review(String title, String content, Date postDate, Author reviewer, Document reviewedDocument, int rating) {
        postId = nextPostId++;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.reviewer = reviewer;
        this.reviewedDocument = reviewedDocument;
        this.rating = rating;
    }

    public String getAgeText() {
        return "Vor " + postDate.daysUntil(Date.today()) + " Tagen gepostet";
    }

    @Override
    public String toString() {
        return "[" + title + ": " + reviewedDocument + ", " + rating + " stars]";
    }

    public String toPrintText() {
        return "<|==== Review of " + reviewedDocument + " ====|>\n(" + rating + " stars)" + "\nTitle: " + title + "\n"
                + content + "\nPosted at " + postDate + "\nPosted by " + reviewer;
    }

    public static int numberOfCreatedReviews() {
        return nextPostId;
    }

    //Getter
    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Author getReviewer() {
        return reviewer;
    }

    public Document getReviewedDocument() {
        return reviewedDocument;
    }

    public int getRating() {
        return rating;
    }

    //Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean equals(Review other) {
        return postId == other.postId;
    }
}
