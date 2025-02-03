package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

public class Document {

	private static final String ANY_WHITESPACE = "\\s+";
	private static final String FILTERED_CHARS = "[.,!?;*()]";
	private static final String EMPTY_STRING = "";
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

	public WordCount[] getWordCountArray() {

		String[] words = this.content.replaceAll(FILTERED_CHARS, EMPTY_STRING).toLowerCase().split(ANY_WHITESPACE);

		WordCount[] temp = new WordCount[words.length];
		int uniqueWords = 0;

		for (String word : words) {

			if (word.isBlank()) {
				continue;
			}

			int index = findIndex(temp, word);

			if (index == -1) {
				temp[uniqueWords++] = new WordCount(word, 1);
			} else {
				temp[index].increment();
			}
		}
		WordCount[] wordCounts = new WordCount[uniqueWords];
		System.arraycopy(temp, 0, wordCounts, 0, wordCounts.length);

		return wordCounts;
	}

	public static WordCount[] equalizeWordCountArray(WordCount[] first, WordCount[] second) {

		WordCount[] temp = new WordCount[first.length + second.length];


		System.arraycopy(first, 0, temp, 0, first.length);
		int size = first.length;

		for (WordCount wordCount : second) {

			if (findIndex(first, wordCount.getWord()) == -1) {
				temp[size++] = new WordCount(wordCount.getWord());
			}
		}


		WordCount[] result = new WordCount[size];
		System.arraycopy(temp, 0, result, 0, size);

		return result;
	}

	public static void sort(WordCount[] unsorted) {
		int n = unsorted.length;
		boolean swapped;

		for (int i = 0; i < n - 1; i++) {
			swapped = false;
			for (int j = 0; j < n - i - 1; j++) {
				if (unsorted[j].getWord().compareTo(unsorted[j + 1].getWord()) > 0) {
					WordCount temp = unsorted[j];
					unsorted[j] = unsorted[j + 1];
					unsorted[j + 1] = temp;
					swapped = true;
				}
			}
			if (!swapped)
				break;
		}
	}

	public static double similarity(WordCount[] first, WordCount[] second) {
		if (first.length != second.length) {
			return -1;
		}

		if (first.length == 0) {
			return 0.0;
		}

		double result = 0;

		for (int i = 0; i < first.length; ++i) {
			result += first[i].getCount() * second[i].getCount();
		}

		return result / (first.length * second.length);
	}

	public double computeSimilarity(Document other) {
		WordCount[] thisDocument = getWordCountArray();
		WordCount[] otherDocument = other.getWordCountArray();

		thisDocument = equalizeWordCountArray(thisDocument, otherDocument);
		otherDocument = equalizeWordCountArray(otherDocument, thisDocument);

		sort(thisDocument);
		sort(otherDocument);

		return similarity(thisDocument, otherDocument);
	}

	/**
	 * Hilfsmethode um nach einem bestimmten Word in einem WordCount Array zu suchen
	 *
	 * @param wordCounts das zu durchsuchende Array
	 * @param word       das gesuchte Wort
	 * @return der Index von dem {@link WordCount} mit dem gesuchten Wort, sonst -1
	 */
	private static int findIndex(WordCount[] wordCounts, String word) {
		for (int i = 0; i < wordCounts.length; i++) {
			if (wordCounts[i] != null && wordCounts[i].getWord().equals(word)) {
				return i;
			}
		}
		return -1;
	}

	public int yearsSinceRelease() {
		return releaseDate.yearsUntil(Date.today());
	}

	public int daysSinceLastUpdate() {
		return lastUpdateDate.daysUntil(Date.today());
	}

	// Getter
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

	// Setter
	public void setTitle(String title) {
		lastUpdateDate = Date.today();
		this.title = title;
	}

	public void setDescription(String description) {
		lastUpdateDate = Date.today();
		this.description = description;
	}

	public void setContent(String content) {
		lastUpdateDate = Date.today();
		this.content = content;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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
}
