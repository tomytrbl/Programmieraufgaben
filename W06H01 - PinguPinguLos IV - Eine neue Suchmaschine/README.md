#  **W06H01 - PinguPinguLos IV - Eine neue Suchmaschine**


### Aufgabenstellung

Wir können nach letzter Woche für zwei Dokumente ein Gleichheitsmaß berechnen. Für unsere Suchmaschine müssen wir dies jedoch für eine Vielzahl von Dokumenten erledigen. Wir brauchen also eine Datenstruktur, um auf alle uns zur Verfügung stehenden Dokumente zuzugreifen. Diese DocumentCollection wollen wir jetzt implementieren.

## Die Datenstrukturen
In der W06P02 hast du bereits einfach verkette Listen kennengelernt. Um bei diesen an ein Element zu kommen, muss jedoch im schlimmsten Fall über die gesamte Liste iteriert werden. Bei einem Array hingegen können wir direkt auf das Element zugreifen, wenn wir seinen Position im Array kennen. Jedoch können wir die Größe des Arrays im Nachhinein nicht mehr verändern und müssten immer ein neues Array erstellen. Um die Vorteile von beiden ausnutzen zu können, kombinieren wir beide Datenstrukturen.


Die Grundlage unserer Collection stellt ein Array dar, dessen Größe wir beim Erstellen der Collection festlegen. Dies ist links in der Abbildung dargestellt. Jedes Array-Element enthält eine Referenz auf eine Liste. Im Falle der Abbildung handelt es sich um eine einfach verkettete Liste (wie aus Aufgabe W06P02), die Strings speichert. Wir wollen jedoch statt einer einfach verketteten Liste eine doppelt verkettete Liste nutzen und statt Strings Documents abspeichern. Beim Einfügen eines Elementes (egal ob String oder Dokument) wird als erster Schritt diesem Objekt ein Index im Array zugewiesen und anschließend in dessen Liste eingefügt. Dadurch können wir beliebig viele Elemente hinzufügen, da sich unsere doppelt verketteten Listen dynamisch erweitern lassen.

Der Unterschied zwischen einer einfach und doppelt verketteten Liste ist, dass bei einer doppelt verketten Liste jedes Listenelement zusätzlich noch eine Referenz auf das vorherige Element besitzt.

An jedem Index des Arrays befindet sich damit eine Referenz auf eine doppelt verkette Liste, in welcher wir die Dokumente letztendlich einfügen. Unsere Datenstruktur ist damit ein Array von doppelt verketteten Listen.

## Die Aufgabe
#### 1. Die doppelt verkettete Liste 4 of 4 tests passing
Auch für eine doppelt verkettete Liste benötigen wir eine eigene Klasse DocumentListElement für jedes Element in der Liste:
- Füge die Attribute document, pre und next der Klasse DocumentListElement hinzu. document soll die Referenz auf das in diesem Listenelement gespeicherte Dokument beinhalten. Die Variable pre besitzt die Referenz auf das vorherige Listenelement oder null, falls keines existiert. Entsprechend hat next die Referenz auf das nächste Listenelement oder null.
- Erstelle einen Konstruktor DocumentListElement(Document), welcher nur das zu speichernde Dokument entgegen nimmt. Die anderen Variablen sollen mit dem Wert null initialisiert werden. Die Behandlung von null beim Parameter ist an dieser Stelle nicht erforderlich.
- Füge zuletzt noch den Getter Document getDocument() hinzu.
Du darfst weitere private Attribute, sowie öffentliche Hilfsmethoden und Konstruktoren hinzufügen, wenn du diese im Laufe der Aufgaben benötigst. Sofern du Hilfsmethoden nur in DocumentListElement verwendest, ist es sinnvoll, diese ebenfalls als private zu markieren.

Damit haben wir schon einmal unsere Listenelemente. Wir möchten jedoch auch noch eine übergeordnete Klasse Bucket haben, über welcher wir auf den Anfang oder das Ende der Liste zugreifen können:
Füge der Klasse Bucket die beiden Attribute head und tail hinzu. head soll dabei immer auf das erste Element in der Liste zeigen und tail auf das letzte. Wenn die Liste leer ist, sollen beide den Wert null besitzen.

#### 2. Die DocumentCollection 
2 of 2 tests passing 
Damit haben wir eine funktionsfähige doppelt verkettete Liste, in welche wir theoretisch Elemente einfügen können. Jedoch wollen wir nicht alle Elemente in eine Liste einfügen, sondern diese auf mehrere Listen verteilen. Dies soll in der Klasse DocumentCollection passieren:
- Erstelle in der Klasse DocumentCollection das Attribut Bucket[] buckets. Das Array buckets speichert sämtliche Referenzen auf unsere Listen und soll nach der Initialisierung nicht mehr vergrößert oder verkleinert werden.
- Implementiere einen Konstruktor, welcher die Anzahl an doppelt-verketteten Listen entgegennimmt, die entsprechende Anzahl an Listen erstellt und in buckets speichert. Sollte eine Anzahl übergeben werden, die kleiner oder gleich 0 ist, soll buckets genau eine Liste enthalten.
Der Strukturtest wird erst durchlaufen, sobald auch alle nachfolgenden Methoden implementiert wurden.

#### 3. Den Bucketindex berechnen
1 of 1 tests passing
Unsere Datenstruktur verhält sich ähnlich zur HashMap aus der Standardbibliothek. Damit wir unsere Elemente sinnvoll verteilen können, brauchen wir eine Hilfsfunktion, welche uns den Index für ein Dokument berechnet. Füge dafür eine private Methode int indexFunction(int) hinzu, die den Index im bucket-Array für ein Dokument mit der übergebenen ID nach folgender Funktion zurückliefert: index = documentID % numberOfBuckets.
#### 4. Einfügen von Elementen 

2 of 2 tests passing
Als erstes wollen wir Elemente einfügen können. Implementiere dafür eine Methode boolean add(Document). Wie oben beschrieben, müssen wir jedem Element eine doppelt verkettete Liste in unserem Array zuweisen. Mit der gerade implementierten Hilfsfunktion können wir das Dokument in die entsprechende Liste, die am berechneten Index in buckets steht, einfügen. Achte dabei darauf, dass nach dem Einfügen die IDs der Dokumente innerhalb der Liste vom head ausgehend aufsteigend sortiert sind. Stelle auch sicher, dass head und tail auf die richtigen Elemente zeigen. Falls ein Dokument mit der identischen ID bereits in der Collection vorhanden oder die ID des übergebenen Dokumentes negativ ist, soll nichts eingefügt werden. Gleiches gilt, wenn Document null ist. Zurückgegeben werden soll true genau dann, wenn Document in unsere Collection eingefügt wurde und ansonsten false.
Im Test DocumentCollection add() Basic ist auch ein Test dabei, welcher versucht 100.000 pseudo-zufällige Documents in die Collection einzufügen. Achte also darauf, dass deine Implementierung nicht allzu langsam ist.
#### 5. Eine weitere kleine Hilfsmethode 1 of 1 tests passing
Implementiere als nächstes in DocumentCollection die Methode boolean isEmpty(), welche genau dann true zurückgeben soll, wenn sich kein Dokument in unserer Collection befindet. Diese kannst du unter Umständen für die nachfolgenden Methoden gebrauchen.
#### 6. Finden von Elementen 2 of 2 tests passing
Wir wollen auch in der Lage sein, ein Dokument in der Collection mithilfe der ID zu finden. Dafür soll eine Methode Document find(int) hinzugefügt werden, welche das Dokument mit der übergebenen ID zurückliefert. Falls kein solches Dokument existiert, soll null zurückgegeben werden.
Entfernen von Elementen 4 of 4 tests passing
Zu den Grundoperationen auf eine Datenstruktur gehört auch das Löschen von Elementen. Dafür wollen wir unserem Kontext entsprechend vier Methoden in DocumentCollection implementieren:
- boolean removeDocument(int): Entfernt das Listenelement, welches ein Dokument gespeichert hat, dessen ID mit dem Parameter übereinstimmt. Gibt false zurück, wenn das Dokument nicht existiert.
- boolean removeDocumentsFromAuthor(Author): Entfernt alle Dokumente des übergebenen Autors. Gibt false zurück, wenn kein Dokument vom Autor in der Collection existiert.
- boolean removeAll(): Entfernt alle Dokumente aus unserer Collection. Gibt false zurück, wenn die Collection bereits leer ist.
- boolean removeOldDocuments(Date releaseDate, Date lastUpdated): Entfernt alle Dokumente, die vor dem übergebenen releaseDate veröffentlicht und zudem vor lastUpdated zuletzt aktualisiert wurden.
Falls der übergebene Parameter lastUpdated null ist, kann lastUpdateDate von Document ignoriert werden und ein Dokument soll nur gelöscht werden, wenn es vor releaseDate veröffentlicht wurde.
Falls der übergebene Parameter releaseDate null ist, kann das Veröffentlichungsdatum des Dokumentes ignoriert werden und es sollen alle Dokumente gelöscht werden, die vor lastUpdated zuletzt aktualisiert wurden.
Falls beide Parameter null sind, sollen alle Dokumente gelöscht werden. Wenn lastUpdated vor releaseDate liegt, soll nichts entfernt werden.
Der Rückgabewert der Methode ist genau dann true, wenn mindestens ein Dokument erfolgreich entfernt wurde.
#### 7. Und noch mehr kleine Methoden! 4 of 4 tests passing
Zuletzt sollen noch einige weitere, kleinere Methoden ebenfalls in DocumentCollection hinzugefügt werden:

- getHead(int bucketIndex): Gibt den head der doppelt verketteten Liste an Index bucketIndex oder null, wenn der übergebene Index ungültig ist, zurück.
- getTail(int bucketIndex): Gibt den tail der doppelt verketteten Liste an Index bucketIndex oder null, wenn der übergebene Index ungültig ist, zurück.
- contains(Document): Gibt true zurück, wenn ein Dokument mit der identischen ID in der Collection vorhanden ist.
- size(): Gibt die Anzahl an gespeicherten Elementen zurück.
Testen der DocumentCollection
In dieser Aufgabe sollst du lediglich Tests für DocumentCollection schreiben. Für den Konstruktor, add(Document), find(int) und alle vier remove()-Methoden sollst du mit deinen Tests jeweils einen allgemeinen Fall abdenken und durch einen zweiten Test einen beliebigen Sonderfall.

Es soll weiterhin über jedem Test ein Kommentar existieren, warum ihr den Konstruktor oder die Methode auf diese Art und Weise testet, also warum euer Test aussagekräftig ist, und bei den Sonderfällen noch zusätzlich, warum es sich bei den gewählten Testdaten um einen Sonderfall handelt. Die Kommentare dürfen wie immer auch auf Englisch sein!