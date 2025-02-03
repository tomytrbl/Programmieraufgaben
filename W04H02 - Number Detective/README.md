
#  **W04H02 - Number Detective**


### Aufgabenstellung: Number Detective 🐧💭🔢

Nach einem stressigen Monat an der PUM haben die Pinguine beschlossen, dass es an der Zeit ist, sich ein wenig zu entspannen und Spaß zu haben. Sie sind auf die Idee gekommen, ein lustiges Zahlenratespiel zu entwickeln, doch sie sind gerade nicht erfahren im Programmieren. Deshalb bitten sie dich um Unterstützung, um das Spiel zu implementieren.

In diesem Spiel hat der Spieler zu Beginn 3 Leben. Ziel des Spiels ist es, eine zufällig generierte Zahl zu erraten und Punkte sammeln. Der Spieler kann zwischen drei Schwierigkeitsgraden wählen. Je nachdem wird die gesuchte Zahl innerhalb eines bestimmten Bereichs liegen, und er erhält eine festgelegte Anzahl an Versuchen. Der Schwierigkeitsgrad beeinflusst auch, wie viele Punkte und Leben der Spieler für das richtige Raten der Zahl erhält.

Zahl Anzahl Versuche	Gewinn

Einfach	[0;100)	8	+200 Punkte

Mittel	[0;500)	10	+200 Punkte +1 Leben

Schwer	[0;1000)	10	+500 Punkte +3 Leben

Der Spieler erhält nach jedem Versuch immer Hinweise, ob die gesuchte Zahl höher oder niedriger als seine Eingabe ist. Wenn der Spieler die geheime Zahl nicht innerhalb der verfügbaren Versuche findet, verliert er ein Leben. Wenn der Spieler alle Leben verloren hat, wird das Spiel beendet.

## Allgemeine Hinweise

- Um zufällige Zahlen zu generieren, musst du die schon implementierte Klasse /src/pgdp/RandomNumberGenerator.java und deren generate(int upperBound)-Methode benutzen. Diese Methode nimmt einen Parameter upperBound >
0 entgegen und generiert eine Zahl im Intervall [0;upperBound). An dieser Klasse darfst du nichts ändern.
- Zum Testen deiner Implementierung kannst du Seeds nutzen. Bei gleichem Seed und gleicher Reihenfolge der Aufrufe sind auch die zurückgegebenen Zufallszahlen identisch. Den Seed sollst du nur zum Testen verwenden, nicht aber für die finale Abgabe.
- Zum Einlesen von Nutzereingaben verwende die readInt()-Methode aus der Klasse /src/pgdp/InputReader.java. Du kannst davon ausgehen, dass der Spieler nur Ganzzahlen eingeben wird.
- Die Verwendung privater Hilfsmethoden und Hilfsvariablen ist erlaubt. Die guessTheNumber()-Methode muss aber ohne zusätzliche Aufrufe funktionieren. Außerdem gilt zu beachten, dass die Tests mehrere Methodenaufrufe hintereinander ausführen und statische Hilfsvariablen dazwischen nicht zurückgesetzt werden.
- In der Aufgabenstellung wird in jedem String das Zeichen ⎵ verwendet, um Leerzeichen zu kennzeichnen. Ersetze sie dann später im Code durch ein richtiges Leerzeichen.

## Guess The Number
Deine Aufgabe ist es, die Methode guessTheNumber() in der Klasse /src/pgdp/game/GuessTheNumber.java zu implementieren.

## Spielstart & Ende 5 of 5 tests passing
Wenn ein Nutzer das Spiel startet, wird er auf der Konsole mit "Hello,⎵Number⎵Detective!" begrüßt. Anschließend hat er die Möglichkeit, einen Schwierigkeitsgrad auszuwählen. Dazu wird ihm seinen aktuellen Lebens- und Punktestand ("You⎵have⎵<Lebensstand>⎵lives⎵and⎵<Punktestand>⎵points.") angezeigt, gefolgt von einer Liste von möglicher Eingaben:

Choose⎵difficulty⎵level⎵to⎵start⎵a⎵new⎵game:
(1)⎵Easy⎵⎵⎵[0;100)⎵⎵⎵8⎵Attempts,⎵Reward:⎵+200⎵Points
(2)⎵Medium⎵[0;500)⎵⎵10⎵Attempts,⎵Reward:⎵+200⎵Points⎵+1⎵Life
(3)⎵Hard⎵⎵⎵[0;1000)⎵10⎵Attempts,⎵Reward:⎵+500⎵Points⎵+3⎵Lives
(4)⎵Exit
Hinweis: printMenu()-Methode!

Gibt der Spieler eine Zahl ein, die nicht zu den möglichen Optionen gehört, wird die Fehlermeldung "This⎵was⎵not⎵a⎵valid⎵choice,⎵please⎵try⎵again." ausgegeben, und das Programm wartet erneut auf eine Eingabe, bis der Spieler eine gültige Option wählt. Falls der Spieler die vierte Option wählt, um das Spiel zu verlassen, wird auf der Konsole die Nachricht Goodbye! ausgegeben.

Sobald das Spiel endet, entweder durch das Verlassen des Spiels oder durch das Aufbrauchen aller Leben, wird dem Spieler sein finaler Punktestand angezeigt: "You⎵are⎵leaving⎵with⎵<Punktestand>⎵points!"

## Spielablauf 
11 of 11 tests passing
Basierend auf der Auswahl des Schwierigkeitsgrads wird dann eine zufällige Zahl generiert und der Spieler hat eine festgelegte Anzahl an Versuchen, um die Zahl zu erraten. Vor jedem Versuch wird angezeigt, wie vielte der aktuelle Versuch ist: "(<X>/<Y>)⎵Enter⎵your⎵guess:", wobei X die aktuelle Versuchsnummer und Y die maximale Anzahl der Versuche ist. Dann wird eine Eingabe vom Spieler erwartet. Nach jeder Eingabe erfährt der Spieler, ob die gesuchte Zahl höher ("The⎵number⎵is⎵higher.") oder niedriger ("The⎵number⎵is⎵lower.") als seine Eingabe ist.

Wenn der Spieler die richtige Zahl errät, wird die Runde mit der Erfolgsmeldung "Congrats!⎵You⎵guessed⎵the⎵correct⎵number." abgeschlossen, und er erhält die entsprechenden Preise. Sollte der Spieler alle Versuche aufgebraucht haben, ohne die richtige Zahl zu erraten, wird die gesuchte Zahl angezeigt ("Sorry,⎵you've⎵used⎵all⎵attempts.⎵The⎵correct⎵number⎵was⎵<gesuchte Zahl>.") und er verliert ein Leben. Falls der Spieler keine Leben mehr hat, wird er mit der Nachricht "Game⎵over!⎵You⎵are⎵out⎵of⎵lives." darüber informiert, dass das Spiel beendet ist.

Hat der Spieler jedoch noch Leben übrig, wird ihm sein aktueller Punktestand und Lebensstand angezeigt, gefolgt vom Menü, damit er einen neuen Schwierigkeitsgrad auswählen kann, um eine neue Runde zu starten.

### Hinweis
Sollte der Spieler im letzten Versuch angekommen sein, hat er, sofern er genügend Punkte hat, die Möglichkeit, für 600 Punkte einen speziellen Hinweis zu kaufen, der ihm verrät, ob die gesuchte Zahl gerade oder ungerade ist. Vor dem letzten Versuch wird der Spieler gefragt: "LAST⎵ATTEMPT!⎵Do⎵you⎵want⎵to⎵buy⎵a⎵hint⎵for⎵600⎵points?⎵(1)⎵yes⎵(2)⎵no". Sollte der Spieler eine andere Eingabe machen, wird ihm die Fehlermeldung "This⎵was⎵not⎵a⎵valid⎵choice,⎵please⎵try⎵again." angezeigt, und das Programm wartet auf eine gültige Eingabe. Wenn der Spieler den Hinweis kauft, wird eine der folgenden Meldungen ausgegeben: "The⎵number⎵is⎵even!" oder "The⎵number⎵is⎵odd!". Dann wird der Spieler aufgefordert, seine letzte Schätzung abzugeben.

Beispiel 1 of 1 tests passing
Beispiel mit Seed 1304 (zum Ausklappen hier klicken)

## Games 
2 of 2 tests passing
Nun wird alles zusammengetestet. Diese Tests simulieren vollständige Spielsitzungen. Um diese Tests zu bestehen, müssen alle Teile des Spiels korrekt zusammenarbeiten. Vergiss nicht: Du kannst deine Implementierung testen, indem du die main-Methode ausführst. So kannst du eventuelle Probleme in deiner Implementierung einfach beim Spielen des Spiels entdecken!