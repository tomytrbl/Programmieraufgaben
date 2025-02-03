package pgdp.searchengine.util;

public class WordCount {

	private final String word;
	private int count;

	public WordCount(String word, int count) {
		this.word = word;
		this.count = Math.max(count, 0);
	}

	public WordCount(String word) {
		this(word, 0);
	}

	public void increment() {
		this.count += 1;
	}

	public int getCount() {
		return count;
	}

	public String getWord() {
		return word;
	}

	@Override
	public String toString() {
		return "WordCount{" + "word='" + word + '\'' + ", count=" + count + '}';
	}
}
