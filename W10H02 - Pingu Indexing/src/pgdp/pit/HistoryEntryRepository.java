package pgdp.pit;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pgdp.pit.Pit.PIT_DIR;
import static pgdp.pit.Pit.init;

public class HistoryEntryRepository {

    public static HistoryEntry load(Path pitDirectory, int hash) throws IOException {
        if (hash == 0) {
            return new HistoryEntry(pitDirectory, "");
        }
        String s = Integer.toHexString(hash);
        if (s.length() % 2 == 1) {
            s = '0' + s;
        }

        String finalS = s;
        List<String> sArray = new java.util.ArrayList<>(IntStream.range(0, s.length()).filter(i -> i % 2 == 0).mapToObj(i -> finalS.substring(i, i + 2)).toList());
        if (sArray.isEmpty()) {
            return null;
        }
        Collections.reverse(sArray);

        String[] parts = sArray.toArray(new String[0]);
        Path currentpath = pitDirectory;
        for (int i = 0; i < parts.length - 1; i++) {
            currentpath = currentpath.resolve(parts[i]);
        }
        Path filepath = currentpath.resolve(parts[parts.length - 1]);
        if (!Files.exists(filepath)) {
            return null;
        }

        List<String> HEDatei = Files.readAllLines(filepath);
        if (HEDatei.isEmpty()) return null;

        List<String> content = new ArrayList<>(HEDatei);
        content.remove(0);
        String cont = String.join("\n", content);

        if (cont.isEmpty()) return null;


        HistoryEntry toLoad = new HistoryEntry(pitDirectory, cont);


        if (HEDatei.get(0).contains(":")) {
            String[] firstline = HEDatei.get(0).split(":");
            toLoad.setNextHash(Integer.parseInt(firstline[1], 16));
            if (!firstline[0].isEmpty()) {
                toLoad.setPrevHash(Integer.parseInt(firstline[0], 16));
            }
        } else if (!HEDatei.get(0).isEmpty()) {
            toLoad.setPrevHash(Integer.parseInt(HEDatei.get(0), 16));
        }
        return toLoad;
    }


    public static void store(Path pitFolder, HistoryEntry entry) throws IOException {
        int hashc = entry.getContent().hashCode();
        String s = Integer.toHexString(hashc);
        if (s.length() % 2 == 1) {
            s = '0' + s;
        }

        String finalS = s;
        List<String> sArray = new java.util.ArrayList<>(IntStream.range(0, s.length()).filter(i -> i % 2 == 0).mapToObj(i -> finalS.substring(i, i + 2)).toList());
        if (sArray.isEmpty()) {
            return;
        }
        Collections.reverse(sArray);

        String[] parts = sArray.toArray(new String[0]);
        Path currentpath = pitFolder;
        for (int i = 0; i < parts.length - 1; i++) {
            currentpath = currentpath.resolve(parts[i]);
        }
        Path filepath = currentpath.resolve(parts[parts.length - 1]);
        Files.createDirectories(currentpath);
        if (!Files.exists(filepath)) {
            Files.createFile(filepath);
        }

        StringBuilder fileContent = new StringBuilder();

        if (entry.getPrevHash() == null && entry.getNextHash() == null) {
            fileContent.append("\n").append(entry.getContent());
        } else if (entry.getPrevHash() == null) {
            fileContent.append(":").append(Integer.toHexString(entry.getNextHash())).append("\n");
            fileContent.append(entry.getContent());
        } else if (entry.getNextHash() == null) {
            fileContent.append(Integer.toHexString(entry.getPrevHash())).append("\n");
            fileContent.append(entry.getContent());
        } else {
            fileContent.append(Integer.toHexString(entry.getPrevHash()))
                    .append(":")
                    .append(Integer.toHexString(entry.getNextHash()))
                    .append("\n");
            fileContent.append(entry.getContent());
        }
        Files.writeString(filepath, fileContent.toString());
    }

    public static void main(String[] args) {
    }
}