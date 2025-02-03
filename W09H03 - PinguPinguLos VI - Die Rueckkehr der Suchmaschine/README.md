#  **W09H03 - PinguPinguLos VI - Die Rueckkehr der Suchmaschine**


### Aufgabenstellung

### Ziel

Mithilfe der Links, die in der letzten Woche unserer Suchmaschine hinzugefügt wurden, können wir nun den PageRank-Wert jedes Dokumentes bestimmen. Dies ist Maß dafür, wie wichtig oder relevant ein Dokument ist. Kurz gesagt: Je mehr eingehende Links ein Dokument besitzt, desto interessanter scheint es wohl zu sein. Dementsprechend soll ein solches Dokument auch einen hohen PageRank-Wert erhalten.

Des weiteren werden wir unsere DocumentCollection mit einem Beispiel-Internet füllen. Um dieses lokal einzusehen, könnt ihr über den Befehl docker run --rm -d -p 127.0.0.1:8000:80 -v /usr/share/man:/usr/share/man:ro gray542/man2html einen Docker Container starten. Unter localhost:8000 findet ihr dann das "Internet".

### 1. The Page Rank
   Der Page Rank ist der letzte fehlenden Teil, um eine funktionsfähige Suchmaschine zu erhalten. Je mehr eingehende Links ein Dokument besitzt, desto wichtiger ist es für uns, da viele Dokumente darauf weisen. Diese Dokumente sollen einen hohen PageRank-Wert bekommen. Den Wert selbst berechnen wir rekursiv mit folgender Formel:
   Der Startwert für den PageRank-Wert jedes Dokumentes (Rekursionstiefe 0) ist
   P
   R
   i
   ,
   0
   =
   1
   n
   PR
   i,0
   ​
   =
   n
   1
   ​
   . Falls ein Dokument keine ausgehenden Links besitzt, behandeln wir dieses Dokument so, als hätte es zu jedem anderen Dokument einen ausgehenden Link. Am Ende der Aufgabe befindet sich ein ausführliches Beispiel inklusive Erklärung zur Berechnung des PageRank-Wertes.

1. Ergänze die Klasse DocumentListElement um ein privates Attribut double pageRank inklusive Getter und Setter.
2. Implementiere eine Methode double pageRankRec(DocumentListElement[] indices, int i, double d, int recDepth) in LinkedDocumentCollection. Diese Methode soll für das Dokument an Index i von indices den PageRank-Wert nach der gegebenen Formel rekursiv berechnen. Der übergebene Parameter indices enthält alle Listenelemente unserer Collection. Der Parameter d entspricht dem Dämpfungsfaktor und recDepth bestimmt die maximale Rekursionstiefe, also wie oft pageRankRec rekursiv aufgerufen werden soll, bevor abgebrochen wird. Bei einer Rekursionstiefe von 0 oder kleiner soll der Startwert unseres PageRank-Wertes zurückgegeben werden. Andernfalls wird immer der neue berechnete Wert zurückgegeben. Du kannst davon ausgehen, dass die Parameter indices, i und d immer gültig sind.
6 of 6 tests passing
3. Implementiere eine Methode double[] pageRank(double d, int recDepth) in LinkedDocumentCollection. Diese Methode soll für alle Dokumente in der Collection den PageRank-Wert berechnen und die Werte als Array zurückgeben. An Index 0 soll dabei der PageRank-Wert des Dokumentes mit der niedrigsten ID stehen und am höchsten Index der Wert des Dokumentes mit der höchsten ID. Du kannst davon ausgehen, dass d immer gültig ist. Stelle zudem sicher, dass das pageRank-Attribut von jedem Listenelement korrekt aktualisiert wurde.
8 of 8 tests passing
2. Die DocumentCollection füllen
   Nach der Aufgabenstellung findest du nähere informationen zu HTTP und HTML.

### 2.1. Eine Seite aus dem Netz holen
Um die Kommunikation mit dem Server, von dem wir die Seiten holen wollen, zu implementieren, wollen wir eine Anfrage an einen fremden Host sowie dessen Antwort je als eine eigene Klasse modellieren. Implementiere also die folgenden beiden Klassen:

#### HTTPRequest 3 of 3 tests passing
Diese Klasse soll eine Anfrage an einen Host repräsentieren. Da wir nur Seiten holen werden und keine Daten an Hosts verschicken wollen, wird HTTPRequest stets einen GET-Request darstellen. Gib der Klasse ein Attribut host für die Domain sowie ein Attribut path für den Pfad, auf den zugegriffen werden soll. Für beide Attribute soll es Getter geben. Füge außerdem einen Konstruktor hinzu, der diese beiden Werte entgegennimmt und in die entsprechenden Attribute schreibt.
Zudem soll die Klasse HTTPRequest noch eine Methode HTTPResponse send(int port) enthalten. Diese soll den durch das Objekt this repräsentierten GET-Request über einen neu erstellten Socket absenden und aus der erhaltenen Antwort ein neues HTTPResponse-Objekt kreieren und dieses zurückgeben. Achte darauf, dass Requests mit einer Leerzeile enden müssen (es sei denn, sie haben noch einen Body, was unsere Requests allerdings nicht haben).

Hinweis: Beachte nochmals, dass für das Lösen dieser Aufgabe nur Klassen aus den Packages java.lang, java.util, java.util.stream, java.io, im JDK mitgelieferte Exception-Klassen und die Socket-Klasse aus dem Package javax.net.Socket verwendet werden dürfen. Insbesondere darfst du nicht die Klassen aus java.net.http verwenden.

#### HTTPResponse 3 of 3 tests passing
Diese Klasse soll eine Antwort eines Hosts auf eine HTTPRequest darstellen. Dazu soll sie sich in einem Attribut status den Status der Response (siehe das im Template mitgelieferte Enum HTTPStatus) und in einem weiteren Attribut html den "rohen" HTML-Code, der mit der Response geliefert wurde, als String abspeichern. Zu beiden Attributen soll es Getter geben, der Konstruktor soll direkt den Antworttext auf ein GET-Request (also mit den Headern) entgegennehmen und daraus die Werte der eigenen Attribute berechnen.
Du kannst für das HTML-Tag (und nur dieses!) davon ausgehen, dass es in der gesamten Antwort den Text "<html>" (oder "<html " + einige Attribute + ">") und den Text "</html>" je nur einmal gibt und nie innerhalb eines Strings. Dir wird also in unseren Tests kein HTML-Code wie

vorgesetzt, bei dem ein "Fake-Tag" in einem String versteckt ist. Achte allerdings darauf, dass das Tag auch in Großbuchstaben ("<HTML></HTML>") ankommen kann und in diesem Falle genauso erkannt werden soll.

### 2.2 Den erhaltenen HTML-Code verarbeiten
Du darfst davon ausgehen, dass sämtlicher in dieser Aufgabe übergebener HTML-Code wohlgeformt ist und keine Kommentare enthält. Genauso darfst du von den Sachen ausgehen, die dir weiter oben für unseren HTML-Code garantiert wurden. Den Methoden, die List<HTMLToken>-Objekte entgegennehmen (HTMLToken wirst du gleich selbst implementieren), werden wir in den Tests nur solche Token-Listen übergeben, die entweder wohlgeformten HTML-Code repräsentieren oder eine Teilliste einer Liste sind, die wohlgeformten HTML-Code repräsentiert.

### HTMLToken 3 of 3 tests passing
Um den vorliegenden "rohen" HTML-Code besser untersuchen zu können, wollen wir ihn erst einmal in logische Einheiten unterteilen. Dafür wählen wir je ein Tag (egal, ob Starttag oder Endtag) als eine logische Einheit und ein Stück Fließtext zwischen zwei (nicht notwendigerweise gleichen) Tags als eine weitere logische Einheit. Der HTML-Code

Genau eine solche logische Einheit soll von der Klasse HTMLToken repräsentiert werden. Sie soll sich die Art des Tokens tokenType und dessen Inhalt content merken. Ersteres soll über ein (in HTMLToken intern definiertes) Enum TokenType geschehen, das die Werte TAG und TEXT annehmen kann. tokenType soll von diesem Enum-Typen sein. Letzteres soll durch einen StringBuilder dargestellt werden, sodass es leicht erweiterbar ist.

Die Klasse HTMLToken soll außerdem folgende fünf Methoden enthalten:

- Konstruktor: Nimmt nur den tokenType entgegen. Der content ist beim Erstellen eines HTMLToken-Objektes noch leer.
- Getter für tokenType
- String getContentAsString(): Gibt das Attribut content zurück, aber als String
- void addCharacter(char c): Fügt den übergebenen Character hinten an den bisherigen content an
- String toString(): Gibt "Tag:⎵<content als String>" zurück, falls this einen Tag darstellt, "Text:⎵<content als String>", falls es einen Text darstellt.

### HTMLProcessing
In der Klasse HTMLProcessing sind die Köpfe von vier statischen Methoden zum Verarbeiten des in der Response mitgesendeten HTML-Codes enthalten. Vervollständige die Rümpfe, sodass die Methoden das hier definierte Verhalten an den Tag legen.

#### tokenize 2 of 2 tests passing
Die Methode List<HTMLToken> tokenize(String) soll den übergebenen String gemäß den oben beschriebenen Regeln in eine ArrayList von HTMLToken-Objekten umwandeln. Bei Tags sollen, wie oben gezeigt, die spitzen Klammern nicht mit in den content des Tokens aufgenommen werden, alles andere schon. Achte beim Einlesen eines Tags auf Strings, sowohl solche, die mit " beginnen und enden, als auch solche, die mit ' beginnen und enden. Wenn man sich innerhalb eines Strings befindet und auf eine spitze Klammer zu trifft, ist das nicht das Ende des Tags.
Außerdem solltest du Tag-Namen wie "A", "HTML" und "BODY", Attribut-Bezeichner wie "HREF" und Text auch vollständig in Kleinbuchstaben umwandeln, sodass man später leichter danach suchen kann. Wandle allerdings nicht den gesamten HTML-String in Kleinbuchstaben um, da er auch Komponenten (für uns sind das im Speziellen Links) enthält, bei denen Groß-/Kleinschreibung wichtig ist.
Hier noch ein Tipp zum Lösen der Aufgabe: Man muss den HTML-String nur einmal von vorne bis hinten durchgehen und ihn dabei Buchstabe für Buchstabe ansehen, um ihn in die Token-Liste umzuwandeln. Dabei sollte man sich immer merken, in welchem Zustand man gerade ist: Bin ich in einem Tag? Bin ich in einem String, der mit ' begonnen wurde (also auch damit aufhören muss)? usw... Abhängig von einerseits dem Zustand und andererseits dem aktuell gelesenen Zeichen ergibt sich dann, was man tun muss und ob/wie der Zustand zu ändern ist. (Das ist nur ein Tipp! Du darfst die Aufgabe natürlich lösen, wie du willst.)

#### filterLinks 2 of 2 tests passing
Die Methode String[] filterLinks(List<HTMLToken>, String) nimmt eine Liste an HTMLTokens und den Namen eines Hosts entgegen. Die Liste an Tokens beschreibt dabei eine HTML-Seite, der Host ist derjenige, von dem die HTML-Seite erhalten wurde. Wenn also von "http://man1.pgdp.sse.in.tum.de/rstart.1.html" die Seite
geholt wurde, wird filterLinks() mit der Liste [Tag: html, Tag: head, Tag: title, Text: man page of rstart, ..., Tag: /html] (wobei die HTMLToken-Objekte hier natürlich durch den Rückgabewert ihrer toString()-Methode repräsentiert werden) und dem String "man1.pgdp.sse.in.tum.de“ aufgerufen (vorausgesetzt natürlich tokenize() funktioniert wie zuvor beschrieben).

Die Methode soll nun ein Array mit den Adressen aller Seiten (ungleich der aktuellen Seite) erstellen, auf die die aktuelle (durch die List<HTMLToken> beschriebene) Seite verlinkt (Auf Selbst-Links wie 127.0.0.1 wird nicht getestet). Die Adresse beinhaltet dabei sowohl Host als auch Pfad, nicht aber das Protokoll. Dabei werden dir in unseren Seiten folgende Typen von Links begegnen:

Das href-Attribut des ersten der drei dargestellten Links verlinkt auf einen anderen Host ("pgdp.sse.in.tum.de" statt "man1.pgdp.sse.in.tum.de"). Die Adressen solcher Links sind stets vollständig mitsamt Protokoll (immer "http://") angegeben. Das Protokoll soll, wie eben gesagt, nicht mit in den Ausgabe-String, alles andere schon.
Der zweite Link hat gar kein href-Attribut, sondern verlinkt auf einen anderen Abschnitt auf derselben Seite. Solche Links sollen ignoriert werden.
Das href-Attribut des dritten Links verlinkt auf eine andere Seite auf demselben Host. Bei solchen Links ist nur der Pfad angegeben, nicht Host und Protokoll. Ersterer soll im Ausgabe-String ergänzt werden, das Protokoll aber nicht.
In dem obigen Beispiel sollte also das Array ["pgdp.sse.in.tum.de/index.html“, "man1.pgdp.sse.in.tum.de/cgi-bin/man/man2html“] zurückgegeben werden (plus eventuelle weitere Links, die in den ... stehen könnten).

Du kannst davon ausgehen, dass alle Links, mit denen wir testen, in einer dieser drei Formen ist. Mit allen anderen Links (z.B.)< a href="#index">) darfst du umgehen, wie du willst/wie es dir sinnvoll erscheint. Die Links sollen in der gleichen Reihenfolge im zurückgegebenen Array stehen, in der sie auch in der Seite auftauchen. Du musst dir keine Gedanken über mehrfach auftauchende Adressen machen. Das werden wir nicht testen.

Sowohl "a" als auch "href" können im vom Server heruntergeladenen HTML sowohl als Kleinbuchstaben als auch als Großbuchstaben auftauchen. Da du aber bei der Implementierung von tokenize() bereits darauf geachtet hast, kannst du jetzt davon ausgehen, dass Tag- und Attribut-Namen nur in Kleinbuchstaben vorliegen. Das Gleiche gilt auch für die nächsten beiden Methoden.

Hinweis: Die Methode filterLinks() soll mit Streams und Lambdas implementiert werden. Insbesondere gibt es auf die Lösung dieser Aufgabe nur dann Punkte, wenn keine Kontrollstruktur verwendet wurde. Die Schlüsselwörter if, else, for, do, while, switch, case, break und continue sind innerhalb von filterLinks() verboten! Stattdessen soll die Token-Liste sofort in einen Stream umgewandelt und mit Stream-Methoden wie map(), filter() und reduce() weiterverarbeitet werden (es müssen nicht genau diese verwendet werden, das sollen nur Beispiele/Hinweise sein!). Des Weiteren ist auch der ternäre Operator erlaubt. Die Methode muss von folgender Form sein:

Du darfst außerdem keine eigenen Hilfsmethoden aufrufen/referenzieren, sondern nur die fünf von der Aufgabenstellung für HTMLToken geforderten Methoden, sowie die Methoden von Klassen des Packages java.lang, die Methode java.util.Arrays.stream() und natürlich die Methoden von java.util.stream.Stream. Alle anderen Methoden sind verboten. Du darfst auch keine nicht-final statischen Variablen und keine Instanzvariablen (direkt - mittels einer der erlaubten Methoden geht natürlich in Ordnung) referenzieren. static final-Variablen, also Konstanten, dürfen referenziert werden, um Code Issues zu vermeiden. Auf die Zustände eines Enums darf auch direkt zugegriffen werden. Die zählen als static final.

### filterText 2 of 2 tests passing
Die Methode String filterText(List<HTMLToken>) soll ähnlich filterLinks() die übergebene Token-Liste (die eine HTML-Seite darstellt) untersuchen und Informationen daraus sammeln. Nur soll diesmal der gesamte Text (also alles, was im ursprünglichen HTML-Code nicht innerhalb eines Tags stand) zu einem langen String konkateniert zurückgegeben werden. Füge zudem zwischen zwei durch Tags zuvor getrennte Textstellen stets genau ein Leerzeichen ein. Aus dem 

soll ein Dokument generiert werden, in dem man nach den Wörtern "kühl" und "schrank", nicht aber dem Wort "kühlschrank" suchen kann. Der Output von filterText() sollte hierfür also in⎵sibirien⎵ist⎵es⎵recht⎵kühl⎵schrank⎵und⎵schreibtisch⎵stehen⎵im⎵zimmer generieren.

Hinweis: Auch die Methode filterText() soll mit Streams und Lambdas implementiert werden. Insbesondere gibt es auf die Lösung dieser Aufgabe nur dann Punkte, wenn keine Kontrollstruktur verwendet wurde. Die Schlüsselwörter if, else, for, do, while, switch, case, break und continue sind innerhalb von filterText() verboten! Stattdessen soll die Token-Liste sofort in einen Stream umgewandelt und mit Stream-Methoden wie map(), filter() und reduce() weiterverarbeitet werden (es müssen nicht genau diese verwendet werden, das sollen nur Beispiele/Hinweise sein!). Des Weiteren ist auch der ternäre Operator erlaubt. Die Methode muss von folgender Form sein:

Du darfst außerdem keine eigenen Hilfsmethoden aufrufen/referenzieren, sondern nur die fünf von der Aufgabenstellung für HTMLToken geforderten Methoden, sowie die Methoden von Klassen des Packages java.lang, die Methode java.util.Arrays.stream() und natürlich die Methoden von java.util.stream.Stream. Alle anderen Methoden sind verboten. Du darfst auch keine nicht-final statischen Variablen und keine Instanzvariablen (direkt - mittels einer der erlaubten Methoden geht natürlich in Ordnung) referenzieren. static final-Variablen, also Konstanten, dürfen referenziert werden, um Code Issues zu vermeiden. Auf die Zustände eines Enums darf auch direkt zugegriffen werden. Die zählen als static final.

### filterTitle 2 of 2 tests passing

Die Methode String filterTitle(List<HTMLToken>) soll aus der übergebenen Token-Liste den Titel eines Dokuments extrahieren. Dieser ist stets der Text, der sich innerhalb des title-Elements befindet, welches Kind des <head>-Elements ist, welches wiederum direktes Kind des <html>-Elements (also des Elements auf oberster Ebene ist). Eine typische HTML-Seite mit Titel sieht folgendermaßen aus:

Der Text innerhalb des title-Elements soll von der Methode extrahiert werden. Du kannst davon ausgehen, dass es immer nur ein title-Element gibt, dieses sich immer direkt innerhalb des ebenfalls nur einmal vorkommenden <head>-Elements befindet, welches wiederum direktes Kind von dem obersten Element (<html>) ist.

Hinweis: filterTitle() muss nicht mit Streams und Lambdas implementiert werden. Hier sind wieder alle Schlüsselwörter erlaubt. Du darfst gerne auch diese Methode mit Streams implementieren, sie bietet sich aber dafür bei Weitem nicht so gut an, wie die beiden vorherigen.

## 2.3. Die LinkedDocumentCollection mit Seiten aus dem Netz füllen
Nachdem wir nun Code haben, der uns Seiten als HTML aus dem Netz holt und Code, der aus dem erhaltenen HTML Links, Inhalt und Titel extrahiert, können wir uns nun daran machen, für die geholten Seiten je ein neues LinkedDocument zu erstellen und in unsere LinkedDocumentCollection einzufügen.

###  addToResultCollection updaten 2 of 2 tests passing
Gehe als erstes in die LinkedDocumentCollection und ändere die Methode addToResultCollection(AbstractLinkedDocument) so ab, dass sie zusätzlich noch die Outgoing-Links des übergebenen Dokuments als Parameter entgegennimmt. Die Signatur sollte also zu addToResultCollection(AbstractLinkedDocument, String[]) geändert werden. Im Methodenrumpf sollen nun beim Updaten der Incoming-Links diese nicht länger aus dem content des übergebenen AbstractLinkedDocument ausgelesen werden (das war nur eine Zwischenlösung für die letzten Wochen), sondern es sollen die übergebenen Outgoing-Links verwendet werden, um zu bestimmen, für welche Dokumente das übergebene AbstractLinkedDocument zu den Incoming-Links hinzugefügt werden muss bzw. welche Dummies neu erstellt werden müssen.
### Eine Seite Crawlen 2 of 3 tests passing
In der Klasse PageCrawling im networking-Package findest du eine (noch leere) Methode crawlPage(LinkedDocumentCollection, String). Diese soll die Seite an der übergebenen Adresse (im Format Host/Pfad, z.B. "man7.pgdp.sse.in.tum.de/iso_8859_1.7.html") holen, daraus mithilfe der zuvor in HTMLProcessing implementierten Methoden ein LinkedDocument-Objekt erstellen und dieses in die LinkedDocumentCollection einfügen. Die Konstruktorparameter description, releaseDate, author und collectionSize von LinkedDocument dürfen beliebig gesetzt werden, wobei letzeres logischerweise größer null sein sollte. Die Links sollen alle korrekt in die Collection übertragen werden. Achte also darauf, die richtige Methode zum Einfügen in die Collection zu verwenden und ihr die richtigen Parameter zu übergeben.
- Beim Holen der Seite von der jeweiligen Adresse solltest du die Nachricht an Port 8000 senden.
- Wenn der Zugriff auf die übergebene Adresse nicht möglich war (also ein Statuscode ungleich 200 zurückkam) soll nichts in die Collection eingefügt werden und false zurückgegeben werden. Wenn der Zugriff erfolgreich war, soll true zurückgegeben werden.
- Eine noch nicht gecrawlte Adresse aus der LinkedDocumentCollection holen 0 of 1 tests passing
- Jetzt können wir eine einzelne Seite für eine vorgegebene Adresse holen. Wir wollen aber viele Seiten aus dem Netz holen und in unser Page Repository eintragen können. Wir könnten einfach für jede geholte Seite deren Outgoing-Links direkt verwenden, um neue Seiten zu finden. Allerdings führt das zu dem Problem, dass Seiten einander im Kreis verlinken können und man dann ewig lange die gleichen Seiten durchsucht. Um das zu verhindern, wollen wir in der LinkedDocumentCollection nachsehen, um nur solche Seiten zu holen, die dort noch nicht eingetragen sind. Glücklicherweise bietet uns diese dafür genau die richtige Struktur. Wir haben LinkedDocuments in ihr, die Seiten repräsentieren, die bereits gecrawlt wurden und DummyLinkedDocuments für Seiten, von deren Existenz wir wissen bzw. deren Adresse wir kennen, deren Inhalt wird aber noch nicht heruntergeladen haben.

Implementiere also in LinkedDocumentCollection eine neue Methode String getNextUncrawledAddress(), die die Collection nach einem AbstractLinkedDocument durchsucht, das noch nicht gecrawlt wurde. Welches derartige AbstractLinkedDocument hier genau zurückgegeben wird, spielt keine Rolle, solange es noch nicht gecrawlt wurde und in der Collection ist. Die Adresse dieses Dokuments soll zurückgegeben werden. Wenn bereits alle Dokumente in der Collection gecrawlt wurden, soll null zurückgegeben werden.
### Viele Seiten Crawlen 1 of 1 tests passing
Implementiere nun die Methoden void crawlPages(LinkedDocumentCollection collection, int number, String startingAddress) und void crawlPages(LinkedDocumentCollection collection, int number) in der Klasse PageCrawling. Erstere soll number Seiten crawlen und in die collection einfügen. Die erste gecrawlte Seite soll dabei die in startingAddress sein. Alle weiteren sollen mittels getNextUncrawledAddress() ermittelt werden. Wenn eine der dabei gecrawlten Adressen nicht erreichbar ist (also von crawlPage() false zurückgegeben wurde), soll das entsprechende Dummy-Dokument aus der Collection entfernt werden. Diese Adresse zählt dann nicht zu der number der zu crawlenden Seiten dazu. Zweitere Methode soll das gleiche tun, nur dass die erste gecrawlte Seite ebenfalls mittels getNextUncrawledAddress() ermittelt werden soll.

