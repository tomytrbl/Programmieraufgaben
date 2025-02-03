#  **W11H02 - Parallele Baumsumme**


### Aufgabenstellung
#### Das Ziel
In dieser Aufgabe wollen wir die Summe über einen Binärbaum parallelisiert berechnen. Zur Vereinfachung lassen wir die Wrapper-Klasse weg und arbeiten direkt mit den Knoten, wie sie in der Node Klasse definiert sind.

In der Klasse Main ist bereits Code vorgegeben mit dem du deine Implementierung testen kannst. Diesen musst du natürlich für unterschiedliche Fälle anpassen z.B. um mit einer anderen Anzahl an Threads zu arbeiten. Überlege dir außerdem, welche Edgecases bei einer Multithreaded-Applikation interessant sein könnten.

### Aufsummieren ohne Parallelisierung
1. Implementiere die Methode sum in der Klasse Node 1 of 1 tests passing
Zuerst berechnen wir die Summe ohne Threads. Diesmal verwenden wir für die Kinderknoten Optionals. Optional ist ein Container-Type, der es uns erlaubt, die Abwesenheit eines Wertes darzustellen, ohne null nutzen zu müssen. Du solltest ihn noch aus der letzten Woche kennen. Optional können wir also verwenden, um die Kinderknoten darzustellen; es müssen ja nicht alle Knoten links und rechts einen Kinderknoten haben. Ansonsten ändert sich nichts.
Hinweis: Es ist umstritten, ob diese Art von Verwendung der Optionals zu den Best-Practices gehört. Du kannst dich hier weiterinformieren.

### Parallelisierung
In diesem Schritt wollen wir die Berechnung mithilfe der Klasse SumThread parallelisieren, indem wir die Summe von jedem Teilbaum in einem neuen Thread berechnen bis wir die erwünschte Anzahl der Threads erreichen. Das Ergebnis jeder Berechnung wird im Feld sum gespeichert, das zu Beginn des Threads noch keinen Wert enthält. Aus diesem Grund ist auch dieses Feld vom Typ Optional.

Zur Begrenzung der Gesamtanzahl der Threads wird jeder SumThread mit der Variable remainingThreads initialisiert. Diese gibt die maximale Anzahl an Threads an, die in dieser Teilberechnung inklusive des aktuellen Threads verwendet werden dürfen.

2. Implementiere den Konstruktor 1 of 1 tests passing 
Implementiere den Konstruktor public SumThread(Node node, int remainingThreads) in der Klasse SumThread. Initialisiere die Attribute sum, node und remainingThreads mit sinnvollen Werten. Mit den Threads soll in diesem Schritt noch nichts passieren.
3. Implementiere leftThreadCount() 1 of 1 tests passing
Implementiere die Methode protected int leftThreadCount() in der Klasse SumThread. Sie soll die Anzahl der verbleibenden Threads für den linken Teilbaum berechnen. Die Threads werden noch nicht gestartet. Falls es einen rechten und linken Teilbaum gibt, soll die Anzahl an Threads möglichst gleichmäßig auf beide Teilbäume aufgeteilt werden. Wenn beide Teilbäume existieren und die Threads nicht gleichmäßig aufgeteilt werden können, soll die rechte Seite einen Thread mehr zugeteilt bekommen als die linke Seite. Wenn ein Teilbaum leer ist, werden für diesen keine Threads reserviert.
4. Implementiere startChildThreads() 1 of 1 tests passing
Implementiere die Methode protected void startChildThreads() in der Klasse SumThread. Berechne dafür zuerst die Anzahl der Threads für den rechten Teilbaum und erzeuge neue SumThreads für die Teilbäume. Achte auf die Mitgabe des richtigen Nodes und der richtigen Anzahl an Threads für den jeweiligen Teilbaum. Rufe auf den neu erzeugten SumThreads die Methode start() auf. Die Methode soll auch funktionieren, wenn einer der zwei Teilbäume leer ist.
5. Implementiere run() 1 of 1 tests passing
Implementiere die Methode public void run() in der Klasse SumThread. Falls es keinen Thread für den Teilbaum gibt, der Teilbaum aber existiert, soll die Summe von diesem Teilbaum ohne Threads berechnet werden. Ansonsten erhälst du den Wert aus dem zugehörigen Thread. Speichere dein Endergebnis wieder in dem Attribut sum des aktuellen Nodes.
### Freies Spiel
Glückwunsch, dass du es so weit geschafft hast. Der Rest dieser Aufgabe ist zum freien Experimentieren da, und es gibt keine automatisierte Tests dazu.

Jetzt kannst du dir wieder die Main-Klasse anschauen und die Laufzeiten vergleichen. Experimentiere mit unterschiedlichen Baumgrößen. Ab wann lohnt sich der Verwaltungsaufwand für die Parallelisierung?

Um dir einen besseren Überblick zu schaffen, kannst du die Main-Klasse mit einer Methode ergänzen, die als Parameter die Anzahl der Threads und die Baumgröße entgegennimmt und die Bearbeitungsdauer zurückgibt.

Überlege dir als letztes, ob die gegebene Anzahl an Threads immer erzeugt wird. Kannst du einen Baum konstruieren, in dem es möglichst ineffizient läuft? Wie vergleichen sich die Zeiten jetzt?