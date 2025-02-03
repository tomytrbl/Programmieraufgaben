package pgdp.pit;

import java.io.IOException;
import java.nio.file.Path;

import static pgdp.pit.HistoryEntryRepository.load;

public class HistoryEntry {

    private Integer prevHash;

    private Integer nextHash;

    private HistoryEntry prev;

    private HistoryEntry next;

    private String content;

    private Path pitDirectory;

    HistoryEntry(Path pitDirectory, String content) {
        this.pitDirectory = pitDirectory;
        this.content = content;
        prevHash = null;
        nextHash = null;
        prev = null;
        next = null;
    }

    public Integer getPrevHash() {return prevHash;}

    public void setPrevHash(int prevHash) {this.prevHash = prevHash;}

    public Integer getNextHash() {return nextHash;}

    public void setNextHash(int nextHash) {this.nextHash = nextHash;}

    public HistoryEntry getPrev() throws IOException{
        if (prev != null) {
            return prev;
        }
        if (prevHash == null) {
            return null;
        }
        prev = load(pitDirectory, prevHash);
        return prev;}

    public void setPrev(HistoryEntry prev) {
        this.prev = prev;
        if(prev != null){
            this.prevHash = prev.getContent().hashCode();
        } else{
            this.prevHash = null;
        }
    }

    public HistoryEntry getNext() throws IOException {
        if (next != null) {
            return next;
        }
        if (nextHash == null) {
            return null;
        }
        next = load(pitDirectory, nextHash);
        return next;}

    public void setNext(HistoryEntry next) {
        this.next = next;
        if(next != null){
            this.nextHash = next.getContent().hashCode();
//            next.setPrev(this);
        } else {
            this.nextHash = null;
        }
    }

    public String getContent() {return content;}

    public void setContent(String content) {
        this.content = content;
    }

}
