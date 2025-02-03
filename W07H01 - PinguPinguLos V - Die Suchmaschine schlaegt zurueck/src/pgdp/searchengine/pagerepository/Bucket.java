package pgdp.searchengine.pagerepository;

public class Bucket {
	private DocumentListElement head;
	private DocumentListElement tail;
	private int size;

	public Bucket() {
		this.head = null;
		this.tail = null;
	}

	public boolean add(Document document) {
		if (document == null || document.getDocumentId() < 0) {
			return false;
		}

		DocumentListElement newLE = new DocumentListElement(document);

		if (head == null) {
			head = newLE;
			tail = head;
			size++;
			return true;
		}

		for (DocumentListElement current = head; current != null; current = current.getNext()) {
			if (current.getDocumentId() == document.getDocumentId()) {
				return false;
			}

			if (document.getDocumentId() < current.getDocumentId()) {
				DocumentListElement prev = current.getPre();
				newLE.setNext(current);
				newLE.setPre(prev);
				current.setPre(newLE);

				if (prev != null) {
					prev.setNext(newLE);
				} else {
					head = newLE;
				}
				size++;
				return true;
			}
		}

		newLE.setPre(tail);
		tail.setNext(newLE);
		tail = newLE;
		size++;
		return true;
	}

	public boolean remove(DocumentListElement listElementToRemove) {
		if (listElementToRemove == null) {
			return false;
		}

		if (listElementToRemove == head) {
			head = listElementToRemove.getNext();
		} else {
			listElementToRemove.getPre().setNext(listElementToRemove.getNext());
		}

		if (listElementToRemove == tail) {
			tail = listElementToRemove.getPre();
		} else {
			listElementToRemove.getNext().setPre(listElementToRemove.getPre());
		}

		size--;
		return true;
	}
	public boolean remove(DummyLinkedDocument listElementToRemove) {

		if (listElementToRemove == null) {
			return false;
		}

		DocumentListElement removeTarget = null;

		for (DocumentListElement current = head; current != null; current = current.getNext()) {
			if (((AbstractLinkedDocument)current.getDocument()).getAddress().equals(listElementToRemove.getAddress())) {
				removeTarget = current;
				break;
			}
		}

		if (removeTarget == null) return false;

		if (head.getDocument().equals(listElementToRemove)) {
			head = head.getNext();
		} else {
			removeTarget.getPre().setNext(removeTarget.getNext());
		}

		if (tail.getDocument().equals(listElementToRemove)) {
			tail = tail.getPre();
		} else {
			removeTarget.getNext().setPre(removeTarget.getPre());
		}

		size--;
		return true;
	}

	public DocumentListElement find(int documentId) {
		if (documentId < 0) {
			return null;
		}

		for (DocumentListElement current = head; current != null; current = current.getNext()) {
			if (current.getDocumentId() == documentId) {
				return current;
			}
		}

		return null;
	}

	public DocumentListElement getHead() {
		return head;
	}

	public void setHead(DocumentListElement head) {
		this.head = head;
	}

	public DocumentListElement getTail() {
		return tail;
	}

	public void setTail(DocumentListElement tail) {
		this.tail = tail;
	}

	public int size() {
		return size;
	}

}
