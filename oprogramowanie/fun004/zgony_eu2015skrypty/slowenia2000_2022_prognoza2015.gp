reset
set termoption enhanced
set datafile separator comma
set datafile missing ""
set xrange [2000:2023]
set ylabel "liczba zgonów w roku"
set key center bottom horizontal outside
set grid

# set style patrz C:\programy\gnuplot\moje_pliki\box_rectangle.gp
# 11.03.2020 początek pandemii
# C:\rob\koronawirus\who_declares_pandemic.txt
# 5.05.2023 koniec pandemii
# C:\rob\koronawirus\koniec_pandemii_linki.txt
set style rect fc lt -1 fs solid 0.15 noborder
set object 1 rect from 2020,0 to 2023, graph 1 behind
# behind jest po to, żeby prostokąt był w tle, patrz str 143 (gnuplot.pdf)

set title "Zgony w Słowenii w latach 2000-2022"

stats [2015:2019] 'slowenia1950_2022.csv' using 1:2 prefix "A"

# C:\programy\gnuplot\moje_pliki\zgony_eu4\PL_zgonyR.ods
# C:\rob\zgony_polska\prediction_interval_test1.JPG
ta2 = 3.1824
se = 251.1841290103
N = 5
xbar = 2017
Ssx = 10

# sqrt patrz str 27
przedzial_ufnosci(x) = ta2 * se * sqrt(1+1/N+(x - xbar)*(x - xbar)/Ssx)
gornag(x) = A_slope * x + A_intercept + przedzial_ufnosci(x)
dolnag(x) = A_slope * x + A_intercept - przedzial_ufnosci(x)
liniar(x) = A_slope * x + A_intercept

plot 'slowenia1950_2022.csv' using 1:2 with linespoints title "zgony rocznie" lt 7 lc rgb "cyan",\
[2015:2019] gornag(x) title "górna granica" lc rgb "green",\
[2019:2023] gornag(x) title "przedłużenie górnej granicy" dt 2 lc rgb "green",\
[2015:2019] dolnag(x) title "dolna granica" lc rgb "dark-violet",\
[2019:2023] dolnag(x) title "przedłużenie dolnej granicy" dt 2 lc rgb "dark-violet",\
[2015:2019] liniar(x) title "linia regresji" lc rgb "orange",\
[2019:2023] liniar(x) title "przedłużenie linii regresji" dt 2 lc rgb "orange",\
[2020:2020] A_slope * x + A_intercept with points title "prognoza zgonów" lt 7 lc rgb "black",\
[2021:2021] A_slope * x + A_intercept with points notitle lt 7 lc rgb "black",\
[2022:2022] A_slope * x + A_intercept with points notitle lt 7 lc rgb "black"

# str 80 Ranges specified
# on the plot command apply only to the first set of axes (bottom left).
# komenda test pokazuje możliwości terminala
# kolory C:\rob\inne\froon_kremers\strona_inne\material-design-color-chart.png
# rgb(224,64,251) e040fb
# składnia rgb(224,64,251) nie działa dla lc
# działa rgb "#e040fb"
# kolory można sprawdzić za pomocą painta (windows)

set terminal push
set terminal png enhanced size 1280,726 truecolor
set output "Slowenia2000_2022_prognoza2015.png"
replot
set output
set terminal pop
