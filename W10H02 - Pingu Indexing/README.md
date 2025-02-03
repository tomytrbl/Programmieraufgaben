#  **W10H02 - Pingu Indexing**


### Aufgabenstellung: Pingu Indexing Tool (PIT)

### Overview
Ziel der Aufgabe ist es, die verschiedenen Interaktionen mit dem Dateisystem kennen zu lernen.

#### Story
Die Pinguine der PUM haben festgestellt, dass es sehr schwierig ist, verschiedene Versionen ihrer Programme zu speichern. Sie möchten, dass du ihnen hilfst, ein System zu entwickeln, um mehrere Versionen einer Datei zu speichern.

### HistoryEntry 1 of 1 tests passing
Die Klasse HistoryEntry enthält Felder für Hashes von vorhergegangenen (prevHash) und nachfolgenden (nextHash) HistoryEntries. Diese können null sein, falls keine solche Dokumente existieren.

Das Feld content enthält den Dateiinhalt der Version, die der HistoryEntry beschreiben soll. Das pitDirectory ist das Verzeichnis in welchem sich alle PIT Dokumente (also nicht die des Nutzers) befinden.

Implementiere Getter und Setter für alle gegebenen Felder. Implementiere zudem Getter für einen nächsten (getNext()) bzw. vorhergegangenen (getPrev()) HistoryEntry (s. HistoryEntryRepository). Hint: Wenn der Test mit "not yet implemented" failen sollte - schaut euch load an

### Das Interface
Wir werden nun einen Blick auf das Interface werfen, über welches wir mit dem PIT interagieren. Um die verschieden Versionen persistent speichern zu können, müssen wir sie selbst in irgendeiner Form im Dateisystem speichern. Wir verwenden dafür folgende Dateistruktur:

rootDir

|-.pit

|-pit_index.json


Das Verzeichnis rootDir ist das Verzeichnis, dessen Inhalt wir überwachen wollen. .pit ist das Verzeichnis in dem wir alle für uns relevanten Daten speichern. In der pit_index.json speichern wir eine Referenz auf die neuste uns bekannte Version eines Dokumentes.

Da wir hier mit einer JSON Datei arbeiten werden, empfiehlt sich ein Blick auf die Dokumentation von org.json.

#### Initialisieren 1 of 1 tests passing
Wenn ein Nutzer die Änderungen in einem Verzeichnis überwachen möchte, ruft er die Funktion init(Path) mit dem entsprechenden Verzeichnis als Parameter auf.

Ergänze die Funktion, sodass die oben beschriebene Verzeichnisstruktur erstellt wird. Bedenke, dass pit_index.json immer eine valide JSON Datei sein sollte.

#### Neue Dateien 3 of 3 tests passing
Da man nicht immer einen Überblick hat, welche Dateien schon dem PIT hinzugefügt wurden, oder aktuell sind, möchten wir dem Nutzer dabei helfen.

Die Funktion listNewFiles(Path) bekommt das oberste Verzeichnis übergeben und gibt einen Stream an Paths zurück. Eine Datei ist neu, wenn sie nicht in der pit_index.json als Schlüssel vorkommt. Die Dateien des .pit Verzeichnisses und die Verzeichnisse im Allgemeinen sollen in diesem Stream nicht vorkommen.

Um die so gefundenen Dateien hinzuzufügen,stellen wir die Methode addFile(Path, Path) zur Verfügung. Diese nimmt das oberste Verzeichnis, sowie einen Pfad zur entsprechenden Datei entgegen. Für die Datei wird ein neuer HistoryEntry mit entsprechendem Inhalt erstellt und abgespeichert (s. HistoryEntryRepository). Der pit_index.json Datei wird ein neuer Eintrag hinzugefügt. Dieser enthält den Dateipfad der Datei relativ zum obersten Verzeichnis, wir betrachten den Pfad als Schlüssel und den hashCode() des Dateiinhalts als Hexstring als Wert.

#### Geänderte Dateien 3 of 3 tests passing
Die Funktion listChangedFiles(Path) funktioniert analog zur Funktion listNewFiles(Path). Hier sollen jedoch nur Dateien zurückgegeben werden, welche bekannt sind aber verändert wurden. Eine Datei gilt als verändert, wenn der hashCode() des Inhalts nicht mit dem gespeicherten übereinstimmt.

Um die Änderungen einer Datei auch in das PIT zu übernehmen, gibt es die Funktion commitFile(Path, Path). Diese nimmt wie auch addFile das oberste Verzeichnis und den Pfad zur Datei als Paramteter entgegen. Es soll ein neuer HistoryEntry für die aktuelle Version der Datei erstellt werden. Sowohl der neue Eintrag, als auch der Eintrag der zuletzt bekannten Version werden um Referenzen aufeinander ergänzt und abgespeichert. Zu guter Letzt, wird der Hash in pit_index.json auf den des neuen Eintrags aktualisiert.

### Einträge persistent machen
Da die Versionierung des PIT System Neustarts überstehen können soll, müssen alle Daten im Dateisystem abgespeichert werden. Dafür haben wir mit der init Funktion bereits die Grundlagen gelegt. Nun benötigen wir einen Weg HistoryEntrys systematisch im Dateisystem abzulegen.

#### Einträge speichern 1 of 1 tests passing
Die Funktion store(Path, HistoryEntry) nimmt den Pfad des .pit Verzeichnises und den zu speichernden HistoryEntry entgegen, um ihn zu speichern. Dazu wandeln wir zu allererst den hashCode() descontents in einen Hexstring um. Nun wird der Hexstring um eine führende Null erweitert, falls er eine ungerade Länge hat, und in 2er Blocks aufgeteilt. Der Pfad zu unserer Datei wird nun aus den Blöcken konstruiert, in dem man sie als Verzeichnisse rückwärts aneinanderhängt. Dabei ist der jetzt letzte Block der Dateiname.

Beispiel
Das Dateiformat ist recht einfach. In der ersten Zeile sollen die Hashes vorhergehender oder nachfolgender Einträge stehen. Dabei steht der Hash eines Vorgängers, wenn es keinen Nachfolger gibt als Hexstring in der ersten Zeile. Falls es einen Nachfolger gibt, wird dieser(auch als Hexstring) mittels eines Doppelpunktes : vom Vorgänger getrennt. Sollte es einen Nachfolger aber keinen Vorgänger geben, ist der Hexstring des Vorgängers "".

Anschließend kommt der Inhalt des Eintrags ohne weitere Zeichen.

prevHash:nextHash
content
#### Einträge lesen 1 of 1 tests passing
Die Funktion load(Path, int) lädt die wie oben gespeicherten Einträge wieder aus dem Dateisystem. Dafür bekommt sie den Pfad zum .pit Verzeichnis und den Hash des zu ladenden HistoryEntrys. Entsprechend der oben beschrieben Methode wird zuerst der Dateipfad des Eintrags aus dem Hash konstruiert. Anschließend kann der Eintrag entsprechend des beschriebenen Dateiformats rekonstruiert werden.