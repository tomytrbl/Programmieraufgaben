#  **W12H03 - PSA Shuttle Computer**


### Aufgabenstellung

Die PSA (Pingu Space Agency) entwickelt derzeit ein neues Spaceshuttle und steht vor einer besonderen Herausforderung: Der Bordcomputer muss nicht nur leistungsstark sein, sondern auch absolut zuverlässig arbeiten. Um dies zu gewährleisten, setzt die PSA auf ein innovatives Multithreading-Konzept, bei dem mehrere Prozessoren parallel die gleichen Berechnungen durchführen. Deine Aufgabe wird es sein, die verschiedenen Thread-Komponenten zu implementieren und dabei besonders auf korrekte Synchronisation zu achten. Die redundante Berechnung ist zwingend erforderlich, da die intensive Strahlung im All zu sogenannten Bitflips führen kann. Das sind zufällige Änderungen einzelner Bits im Speicher, die falsche Berechnungsergebnisse verursachen können (siehe hierzu dieses sehr empfehlenswerte Video, kein Muss für diese Aufgabe!).

#### Struktur und allgemeine Funktionsweise
Der ShuttleComputer, den du implementieren sollst, basiert auf einem verteilten System von Threads, die das ShuttleComputerComponent-Interface implementieren. Im Zentrum steht der TaskDistributer-Thread, der mithilfe eines TaskGenerators eine Anzahl von Berechnungsaufgaben (ShuttleTasks) erzeugt. Diese Aufgaben werden an vier ShuttleProcessor-Threads weitergeleitet (die Anzahl ist konfigurierbar), die jeweils unabhängig voneinander die gleiche Berechnung durchführen. Ein TaskChecker-Thread vergleicht die Ergebnisse der verschiedenen Prozessoren und akzeptiert ein Resultat nur dann, wenn mindestens drei identische Ergebnisse vorliegen. Der ShuttleOutput-Thread gibt dann die Ergebnisse aus.

Der Shuttle Computer produziert also Aufgaben, die dann parallel gelöst werden. Anschließend werden die Ergebnisse überprüft und ggf. eine erneute Lösung in die Wege geleitet. Korrekt gelöste und überprüfte Aufgaben werden schließlich auf der Konsole ausgegeben.

### Template
Im Template findest du bereits eine vollständige Implementierung von ShuttleTask, das Interface TaskGenerator, sowie zwei Implementierungen davon: ErrorlessTaskGenerator, dessen ShuttleTasks immer das richtige Ergebnis berechnen; und ErrorProneTaskGenerator, dessen ShuttleTasks mit einer übergebenen Wahrscheinlichkeit ein falsches Ergebnis zurückgeben (um die Bitflips zu simulieren). Solltest du eine eigene lustige oder interessante Idee für einen TaskGenerator haben, darfst du diesen gerne auch schon vor der Deadline der Aufgabe öffentlich teilen, solange (wie immer) nichts von der Implementierung der eigentlichen Aufgabe verraten wird.
Weiterhin enthält das Template bereits Implemenierungen der übrigen bisher beschriebenen Klassen, die du beliebig erweitern darfst (die Konstruktor und Methoden-Signaturen, die bereits vorgegeben sind, müssen aber gleich bleiben!). Deine Aufgabe ist es, die Methoden shutDown und run in diesen Klassen zu implementieren. Weitere Informationen findest du im Punkt "Aufgaben". In der Klasse ShuttleComputer findest du außerdem eine main-Methode, mit der du deine Implementierung ausprobieren kannst.

### Allgemeine Hinweise
- Am besten liest du dir die ganze Aufgabenstellung einmal durch, bevor du mit der Implementierung der einzelnen Teilaufgaben beginnst
- "⎵" dient wie immer nur der besseren Lesbarkeit und soll in der tatsächlichen Ausgabe durch ein Leerzeichen ersetzt werden.
- shutDown muss für jede Objekt-Instanz nur einmal funktionieren
- run muss für jede Objekt-Instanz nur einmal funktionieren
- Die Klassen (außer ShuttleComputer !!) werden unabhängig voneinander getestet
- Deine Implementierung darf bei der Interaktion mit Objekten aus dem Package pgdp.shuttle.tasks nur die vorgegebenen Methoden verwenden und diese dürfen nicht verändert werden.
- Sollte ein Test fehlschlagen und "This could be an Artemis issue." beinhalten, ist es möglich, dass das Problem an Artemis und nicht an deiner Implementierung liegt. In solch einem Fall solltest du 1.) deinen Code nochmal genau überprüfen, ob du nicht doch einen Fehler findest, und 2.) etwas abwarten und nochmal pushen (möglicherweise löst sich dadurch der Fehler von selbst)
- Falls ein Thread gerade nichts zu tun hat, soll er warten. Ebenso soll ein Thread informiert werden, falls er wieder etwas zu tun bekommt. (Stichwort wait und notify)
- Falls andere Exceptions auftreten als InterruptedExceptions wird kein spezielles Verhalten erwartet.
- Du musst dir keine Gedanken machen, ob im Konstruktor initialisierte Variablen null sein könnten.

### Aufgaben
Wie bereits oben beschrieben musst du die Methoden shutDown und run implementieren. Allgemein gilt für die Methoden:

- shutDown: beendet den Thread. Das Beenden soll "auf natürliche Weise", d.h. ohne Exceptions, etc., sondern durch verlassen der run-Methode erreicht werden. Dies muss nur einmal pro Objekt-Instanz funktionieren.
- run: übernimmt die im folgenden definierten Aufgaben je Klasse. Muss nur einmal pro Objekt-Instanz funktionieren. Wenn nicht anders definiert, wartet diese Methode auch immer, wenn sie gerade nichts zu tun hat.

TaskDistributer Console-Output 2 of 2 tests passing
TaskDistributer Distribution 1 of 1 tests passing

Diese Klasse übernimmt im Konstruktor die Anzahl der zu generierenden Aufgaben (tasksToGenerate), eine Liste von ShuttleProcessoren (processors) und einen TaskGenerator (generator). In der run-Methode sollen solange ShuttleTasks mit dem Generator erstellt und an alle Prozessoren mit normaler Priorität verteilt werden (jeder Prozessor erhält jede Task, zum Verteilen existiert die Methode addTask), bis entweder tasksToGenerate Aufgaben erstellt und verteilt wurden, oder der TaskDistributer mittels shutDown beendet wird. In letzterem Fall soll aber immer eine schon generierte Task auf alle Prozessoren fertig verteilt werden. Weiterhin sollen folgende Ausgaben auf der Konsole getätig werden:
- Direkt beim Starten: "TaskDistributer⎵starting⎵to⎵generate⎵tasks."
- Bei normalem Beenden (sobald die maximale Anzahl and Tasks generiert wurde oder shutDown aufgerufen wurde): "TaskDistributer⎵finished⎵generating⎵<Anzahl tatsächlich generierter Tasks>/<tasksToGenerate>⎵tasks.⎵Shutting⎵down."
- Beim Auftreten einer InterruptedException statt der Nachricht zum normalen Beenden: "TaskDistributer⎵was⎵interrupted⎵after⎵<Anzahl tatsächlich generiertet (und fertig verteilter) Tasks>⎵tasks!"

ShuttleProcessor Console-Output 1 of 1 tests passing
ShuttleProcessor Runbehavior 1 of 1 tests passing

Diese Klasse verwaltet ShuttleTasks in zwei Warteschlagen, einer mit normaler (taskQueue) und einer mit hoher Priorität (priorityTaskQueue). In der run Methode sollen ShuttleTasks evaluiert (evaluate()) und an den TaskChecker weitergegeben werden (checker.addTask()). Dabei sollen ShuttleTasks aus der priorisierten Warteschlange bevorzugt werden. Ein ShuttleProcessor soll nur durch einen Aufruf von shutDown oder eine InterruptedException beendet werden. Sollte bei einem Aufruf von shutDown bereits eine ShuttleTask evaluiert worden sein, soll diese noch and den TaskChecker weitergegeben werden. Außerdem sollen noch folgende Ausgaben auf der Konsole getätigt werden:
- Bei normalem Beenden (durch shutDown): "ShuttleProcessor⎵shutting⎵down."
- Beim Auftreten einer InterruptedException statt der Nachricht zum normalen Beenden: "ShuttleProcessor⎵was⎵interrupted.⎵Shutting⎵down."

TaskChecker Console-Output 1 of 1 tests passing
TaskChecker Runbehavior 0 of 1 tests passing

Diese Klasse überprüft in der run-Methode, ob sich die verschiedenen ShuttleProcessoren auf ein Ergebnis für die ShuttleTask "einigen" konnten. Voraussetzung dafür ist, dass mindestens vier Prozessoren die Task evaluiert haben. Falls dies nicht der Fall ist, kann die ShuttleTask erstmal ignoriert werden, bis sie wieder in der taskQueue landet. Die ShuttleProcessoren "einigen" sich auf ein Ergebnis, wenn mindestens dreimal dasselbe Ergebnis berechnet wurde. In diesem Fall wird result der ShuttleTask auf den ensprechenden Wert gesetzt und die Task an den ShuttleOutput weitergegeben (Hinweis: jede ShuttleTask soll nur genau einmal an den ShuttleOutput weitergegeben werden). Andernfalls wird die ShuttleTask zurückgesetzt und wieder auf die Prozessoren verteilt, um neu berechnet zu werden. Die Neuberechnung hat dabei hohe Priorität. ShuttleTask bietet bereits alle nötigen Methoden, um dieses Verhalten umzusetzen. Ein TaskChecker soll nur durch einen Aufruf von shutDown oder eine InterruptedException beendet werden. Angefangene Überprüfungen werden vor dem Beenden mit shutDown noch zu Ende geführt. Außerdem sollen noch folgende Ausgaben auf der Konsole getätigt werden:
- Bei normalem Beenden (durch shutDown): "TaskChecker⎵shutting⎵down."
- Beim Auftreten einer InterruptedException statt der Nachricht zum normalen Beenden: "TaskChecker⎵was⎵interrupted.⎵Shutting⎵down."

ShuttleOutput Console-Output 1 of 1 tests passing
ShuttleOutput Runbehavior 1 of 1 tests passing

Diese Klasse ist letztlich dafür verantwortlich die Ergebnisse fertiger ShuttleTasks auf der Konsole auszugeben. Dies soll natürlich in der run-Methode geschehen. Außerdem soll auch ShuttleOutput nur durch einen Aufruf von shutDown oder eine InterruptedException beendet werden. Angefangene Ergebnis-Ausgaben werden vor dem Beenden mit shutDown noch vollendet. Diese Klasse tätigt folgende Konsolenausgaben:
- Beim Output eines Ergebnisses: "Result:⎵<toString-Repräsentation des Ergebnisses>"
- Bei normalem Beenden (durch shutDown): "ShuttleOutput⎵shutting⎵down."
- Beim Auftreten einer InterruptedException statt der Nachricht zum normalen Beenden: "ShuttleOutput⎵was⎵interrupted.⎵Shutting⎵down."

- ShuttleComputer Console-Output 1 of 1 tests passing
ShuttleComputer Runbehavior 0 of 1 tests passing

Schließlich fehlt noch die Klasse ShuttleComputer. Diese soll in der run-Methode einen TaskDistributer, vier ShuttleProcessor, einen TaskChecker und einen ShouttleOutput initialisieren (tasksToGenerate und der benötigte TaskGenerator werden im Konstruktor initialisiert). Diese Komponenten sollen dann parallel ausgeführt werden. Nach sleeptime Millisekunden soll der TaskDistributer wieder heruntergefahren werden (falls er nicht schon fertig und damit heruntergefahren ist; trotzdem soll immer die volle sleeptime gewartet werden) und anschließend auch alle anderen ShuttleComputerComponenten. Nachdem alle Threads beendet sind, soll sich auch der ShuttleComputer beenden. Während der Ausführung tätigt ShuttleComputer folgende Ausgaben auf der Konsole:
- Zu Beginn: "ShuttleComputer⎵booting⎵up."
- Bei normalem Beenden ganz zum Schluss: "ShuttleComputer⎵shutting⎵down."
- Falls eine InterruptedException auftritt, während auf den TaskDistributer gewartet wird, anstatt der normalen Schluss-Nachricht: "ShuttleComputer⎵crashed⎵(interrupted⎵while⎵waiting⎵for⎵TaskDistributer)!"
- Falls eine InterruptedException auftritt, während auf das Beenden der übrigen ShuttleComputerComponenten gewartet wird, anstatt der normalen Schluss-Nachricht: "ShuttleComputer⎵crashed⎵(interrupted⎵while⎵waiting⎵for⎵ShuttleComputerComponent)!"