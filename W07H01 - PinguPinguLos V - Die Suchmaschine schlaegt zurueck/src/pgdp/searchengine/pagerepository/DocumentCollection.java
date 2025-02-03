package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import static pgdp.searchengine.pagerepository.Document.*;

public class DocumentCollection {
	private final Bucket[] buckets;

	public DocumentCollection(int numberOfBuckets) {
		buckets = new Bucket[Math.max(numberOfBuckets, 1)];
		for (int i = 0; i < buckets.length; ++i) {
			buckets[i] = new Bucket();
		}
	}

	public boolean add(Document document) {
		if (document == null || document.getDocumentId() < 0) {
			return false;
		}

		int bucketIndex = indexFunction(document.getDocumentId());

		return buckets[bucketIndex].add(document);
	}

	public Document find(int documentId) {
		if (documentId < 0) {
			return null;
		}

		int bucketIndex = indexFunction(documentId);

		DocumentListElement foundListElement = buckets[bucketIndex].find(documentId);

		return foundListElement != null ? foundListElement.getDocument() : null;
	}

	public boolean removeDocument(int documentId) {
		if (documentId < 0) {
			return false;
		}

		int bucketIndex = indexFunction(documentId);

		DocumentListElement listElementToRemove = buckets[bucketIndex].find(documentId);

		return buckets[bucketIndex].remove(listElementToRemove);
	}

	public boolean removeDocumentsFromAuthor(Author author) {
		if (author == null) {
			return false;
		}

		DocumentListElement currentListElement;
		boolean removedSomething = false;

		for (int i = 0; i < buckets.length; ++i) {
			currentListElement = buckets[i].getHead();
			while (currentListElement != null) {
				if (currentListElement.getDocument().getAuthor().equals(author)) {
					DocumentListElement listElementToRemove = currentListElement;
					currentListElement = currentListElement.getNext();
					removedSomething |= buckets[i].remove(listElementToRemove);
				} else {
					currentListElement = currentListElement.getNext();
				}
			}
		}
		return removedSomething;
	}

	public boolean removeAll() {
		if (this.isEmpty()) {
			return false;
		}

		for (int i = 0; i < buckets.length; ++i) {
			buckets[i] = new Bucket();
		}
		return true;
	}

	public boolean removeOldDocuments(Date releaseDate, Date lastUpdated) {
		if (releaseDate == null && lastUpdated == null) {
			if (!removeAll()) {
				System.out.println("Hello there!");
				return false;
			}
			return true;
		}

		if (releaseDate != null && lastUpdated != null && releaseDate.daysUntil(lastUpdated) < 0) {
			return false;
		}

		DocumentListElement currentListElement;
		boolean removedSomething = false;

		for (int i = 0; i < buckets.length; ++i) {
			currentListElement = buckets[i].getHead();
			while (currentListElement != null) {
				if (releaseDate != null
						&& currentListElement.getDocument().getReleaseDate().daysUntil(releaseDate) > 0) {
					if (lastUpdated != null
							&& currentListElement.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0) {
						DocumentListElement listElementToRemove = currentListElement;
						removedSomething |= buckets[i].remove(listElementToRemove);
					} else if (lastUpdated == null) {
						DocumentListElement listElementToRemove = currentListElement;
						removedSomething |= buckets[i].remove(listElementToRemove);
					}
				} else if (releaseDate == null
						&& currentListElement.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0) {
					DocumentListElement listElementToRemove = currentListElement;
					removedSomething |= buckets[i].remove(listElementToRemove);
				}
				currentListElement = currentListElement.getNext();
			}
		}
		return removedSomething;
	}

	private int indexFunction(int documentId) {
		return documentId % buckets.length;
	}

	public DocumentListElement getHead(int bucketIndex) {
		if (bucketIndex < 0 || bucketIndex >= buckets.length) {
			return null;
		}
		return buckets[bucketIndex].getHead();
	}

	public DocumentListElement getTail(int bucketIndex) {
		if (bucketIndex < 0 || bucketIndex >= buckets.length) {
			return null;
		}
		return buckets[bucketIndex].getTail();
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public boolean contains(Document document) {
		if (document == null) {
			return false;
		}
		return this.find(document.getDocumentId()) != null;
	}

	public int getNumberOfBuckets() {
		return buckets.length;
	}

	public Bucket[] getBuckets() {
		return buckets;
	}

	public int size() {
		int size = 0;
		for (int i = 0; i < buckets.length; ++i) {
			size += buckets[i].size();
		}
		return size;
	}

	public WordCount[] getCompleteWordCountArray() {
		WordCount[] tmp = new WordCount[0];
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].size(); j++) {
				DocumentListElement current = buckets[i].getHead();
				while (current != null) {
					tmp = equalizeWordCountArray(tmp, current.getWordCountArray());
					current = current.getNext();
				}
			}
		}

		return tmp;
	}

	public void equalizeAllWordCountArrays() {
		WordCount[] tmp = getCompleteWordCountArray();
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i].size() > 0) {
				DocumentListElement current = buckets[i].getHead();
				while (current != null) {
					WordCount[] currdoc = current.getWordCountArray();
					current.setWordCountArray(equalizeWordCountArray(currdoc, tmp));
					sort(current.getWordCountArray());

					current = current.getNext();
				}
			}
		}
	}

	public int getNumberOfDocumentsContaining(String s) {
		if (s == null) {
			return 0;
		}
		s = s.replaceAll("\\.|,|\\!|\\?|;|\\*|\\(|\\)", "").toLowerCase(); //Aus Document-Klasse kopiert, oder halt alte Hausaufgabe
		int count = 0;
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i].size() > 0) {
				DocumentListElement current = buckets[i].getHead();
				while (current != null) {
					WordCount[] currentArray = current.getWordCountArray();
					for (int j = 0; j < currentArray.length; j++) {
						if (currentArray[j].getWord().equals(s)) {
							if(currentArray[j].getCount() > 0){
							count += 1;
							break;}
						}
					}
					current = current.getNext();
				}
			}
		}
		return count;
	}

	private void calculateWeights(WordCount[] wordCounts){
		if(wordCounts == null){
			return;
		}
		int Docsind = 0;
		for (int i = 0; i < buckets.length; i++) {
			Docsind += buckets[i].size();}

		for (int i = 0; i < wordCounts.length; i++) {
		double invertedFrequency = Math.log((double) (Docsind + 1) / (this.getNumberOfDocumentsContaining(wordCounts[i].getWord())))/Math.log(2);
		double weight = wordCounts[i].getCount() * invertedFrequency;
		double weightvad = 0;
			for (int j = 0; j < wordCounts.length; j++) {
				double invertedFrequencyV = Math.log((double) (Docsind + 1) / (this.getNumberOfDocumentsContaining(wordCounts[j].getWord())))/Math.log(2);
				double weightV = Math.pow(wordCounts[j].getCount() * invertedFrequencyV,2);
				weightvad += weightV;
			}
			double normalizedWeight = weight / Math.sqrt(weightvad);
			wordCounts[i].setWeight(weight);
			wordCounts[i].setNormalizedWeight(normalizedWeight);
		}
	}

	public Document[] query(String s) {
		if (s == null){
			return new Document[0];
		}
		Author newA = new Author("", "", "", "", Date.today());
		Document query = new Document("S", "", s, Date.today(), newA);
		this.add(query);
		equalizeAllWordCountArrays();
		calculateWeights(query.getWordCountArray());

		for (int i = 0; i < buckets.length; i++) {

			if (buckets[i].size() > 0) {
				DocumentListElement current = buckets[i].getHead();
				while (current != null) {
					calculateWeights(current.getWordCountArray());
					double similarity = complexSimilarity(query.getWordCountArray(), current.getWordCountArray());
					current.setSimilarity(similarity);

					current = current.getNext();
				}
			}

		}
		this.removeDocument(query.getDocumentId());
		return similarityRanking();
	}
	public void sortBuckets() {
		for (Bucket bucket : buckets) {
			for (int i = 0; i < bucket.size(); ++i) {
				DocumentListElement current = bucket.getHead();
				for (int j = 0; j < bucket.size() - i - 1; ++j) {
					if (current.getSimilarity() < current.getNext().getSimilarity()) {


						//pre cur next
						DocumentListElement pre = current.getPre();
						DocumentListElement next = current.getNext();

						current.setNext(next.getNext());
						next.setPre(pre);
						next.setNext(current);
						current.setPre(next);
						if (current.getNext() == null) {
							bucket.setTail(current);
						} else {
							current.getNext().setPre(current);
						}

						if (next.getPre() == null) {
							bucket.setHead(next);
						} else {
							next.getPre().setNext(next);


						}
						current = next;

					}
					current = current.getNext();
				}
			}
		}
	}

	public Document[] similarityRanking(){
		sortBuckets();
		Document[] ret  = new Document[size()];
		Bucket[] tmpCopy = new Bucket[buckets.length];

		for (int a = 0; a < buckets.length; a++)
			tmpCopy[a] = buckets[a];
		int rankingPointer = 0;
		while (!isEmpty()) {
			int maxIndex = 0;
			while (tmpCopy[maxIndex].getHead() == null)
				maxIndex++;
			for (int a = maxIndex + 1; a < tmpCopy.length; a++) {
				if (tmpCopy[a].getHead() == null) continue;
				if (tmpCopy[a].getHead().getSimilarity() > tmpCopy[maxIndex].getHead().getSimilarity())
					maxIndex = a;
				else if (tmpCopy[a].getHead().getSimilarity() == tmpCopy[maxIndex].getHead().getSimilarity()){
					if (tmpCopy[a].getHead().getDocumentId() < tmpCopy[maxIndex].getHead().getDocumentId())
						maxIndex = a;
				}
			}
			ret[rankingPointer] = tmpCopy[maxIndex].getHead().getDocument();
			tmpCopy[maxIndex].remove(tmpCopy[maxIndex].getHead());
			rankingPointer++;
		}
		return ret;
	}

}
