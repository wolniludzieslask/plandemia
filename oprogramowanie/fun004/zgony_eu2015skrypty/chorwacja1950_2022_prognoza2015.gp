reset
set termoption enhanced
set datafile separator comma
set datafile missing ""
set xrange [1949:2025]
set ylabel "liczba zgonów rocznie"
set key center bottom horizontal outside
set grid

set title "Zgony w Chorwacji w latach 1950-2022"

stats [2015:2019] 'chorwacja1950_2022.csv' using 1:2 prefix "A"

# patrz C:\rob\world_mortality\polska\zgony_wikipedia_prognoza.ods
ta2 = 3.1824
se = 1107.92
N = 5
xbar = 2017
Ssx = 10
przedzial_ufnosci(x) = ta2 * se * sqrt(1+1/N+(x - xbar)*(x - xbar)/Ssx)
gornag(x) = A_slope * x + A_intercept + przedzial_ufnosci(x)
dolnag(x) = A_slope * x + A_intercept - przedzial_ufnosci(x)
liniar(x) = A_slope * x + A_intercept

plot 'chorwacja1950_2022.csv' using 1:2 with lines title "zgony rocznie" lc rgb "cyan",\
[2015:2019] A_slope * x + A_intercept title "linia regresji" lc rgb "orange",\
[2020:2025] A_slope * x + A_intercept title "przedłużenie linii regresji" dt 2 lc rgb "orange",\
[2015:2019] gornag(x) title "górna granica" lc rgb "green",\
[2020:2025] gornag(x) title "przedłużenie górnej granicy" dt 2 lc rgb "green",\
[2015:2019] dolnag(x) title "dolna granica" lc rgb "dark-violet",\
[2020:2025] dolnag(x) title "przedłużenie dolnej granicy" dt 2 lc rgb "dark-violet"

set terminal push
set terminal png enhanced size 1280,726 truecolor
set output "Chorwacja1950_2022_prognoza2015.png"
replot
set output
set terminal pop
