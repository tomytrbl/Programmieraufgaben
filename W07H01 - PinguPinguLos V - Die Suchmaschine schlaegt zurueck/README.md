#  **W07H01 - PinguPinguLos V - Die Suchmaschine schlaegt zurueck**


### Aufgabenstellung: PinguPinguLos V - Die Suchmaschine schlägt zurück!

### Ziel

Diese Woche wollen wir zunächst ein verbessertes Ähnlichkeitsmaß implementieren. Durch unsere DocumentCollection haben wir einen Überblick über sämtliche zur Verfügung stehenden Dokumente und können damit auch ermitteln, in wie vielen Dokumenten ein konkretes Wort vorkommt. Wenn ein Wort in vielen Dokumenten vorkommt, ist es vermutlich nicht so ausschlaggebend, um die Ähnlichkeit zweier Texte zu beurteilen. Zusätzlich wollen wir Verweise auf andere Dokumente hinzufügen, um herausfinden zu können, zu welchen anderen Dokumenten man von einem Ausgangsdokument noch gelangen kann.

### 1. Die komplexe Vektorähnlichkeit
#### 1. Die neuen Attribute
   Zuerst müssen wir unsere vorhandenen Klassen, um zusätzliche Attribute erweitern, die in den nachfolgenden Aufgaben benötigt werden:

- Füge der Klasse WordCount die Attribute double weight und double normalizedWeight hinzu. Diese sollen dazu genutzt werden, gewisse Wörter als ausschlaggebender im Vergleich zu anderen Wörtern zu markieren. Bei einem Wert für count von größer 0 sollen die beiden Attribute im Konstruktor mit 1, ansonsten mit 0 initialisiert werden.
Testing Structure WordCount 2 of 2 tests passing
- Füge der Klasse DocumentListElement die Attribute WordCount[] wordCountArray und double similarity hinzu. Das erste Attribut soll immer das aktuelle WordCount-Array speichern und soll dementsprechend im Konstruktor passend initialisiert werden. Das Attribut similarity soll später die Ähnlichkeit mit einer Suchanfrage speichern und soll daher zu Beginn den Wert 0 haben.
Testing Structure DocumentListElement 1 of 1 tests passing
Denke bei den nachfolgenden Aufgaben daran, dass diese Attribute ggf. aktualisiert werden müssen!

#### 2. Alle Wörter unserer Collection
   Als Erstes wollen wir einen Überblick erhalten, welche Wörter sich insgesamt in unserer Collection befinden, und anschließend sicherstellen, dass sämtliche WordCount-Arrays vergleichbar sind:

- Implementiere in DocumentCollection eine Methode WordCount[] getCompleteWordCountArray(). Diese Methode soll die WordCount-Arrays aller in der Collection vorhandenen Dokumente zusammenfügen, sodass das zurückgegebene WordCount-Array zu jedem Wort in einem Text eines beliebigen Dokumentes ein WordCount-Objekt enthält. Der Wert des count-Attributes jedes WordCount-Objektes im Array ist undefiniert. Das zurückgegebene Array muss zusätzlich lexikographisch sortiert sein.
Testing Structure DocumentCollection 1 of 1 tests passing
- Implementiere in DocumentCollection eine Methode void equalizeAllWordCountArrays(), welche für alle DocumentListElement-Objekte in unserer Collection sicherstellt, dass das gespeicherte WordCount-Array mit jedem beliebigen Dokument in unserer Collection verglichen werden kann. Das bedeutet, dass das WordCount-Array von jedem DocumentListElement jedes vorkommende Wort in unserer Collection enthält und lexikographisch sortiert ist.
Testing equalizeAllWordCountArrays, might depend on correct getCompleteWordCountArray 1 of 1 tests passing
### 3. Die Gewichtung vornehmen
   Unsere Gewichte sollen sich unter anderem daran orientieren, in wie vielen Dokumenten ein Wort vorkommt. Je seltener ein Wort ist, desto maßgeblicher sollte es bei der Berechnung der Ähnlichkkeit zweier Dokumente sein. Für die Berechnung der Gewichte eines beliebigen WordCount-Objektes mit dem Wort w in einem gegebenen WordCount-Array a als Teil der DocumentCollection d soll gelten:

1. Implementiere in DocumentCollection eine Methode int getNumberOfDocumentsContaining(String), welche die Anzahl an Dokumenten zurückgibt, in denen das übergebene Wort im Text vorkommt. Groß- und Kleinschreibung und Sonderzeichen sind zu ignorieren, d.h. das Wort "pinguine" kommt im Text "Pinguine sind großartig!" vor, genauso wie "großartig". Falls der übergebene String null ist, soll 0 zurückgegeben werden.
Testing getNumberOfDocumentsContaining 2 of 2 tests passing
2. Implementiere in DocumentCollection eine Methode private void calculateWeights(WordCount[]), welche für alle WordCount-Objekte im übergebenen WordCount-Array beide Gewichte nach den obigen Formeln berechnet und entsprechend setzt. Falls das übergebene Array null ist, soll nichts passieren. Den natürlichen Logarithmus einer Zahl x erhälst du über Math.log(x) und die Quadratwurzel über Math.sqrt(x). Testing calculateWeights 2 of 2 tests passing
3. Implementiere in Document eine Methode static double complexSimilarity(WordCount[], WordCount[]), welche erneut das Skalarprodukt aller enthaltenen WordCount-Objekte berechnet, aber dieses Mal mit den normalizedWeight-Werten, statt den count-Werten. Falls einer der beiden Parameter null ist oder die Längen ungleich sind, soll -1 zurückgegeben werden.

Testing testComplexSimilarity 3 of 3 tests passing
### 4. It's all coming together
   Mit diesen Methoden sind wir in der Lage, die Ähnlichkeit zwischen einem Text und allen Dokumenten in unserer Collection zu berechnen. Es ist also endlich Zeit für die Suchanfrage:

- Implementiere in DocumentCollection eine Methode Document[] query(String), welche für alle Dokumente die Ähnlichkeit mit dem übergebenen String mithilfe von complexSimilarity() berechnet und anschließend ein Ranking nach Ähnlichkeit zurückgibt (die Methode hierfür implementieren wir erst im nächsten Schritt). Du kannst dabei wie folgt vorgehen:
- Füge die als String übergebene Suchanfrage unserer Collection hinzu.
- Sorge dafür, dass die WordCount-Arrays aller Dokumente die gleichen Einträge an der gleichen Stelle haben.
- Berechne zunächst die Gewichte des Dokuments der Suchanfrage, damit die Ähnlichkeiten nicht Null werden.
- Führe nun für jedes Dokument in der Collection folgende Schritte durch:
- Berechne die Gewichte für das WordCount-Array des Dokumentes.
- Berechne die Ähnlichkeit des Dokumentes mit der Suchanfrage.
- Entferne das Dokument mit der Suchanfrage aus der Collection.
- Gebe ein Array zurück, welches nach dem berechneten similarity-Wert absteigend sortiert alle Dokumente der Collection enthält. Die Methoden hierfür implementieren wir als nächstes. Wenn der übergebene String null ist, soll ein leeres Array zurückgegeben werden.
Helper Test für Query, prüfen ob getNumberOfDocumentsContaining korrekt kombiniert mit EqualizeAllWordCount ist 1 of 1 tests passing
Testing Query Public 0 of 1 tests passing

### 5. Ein Ranking erstellen
Als letztes benötigen wir noch Hilfsmethoden, die aus den berechneten similarity-Werten ein Ranking erstellen können.

- Implementiere in DocumentCollection eine Methode void sortBuckets(). Diese soll die doppelt verkettete Liste in jedem einzelnen Bucket unserer Collection nach dem gespeicherten similarity-Wert absteigend sortieren. Nach Ausführung der Methode soll das DocumentListElement, auf welches head verweist, das Dokument mit dem höchsten similarity-Wert beinhalten und tail soll auf das DocumentListElement mit dem Dokument, welches den niedrigsten Wert besitzt, zeigen. Ein Listenelement ist dabei allein über seine Attribute eindeutig identifiziert. Wenn zwei Dokumente den identischen similarity-Wert besitzen, sollen diese nach der ID aufsteigend eingereiht werden. Welchen Sortieralgorithmus du verwendest, ist dir erneut freigestellt. Durch die Sortierung nach dem similarity-Wert wird logischerweise die Sortierung nach der ID zerstört. Ein Dokument kann also nun nicht der ID entsprechend eingefügt werden. Deswegen spielt es keine Rolle mehr, an welcher Stelle ein Dokument in unsere doppelt verkettete Liste eingefügt wird, sobald die Buckets einmal nach der similarity sortiert wurden. Lediglich der Bucket muss beim Einfügen eines Elementes weiterhin stimmen.
Testing sortBucket 2 of 2 tests passing
- Implementiere in DocumentCollection eine Methode Document[] similarityRanking(), welche ein Array aus Dokumenten zurückliefert, bei welchem das erste Element das Dokument mit der größten und das letzte Element das Dokument mit der kleinsten Ähnlichkeit ist. Du kannst dabei ausnutzen, dass du durch die Methode sortBuckets erreichen kannst, dass sich an der Stelle head eines Buckets immer das Element mit der größten Ähnlichkeit befindet. Dokumente mit identischer Ähnlichkeit sollen nach ID aufsteigend im Array vorkommen.
Testing Similarity Ranking und Query vollständige Query 4 of 5 tests passing

Damit ist unsere Suchmaschine in der Lage, eine Suchanfrage entgegenzunehmen, die Ähnlichkeit für jedes Dokument zu berechnen und ein entsprechendes Ranking der Dokumente nach der Ähnlichkeit zurückzugeben!




### 2. Verbindungen zwischen Dokumenten
Wir wollen nun Links auf andere Webseiten bzw. in unserem Fall andere Dokumente handhaben können. Dazu kreieren wir eine Unterklasse von Dokumenten, die derartige Links verwalten kann: AbstractLinkedDocument. Dies ist lediglich eine abstrakte Klasse, da wir später verschiedene Ausprägungen von Dokumenten in Zusammenhang mit Links benötigen. Diese AbstractLinkedDocument erhalten zusätzlich ein Attribut address, über welches sie eindeutig identifizierbar sind. Diese Adressen sind dann auch Teil der Links, die ein Dokument enthalten kann. Die Links selbst widerrum sind ausschließlich Teil des Inhaltes eines Dokumentes, also dem Attribut content. Die Idee ist nun folgende, wobei wir ausschließlich Dokumente mit Links betrachten: Wenn ein neues Dokument dn in unsere Collection hinzugefügt werden soll, suchen wir den Inhalt des Dokumentes nach möglichen Links ab. Dadurch erhalten wir die Adressen aller Dokumente auf die dn einen ausgehenden Link besitzt, d.h. man kann von dn zu diesem anderen Dokument gelangen. Diese Information wollen wir uns irgendwie merken, um nicht immer den Inhalt aller Dokumente in unserer Collection durchgehen zu müssen. Dies speichern wir in den Attributen incomingLinks und outgoingLinks.

Beispiel: Das Dokument dn enthält einen Link auf die Dokumente da und db. Wir suchen also in unserer Collection nach diesen Dokumenten. Dann wird in die incomingLinks-Collection von da das Dokument dn hinzugefügt und in die outgoingLinks-Collection von dn das Dokument da. Angenommen das Dokument db befindet sich noch nicht in unserer Collection. In diesem Fall wollen wir einen Platzhalter hinzufügen, bis das "richtige" Dokument db unserer Collection hinzugefügt wird. Darum benötigen wir zwei Unterklassen von AbstractLinkedDocument, einmal LinkedDocument, welches ein normales Dokument mit ausgehenden Links repräsentiert, und DummyLinkedDocument, welches nur als Platzhalter für eingehende Links dient und deswegen auch nur eine incomingLinks-Collection benötigt. Wir erstellen also ein Dummy-Dokuemnt db, fügen dieses unserer Gesamtcollection hinzu, dn wird in die incomingLinks von db hinzugefügt und das Dummy-Dokument db in die outgoingLinks-Collection von dn.

Damit haben wir alle ausgehenden Links von dn abgearbeitet. Wir müssen nun jedoch noch überprüfen, ob in unserer Gesamtcollection ein Dummy-Dokument für dn existiert. Wenn ja, müssen wir dieses Dummy-Dokument durch das "richtige" Dokument dn in unserer Gesamtcollection und in allen outgoingLinks-Collection, in die das Dummy-Dokument zuvor eingefügt wurde, ersetzen. Dieser Prozess des Aktualisieren sämtlicher eingehender und ausgehender Links der Dokumente in unserer Gesamtcollection, wenn ein neues LinkedDocument hinzugefügt wird, soll nun schrittweise implementiert werden.

#### 1. Zwei Unterklassen
   Nicht jedes Dokument enthält ein Link zu einem anderen Dokument oder ist von einem Link eines anderen Dokumentes erreichbar. Deswegen implementieren wir diese Funktionalität als Unterklasse von Document und DocumentCollection, um zwar die gesamten Funktionalitäten der beiden Oberklasse zu behalten, aber gleichzeitig nicht jedes Dokument mit Links ausstatten zu müssen.

1. Stelle sicher, dass die Klasse AbstractLinkedDocument eine abstrakte Klasse und Unterklasse von Document ist.

2. Der Konstruktor der Klasse AbstractLinkedDocument soll identisch wie bei Document sein, nur als zusätzliche letzte Parameter einen String und einen int (in dieser Reihenfolge) erhalten. Dieser übergebene String soll in einem neuen Attribut final String address gespeichert werden. Dieses Attribut speichert die Adresse, unter der dieses Dokument aufgerufen werden kann. Erstelle zusätzlich noch ein Attribut final LinkedDocumentCollection incomingLinks, welche alle Dokumente speichern soll, über die man zu diesem LinkedDocument gelangen kann. Der übergebene int gibt dabei die Größe der Collection vor. Beide Attribute sollen auch Getter erhalten.

3. Füge der Klasse LinkedDocument den identischen Konstruktor wie der Klasse AbstractLinkedDocument hinzu. Erstelle außerdem ein Attribut final LinkedDocumentCollection outgoingLinks, welches die LinkedDocuments speichert, die von diesem Dokument erreichbar sind. Auch hier soll der übergebene int die Größe vorgeben. Auch dieses Attribut soll einen Getter erhalten.

4. Füge der Klasse DummyLinkedDocument einen Konstruktor DummyLinkedDocument(String, int) hinzu, welcher nur eine Adresse und die Größe der Collection entgegennimmt. Alle anderen Attribute sollen mit leeren Strings, wenn möglich, ansonsten mit null initialisiert werden. Diese Klasse nutzen wir später, um Dokumente darzustellen, von denen wir die Adresse kennen, sie aber noch nicht besucht haben.

testing inheritance 1 of 1 tests passing
5. Stelle sicher, dass die Klasse LinkedDocumentCollection eine Unterklasse von DocumentCollection ist. Überschreibe die Methode boolean add(Document), sodass sichergestellt wird, dass nur Elemente der Klasse AbstractLinkedDocument hinzugefügt werden können. Diese überschriebene Methode soll auch eine überladene Version der indexFunction() nutzen, aber nun mit der Methodensignatur private int indexFunction(String). Diese bestimmt den Index des Buckets anhand der Adresse eines LinkedDocuments. Die Formel dafür lautet folgendermaßen: (address.hashCode % numberOfBuckets + numberOfBuckets) % numberOfBuckets. Wenn die übergebene Adresse null ist, soll -1 zurückgegeben werden. Die Sortierung der Dokumente innerhalb eines Buckets nach der ID bleibt jedoch weiterhin bestehen. Implementiere außerdem eine Methode AbstractLinkedDocument find(String), welche zur übergebenen Adresse das gespeicherte AbstractLinkedDocument-Objekt zurückgibt. Wenn kein solches Objekt in der Collection existiert, soll null zurückgegeben werden. Hinweis: Ob ein Objekt o ein Objekt der Klasse c ist, kannst über den Ausdruck o instanceof c überprüfen.

6. Implementiere eine Methode boolean removeDummy(DummyLinkedDocument) in LinkedDocumentCollection. Diese soll das übergebene DummyLinkedDocument aus der Collection entfernen. Der Rückgabewert ist genauso wie bei removeDocument(int) aus der letzten Woche definiert. Diese Methode kann später genutzt werden, um die Dummy-Elemente wieder aus unserer Collection zu entfernen.

Testing removeDummy 1 of 1 tests passing

testing getIncomingLinks 1 of 1 tests passing

testing LinkedDocument 2 of 2 tests passing

testing DummyLinkedDocument Constructor 1 of 1 tests passing

Testing LinkedDocumentCollection inheritance 1 of 1 tests passing

Testing Add and indexFunction 4 of 4 tests passing

### 2. Die Verbindungen erkennen
Die Links zu anderen LinkedDocuments sind im content eines LinkedDocuments gespeichert. Ein Link beginnt immer mit link:: und darauf folgt eine Adresse unbekannter Länge. Diese Adresse verweist immer auf ein anderes AbstractLinkedDocument-Objekt. Du kannst davon ausgehen, dass die Adresse mindestens die Länge 1 besitzt und sich vor und hinter dem gesamten Link-Ausdruck mindestens ein Leerzeichen befindet, außer wenn sich der Link ganz am Anfang oder Ende des Dokuments befindet. Dann folgt auf den Link nur am Ende bzw. Anfang ein Leerzeichen. Ein Dokument kann beliebig viele Links auf andere Dokumente besitzen. Du musst die Adresse in keinster Weise auf Gültigkeit überprüfen, sondern lediglich aus dem Text herauslesen. Diese Funktionalität implementieren wir allein in LinkedDocument, da wir die ausgehenden Links eines DummyLinkedDocument-Objektes nicht kennen.

- Implementiere in LinkedDocument eine Methode String[] getOutgoingAddresses(), welche den content des LinkedDocuments durchsucht und alle gefundenen Adressen (ohne "link::") zurückgibt. Sonderzeichen dürfen vorher nicht entfernt werden! Das Array muss nicht sortiert werden. Wenn keine Adressen gefunden wurden, soll ein leeres Array zurückgegeben werden.
Testing Public getOutgoingAddresses 1 of 1 tests passing
- Überschreibe die Methode WordCount[] getWordCountArray() in LinkedDocument, sodass sämtliche Links nicht mehr im zurückgegebenen WordCount-Array enthalten sind.
getWordCountArray LinkedDocument 1 of 1 tests passing
### 3. Links empfangen
   WICHTIG LinkedDocumentes sind durch das Attribute address eindeutig identifiziert!
   Bevor wir die gesamte Collection durchgehen und sämtliche eingehenden und ausgehenden Links aktualisieren, benötigen wir zwei Hilfsmethoden:

- Implementiere in LinkedDocument eine Methode boolean addOutgoingLink(AbstractLinkedDocument). Diese soll das übergebene Dokument der outgoingLink-Collection von this hinzugfügen. Falls das Dokument bereits enthalten ist und ein Objekt der Klasse LinkedDocument ist, soll nichts hinzugefügt werden. Falls das Dokument bereits enthalten ist und ein Objekt der Klasse DummyLinkedDocument ist, soll das Dummy-Objekt aus der Collection entfernt werden und das übergebene AbstractLinkedDocument eingefügt werden. Selbstreferenzen sollen nicht eingefügt werden, d.h. das übergebene Dokument darf nicht die gleiche Adresse besitzen wie this. Es soll genau dann true zurückgegeben werden, wenn das übergebene Dokument eingefügt wurde.

Testing outgoingLink 3 of 3 tests passing
- Implementiere in AbstractLinkedDocument eine Methode boolean addIncomingLink(AbstractLinkedDocument). Diese soll das übergebene Dokument der incomingLinks-Collection von this hinzufügen. Falls das Dokument bereits enthalten ist, soll nichts hinzugefügt werden. Selbstreferenzen sollen nicht eingefügt werden, d.h. das übergebene Dokument darf nicht die gleiche Adresse besitzen wie this. Es soll genau dann true zurückgegeben werden, wenn das übergebene Dokument eingefügt wurde.

Testing incomingLink 4 of 4 tests passing
- Die Methode addOutgoingLink() wird lediglich von einem LinkedDocument benötigt, da ein Dummy-Objekt keine ausgehenden Links besitzt, jedoch eingehende Links, weswegen auch ein Dummy-Objekt addIncomingLink() benötigt.

### 4. Alle AbstractLinkedDocuments updaten
   Immer wenn wir der LinkedDocumentCollection unserer Suchmaschine ein neues Dokument hinzufügen, müssen die outgoingLinks- und incomingLinks-Collections aller gespeicherten Dokumente aktualisiert werden. Dabei kann jedoch der Fall auftreten, dass ein neu hinzugefügtes Dokument einen Link auf ein Dokument enthält, welches sich noch gar nicht in unserer Collection befindet. In diesem Fall soll ein DummyLinkedDocument der Collection hinzugefügt werden, bis das eigentliche Dokument in unserer Collection landet.

Implementiere in LinkedDocumentCollection eine private Methode void updateIncomingLinks(LinkedDocument, String[]). Das Array enthält dabei die Adressen aller Dokumente, auf welche das übergebene LinkedDocument einen ausgehenden Link besitzt. Die Methode soll genau diesen Dokumenten einen eingehenden Link von dem übergebenen LinkedDocument hinzufügen. Gleichzeitig soll das übergebene LinkedDocument auch immer einen ausgehenden Link erhalten. Falls eine Adresse in der Collection nicht vorhanden ist, soll stattdessen ein DummyLinkedDocument-Objekt mit der entsprechenden Adresse hinzugefügt werden und mithilfe diesem die ausgehenden Links des übergebenen LinkedDocuments bzw. die eingehenden Links des Dummy-Dokuments aktualisiert werden. Die Größe der Collection des Dummy-Objektes soll identisch zu der Größe der LinkedDocumentCollection sein, die das Dummy-Objekt erstellt. Falls einer der beiden Parameter null ist, soll nichts passieren.

Testing updateIncomingLinks 1 of 3 tests passing
Implementiere in LinkedDocumentCollection eine private Methode void updateOutgoingLinks(AbstractLinkedDocument). Diese soll bei allen Dokumenten in der Collection, die einen Link auf das übergebenen AbstractLinkedDocument besitzen, den Verweis auf das Dummy-Element in ihrer outgoingLinks-Collection durch das übergebene AbstractLinkedDocument ersetzen. Das Dummy-Element soll am Ende nicht mehr in der LinkedDocumentCollection enthalten sein. Genauso soll das übergebene AbstractLinkedDocument-Objekt am Ende alle Dokumente mit eingehenden Links in seiner incomingLinks-Collection gespeichert haben. Falls das übergebene Dokument null ist, soll nichts passieren.

Testing updateOutgoingLinks 1 of 2 tests passing
Implementiere in LinkedDocumentCollection eine Methode boolean addToResultCollection(AbstractLinkedDocument). Du kannst davon ausgehen, dass diese Methode nur auf die Collection unserer Suchmaschine aufgerufen wird, die sämtliche vorhandenen Dokument verwaltet. Diese Methode soll das übergebene AbstractLinkedDocument der Collection hinzufügen und dabei die eingehenden und ausgehenden Links aller Dokumente aktualisieren. Der Rückgabewert und das Verhalten bei null dieser Methode ist genauso wie beim normalen add() definiert.

Testing addToResultCollection 0 of 2 tests passing
Finaler Strukturtest für den 2. Teil 2 of 5 tests passing