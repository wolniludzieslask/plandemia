Katalog zawiera dokumentację i procedury (klasy) wykorzystywane do otrzymania zestawień na stronie
https://wolniludzieslask.github.io/plandemia/
punkt "Nadmiarowe zgony na świecie 2020/2021 (19 maja 2022)" (Plandemia)

Katalog pliki_we_wy zawiera dane wejściowe i wyjściowe wykorzystywane przez klasy projektu.

Katalog testy zawiera zrzuty ekranów ilustrujących działanie klas.

Poniżej opis procedury do otrzymania danych przedstawionych w tabelach:

Rok 2020 i 2021
1. Klasa zgony_swiat_wm.FormatZgonySwiatWm2020_2021<br>
plik wejściowy world_mortality.csv<br>
plik wyjściowy zgony_wm2020_2021.txt
2. zgony_swiat_wm.ZgonySwiatSumyWm<br>
plik wejściowy zgony_wm2020_2021.txt<br>
plik wyjściowy zgony2020_2021suma.txt
3. Plik zgony2020_2021suma.txt wczytujemy do arkusza OpenOffice Calc,<br>
sortujemy rosnąco wg kolumny liczby okresów (kolumna F),<br>
usuwamy kraje z małą liczbą okresów np. poniżej 16 miesięcy<br>
(podzielone przez 2 daje 8 miesięcy w roku)<br>
2 kraje usunąłem (dane z 6.05.2022): Gibraltar (2 mies), Białoruś (6 mies)
4. Sortujemy wg klumny E (zmiana procentowa)<br>
zapisujemy jako plik csv (zgony2020_2021suma.csv)
5. Plik zgony2020_2021suma.csv kopiujemy na zgony2020_2021suma.in
6. Klasa konwersje.DodanieNumerowWierszy<br>
plik wejściowy zgony2020_2021suma.in<br>
plik wyjściowy zgony2020_2021suma1.csv
7. Plik zgony2020_2021suma1.csv wczytujemy do arkusza OpenOffice Calc<br>
Dodajemy nagłówek, podsumowania i formatujemy dane<br>
zapisujemy w formacie ods (zgony2020_2021suma1.ods)
8. Plik zgony2020_2021suma1.ods zapisujemy w formacie html<br>
(zgony2020_2021suma1.html)
9. Plik zgony2020_2021suma1.html wczytujemy do jEdit, zmieniamy szerokości
kolumn tabeli (sekcja COLGROUP) i zapisujemy w kodowaniu UTF-8.<br>
Zmieniamy wielkość czcionki na small, usuwamy kaluzulę RULES=NONE<br>
zmieniamy charset na utf-8<br>
zmieniamy border na 1 (dla tabeli)
10. W pliku nadmiarowe_zgony_swiat2020_2021.html w edytorze<br> 
wklejamy tabelę z pliku zgony2020_2021suma1.html<br>
Opakowujemy tabelę przez
~~~
<div style= "width: 1000px" class="table-container">
<table class='scrollable'>
~~~
Pierwszą sekcję TR z tabeli przesuwamy przed sekcję TBODY i umieszczamy ją
w sekcji THEAD<br>
Zmieniamy z TD na TH dla tego pierwszego wiersza tabeli

Plik zgony2020_2020suma1.html kopiujemy na nadmiarowe_zgony_tabela2020_2021.html
i zmieniamy szerokość kolumn na takie, jak w pliku tabela.css

Rok 2020
  
11. Klasa zgony_swiat_wm.FormatZgonySwiatWm<br>
plik wejściowy world_mortality.csv<br>
plik wyjściowy zgony_wm2020.txt
12. Klasa zgony_swiat_wm.ZgonySwiatWm<br>
plik wejściowy zgony_wm2020.txt<br>
plik wyjściowy zgony_wm2020suma.txt<br>
13. Otworzyć plik zgony_wm2020suma.txt w arkuszu OpenOffice Calc<br>
posortować wiersze wg kolumny zmiana proc.<br>
zapisać (zgony_wm2020suma.txt)
14. Skopiować zgony_wm2020suma.txt na zgony_wm2020suma.in
15. Klasa konwersje.DodanieNumerowWierszy2<br>
plik wejściowy zgony_wm2020suma.in<br>
plik wyjściowy zgony_wm2020suma1.csv
16. Plik zgony_wm2020suma1.csv wczytujemy do arkusza OpenOffice Calc<br>
Dodajemy nagłówek, podsumowania i formatujemy dane<br>
zapisujemy w formacie ods (zgony_wm2020suma1.ods)
17. Plik zgony_wm2020suma1.ods zapisujemy w formacie html<br>
(zgony_wm2020suma1.html)
18. Plik zgony_wm2020suma1.html wczytujemy do jEdit, zmieniamy szerokości<br>
kolumn tabeli (sekcja COLGROUP) i zapisujemy w kodowaniu UTF-8.<br>
Zmieniamy wielkość czcionki na small, usuwamy kaluzulę RULES=NONE<br>
zmieniamy charset na utf-8<br>
zmieniamy border na 1 (dla tabeli)
19. W pliku nadmiarowe_zgony_swiat2020_2021.html w edytorze<br>
wklejamy tabelę z pliku zgony_wm2020suma1.html<br>
Opakowujemy tabelę przez
~~~
<div style= "width: 1000px" class="table-container">
<table class='scrollable'>
~~~
Pierwszą sekcję TR z tabeli przesuwamy przed sekcją TBODY i umieszczamy ją
w sekcji THEAD<br>
Zmieniamy z TD na TH dla tego pierwszego wiersza tabeli

Plik zgony_wm2020suma1.html kopiujemy na nadmiarowe_zgony_tabela2021.html
i zmieniamy szerokość kolumn na takie, jak w pliku tabela.css

Rok 2021
  
20. Klasa zgony_swiat_wm.FormatZgonySwiatWm2021<br>
plik wejściowy world_mortality.csv<br>
plik wyjściowy zgony_wm2021.txt
21. Klasa zgony_swiat_wm.ZgonySwiatWm2021<br>
plik wejściowy zgony_wm2021.txt<br>
plik wyjściowy zgony_wm2021suma.txt
22. Otworzyć plik zgony_wm2021suma.txt w arkuszu OpenOffice Calc<br>
posortować wiersze wg kolumny zmiana proc.<br>
zapisać (zgony_wm2021suma.csv)
23. Skopiować zgony_wm2021suma.csv na zgony_wm2021suma.in
24. Klasa konwersje.DodanieNumerowWierszy2<br>
plikiem wejściowym jest zgony_wm2021suma.in<br>
plikiem wyjściowym zgony_wm2021suma1.csv
25. Plik zgony_wm2021suma1.csv wczytujemy do arkusza OpenOffice Calc<br>
Dodajemy nagłówek, podsumowania i formatujemy dane<br>
zapisujemy w formacie ods (zgony_wm2021suma1.ods)
26. Plik zgony_wm2021suma1.ods zapisujemy w formacie html<br>
(zgony_wm2021suma1.html)
27. Plik zgony_wm2021suma1.html wczytujemy do jEdit, zmieniamy szerokości
kolumn tabeli (sekcja COLGROUP) i zapisujemy w kodowaniu UTF-8.<br>
Zmieniamy wielkość czcionki na small, usuwamy kaluzulę RULES=NONE<br>
zmieniamy charset na utf-8<br>
zmieniamy border na 1 (dla tabeli)
28. W pliku nadmiarowe_zgony_swiat2020_2021.html w edytorze<br>
wklejamy tabelę z pliku zgony_wm2021suma1.html<br>
Opakowujemy tabelę przez
~~~
<div style= "width: 1000px" class="table-container">
<table class='scrollable'>
~~~
Pierwszą sekcję TR z tabeli przesuwamy przed sekcją TBODY i umieszczamy ją
w sekcji THEAD<br>
Zmieniamy z TD na TH dla tego pierwszego wiersza tabeli

Plik zgony_wm2021suma1.html kopiujemy na nadmiarowe_zgony_tabela2021.html
i zmieniamy szerokość kolumn na takie, jak w pliku tabela.css
