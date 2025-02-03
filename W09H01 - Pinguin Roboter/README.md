#  **W09H01 - Pinguin Roboter**


### Aufgabenstellung

In der Mitte dieser Aufgabe stehen die Roboter. Du wirst erst einen programmierbaren Roboter entwickeln. In dem zweiten Teil wirst du dann Roboter erstellen, die sich durch ihre Programmierung unterschiedlich verhalten.

Die Roboter sind kreisförmig und haben einen Durchmesser von 0.5 bis 1m. Sie können sich frei drehen und vorwärts/rückwärts bewegen und sind sogar mit Lautsprechern ausgestattet, sodass sie sprechen können Die Entdeckuine haben auch schon einiges vorbereitet (siehe weiter unten), nur macht der Roboter aktuell leider nicht viel. Die Roboter können weder die Außenwelt wahnehmen, noch können sie denken. Sie können aber programmiert werden. Sie bekommen also eine Function als Programm. Darin können sie die Sensordaten auswerten und eine Liste von Befehlen generieren, die der Roboter anschließend ausführen kann. Die Befehle an sich sind wiederum ebenfalls Objekte eines funktionalen Interfaces. Somit können wir die Funktion erst definieren und zu einem späteren Zeitpunkt an anderer Stelle ausführen. Das Programm als Function hat zudem den Vorteil, dass man es einfach austauschen kann. Somit ist es möglich, zu bestimmten Zeitpunkten zu einer anderen Aufgabe zu wechseln. Dazu aber später mehr.

Deine Aufgabe ist es nun, den Roboter zu vervollständigen. Da du dich mit funktionalen Interfaces sehr gut auskennst, sollte das kein Problem für dich sein. Bei ihren Experimenten haben sie zudem festgestellt, dass die Sensoren nach einiger Zeit kaputt gehen können. Diesen Fall wollen wir hier mithilfe von Exceptions verwalten.

### Part 1 - Der Basisroboter
#### Eine erste Übersicht
Das folgende UML-Diagramm zeigt die zu implementierenden Klassen. Damit das Diagramm übersichtlich bleibt, stehen im Diagramm nur Dinge, die noch nicht implementiert sind. Damit du dir auch sicher sein kannst, dass du alle Mindestanforderungen erfüllst, stehen hier ebenfalls Getter, Setter und toString-Methoden. Achte darauf, dass kursiv geschriebene Methoden abstrakt sind! Zu den Konstruktoren findest du weiter unten genauere Anweisungen. Damit die Tests ausgeführt werden können, muss jede Klasse zumindest existieren, auch wenn sie noch nicht vollständig ist. Bei den Tests für die Robot-Klasse wird zudem erwartet, dass alle Methoden existieren, auch wenn du noch bei den Robot Basics bist. Stelle sicher, dass zunächst alle Teilaufgaben in Part 1 funktionieren, bevor du mit Part 2 weitermachen kannst.

Nun zu den bereits vorgegebenen Klassen:

- Position: Diese Klasse wird benutzt, um Koordinaten darzustellen. Du wirst die folgenden Methoden brauchen:

- void moveBy(double, double): Diese Methode verschiebt die Position um die gegebene Distanz in die gegebene Länge.
- double distanceTo(Position): Diese Methode berechnet die Distanz zur gegebenen Position.
- double directionTo(Position): Diese Methode berechnet die Richtung, in der die gegebene Position von der eigenen Position liegt. Sie gibt eine Zahl zwischen
−π und +
π zurück.
- Robot: Diese Klasse hat schon einige Methoden vorgegeben. Den Kern musst du aber noch selber implementieren. Du findest dort Methoden, um sich zu bewegen,
mit boolean say(String) kannst du was auf der grafischen Oberfläche ausgeben lassen (das sind unsere "Lautsprecher") und mit boolean paintWorld(Position, char) kannst du die Welt verändern.
Zudem könnten auch einige Getter und Setter für dich relevant sein.

- World: Diese Klasse dient hauptsächlich zur Erstellung der grafischen Oberfläche. Sie speichert außerdem die Oberfläche, welche aus einem String generiert wird.
Du kannst für lokales testen den Konstruktor World(mapData: String) benutzen, um dir deine eigene Welt zu erstellen. Weitere nützliche Methoden sind:

    - char getTerrain(Position): Diese Methode gibt den Untergrund an der gegebenen Position zurück.
    - boolean setTerrain(Position, char): Diese Methode setzt den Untergrund auf den gegebenen Wert an der gegebenen Position.
    - String getKey(int, int): Diese (und die nächste) Methode wirst du erst am Ende brauchen. Sie gibt den Key nur dann zurück, falls die übergebene Position korrekt ist.
    - Scepter getScepter(int, int, String): Diese Methode gibt das Zepter zurück, falls die Position und der Schlüssel stimmen.

Du darfst die Klasse World nicht verändern! Selbst Formatierung wird die Tests sonst failen lassen!

User: Diese Klasse darfst du zum freien Testen verwenden. Du findest dort bereits eine kleine Spielwiese als String-Konstante gespeichert. Um die Simulation zu starten, musst du einfach nur die main-Methode laufen lassen, welche dann World.run() aufrufen sollte.

Zum Testen deiner Roboter kannst du später einfach den SimpleFailureSimulator benutzen, welchen du ganz unten in der Klasse Robot findest.

Alle Klassen sollen sich in pgdp.robot befinden.

## Die Basiskomponenten
### Command
Der Roboter soll letztendlich sein Programm dazu nutzen, um eine Reihe von Commands zu generieren. Erstelle dazu das Functional Interface Command, welches nur eine einzige Methode boolean execute(Robot) haben soll.
### Memory
Starten wir mit dem Speicher. Jeder Sensor produziert Daten, welche für die spätere Verarbeitung irgendwo gespeichert werden sollen. Dafür benutzen wir die generische Klasse Memory. Jedes Memory-Objekt hat ein label und ein Attribut data. Implementiere zudem einen Konstruktor, welcher die beiden Attribute in dieser Reihenfolge übergeben bekommt. Füge Getter und Setter für data hinzu, sowie eine toString(), welche folgenden String zurückgeben soll:
"<label>:⎵<data>". Zum Beispiel soll new Memory<Integer>("x", 42).toString den Wert "x: 42" zurückgeben.
### Sensor
Als nächstes geht es mit der abstrakten generischen Klasse Sensor weiter. Jeder Sensor hat eine Referenz owner auf den Roboter, zu dem er gehört, einen processor, welcher die Daten verarbeiten kann, einen Namen und eine Zuverlässigkeit. Füge einen Konstruktor ein, welcher den Namen und die Zuverlässigkeit entgegen nimmt. Zudem brauchen wir noch 2 Setter für owner und processor. Letzterer soll dabei auch diesen Sensor zurückgeben, was uns später die Nutzung angenehmer gestaltet. Zum Schluss brauchen wir noch die beiden abstrakten Methoden getData() und createNewSensor(), welche von den jeweiligen konkreten Sensortypen implementiert werden.
## Die Exception
### MalfunctionException 
Da unsere Sensoren kaputt gehen können, müssen wir dem Roboter irgendwie mitteilen, dass die Sensoren ausgetauscht werden müssen. Da kommt unsere MalfunctionException ins Spiel. Diese soll eine checked Exception sein und wird dann geworfen, wenn ein Sensor kaputt geht. Sie soll eine Referenz auf den sensor speichern, welcher die Exception verursacht hat. Implementiere dazu einen Konstruktor, der einen Sensor<?> annimmt und auch einen passenden Getter. Zudem wollen wir auch eine toString()-Methode haben, damit wir beim Testen und Debuggen wissen, was passiert:
"<sensor>⎵malfunctioned⎵and⎵needs⎵to⎵be⎵replaced". Zum Beispiel würde new MalfunctionException(new TerrainSensor("terrain"), 1).toString() den Wert "TerrainSensor terrain malfunctioned and needs to be replaced" zurückgeben. Was der TerrainSensor macht, wirst du später noch erfahren.
Verändere nun auch die Methode Sensor.getData() so, dass sie eine MalfunctionException werfen könnte.
### Der Roboter
#### Robot Basics
Nun da wir alles haben, können wir den Roboter implementieren. Füge zuerst die fehlenden 4 Attribute hinzu und initalisiere diese passend entweder im Konstruktor oder direkt bei der Deklaration. Die Datenstrukturen sollen leer sein und die Function soll als default eine leere Liste zurückgeben können. Nun gehen wir Schritt für Schritt die weiteren Anforderungen durch:
- Implementiere die Methode void attachSensor(Sensor<?>), welche den übergebenen Sensor in seine Liste einfügt und sich selber als owner des Sensors setzt.
- Implementiere die Methode Memory<T> createMemory(Memory<T> memory), welche die Speichereinheit entgegennimmt, in die Liste einfügt und anschließend auch zurückgibt. Das wird uns die Nutzung später vereinfachen.
- Implementiere die Methode String memoryToString(), welche eine String-Repräsentation des Speichers zusammenbaut. Nutze dafür einen StringBuilder anstelle von String-Konkatenation mit +. Jedes Memory-Objekt soll mit der Form "[<memory>]" an den StringBuilder mit append() angeheftet werden. Zum Schluss kann dann der komplette String mit der toString()-Methode vom StringBuilder zurückgegeben werden. Für 3 Memory-Objekte <mem0>, <mem1> und <mem2> würde dann der "[<mem0>][<mem1>][<mem2>]" zurückgegeben werden.
- Implementiere einen Setter für program.
### Robot Programmablauf
Als nächstes geht es um den Programmablauf. Der Roboter soll die Sensordaten verarbeiten, darauf aufbauend eine Liste aus Commands erstellen und dann ausführen. Das entspricht somit einem Simulationsschritt, also einem einzelnen Frame im Spiel. Alle Methoden außer der letzten sollen private sein:
- Implementiere zuerst die Methode void processSensor(Sensor<T>), welche den übergebenen Sensor auswertet und eventuell eine MalfunctionException werfen kann. Um den Sensor auszuwerten, sollen die Daten des Sensors einfach an seinen processor übergeben werden. Denk daran, was der processor ist und was er somit kann.
- Implementiere als nächstes die Methode void sense(), welche durch alle Sensoren iteriert und diese mit der zuvor implementierten Methode auswerten soll. Sollte einer der Sensoren dabei eine MalfunctionException werfen, musst du diese abfangen. Der betroffene Sensor soll aus der Liste entfernt werden und stattdessen soll ein neuer Sensor wieder an den Roboter angebracht werden, welcher exakt dasselbe kann wie der alte Sensor. Die Hilfsmethoden dazu hast du bereits implementiert oder existieren zumindest als abstrake Methode. Danach soll ein weiteres mal sense() rekursiv aufgerufen werden. Falls alle Sensoren ohne Exception ausgewertet wurden, soll gar nichts weiter passieren.
- Implementiere nun die Methode void think(), welche das program des Roboters ausführen soll und die dabei entstandenen Commands in seine todos aufnimmt.
- Implementiere die Methode void act(), welche die todos abarbeitet. Solange die Queue nicht leer ist, soll der nächste Command geholt und ausgeführt werden. Falls der Command false zurückgibt, sollst du die Methode allerdings vorzeitig abbrechen. Das hat für die Simulation den Vorteil, dass wir gleich mehrere Bewegungsbefehle auf einmal in die todos einfügen können, allerdings immer nur einer pro Frame ausgeführt wird, falls gewünscht. Dadurch bekommt der Roboter den Effekt als würde er gehen und sich nicht teleportieren.
- Implementiere als letztes die public Methode void work(). Falls die todo-Queue aktuell leer ist, sollen die Sensoren ausgewertet werden und über neue Befehle nachgedacht werden. In jedem Fall sollen danach die todos abgearbeitet werden.
Da haben wir es, einen funktionierenden programmierbaren Roboter. Jetzt müssen wir ihn nur noch für konkrete Aufgaben zusammenbauen.

## Part 2 - Verschiedene Roboter erstellen
Wir können einen neuen Roboter erstellen, Sensoren installieren, ihn programmieren und in der Welt platzieren. Die Welt ist ein zweidimensionales Raster aus 1m x 1m großen Blöcken, wobei jeder Block durch ein Zeichen repräsentiert wird. Das Zeichen '#' steht für eine Wand, die die Roboter nicht betreten können. Die Roboter können auf allen anderen Blocktypen laufen. Zusätzlich können wir einen Block mit einem der Zeichen '0' bis '9' markieren, um dann den Roboter auf diesem markierten Block zu platzieren. Jedes andere Zeichen kann vom Roboter beliebig (bzw. der Aufgabe entsprechend) interpretiert werden.


Der Ursprung des Weltkoordinatensystems (0; 0) liegt in der linken oberen Ecke der durch einen String beschriebenen Welt (siehe Beispiel), mit positiver x-Richtung nach Osten und positiver y-Richtung nach Süden. Richtungen sind im Bogenmaß definiert:
0 nach Osten/Rechts,
1
2
⋅
π
2
1
​
⋅π nach Süden/Unten,
π
π nach Westen/Links und
3
2
⋅
π
2
3
​
⋅π nach Norden/Oben
bzw. vielfache davon (ein Kreis entspricht
2
⋅
π
2⋅π). Du kannst zum einfachen umrechnen Math.toRadians(double) verwenden. Math.toRadians(180) würde bspw
π
π ergeben, also die Richtung nach Westen. Alle Blöcke, die nicht explizit durch den angegebenen String definiert sind, werden implizit als ' ' (ein Leerzeichen) betrachtet.

Nun können wir damit anfangen, die konkreten Roboter zu implementieren. Wir nutzen dazu die Klasse RobotFactory und die darin zu implementierenden statischen Methoden, welche unsere Roboter erstellen sollen. Du kannst im weiteren Verlauf alle Sensoren mit einer reliability von 0.97 initialisieren. Wie du die Sensoren oder auch die Memory-Objekte benennst bleibt dir überlassen.

## Panic Penguin
Starten wir mit einem ganz einfachen Roboter. Dieser soll im Kreis laufen und dabei alles ausgeben, was er auf dem Boden sieht. Damit wir den Boden scannen können, brauchen wir einen neuen Sensor.

### TerrainSensor
Implementiere die Klasse TerrainSensor, welche Sensor<Character> erweitert. So wie Sensor soll auch diese Klasse einen Konstruktor haben, welche einen String und einen double annimmt. Nun zu den 3 Methoden:
- Character getData(): Diese soll einfach genau das Zeichen zurückgeben, an dem der Roboter steht. Dafür könnte die Methode World.getTerrain() hilfreich sein. Wie du an die Welt rankommst, kannst du im Roboter herausfinden. Der Sensor kann außerdem kaputt gehen. Um das zu simulieren, gibt es im Roboter das Interface FailureSimulator. Darüber kannst du einen nächsten zufälligen Wert bekommen. Sollte dieser Wert größer als die reliability sein, soll eine neue MalfunctionException geworfen werden.
- Sensor<Character> createNewSensor(): Diese Methode soll eine Kopie von diesem TerrainSensor erstellen und zurückgeben. Name, Zuverlässigkeit und der Processor sollen dabei übernommen werden.
- String toString(): Diese soll einfach einen String nach folgendem Schema zurückgeben: "TerrainSensor⎵<name>".
### Panic Penguin
Nun können wir damit schon unseren ersten Roboter bauen und ausprobieren. Implementiere dazu in der Klasse RobotFactory die statische Methode Robot makePanicPenguin(String, double, FailureSimulator), welche einen name, eine rotation und einen failureSimulator annimmt und einen neuen Roboter zurückgibt. Der Roboter soll den übergebenen name und failureSimulator bekommen, eine Größe von 0.8 haben und nach Osten/Rechts (also 0) ausgerichtet sein. Der Roboter soll außerdem genau ein Memory<Character>-Objekt haben, und einen TerrainSensor, womit er den Untergrund lesen und im Speicher speichern kann. Letzteres ist Aufgabe des processors.
Nun zum Programm des Roboters: Man muss nur das Programm für einen Iterationsschritt in der Function definieren. Das Programm wird dann in jedem Frame ausgeführt, falls keine Befehle existieren (siehe Part 1). Bei der Ausführung soll der Roboter nun in jedem Frame ausgeben, welchen Untergrund er gerade sieht (World.say könnte hilfreich sein), sich um rotation drehen und 0.1m nach vorne gehen. Achte darauf, dass jeder Command true zurückgibt, damit diese 3 Schritte in einem Frame ausgeführt werden.

### Line Follower
Mit dem TerrainSensor können wir auch einen Linienverfolger programmieren. Implementiere dazu in der Klasse RobotFactory die statische Methode Robot makeLineFollower(String, double, FailureSimulator), welche einen name, eine direction und einen failureSimulator annimmt und einen neuen Roboter zurückgibt. Der Roboter soll den übergebenen name und failureSimulator bekommen, eine Größe von 0.8 haben und nach direction ausgerichtet sein. Genauso wie der Panic Penguin soll der Linienverfolger ein Memory<Character>-Objekt haben und einen TerrainSensor benutzen, um den Speicher zu beschreiben. Nun zum Programm: Falls der Roboter das Zeichen > liest, soll er sich nach rechts drehen und geradeaus laufen. Falls der Roboter das Zeichen v liest, soll er sich nach unten drehen und geradeaus laufen. Analog dazu gibt es noch die Fälle < und ^, zu denen er sich entsprechend verhalten soll. Falls die Linie unterbrochen wird, soll der Roboter einfach weiter geradeaus laufen, bis er die Linie wieder gefunden hat. Denk daran, dass der Befehl für das Geradeausgehen false zurückgeben kann, falls du mehrere hintereinander einfügst, damit sie nicht in einem Frame ausgeführt werden. Dadurch entsteht in der Simulation eine flüssigere Bewegung. In jedem Fall soll die maximal erlaubte Schritteweite pro Frame 1m nicht übersteigen.


### RC Car
Der Panic Penguin ist ganz witzig und für den Anfang sehr einfach, aber nicht besonders nützlich. Der Linienverfolger ist deutlich cooler, aber wir wollen noch mehr! Deswegen bauen wir nun einen ferngesteuerten Roboter. Dazu benötigen wir aber erst einen Sensor, welcher Funksignale wahrnehmen kann.

#### ControllerReceiver
Implementiere die Klasse ControllerReceiver, welche Sensor<World.Controller> erweitert. Der Konstruktor soll dabei genauso wie beim TerrainSensor funktionieren. Nun zu den 3 Methoden:
World.Controller getData(): Diese soll einfach das Controller-Objekt der Welt zurückgeben (World.getController() dürfte dir weiterhelfen). Im Fehlerverhalten soll sich die Methode genauso verhalten wie der TerrainSensor
Sensor<World.Controller> createNewSensor(): Diese Methode soll ähnlich wie der TerrainSensor einen neuen ControllerReceiver zurückgeben.
String toString(): Diese Methode soll einen String nach folgendem Schema zurückgeben: "ControllerReceiver⎵<name>".
#### RC Car 
Nun können wir damit einen ferngesteuerten Roboter bauen. Implementiere dazu in der Klasse RobotFactory die statische Methode Robot makeCar(String, FailureSimulator), welche einen name und einen failureSimulator annimmt und einen neuen Roboter zurückgibt. Der Roboter soll den übergebenen name und failureSimulator bekommen, eine Größe von 0.8 haben und nach Süden/Unten (also 90 Grad) ausgerichtet sein. Der Roboter soll außerdem einen ControllerReceiver-Sensor benutzen, um die Tastatur-Inputs zu verarbeiten. Du kannst dabei einfach im processor das World.Controller-Objekt benutzen, um zu überprüfen, ob eine bestimmte Taste gedrückt ist. Benutze bitte Controller.isKeyPressed(). Alle anderen Interface-Methoden werden von den Tests nicht verwendet! Die restlichen Methoden (z.B. die ganezn Mouse-Evenets) könnten dir aber bei der Bonusaufgabe helfen. Wir benutzen die Tasten W, A, S, D zum Steuern. Die entsprechenden Keycodes findest du als Konstanten in java.awt.event.KeyEvent und heißen VK_W, VK_A, VK_S und VK_D. A und D benutzen wir zum Lenken, W und S für die Fortbewegung. Falls A gedrückt wird, soll sich der Roboter um -0.1 drehen, für D analog dazu 0.1. Die beiden Werte sind bereits im Bogenmaß. Falls W gedrückt wird, soll sich der Roboter um 0.1m nach vorne bewegen, für S analog dazu -0.1m. Die Tasten sollen sich zudem nicht gegenseitig ausschließen, sondern unabhängig voneinander funktionieren. Falls also A und D bspw gleichzeitig gedrückt werden, sollen sie sich gegenseitig aufheben, da sich der Roboter gleichzeitig nach rechts und nach links dreht. Falls A und W gleichzeitig gedrückt werden, fährt das Auto nach vorne und lenkt nach links. Achte darauf, dass jeder Command dann true zurückgibt, damit diese beiden Schritte in einem Frame ausgeführt werden. Du darfst so viele Memory-Objekte benutzen wie du brauchst.
### Maze Runner
Damit wir unserem ursprünglichen Ziel näher kommen, wollen wir nun einen Maze Runner bauen. Dafür brauchen wir aber auch wieder einen weiteren Sensor, der diesmal seine physikalische Umgebung wahrnehmen kann.

ProximitySensor
Dieser Sensor wird am Roboter angebracht und kann damit die Distanz in seine Richtung bestimmen. Zum Beispiel kann man einen Sensor nach vorne ausgerichten, um die Distanz zum nächsten Block nach vorne zu messen. Einen weiteren Sensor kann man zur Seite ausrichten, um analog dazu die Distanz zum nächsten Block zur Seite zu messen. Implementiere die Klasse ProximitySensor, welche Sensor<Double> erweitert. Der Konstruktor soll die folgenden Parameter in dieser Reihenfolge annehmen:
- String name: der Name des Sensors
- double directionOffset: Eine Verschiebung der Blickrichtung des Roboters. Dadurch können wir bspw einen Sensor seitlich an den Roboter montieren.
- double accuracy: Da wir nicht kontinuierlich die komplette Strecke messen können, wollen wir das ganze diskret umsetzen. Die accuracy gibt uns die Schrittweite der Diskretisierung vor.
- double maxRange: Der Sensor soll nur eine maximale Sichtweite haben.
- double reliability: Die Zuverlässigkeit des Sensors.

name und reliability sollen an die Oberklasse weitergegeben werden. Für die anderen Parameter sollst du hier gleichnamige protected Attribute erstellen. createNewSensor() und toString() sollen sich genauso wie die anderen Sensoren verhalten, nur eben an den ProximitySensor angepasst. Nun zu Double getData(). Der Fehlerfall funktioniert genauso wie bei allen anderen Sensoren auch. Andernfalls müssen wir jetzt die Distanz messen. Die Idee funktioniert so: Wir erstellen eine Kopie von unserer Position und schieben sie in Blickrichtung des Sensors nach vorne, bis wir dann eine Wand gefunden haben oder die maximale Reichweite erreicht haben. Die Methode Robot.getPosition() ist so implementiert, dass sie dir eine Kopie zurückgeht, welche du somit komplett frei bewegen kannst. Nun können wir schrittweise die Position verschieben und überprüfen, ob wir an der Stelle in der Welt eine Wand (#) finden oder nicht. In jedem Fall geben wir am Schluss die gemessene Distanz zurück, also die Distanz bis zur Wand, oder die gemessene Distanz, bis wir maxRange überschritten haben. Da unsere Ausgangsposition zentriert im Roboter ist, müssen wir noch die Größe des Roboters berücksichtigen. Sonst würden wir von der Mitte aus messen, was allerdings Blödsinn wäre. Die Distanz von der Mitte bis zum Rand ist einfach die Hälfte des Durchmessers. Das soll sich aber nicht auf die maximale Reichweite der Sensoren auswirken. Falls der Sensor maximal 3m weit sehen kann und der Pinguin 1m dick ist, soll man von der Mitte aus gesehen 3.5m weit sehen können.

#### Maze Runner 
Mit dem ProximitySensor können wir nun einen Maze Runner bauen. Implementiere hierzu in der Klasse RobotFactory die statische Methode makeMazeRunner(String, double, FailureSimulator), welche einen name, eine direction und einen failureSimulator annimmt und einen neuen Roboter zurückgibt. Der Roboter soll den übergebenen name und failureSimulator bekommen, eine Größe von 0.8 haben und nach direction ausgerichtet sein. Der Roboter darf maximal 2 ProximitySensoren und beliebig viele Memory-Objekte benutzen, um das Labyrinth zu lösen. Der Ausgang ist dabei mit einem $ markiert. Du kannst davon ausgehen, dass das Labyrinth keine Inseln hat (die Wände sind also alle miteinander verbunden) und der Ausgang befindet sich immer direkt an einer Wand dran. Der Roboter darf keine Wände verändern und sich nicht teleportieren (die maximal erlaubte Schrittweite pro Frame ist 1m). Wie du den Ausgang findest, bleibt dir überlassen. Der Roboter muss den Ausgang mindestens einmal erreicht haben. Danach verdient der Roboter seine Freiheit und darf sich beliebig verhalten. 