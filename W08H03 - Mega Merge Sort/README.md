#  **W08H03 - Mega Merge Sort**


### Aufgabenstellung: Mega Merge Sort

In dieser Aufgabe geht es darum, MegaMergeSort zu implementieren. Dabei handelt es sich um eine Variante des bekannten Divide-and-Conquer Sortieralgorithmus MergeSort. Bei MergeSort werden die zu sortierenden Elemente zuerst in möglichst gleichgroße Bereiche geteilt, indem jeder Bereich immer wieder halbiert wird. Anschließend werden diese Bereiche wieder zusammengeführt (gemergt), wobei sie sortiert werden. Wenn du die Implementierung von MergeSort nochmal üben willst, kannst du die erste Teilaufgabe von der W08P03 bearbeiten. MegaMergeSort funktioniert sehr ähnlich, allerdings werden Bereiche nicht halbiert, sondern in eine festgelegte Anzahl an Teilbereiche zerlegt.

#### 1. Mergen zweier Arrays 5 of 5 tests passing
Zuerst sollst du die Methode int[] merge(int[], int[]) implementieren, die die beiden übergebenen Arrays zu einem neuen zusammenfügt (merget) und dieses zurückgibt. Das Mergen funktioniert dabei wie bei normalem MergeSort: Man beginnt bei beiden Arrays beim ersten Element, vergleicht diese und fügt das kleinere im Ausgabe-Array ein. Das eingefügte Element überspringt man nun im Eingabe-Array und wiederholt die Prozedur, bis alle Elemente in der Ausgabe eingefügt wurden. Die Länge des zurückgegebenen Arrays entspricht natürlich der Summe der Längen der beiden Eingabearrays.

#### 2. Mergen mehrerer Arrays 4 of 4 tests passing
Jetzt sollst du int[] merge(int[][], int, int) implementieren. Diese Methode soll rekursiv die inneren Arrays aus arrays im Indexbereich from (inklusiv) bis to (exklusiv) mergen (also arrays[from], arrays[from + 1], ... und arrays[to - 1] sollen gemerget werden) und das daraus resultierende Array zurückgeben. Falls der Bereich [from, to[ leer ist, soll ein Array der Länge 0 zurückgegeben werden. Die genaue Mergereihenfolge siehst du in folgendem Beispiel:

#### 3. MegaMergeSort 4 of 4 tests passing
Als Letztes fehlt noch die Methode int[] megaMergeSort(int[], int, int, int) (die zugehörige Methode int[] megaMergeSort(int[], int), mit der MegaMergeSort ausgeführt wird, ist schon gegeben). Diese Methode sortiert das array im Bereich [from, to[ mit MegaMergeSort, wobei das Array in jeder Ebene in div-Teile aufgeteilt werden soll. Auch hier soll, falls [from, to[ leer ist, ein Array der Länge 0 zurückgegeben werden. Beim Aufteilen gibt es Folgendes zu beachten: Es wird rekursiv aufgeteilt, bis ein Bereich maximal 1 Element enthält. Falls die Länge des zu sortierende Bereichs nicht restlos durch div teilbar ist, so sollen die ersten Teilbereiche entsprechend um 1 größer sein. Im Beispiel sieht das wie folgt aus:
Beispiel für das Splitten eines Arrays

#### Hinweise 💡
In der gesamten Aufgabe sind Methoden aus java.util.Arrays verboten.
Die Methoden in dieser Aufgabe die Köpfe wie protected int[] merge(..). Dies ist eine Notwendigkeit dafür, wie unsere Tests aufgebaut sind. Du kannst von einer der drei Methoden aus die anderen wie gewohnt mit einem Call wie merge(.. parameter ..) aufrufen. Von der main()- Methode aus kannst du das allerdings nicht mehr genauso. Daher haben wir dir die main()- Methode bereits implementiert. Um dein Verfahren zu testen, musst du nur noch arr und den zweiten Parameter im Call darunter entsprechend abändern.