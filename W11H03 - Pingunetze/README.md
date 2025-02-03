#  **W11H03 - Pingunetze**


### Aufgabenstellung: PinguNetze

Die Pinguine der PUM haben kürzlich Threads kennengelernt. Begeistert von der neuen Fähigkeit, parallel arbeitende Prozesse zu modellieren, haben sie sich ein eigenes System ausgedacht, um größere Systeme zu simulieren: Pingunetze.

Ein Pingunetz ist ein abstraktes Modell zur Beschreibung und Simulation von Systemen, die aus parallelen Prozessen bestehen. Es ist ein Netz (ähnlich wie ein Graph) auf dem Tokens sich von einer Stelle zur nächsten bewegen. Die Stellen sind durch Transitionen verbunden, die bestimmen wann und wie sich die Tokens bewegen können. Da die Transitionen unabhängig voneinander aktiviert werden können, ist ein Pingunetz ein paralleles System.

Ein Pingunetz besteht aus also drei zentralen Komponenten:

- Stellen (Places): Diese sind Puffer, die Tokens (repräsentiert durch Zahlen) enthalten. Sie kontrollieren den Fluss im System.
- Transitionen (Transitions): Das sind die aktiven Elemente, die Tokens aus den Stellen konsumieren und diese weiterverarbeiten.
- Kanten (Edges): Diese verbinden Stellen mit Transitionen und haben ein Gewicht, das angibt, wie viele Tokens durch sie fließen können. 
### Funktionsweise eines Pingunetzes
1. Stellen (Places): Stellen können eine Anzahl von Tokens enthalten, die von Transitionen verwendet werden. Eine Transition kann nur "schalten" (aktiviert werden), wenn alle Stellen, die mit ihr verbunden sind, genügend Tokens bereitstellen.

2. Transitionen (Transitions): Transitionen verbrauchen Tokens aus eingehenden Stellen entsprechend den Gewichten der eingehenden Kanten und fügen den ausgehenden Stellen Tokens hinzu. Eine Transition wird nur aktiviert, wenn alle ihre eingehenden Stellen die erforderliche Anzahl an Tokens bereitstellen können.

3. Kanten (Edges): Die Kanten legen fest, wie viele Tokens zwischen einer Stelle und einer Transition fließen. Es gibt zwei Arten von Kanten:
    - Von einer Stelle zu einer Transition.
    - Von einer Transition zu einer Stelle.

### Beispiele:
In den folgenden Beispielen repräsentieren die Kreise die Stellen und die Boxen die Transitionen.

Hier seht ihr ein simples PinguNet, bestehend aus zwei Stellen und einer Transition. Wie ihr sehen könnt, ist dieses deterministisch, da die Token alle nur von P1 zu P2 "fließen" können.

Jetzt wird es schon etwas spannender. Hier seht ihr ein simples nicht deterministisches PinguNet. Wir haben am Anfang zwei Token in P1. Diese können entweder zu P2 oder P3. Wo die Token landen, hängt davon ab, welcher der Transitionen T1 und T2 als erstes aktiviert, eine feste Reihenfolge ist nicht gegeben. Das sind alle möglichen Endzustände des PinguNets.

Durch diese Modellierung können die Pinguine komplexe Systeme mit parallelen Prozessen und kontrolliertem Ressourcenfluss abbilden. Die grundlegende Implementierung haben die Pinguine dir schon bereits vorgegeben, aber die Implementierung ist noch nicht sicher vor Race Conditions und Deadlocks! Es ist nun deine Aufgabe, deinen Pinguin-Freunden unter die Flossen zu greifen.

Bevor du mit der Implementierung beginnst, solltest du dir unbedingt alle gegebenen Klassen einmal anschauen. Die Stellen für deinen Code sind auch nochmal extra mit einem "TODO" markiert.

### Hinweise und Tipps
- Wenn eine Exception geworfen werde soll, ist die mitgegebene Nachricht für die Tests irrelevant, könnten aber euch beim Debuggen helfen. Insbesondere könnte auch die Methode Thread::setUncaughtExceptionHandler helfen.
- Methode, Felder und Klassen die keine "TODOs" enthalten dürfen auf keinen Fall verändert werden!
- Nur die "Concurrent" Tests geben Punkte! Damit ihr aber seht, ob eure Klassen sequentiell funktionieren, haben wir euch ein paar Tests gegeben als Hilfestellung.
- Ein Timeout in den Tests bedeutet in den meisten Fällen, dass eure Implementierung einen Deadlock produziert.
### Die Klasse Place
Die Klasse Place ist das Herzstück unseres Programmes. Dort passiert auch der Hauptteil der Synchronisierung. In der Vorgabe bekommt ihr bereits die Mindestanforderung an die Struktur der Klasse gegeben. Ihr dürft die in der Vorgabe gegebenen Signaturen, Felder und den Konstruktor auf keinen Fall verändern! Ansonsten werden die Tests fehlschlagen.

1.  Die Methode lockPlace():
Diese Methode ist der Synchronisationsmechanismus der Klasse Place. Durch das Aufrufen dieser Methode soll es möglich sein das Schloss (Lock) auf die Instanz zu bekommen auf der es aufgerufen wird. Sollte ein weiterer Thread diese Methode aufrufen nachdem ein anderer Thread bereits das Schloss bekommen hat, so soll es warten bis das Schloss frei ist und erst weitermachen, wenn es auch Zugang zum Schloss bekommt. Ein Blick in die Folien dieser Woche könnte sehr hilfreich sein...

2. Die Methode unlockPlace:
Analog zu der Methode lockPlace soll diese Methode einem Thread die Möglichkeit geben das Schloss wieder abzugeben. Sollte diese Methode von einem Thread aufgerufen werden, der nicht das Schloss hält, so soll eine IllegalStateException geworfen werden.

3. Die Methode hasEnoughTokens(int):
Diese Methode soll überprüfen und zurückgeben, ob genug tokens vorhanden sind. Sollte diese Methode von einem Thread aufgerufen werden, der nicht das Schloss hält, so soll eine IllegalStateException geworfen werden. Die Nachricht in der Exception ist dabei egal.

4. Die Methode consumeToken(int):
Diese Methode soll die übergeben Menge an tokens konsumieren, falls genug tokens da sind. Sollten nicht genug tokens vorhanden sein, so soll hier eine IllegalArgumentException geworfen werden. Sollte diese Methode von einem Thread aufgerufen werden, der nicht das Schloss hält, so soll eine IllegalStateException geworfen werden. Die Nachricht in der Exception ist dabei egal.

5. Die Methode addTokens(int):
Diese Methode soll die übergebene Menge an tokens hinzufügen. Place hat dabei aber kein oberes Limit und du musst Overflows nicht beachten. Sollte diese Methode von einem Thread aufgerufen werden, der nicht das Schloss hält, so soll eine IllegalStateException geworfen werden. Die Nachricht in der Exception ist dabei egal.

Place Helper-Tests 8 of 8 tests passing
Place Grading-Tests 4 of 4 tests passing
### Die Klasse ProducerTransition
Diese Klasse soll eine primitive Transition modellieren. Wie der Name schon verrät soll diese Transition keinerlei Token konsumieren, sondern nur welche produzieren. In der Vorgabe bekommt ihr bereits die Mindestanforderung an die Struktur der Klasse gegeben. Ihr dürft die in der Vorgabe gegebenen Signaturen, Felder und den Konstruktor auf keinen Fall verändern! Ansonsten werden die Tests fehlschlagen.

Der Klasse fehlt nurnoch die Implementierung der tryFire()-Methode. Diese Methode soll bei allen Places die man über die ausgehenden Kanten (Edge) erreichen kann tokens hinzufügen. Die Menge entspricht dabei dem Gewicht (weight) der Kante. Der Rückgabewert der tryFire() Methode soll dabei singalisieren, ob die Methode erfolgreich war. Achte darauf, dass die Implementierung thread-safe ist und nicht zu Race-Conditions oder Deadlocks führt!

ProducerTransition Grading-Tests 1 of 1 tests passing
### Die Klasse BasicTransition
Diese Klasse soll eine komplexere Transition mit beliebigen Aus- und Eingangskanten modellieren. Auch hier fehlt nur noch die tryFire()-Methode. Diese Methode soll erst alle Places von den eingehenden Kanten abschließen (locken), dann überprüfen ob genug tokens enthalten sind. Sollte ein Place nicht genug tokens haben, sollen alle Places wieder aufgeschlossen werden (unlocked) und ein entsprechender Rückgabewert zurückgegeben werden. Sollten alle Places genug token haben, sollen von allen eingehenden Kanten, entsprechend dem Gewicht der Kante die Tokens eingesammelt werden. Danach müssen noch die Places der ausgehenden Kanten entsprechend dem Gewicht der Kanten gefüllt werden. Anschließend müssen natürlich alle Places wieder aufgeschlossen (unlocked) werden. Achte darauf, dass die Implementierung thread-safe ist und nicht zu Race-Conditions oder Deadlocks führt!

BasicTransition Helper-Tests 1 of 1 tests passing
BasicTransition Grading-Tests 2 of 2 tests passing
### Die Klasse PinguNet
Hier kommt nun alles zusammen. Vieles in der Klasse ist schon vorgegeben, aber du musst den Konstruktor noch vervollständigen. Das Feld transitionThreads soll im Konstruktor mit Threads initialisiert werden die jeweils eine Transition darstellen.

Die Methode start():
Diese Methode soll alle transitionThreads starten.

Die Methode stop():
Diese Methode soll das PinguNet stoppen indem es alle transitionThreads stoppt. Ein Blick in die Klasse Transition könnte hilfreich sein.

PinguNet Grading-Tests 2 of 2 tests passing
#### Graphviz
In der Graphviz Klasse findet ihr bereits implementierte Methoden, um die PinguNets als Graph zu repräsentieren. Wie der Graph aussiehen soll, der in der main Methode steht, seht ihr in pingunetz.txt. Das kann man in Online Viewern wie https://dreampuf.github.io/GraphvizOnline/ einfügen und sehen, wie die Pingunetze aussehen.

Viel Erfolg beim Implementieren! Die Pinguine freuen sich darauf, ihre Pingunetze in Aktion zu sehen! 🐧