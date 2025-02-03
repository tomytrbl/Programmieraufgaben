#  **W07H02 - FlipperFlow**


### Aufgabenstellung: FlipperFlow: Ein zweidimensionales Grid zur Simulation eines Stromkreises
In dieser Übung hat der äußerst kluge Elektroingenieur Donald Duck sein Programmierprojekt an unsere fantastischen Pinguine ausgelagert. Seine Aufgabe besteht darin, für einen gegebenen Stromkreis die Spannung und Stromstärke an jedem Bauteil zu ermitteln. Unsere Pinguine haben diese Herausforderung angenommen, um ihre Fähigkeiten in Polymorphie und Generics in der objektorientierten Programmierung zu perfektionieren – ein leichtes Spiel für sie!

Die Simulation besteht aus zwei Teilen. Die Elemente von dem Stormreis werden erst in einem Grid platziert. Da geht es um ihre Position im Stromkreis zu bestimmen. Aus dem Grid wird berechnet, welche Elemente zusammengeschaltet sind; und es ergibt sich einen Stromkreis. Vereinfachend werden wir nur Stromkreise behandeln, die aus einem geschlossenen Kreis bestehen. Es gibt also keinen parallelen Verbindungen.

### WICHTIG
Bevor nicht alle Attribute, Konstruktoren und Methodensignaturen korrekt implementiert sind (also alle Structural Tests bestanden), werden keine Behavioural Tests ausgeführt! FAQ: Leider können die strukturellen Tests keine Methoden, Attribute, Konstruktoren oder Klassen auf Existenz prüfen, wenn diese einen generischen Typ in der Signatur haben. Daher wird, falls sie fehlen, nur die Meldung 'Test not executed' angezeigt.

### Teil 1: Helferklassen für den Stromkreis
Da unsere ausgezeichneten Pinguin-Entwickler sauberen und qualitativ hochwertigen Code lieben, haben sie vorgeschlagen, einige Helferklassen zu implementieren, um die Umsetzung des Stromkreises zu erleichtern. Diese Klassen dienen dazu, Funktionen zu kapseln, die sie voraussichtlich häufig verwenden werden.

Das folgende UML-Diagramm beschreibt die Gesamtstruktur der Implementierung. Es soll dir dabei helfen, den Überblick zu behalten. Du musst das Diagramm noch nicht im Detail verstehen. Die Erklärungen zu den einzelnen Komponenten folgen später.

WICHTIG in UML fehlt getOutputDirection, siehe die Structural tests

Wir werden die Schaltkomponenten in ein Grid einsetzen. Dafür verwenden wir Methoden wie insertAt(x,y), bei denen uns Klassen aus dem Helpers-Package helfen. Wenn unsere Schaltung vollständig ist, wandeln wir sie mit der Methode generateLinked() in eine Linked List um, die eine LinkedCircuit erstellt. Alle Methoden müsst ihr selbst implementieren!

### Aufgaben
#### 1. Implementiere die Pair-Klasse
Wir brauchen eine Möglichkeit, die Positionen der Elemente im Schaltkreis zu repräsentieren. Dazu werden wir gleich die Vector2 Klasse implementieren. Als Grundlage dazu haben wir die Klasse Pair. Pair ist eine sehr einfache Datenstruktur, in der zwei Werte gespeichert werden. Da wir möchten, dass Pair vielseitig ist und nicht nur für Integer funktioniert, machen wir die Klasse generisch.
Implementiere die Methoden aus dem Diagramm. Beachte, dass die equals-Methode überschrieben werden sollte – wann sind zwei Pair gleich?

#### 2.Implementiere die Vector2-Klasse
Vector2 ist eine Spezialisierung von Pair<Integer, Integer>. Beachte, dass wir nicht von Pair erben, sondern Komposition verwenden (für Interessierte: composition over inheritance).
Die Methode add soll komponentenweise Addition mit dem Parameter other durchführen und das Ergebnis in einem neuen Vector2 zurückgeben (ohne this oder other zu verändern). Vergiss außerdem nicht, die equals-Methode zu überschreiben. Dafür kannst du die bereits implementierte Pair-equals-Methode nutzen. Die toString-Methode soll auch überschrieben werden, für einen Vektor mit Koordinaten (3,5) sollte (3, 5) als String ausgegeben werden.
#### 3. Implementiere den Direction-Enumerator No results
Ein Schaltelement kann auf einer Position unterschiedlich platziert werden, abhängig davon mit welchen Nachbarn es verbunden ist. Daher brauchen wir eine Möglichkeit, Richtungen darzustellen.
Der Enumerator Direction soll die Berechnung von Positionen im Grid vereinfachen. Jede Richtung (UP, DOWN, LEFT, RIGHT) speichert einen Vector2 mit der 2D-Darstellung einer Richtung (z.B. (0, 1) für UP).

- reverse gibt die entgegengesetzte Richtung des Parameters zurück.
- getCoordinatesFromDirection(dir,pos) liefert die benachbarte Position von pos in Richtung dir mithilfe von Vektoraddition mit den gespeicherten Direction-Vektoren (Beispiel: (2, 4) + UP -> (2, 5)).
- getDirectionFromCoordinates(currentCoordinates, nextCoordinates) führt die umgekehrte Berechnung durch, indem die Richtung bestimmt wird, in der sich nextCoordinates von der Position currentCoordinates aus befindet. Die Funktion gibt null zurück, wenn die Positionen nicht benachbart sind.
### Teil 2: Komponenten des Stromkreises + LinkedCircuit
Unsere Pinguine haben in ihren Vorlesungen über Polymorphie und Vererbung gelernt und beschlossen, eine Hierarchie der Komponenten ihres Stromkreises zu erstellen. Die Idee ist, eine generische Oberklasse (Piece) zu erstellen, von der spezifischere Komponenten wie Kabel, Widerstände oder Stromquellen abgeleitet werden. Dadurch können sie Objekte verschiedener Klassen im selben Array speichern. Wie das funktioniert? Indem der statische Typ des Objekts beim Speichern in die generelle Klasse gecastet wird und beim Abrufen für spezielle Fälle in ihre spezifischere Klasse zurückgecastet werden kann.

Damit steht die nächste Aufgabe der Übung fest: Wir werden alle benötigten Klassen für die Komponenten implementieren.

#### 1. Implementiere die `Piece`-Klasse
Zunächst möchten wir die abstrakte Klasse Piece implementieren. Diese Klasse muss abstrakt sein, da sie noch kein echtes Bauteil repräsentiert, sondern lediglich eine Anforderung bzw. Vorlage für alle realen Bauteile darstellt, und somit nicht instanziiert werden soll.
- Jedes Piece hat genau eine Eingangs- und eine Ausgangsrichtung. Diese Richtungen repräsentieren, wie der Stromfluss später durch das Piece verlaufen soll. Wir beschränken uns auf nur vier Richtungen (die in der Direction-Hilfsklasse definiert sind). Standardmäßig sollen beide Richtungen null sein.

- Erklärung Directions: Stellen wir uns vor, wir haben einen Pfeil, der im Piece beginnt und in die Richtung zum nächsten Piece zeigt – das ist unsere Ausgangsrichtung. Die Richtung zum vorherigen Piece, von dem der Strom kommt, ist die Eingangsrichtung, also die Richtung, aus der der Strom kommt.
Wichtig: Unsere Pieces können nicht verbogen werden, somit können die Eingangs- und Ausgangsrichtung nur einmal gesetzt werden. Bitte berücksichtige dies bei der Implementierung der Setter - bei mehrmaligem Versuch diese zu setzen soll dementsprechend nichts passieren. Darüber hinaus enthält jedes Piece einen Widerstandswert. Standardmäßig soll dieser auf 0 gesetzt werden.
Es gibt auch ein Boolean-Flag inserted, um zu verhindern, dass ein Piece mehr als einmal eingefügt wird. Sobald die Eingangsrichtung eines Piece gesetzt ist, wissen wir, dass es in einen Stromkreis eingesetzt wurde, und können das Flag auf true setzen.

#### 2. Implementiere das `Conductive`-Interface
Das Conductive-Interface kennzeichnet Pieces, die Elektrizität übertragen können. Jedes leitfähige Piece muss in der Lage sein, seine Eingangsstärke (currentInput) und die Eingangsspannung (voltageInput) sowie die Ausgangsstärke (currentOutput) und die Ausgangsspannung (voltageOutput) zu liefern.
#### 3. Implementiere die `Cable`-Klasse No results
Als nächstes implementieren wir echte Komponenten für unseren Stromkreis. Ein Kabel ist ein Piece, und hat zusätzlich noch die Eigenschaft, leitfähig zu sein. Daher sollte ein Kabel alle Funktionen des Conductive-Interfaces implementieren.
Ein Kabel speichert Eingangsstärke und Eingangsspannung. Wir nehmen an, dass ein Kabel keinen Einfluss auf die Spannung eines Stromkreises hat (d.h., wir ignorieren dessen Widerstand). Somit sind getVoltageOutput und getCurrentOutput trivial.
#### 4. Implementiere die Resistor-Klasse
Nun wollen wir Widerstände implementieren. Wichtig zu beachten ist, dass ein Widerstand die Spannung in unserem Stromkreis beeinflusst. Wenn Strom durch einen Resistance fließt, dann wird sich der outputVoltage nach folgender Formel ändern: VoltageOutput = VoltageInput - Resistance × Current Der Konstruktor der Resistor-Klasse muss folgende Parameter in der angegebenen Reihenfolge entgegennehmen:
- resistance (double): Der Widerstandswert in Ohm.
- minCurrent (double): Der minimale Strom, den der Widerstand handhaben kann.
- minVoltage (double): Die minimale Spannung, die durch den Widerstand fließen kann.
- maxCurrent (double): Der maximale Strom, den der Widerstand sicher verarbeiten kann.
- maxVoltage (double): Die maximale Spannung, die durch den Widerstand fließen darf.
- getStatus prüft, ob der Resistor innerhalb seiner Spezifikationen arbeitet. Das heißt, es wird geprüft, ob der aktuelle Strom (currentInput) und die Spannung (voltageInput) innerhalb der definierten Minimal- und Maximalwerte liegen. Die Methode soll als Ergebnis entweder WORKING oder NOT WORKING zurückgeben, je nachdem, ob der Resistor alle Spezifikationen erfüllt.

- toString überschreibt diese Methode, um einen sinnvolles Output in Textform über den Zustand des Resistors zu bekommen. Ach auf eine gute Formatierung der Werte, z.B. auf zwei Nachkommastellen für die Ausgabe der Spannung und des Stroms. Ihr dürft String.format("%.2f", DOUBLE_VALUE) aufrufen. Wenn z.B. positionInGrid == null also nicht gesetzt ist, fügen wir in String ganz normal positionInGrid hinzu (auch wenn null) (Erinnerung: Wir haben toString in Vector2 überschrieben).

#### 5. Implementiere die `PowerSource`-Klasse No results
Nun wollen wir die Stromquelle implementieren. Da eine Stromquelle kein Kabel ist (unsere Pinguine haben das durch Nachfragen im MI-Gebäude in Garching herausgefunden), besitzt sie andere Eigenschaften.
Am wichtigsten ist, dass eine Stromquelle keine Eingangs-/Ausgangswerte für Spannung und Strom hat, da Spannung und Strom des Stromkreises erst durch die Stromquelle festgelegt werden. Daher hat sie zwei festgesetzte Attribute: voltage und current.
Eine Stromquelle ist die allererste Komponente, die wir in unser Grid einfügen, und muss daher im Konstruktor mit einer festen Eingangs- und Ausgangsrichtung initialisiert werden. Standardmäßig sollte voltage auf 10.0 und current auf 0.0 gesetzt werden. Für PowerSource gilt - wie auch bei Piece -, dass die input-/output-Direction nur einmal gesetzt werden darf. Ausserdem brauchen wir hier einen Konstruktor: PowerSource(Direction inputDirection, Direction outputDirection), der die Directions setzt.
#### 6. Implementiere die `LinkedCircuit`-Klasse No results
Vereinfachend begrenzen wir uns auf Schaltkreise, die aus einem geschlossenen Kreis bestehen, also keine parallelen Verbindungen haben. Da jedes Schaltelement somit einen Vorgänger und einen Nachgänger hat, eignet sich eine verkettete Liste, um solche Schaltkreise zu repräsentieren.
LinkedCircuit<T extends Cable, R extends PowerSource> ist unsere spezielle Implementierung einer verketteten Liste. Wir speichern die PowerSource, start sowie end der Liste. Um die Klasse zukunftssicher für eventuell bessere Stromkreise mit besseren Komponenten zu machen, fordern wir mit Generics, dass unsere Kabel (Generic T) lediglich eine Unterklasse von Cable sind, und die Stromquelle (Generic R) von PowerSource erbt.

ACHTUNG: Das T in CableLink kommt aus LinkedCircuit<T>! CableLink ist eine INNERE KLASSE von LinkedCircuit<T>!!! deswegen CableLink<T> ist totales quatsch!
- Innere Klasse CableLink: Repräsentiert ein Element in der Liste, speichert ein T cable und eine Referenz auf das nächste Element CableLink next. Die Methode propagate leitet den Strom weiter zum nächsten Schaltelement, indem sie den currentInput des nächsten Schaltelements auf den currentOutput des aktuellen Schaltelements setzt. Ähnlich wird der voltageInput des nächsten Schaltelements auf den voltageOutput des aktuellen Schaltelementss gesetzt. Wenn next null ist, geschieht nichts.
- link: Fügt ein neues CableLink mit dem gegebenen Cable am Ende der Liste hinzu.
- isClosed: Gibt true zurück, wenn der LinkedCircuit geschlossen ist, d. h. wenn die powerSource ungleich null ist.
- getTotalResistance: Liefert den Gesamtwiderstand (Summe) aller Schaltelemente in der Liste (unabhängig davon, ob Widerstände WORKING/NOT WORKING sind). Hier wird klar, warum wir wollen, dass T von Cable erbt, damit wir den Getter für den Widerstand der Komponente aufrufen können.
- printStatusReport: Hier sollte die String-Darstellung Jedes Resistors im Schaltkreis gesammelt und zurückgegeben werden. Ihr dürft annehmen, dass Resistor irgendwo in grid liegt, wenn diese Methode aufgerufen werden kann.
### Teil 3: Der Stromkreis
#### 1. Übersicht für Circuit
Jetzt kommen wir endlich zum Herz der Übung, der Circuit-Klasse. Wir speichern ein zweidimensionales Array grid, das alle Positionen repräsentiert, an denen ein Piece platziert werden kann, wobei (0, 0) die untere linke Ecke darstellt.

- Konstruktor: Initialisiere grid mit den gegebenen Dimensionen. Speichere die Position der Stromquelle. Erstelle eine neue Stromquelle an der angegebenen Position. Der Konstruktor von Circuit sieht so aus: public Circuit(int width, int height, int sourceX, int sourceY). **WICHTIG: ** Hier dürft ihr annehmen, dass 2 < width und 2 < height und dass sourceX und sourceY immer in grid liegen.
- Standardmäßig hat die Stromquelle die Eingangsrichtung LEFT und die Ausgangsrichtung RIGHT. Befindet sich die Stromquelle jedoch entlang des rechten Randes, sollte die Ausgangsrichtung UP sein, falls sie in der oberen rechten Ecke platziert wurde, sollte sie DOWN sein. Wenn sie sich entlang des linken Randes befindet, sollte ihre Eingangsrichtung DOWN sein, in der unteren linken Ecke sollte sie UP sein.
- openEnd sollte immer die Position des zuletzt platzierten Piece angeben, das aktuelle "offene Ende" des Stromkreises. Wenn der Stromkreis geschlossen ist (siehe später), sollte es null sein.
- getPowerSource: Gibt die PowerSource des Stromkreises zurück.
- setSourceVoltage, setSourceCurrent: Setzt die Spannung bzw. die Stärke der Stromquelle.
- getAt: Gibt das Piece an einer bestimmten Position zurück oder null, wenn die Position leer oder ungültig (out of bounds) ist.
- insertAt(x,y): Versucht, ein Piece an einer bestimmten Position einzufügen. Gibt true bei Erfolg zurück. Statische Funktionen aus Direction werden für diese Methode hilfreich sein 🙂. Constraints:
  - Es darf nur eine Stromquelle im Stromkreis existieren.
  - Ein Piece darf nicht zweimal eingefügt werden.
  - Ein Piece darf keine andere Komponente an einer Position ersetzen, d.h., wenn eine Position bereits besetzt ist, kann dort kein weiteres Piece eingefügt werden.
  - Ein Piece darf nur an einer der 3 angrenzenden Positionen von openEnd eingefügt werden. Wenn z.B. die inputDirection von openEnd LEFT ist, d.h. der Strom kommt von links bzw. das vorherige Piece ist eine Position links von openEnd, so darf ein neues Piece nur darüber, darunter, oder rechts von openEnd eingefügt werden (sofern diese Positionen frei sind).
  - Kann ein Piece erfolgreich platziert werden, stelle sicher dass die inputDirection vom neu platzierten Piece, sowie die outputDirection von openEnd richtig gesetzt werden!
  - Sobald ein Piece an der Eingangsposition der Stromquelle (also an der anliegenden Position in Richtung inputDirection) platziert wird, ist der Stromkreis geschlossen, und openEnd kann auf null gesetzt werden (vergiss nicht, in dem Fall direkt die outputDirection des neu platzierten Piece zu setzen ;)).
  - Wenn ein Resistor hinzugefügt ist, man muss entsprechend die positionInGrid dafür setzen.
  - Wenn der Stromkreis bereits geschlossen ist, dürfen keine weiteren Pieces eingefügt werden.
  - Hilfsmethoden für insertAt(x,y): positionIsEmpty soll true returnen, wenn die gegebene Position innerhalb der Grenzen des grids liegt, und kein Piece an dieser Position ist. getPossibleLinkPositions gibt ein Array mit den maximal drei anliegenden Positionen von openEnd zurück, an welchen das nächste Piece platziert werden kann (Achtung - wenn die PowerSource aktuell das einzige Piece im Stromkreis ist, vergiss nicht, dass das nächste Piece nur an der anliegenden Position in Richtung outputDirection der Stromquelle gesetzt werden kann).
- generateLinked: Wird nach dem Platzieren aller Pieces verwendet. Erstelle eine verkettete Liste aller Pieces mit unserer LinkedCircuit-Klasse, die dann verwendet werden kann, um endlich den Strom durch unseren Stromkreis zu leiten!
- Beachte, dass die Klasse die PowerSource seperat speichert (also diese kein Teil der LinkedList ist). 
- Das heißt wir starten am ersten Kabel neben der Stromquelle, folgen den Ausgangsrichtungen und fügen jedes Kabel zu LinkedCircuit hinzu (link-Methode)
- Wenn wir bei der PowerSource ankommen, d.h. unser Stromkreis geschlossen ist, setzen wir uns das powerSource-Attribut von LinkedCircuit. Wenn unser Stromkreis nicht geschlossen ist, bleibt das powerSource-Attribut in LinkedCircuit null. 
- propagate: Hier kommt nun alles zusammen. Erstelle einen LinkedCircuit. Wenn dieser nicht geschlossen ist, verlasse die Methode sofort. Berechne die Stromstärke, indem die Spannung der Stromquelle durch den Gesamtwiderstand des Stromkreises geteilt wird (getTotalResistance in LinkedCircuit). Setze die Stromstärke der Stromquelle auf den berechneten Wert. Setze dann die Eingangsspannung und -stärke des ersten Kabels (getStart) auf die Ausgangswerte der Stromquelle. Iteriere über alle Kabel im LinkedCircuit und rufe propagate für jedes Kabel auf, um den Strom Kabel für Kabel weiterzuleiten. 
- printStatusReport: Hier sollte ein wrapper für printStatusReport von LinkedCircuit implementiert werden. (wird nicht getestet)
  
WICHTIG

  Circuit hängt von der korrekten Implementierung aller Klassen ab, auf die es angewiesen ist. Gleichzeitig benötigt LinkedCircuit eine funktionierende Implementierung der Helper-Klassen, damit Circuit korrekt arbeiten kann. Das bedeutet: Damit Circuit funktioniert, muss auch LinkedCircuit korrekt implementiert sein, was wiederum voraussetzt, dass die zugrunde liegenden Komponenten richtig umgesetzt sind.