
#  **W05H02 - Schiffe versenken**


### Aufgabenstellung: Schiffe versenken

Die drei Entdeckuine Antonia, Christian und Jonas sind schon lange auf der Suche nach dem Zepter der Unterwerfung. Jedoch ist die Eiswüste sehr eintönig und sie wollen sich abends mit einem Spiel ablenken. Dabei fällt Christian ein Spiel aus seiner Kindheit ein. Robben ... ähh ... Schiffe versenken. Er hat euch auch schon etwas vorbereitet. Ihr sollt ihm nun dabei helfen, das Spiel fertig zu programmieren.

## Aufbau
In dieser Aufgabe sollst du ein Programm schreiben, mit dem man "Schiffe versenken" gegen den Computer spielen kann. Der Spielablauf ist dabei wie folgt: Das Spielfeld besteht aus zwei 8x8 Feldern. Auf dem linken Feld befinden sich deine eigenen Schiffe, während auf dem rechten Spielfeld sich die feindlichen Schiffe befinden, die für den Spieler nicht sichtbar sind. Es gibt 1 Schiff der Länge 4, 2 Schiffe der Länge 3 und 3 Schiffe der Länge 2. Die Schiffe dürfen sich nicht direkt berühren, über Eck ist aber erlaubt. Der Spieler kann nun eines der Felder aussuchen, um darauf zu schießen. Befindet sich dort ein feindliches Schiff, wird das Feld als Treffer markiert, andernfalls als Fehlwurf. Sobald alle feindlichen Schiffe getroffen wurden, gewinnt der Spieler. Falls der Computer zuerst alle Schiffe des Spielers erwischt, gewinnt natürlich der Computer.

Im Programm wird das Spielfeld als zweidimensionales int-Array repräsentiert, wobei der erste Index die Zeile angibt und der zweite Index die Spalte, gezählt von links oben. Das Feld [0][0] ist die linke obere Ecke, das Feld [1][0] befindet sich darunter und [0][1] rechts daneben. An jeder Position im Array steht eine Zahl, die angibt, was sich am jeweiligen Punkt befindet: 0 steht für Wasser, 1 für ein noch nicht entdecktes Schiff, 2 für einen Fehlschuss und 3 für einen Treffer. Dem Spieler gegenüber werden die Zeilen mit den Ziffern 1-8 und die Spalten mit den Buchstaben a-h nummeriert.

## Wichtige Hinweise:
In den 3 Klassen AI, Battleship und Player sind bereits alle benötigten Methodenköpfe vorgegeben. Du musst also keine neuen erstellen.
Einige der Hilfsmethoden sind bereits für dich vorgefertigt.
Du findest in allen Klassen ganz oben einige String-Konstanten. Diese kannst du verwenden, damit du dich beim Konsolenoutput nicht vertippst.
Die AI-Klasse darfst du beliebig anpassen, solange du die vorhandenen Methodenköpfe nicht löschst.
Im Code findest du den Teilaufgaben entsprechend TODO-Kommentare.
Die ersten 4 Teilaufgaben sind Hilfsmethoden und sollen mit Schiffen beliebiger Länge klarkommen können.
## Aufgabe
Die Aufgabe ist in kleinere Methoden aufgeteilt. Du kannst davon ausgehen, dass die Attribute und Parameter vom Typ int[][] immer quadratisch, nicht null sind und die Größe 8 haben.

### 1. Spielfelder ausgeben 2 of 2 tests passing
In der Klasse Battleship ist bereits der Kopf der printBoard()-Methode vorgegeben. Du sollst diese nun vervollständigen. Das playerBoard soll dabei links und das aiBoard rechts ausgegeben werden. Um das zu erreichen, müssen wir durch beide Boards zeilenweise durchgehen und folgendes ausgeben. Ganz oben findest du einen dazu passenden String, damit du es nicht selber abtippen musst. Die \t sorgen dafür, dass es auf der Konsole schöner aussieht.
"Your⎵Board:\t\t\tAI⎵Board:"
"+⎵a⎵b⎵c⎵d⎵e⎵f⎵g⎵h\t\t+⎵a⎵b⎵c⎵d⎵e⎵f⎵g⎵h"
Nun soll für jede Zeile des Bretts erst die aktuelle Zeilennummer (wir fangen bei 1 an zu zählen!) gefolgt von der aktuellen Zeile des playerBoards ausgegeben werden. Dann "\t\t" gefolgt von der aktuellen Zeilennummer. Den Abschluss der aktuelle Zeile bildet dann die Zeile des aiBoards.
Bei beiden Boards soll Wasser durch "⎵⎵", ein getroffenes Schiff durch "⎵x" und ein Fehlschuss durch "⎵o" dargestellt werden. Zudem werden die unentdeckten Schiffe des Spielers durch "⎵■" und die unentdeckten Schiffe des Computers durch "⎵⎵" (also gleich wie Wasser) dargestellt. Auch dafür findest du die passenden Strings oben in der Klasse.


#### 2. Test auf Ende 1 of 1 tests passing
Die Methode shipsLeft() prüft, ob das übergebene Spielfeld noch verdeckte Schiffe enthält. Enthält das übergebene Feld keine verdeckten Schiffe, soll false zurückgegeben werden, andernfalls true.

Die nächsten Aufgaben sind in der Player-Klasse

#### 3. Platziere das Schiff 1 1 of 1 tests passing
Die Methode setShip() bekommt 4 ints übergeben. Die ersten beiden sind die Reihe und Spalte der ersten Koordinate, die letzten beiden sind die Reihe und Spalte der zweiten Koordinate. Du kannst davon ausgehen, dass die erste Koordinate in beide Dimensionen kleiner-gleich der zweiten Koordinate ist und sich die Koordinaten in einer Linie (horizontal oder vertikal) befinden. Deine Aufgabe ist nun die Felder zwischen den beiden Koordinaten (inklusive) auf ein verdecktes Schiff zu setzen.
#### 4. Platziere das Schiff 2 2 of 2 tests passing
Die Methode placeShip() bekommt eine Länge und eine Nachricht übergeben. Das Ziel ist, Platz für ein Schiff der übergebenen Länge zu finden und es dort zu platzieren. Die Länge darf dabei beliebig sein, ist aber garantiert kleiner-gleich 8. Die folgenden Schritte sollen solange wiederholt ausgeführt werden, bis eine geeignete Stelle gefunden wurde:
- Gibt auf der Konsole die übergebene Nachricht aus.
- Lese nacheinander 2 Koordinaten ein. Frage dabei jeweils zuerst nach der Zeilen-Nummer gefolgt von dem Spalten-Buchstaben (du findest weiter unten in der Klasse geeignete Hilfsmethoden, die eine Zahl zwischen 1 und 8 bzw. einen Buchstaben zwischen a und h einlesen und jeweils eine Zahl zwischen 0 und 7 zurückgeben). Beide Koordinaten sollen im Folgenden als inklusive Grenzen verwendet werden.
- Die Koordinaten müssen in einer Linie liegen (entweder horizontal oder vertikal), müssen so weit auseinander sein, wie das Schiff lang ist und die erste Koordinate muss kleiner-gleich der zweiten Koordinate sein. Ein Beispiel für valide Koordinaten für ein Schiff der Länge 3 wäre (1, 3) und (1, 5). Ein Beispiel für falsche Koordinaten wäre (6, 1) und (3, 2), da die erste Koordinate größer als die zweite ist, die Distanz für das gegebene Schiff zu groß ist und die beiden Koordinaten weder horizontal noch vertikal in einer Linie sind.
- Falls die Koordinaten diese Eigenschaften erfüllen, musst du als nächstes noch überprüfen, ob das neue Schiff eines der bereits existierenden Schiffe berühren würde. Du kannst jede Position zwischen den Koordinaten und die Koordinaten selbst mit einer bereits implementieren Methode überprüfen.
- Falls die Koordinaten auch diese Überprüfung erfüllt haben, kannst du sie mit der setShip()-Methode auf dem Board platzieren und das aktuelle Board mit der Methode aus Battleship auf der Konsole ausgeben.
- Falls die Korrdinaten irgendeine der Bedingungen nicht erfüllen, soll die Fehlernachricht "There⎵is⎵a⎵problem⎵with⎵your⎵coordinates.⎵Try⎵again!" auf der Konsole ausgegeben werden und die Methode beginnt von vorne.
So könnte beispielsweise ein Ablauf für ein Schiff der Länge 3 mit der übergebenen Nachricht PLACE_LENGTH_3 aussehen:


Die Zeilen, die mit `"> "` beginnen, markieren dabei die Nutzereingaben und werden nicht von dem Programm ausgegeben. Sie dienen nur der besseren Verständlichkeit.
#### 5. Erstelle das Spielfeld 1 of 1 tests passing
Nun geht es darum, das komplette Spielfeld zu erstellen. Initialisiere dafür das board mit der geeigneten Größe und platziere 1 Schiff der Länge 4, 2 Schiffe der Länge 3 und 3 Schiffe der Länge 2 auf dem Spielfeld (die passende Methode hast du bereits erstellt). Bei den Schiffen der verschiedenen Längen sollen folgende Nachrichten übergeben werden:
4: "Place⎵a⎵ship⎵of⎵length⎵4.⎵Enter⎵the⎵coordinates⎵of⎵both⎵ends."
3: "Place⎵a⎵ship⎵of⎵length⎵3."
2: "Place⎵a⎵ship⎵of⎵length⎵2."
#### 6. Das Spiel spielen 1 of 1 tests passing
Nachdem wir das Spielfeld erstellt haben, geht es nun darum, einen Spielzug durchzuführen. Als Parameter wird das aktuelle aiBoard des Computers übergeben. Dazu sollen so lange die folgenden Schritte ausgeführt werden, bis eine valide Koordinate eingegeben wurde:
Zunächst wird wie zuvor eine Reihen-Nummer und ein Spalten-Buchstabe eingelesen.
Falls das Feld bereits beschossen wurde (egal ob getroffen oder nicht), soll "Field⎵is⎵already⎵hit!" ausgegeben werden und die Methode beginnt von vorne.
Ansonsten befindet sich auf dem Feld entweder Wasser oder ein verdecktes Schiff. Falls Wasser getroffen wurde, soll "You⎵missed.⎵Better⎵luck⎵next⎵time!" ausgegeben und false zurückgegeben werden. Falls ein verdecktes Schiff getroffen wurde, soll "You⎵hit⎵it!" ausgegeben und true zurückgegeben werden. In beiden Fällen wird der Wert des Feldes auf den entsprechenden Wert gesetzt.
#### 7. Das Spiel vollenden 2 of 2 tests passing
- Zu guter Letzt muss jetzt nur noch die game() Methode in Battleship implementiert werden. Diese soll die folgenden Schritte durchführen:
- Zuerst soll auf der Konsole "Welcome⎵to⎵Battleships" ausgegeben werden.
- Als nächstes soll ein komplett leeres Spielfeld ausgegeben werden.
- Danach soll der player aufgefordert werden, das playerBoard zu initialisieren.
- Nun soll "Let's⎵start⎵the⎵game!" ausgegeben werden und das eigentliche Spiel kann beginnen. 
- Das Spiel läuft solange, bis einer der beiden (ai oder player) keine Schiffe mehr übrig hat.
#### Beginnend mit dem player sollen die beiden abwechselnd spielen.
- Falls der aktuelle Spieler getroffen hat, darf er nochmal schießen.
- Der aktuelle Spieler darf so oft hintereinander schießen, bis er nicht mehr trifft.
- Falls nicht, ist der andere Spieler dran.
- Nach jedem Schuss soll einmal das Spielfeld ausgegeben werden.
- Falls der player es schafft, als erster alle Schiffe der ai zu treffen, soll "Congrats!⎵You⎵won!" ausgegeben werden und das Spiel beendet werden.
- Falls die ai es schafft, als erster alle Schiffe des players zu treffen, soll "Too⎵bad,⎵you⎵lost!" ausgegeben werden und das Spiel beendet werden.
- Die Tests testen einmal ein Spiel, in dem der Spieler gewinnt, und ein Spiel, in dem der Computer gewinnt.

