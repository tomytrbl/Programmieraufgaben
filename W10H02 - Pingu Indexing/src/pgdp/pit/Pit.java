package pgdp.pit;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Pit {

    final static String INDEX_FILE = "pit_index.json";

    final static String PIT_DIR = ".pit";

    static Path init(Path rootDirectory) {
        try {
            Files.createFile(Files.createDirectory(rootDirectory.resolve(PIT_DIR)).resolve(INDEX_FILE));
            return rootDirectory;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void addFile(Path rootDirectory, Path file) {
        List<String> l = null;
        try {
            l = Files.readAllLines(file);
            String contentFile = String.join("\n", l);
            HistoryEntry toAdd = new HistoryEntry(rootDirectory.resolve(PIT_DIR), contentFile);


            Path pathTojsonObject = rootDirectory.resolve(PIT_DIR).resolve(INDEX_FILE);
            List<String> lines = Files.readAllLines(pathTojsonObject);
            String content = String.join("\n", lines);
            JSONObject jsonObject = new JSONObject(content);

            int HashCode = contentFile.hashCode();
            String hexString = Integer.toHexString(HashCode);
            jsonObject.put(rootDirectory.relativize(file).toString(), hexString);


            BufferedWriter br = new BufferedWriter(new FileWriter(pathTojsonObject.toString(), false));
            br.write(jsonObject.toString());
            br.close();

            HistoryEntryRepository.store(rootDirectory.resolve(PIT_DIR), toAdd);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void commitFile(Path rootDirectory, Path file) {
        List<String> l = null;
        try {
            l = Files.readAllLines(file);
            l.remove(0);
            String contentFile = String.join("\n", l);
            HistoryEntry toAdd = new HistoryEntry(rootDirectory.resolve(PIT_DIR), Files.readString(file));

            Path pathTojsonObject = rootDirectory.resolve(PIT_DIR).resolve(INDEX_FILE);
            List<String> lines = Files.readAllLines(pathTojsonObject);
            String content = String.join("\n", lines);
            JSONObject jsonObject = new JSONObject(content);


            String relPath = rootDirectory.relativize(file).toString();
            String storedHash = jsonObject.getString(relPath);
            HistoryEntry old = HistoryEntryRepository.load(rootDirectory.resolve(PIT_DIR), Integer.parseInt(storedHash, 16));

            int HashCode = contentFile.hashCode();
            String hexString = Integer.toHexString(HashCode);

            if (old != null) {
                old.setNextHash(toAdd.getContent().hashCode());
                toAdd.setPrevHash(old.getContent().hashCode());
            }

            HistoryEntryRepository.store(rootDirectory.resolve(PIT_DIR), toAdd);
            if (old != null) {
                HistoryEntryRepository.store(rootDirectory.resolve(PIT_DIR), old);
            }

            jsonObject.put(relPath, Integer.toHexString(toAdd.getContent().hashCode()));
            BufferedWriter br = new BufferedWriter(new FileWriter(pathTojsonObject.toString(), false));
            br.write(jsonObject.toString());
            br.close();

        } catch (IOException e) {
            return;
        }
    }


    static Stream<Path> listNewFiles(Path rootDirectory) {

        Path pathTojsonObject = rootDirectory.resolve(PIT_DIR).resolve(INDEX_FILE);

        try {
            List<String> lines = Files.readAllLines(pathTojsonObject);
            String content = String.join("\n", lines);
            JSONObject jsonObject = new JSONObject(content);
            return Files.walk(rootDirectory).filter(Files::isRegularFile)
                    .filter(path -> !path.toString().contains(".pit"))
                    .filter(path -> {
                        String relativePath = rootDirectory.relativize(path).toString();
                        return !jsonObject.has(relativePath);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static Stream<Path> listChangedFiles(Path rootDirectory) {

        Path pathTojsonObject = rootDirectory.resolve(PIT_DIR).resolve(INDEX_FILE);

        try {
            List<String> lines = Files.readAllLines(pathTojsonObject);
            String content = String.join("\n", lines);
            JSONObject jsonObject = new JSONObject(content);

            return Files
                    .walk(rootDirectory)
                    .filter(path -> Files.isRegularFile(path))
                    .filter(path -> jsonObject.has(rootDirectory.relativize(path).toString()))
                    .filter(path -> {
                        List<String> l = null;
                        try {
                            l = Files.readAllLines(path);
                            String filecontent = String.join("\n", l);
                            int HashCode = filecontent.hashCode();
                            String HexString = Integer.toHexString(HashCode);

                            String storedHash = jsonObject.getString(rootDirectory.relativize(path).toString());
                            return !storedHash.equals(HexString);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
