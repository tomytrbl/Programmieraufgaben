#  **W12H01 - Penguin Kitchen**


### Aufgabenstellung

#### Penguin Kitchen
Die Pinguine der PUM haben ein Problem: Die Vorlesungen haben wieder angefangen und tausende hungrige Pinguine stürmen täglich die Kantine. Das bisherige System zur Bestellungsverarbeitung ist aber hoffnungslos überfordert, da es Bestellungen nur sequentiell abarbeiten kann. Die exzellenten Entwickler der PUM haben bereits ein neues System designed, das Bestellungen parallel verarbeiten soll. Leider sind sie mit den Vorbereitungen für die Prüfungen beschäftigt - für die Entwicklung kommst deshalb du ins Spiel.

### Das System im Überblick
Eine Bestellung in unserem System wird durch die Order-Klasse repräsentiert. Sie enthält eine eindeutige ID, eine maximale Zubereitungszeit und die Liste der Stationen, die durchlaufen werden müssen. Die Order-Klasse ist bereits vollständig implementiert und simuliert die Verarbeitungszeit an den Stationen durch ihre cook()-Methode.

Die parallele Verarbeitung stellt besondere Anforderungen an die Koordination. Jede Station (KitchenStation) hat nur begrenzt viel Platz - dies wird durch einen Semaphor kontrolliert. Die Köche (Chef) arbeiten als eigenständige Threads und teilen sich eine Warteschlange für neue Bestellungen. Der KitchenManager startet und beendet den Prozess und verwaltet einkommende Bestellungen.

Eine Bestellung muss innerhalb ihrer Zeitvorgabe alle benötigten Stationen durchlaufen. Ist diese Zeit beim Erreichen einer Station überschritten, muss die Bestellung aus dem System entfernt werden. Achte auf eine korrekte Synchronisation zwischen allen Komponenten.

### 1. KitchenStation
   Gegeben ist bereits eine Methode validateOrder(), mit der ein Chef prüft, ob eine Bestellung noch innerhalb ihrer Zeitvorgabe liegt. Du implementierst nun processOrder(), die den eigentlichen Verarbeitungsprozess einer Bestellung an der Station steuert: Die Methode muss berücksichtigen, dass jede Station nur begrenzt viel Platz hat, bevor sie eine Order kocht.

### 2. Chef
   Das Verhalten der Köche wird in der Chef-Klasse modelliert. Jeder Koch arbeitet als eigenständiger Thread und ist für die vollständige Verarbeitung von Bestellungen zuständig. In der run()-Methode implementierst du den Arbeitszyklus: Der Koch entnimmt kontinuierlich neue Bestellungen aus der geteilten Queue und verarbeitet diese an den erforderlichen Stationen. Dabei muss er sowohl auf abgelaufene Orders als auch auf mögliche Unterbrechungen reagieren. Ein Koch arbeitet so lange, bis er explizit gestoppt wird.

### 3. KitchenManager
   Der KitchenManager koordiniert das Gesamtsystem über seine synchronisierten Methoden. Die start()-Methode initialisiert das System und lässt die Köche starten. In stop() werden die Prozesse geordnet heruntergefahren und der KitchenManager zurückgesetzt. Wenn das System bereits im entsprechend korrekten Zustand ist, sollen die Methoden nichts machen. submitOrder() nimmt neue Bestellungen entgegen solange die Küche läuft und fügt sie in die priorisierte Warteschlange ein.

Achte darauf, dass der activeOrders-Zähler, der zu jedem Zeitpunkt angibt, wie viele Bestellungen sich im System befinden - sowohl wartende als auch solche, die von Kochthreads bearbeitet werden. Eine Bestellung, die abgearbeitet oder abgelaufen ist, befindet sich nicht im System. Das Ablaufdatum muss nur für die Übergabe an processOrder() geprüft werden, während der Verarbeitung können Bestellungen ablaufen.

KitchenStation 1 of 1 tests passing
KitchenManager 6 of 7 tests passing
Chef 1 of 1 tests passing