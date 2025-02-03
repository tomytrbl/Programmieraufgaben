
#  **W04H01 - PinguPinguLos II - Angriff der Suchmaschine**


### Aufgabenstellung: Suchmaschine, die Zweite

Letzte Woche haben wir die grundlegenden Klassen des Page-Repositorys implementiert. Dazu haben wir noch eine Klasse Date erstellt, welche zur Darstellung von Geburts-/Veröffentlichungs-/Änderungsdaten etc. verwendet werden kann. Da wir allerdings noch nicht die notwendigen Werkzeuge erlernt hatten, um nützliche Methoden für diese Klasse zu implementieren, bleibt uns diese Aufgabe für diese Woche. Erweitere also im Folgenden die Suchmaschine um die in dieser Aufgabe beschriebenen Funktionalitäten.

Wichtig!

- In der ganzen Aufgabe ist das Benutzen der java.time.*, java.util.Calendar, java.util.Date Libraries (oder anderer ähnlicher Bibliotheken, die den von dir zu leistenden Anteil der Implementierung ersetzen) untersagt. Wir behalten uns vor, Abgaben, die diesem Kriterium widersprechen, entweder komplett oder einzelne Teilaufgaben davon mit 0 Punkten zu bewerten.
- Auch wenn es für viele der im Folgenden zu implementierenden Methoden Sinn machen würde, sie als private zu kennzeichnen (z.B. da sie nur Hilfsmethoden sind und nicht von außen aus zugegriffen werden müssen), markiere dennoch alle Methoden als public, sodass du sie später leichter testen kannst.
- Du kannst (musst aber nicht) deine Implementierung von letzter Woche weiter verwenden. Stelle aber sicher, dass du eine Version vorliegen hast, die alle Anforderungen von letzter Woche erfüllt. Die Funktionalitäten von letzter Woche werden nicht erneut getestet und bewertet werden. Allerdings könnten einige der neuen Funktionen auf alten aufbauen/diese erweitern. Dann müssen die alten natürlich funktionieren, um korrektes Verhalten der neuen garantieren zu können. Falls du es also vorige Woche nicht geschafft hast, alles zu implementieren bzw. dir unsicher bist, ob dein bisheriger Code korrekt funktioniert, kannst du deine bisherigen Implementierungen mit dem Template dieser Aufgabe abgleichen.
Bevor du eine Methode weiterverwendest, stelle sicher, dass diese für alle Eingaben und Randfälle die richtige Werte zurückgibt. Deine Implementierungen kannst und sollst du auch lokal testen.

Struktur 4 of 4 tests passing

Bevor nicht alle Methodensignaturen korrekt implementiert sind (also bevor Namen, Access Modifiers, Parameters, Return Types, etc. stimmen), wird kein Behavioral Test ausgeführt. Hinweise zu Fehlern in der Struktur liefern die Structural Tests (beispielsweise gibt "testTestMethods[Date]" Hinweise auf Fehler in den Methoden der Klasse Date). Um alle Structural Tests zu bestehen, müssen die Methoden noch keine Funktionalität haben. Es reicht aus, dass sie existieren.

# Klasse Date
Die meisten Änderungen an der Suchmaschine werden diese Woche an der Klasse Date stattfinden. Nach Abarbeiten dieser Aufgabe sollte die Klasse Date durch beliebigen Code außerhalb der Klasse nicht mehr auf ungültige Daten gesetzt werden können.

# Gültigkeit der Daten
Als gültige Daten gelten alle nach dem gregorianischen Kalender gültigen Daten, wobei es aber ein Jahr 0 gibt, sodass das Attribut year beliebige Werte vom Typen int annehmen darf. Dabei hat der Februar in Schaltjahren 29, in allen anderen Jahren 28 Tage. Die Schaltjahre sind alle Jahre, die durch 4, aber nicht durch 100 teilbar sind. Zusätzlich sind alle Jahre, die durch 400 teilbar sind Schaltjahre, auch wenn sie ein Vielfaches von 100 sind.

Implementiere hierzu vier Hilfsmethoden in der Klasse Date. Alle vier müssen (und sollen!) nicht von einem konkreten Date-Objekt aus aufgerufen werden müssen, da sie nicht von einem konkreten Datum abhängen. Stelle sicher, dass die Modifikatoren der Methoden entsprechend gesetzt sind.

Leap Years 1 of 1 tests passing
Die Methode boolean isLeapYear(int year) soll den Wahrheitswert zurückgeben, ob das übergebene Jahr ein Schaltjahr ist.

Days in Year 1 of 1 tests passing
Die Methode int daysInYear(int year) soll die Anzahl an Tagen zurückgeben, die das übergebene Jahr hat.

Days in Month 1 of 1 tests passing
Die Methode int daysInMonth(int month, int year) soll die Anzahl an Tagen im übergebenen Monat zurückgeben, abhängig von dem übergebenen Jahr.

Valid Date 1 of 1 tests passing
Die Methode boolean isValidDate(int day, int month, int year) soll überprüfen, ob die übergebenen Parameter ein gültiges Datum bilden.
Wichtig: Es soll hier nicht für ein Date-Objekt bestimmt werden, ob es gültig ist, sondern ob die Parameter ein gültiges Date-Objekt formen könnten.

Date 1 of 1 tests passing
Wir wollen nun sicherstellen, dass nur gültige Daten in die Attribute day, month und year geschrieben werden können. Dies soll der Konstruktor erzwingen. Du sollst ihn so anpassen, dass nur noch oben erlaubte Werte für die Attribute gesetzt werden können. Falls der Konstruktor von 'Date' ein ungültiges Datum übergeben bekommt, sollst du das Datum in einen Fehlerzustand setzen, nämlich alle Attribute auf -1, und eine passende Meldung auf der Konsole ausgeben, die das ungültige Datum beinhaltet. z.B.: "Der⎵37.3.2021⎵ist⎵kein⎵valides⎵Datum" oder "Der⎵29.2.2022⎵ist⎵kein⎵valides⎵Datum.". Die genaue Formulierung ist nicht weiter wichtig, solange sie das ungültige Datum, das zu erzeugen versucht wurde, im Format "<Tag>.<Monat>.<Jahr>" enthält.

# Zeitspannen zwischen Daten
Nun wollen wir die Zeitspanne zwischen zwei Daten berechnen können. Je nach Anwendung reicht uns teilweise eine grobe Angabe in (vollen) Jahren vom ersten Datum zum zweiten, teils wollen wir eine genaue Angabe in Tagen haben.

Days Left & Passed This Year 2 of 2 tests passing
Schreibe zuerst aber die zwei Methoden int daysLeftThisYear() und int daysPassedThisYear(), welche die Anzahl an Tagen ab dem durch this beschriebenen bis zum Ende des Jahres (ersteren ausgeschlossen) respektive die Anzahl an Tagen seit Jahresbeginn (den durch this beschriebenen eingeschlossen) ermitteln.
Implementiere nun folgende zwei Methoden und verwende dabei daysLeftThisYear() und daysPassedThisYear():

Years Until 1 of 1 tests passing
Die Methode int yearsUntil(Date other) soll die Anzahl an Jahren zwischen dem Datum, für das sie aufgerufen wird und dem Datum other berechnen und zurückgeben. Dabei zählen nur am zweiten Datum abgeschlossene Jahre. Liegt other vor this, wird in die Negativen gezählt. So beträgt der Abstand
- vom 03.09.2001 bis zum 02.09.2005 3 Jahre (das vierte ist noch nicht abgeschlossen)
- vom 03.09.2001 bis zum 03.09.2012 11 Jahre (hier ist das elfte gerade abgeschlossen)
- vom 20.06.2020 bis zum 19.06.2020 -1 Jahre (alles vor dem ersten Datum zählt als negatives Jahr)
- vom 20.06.2020 bis zum 08.11.2010 -10 Jahre (das -10te Jahre vor dem 20.06.2020 ist das Jahr vom 20.06.2010 bis zum 19.06.2011)
- vom 29.02.2016 bis zum 28.02.2017 0 Jahre (auch trotz der Tatsache, dass es keinen 29.02.2017 gibt; erst ab 01.03.2017 gilt das erste Jahr als vergangen)

Days Until 1 of 1 tests passing
Die Methode int daysUntil(Date other) soll nun die Anzahl an Tagen zwischen den zwei Daten berechnen. Achte dabei auch auf Schaltjahre. Wieder gilt, dass wenn other vor this liegt, eine negative Zahl zurückgegeben werden soll. D.h.
- vom 03.09.2001 bis zum 06.09.2001 sind es 3 Tage
- vom 03.09.2001 bis zum 03.09.2002 365 Tage
- vom 20.06.2020 bis zum 20.05.2020 -31 Tage
- vom 20.06.2020 bis zum 20.06.2019 -366 Tage (da hier der 29.02.2020 mit dabei ist)

Invalid Dates 1 of 1 tests passing
Die vier in diesem Task implementierten Methoden sollen alle Objekt-Methoden sein. Wenn eine der vier Methoden auf einem ungültigen Datum bzw. mit einem ungültigen Datum als Parameter aufgerufen wird, soll eine Fehlermeldung, nämlich "Methode⎵auf⎵ungültigem⎵Datum⎵aufgerufen!", auf der Konsole ausgegeben werden. Achte, wie immer, auf Korrektheit des Textes.

# Heutiges Datum

Für viele Anwendungen möchte man das aktuelle Datum ermitteln können. Dieses kann man normalerweise recht leicht mit der Java Standardbibliothek ermitteln. Aber das wäre natürlich witzlos, weswegen du diese nicht verwenden darfst. Die einzige Ausnahme ist die Methode long System.currentTimeMillis(), welche die aktuelle (nach Systemzeit) Anzahl an Millisekunden seit Neujahr (also dem 01. Januar um Punkt 00:00 Uhr) 1970 zurückgibt. Diese darfst und sollst du im Folgenden verwenden. Folgende zwei Methoden müssen nicht von einem konkreten Date-Objekt aus aufgerufen werden müssen.

Millis Since 1970 1 of 1 tests passing
Implementiere die Methode Date dateMillisecondsAfterNewYear1970(long millis), die das Datum millis Millisekunden nach Neujahr 1970 berechnet und es als neues Date-Objekt zurückgibt. Tipp: Die zuvor implementierten Hilfsmethoden kannst du hier wiederverwenden.
Today 1 of 1 tests passing
Implementiere die Methode Date today(), die das (nach der Systemzeit) aktuelle Datum berechnet und es als neues Date-Objekt zurückgibt. Tipp: Du kannst hierzu obige Methode und die Methode long System.currentTimeMillis() verwenden
# Klassen Author, Document und Review
Jetzt werden wir noch ein Paar Methoden für die drei Klassen Author, Document und Review schreiben. Stelle also wieder sicher, dass du eine Version der Klassen vorliegen hast, die die Anforderungen der letzten Woche erfüllt.

Author
1 of 1 tests passing
Der Author soll nun eine neue Methode int getAge() bekommen, welche sein Alter in Jahren am heutigen Tage (zumindest nach der Systemzeit) zurückgibt.

Document 2 of 3 tests passing
Dem Document sollst du zwei neue Methoden hinzufügen. Eine Methode mit dem Kopf int yearsSinceRelease(), welche die Anzahl an Jahren seit der Veröffentlichung ermittelt und eine Methode int daysSinceLastUpdate(), welche die Anzahl an Tagen seit dem letzten Update des Dokuments zurückgibt. In beiden Fällen gilt wieder, dass das heutige Datum das der Systemzeit ist.
Außerdem sollst du das lastUpdateDate bei jedem Update (d.h. jedem Call eines Setters außer setLastUpdateDate()) auf das heutige Datum (nach Systemzeit) setzen.

Review 0 of 1 tests passing
Der Review sollst du eine Methode String getAgeText() hinzufügen, die einen Text zurückgibt, welcher das Alter des Posts in Tagen (nach Systemzeit) enthält. Dieser Text soll von der Form "Vor⎵<Tagen>⎵Tagen⎵gepostet" sein.
Alle Zeitspannen sind wieder wie für yearsUntil() und daysUntil() beschrieben zu implementieren. D.h. ein am 03.09.2001 erstelltes Dokument soll, wenn die Systemzeit auf den 02.09.2005 eingestellt ist, 3 Jahre alt sein, wenn die Systemzeit auf den 03.09.2012 eingestellt ist, 11 Jahre usw.