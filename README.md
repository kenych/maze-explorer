Example of exploring the maze.

Given maze with start point S and exit F and maze localed at start point, it should find the exit:
```
XXXXXXXXXXXXXXX
X             X
X XXXXXXXXXXX X
X XS        X X
X XXXXXXXXX X X
X XXXXXXXXX X X
X XXXX      X X
X XXXX XXXX X X
X XXXX XX   X X
X X    XX XXX X
X X XXX     X X
X X XXXXXXXXX X
X X         X X
X XXXXXXXXX   X
XFXXXXXXXXXXXXX
```

An example with path after running testFindExit2:
```
XXXXXXXXXXXXXXX
X<<<<<<<<<<<<^X
XvXXXXXXXXXXX^X
XvXS********X^X
XvXXXXXXXXX*X^X
XvXXXXXXXXX*X^X
XvXXXX<<<<<*X^X
XvXXXXvXXXX*X^X
XvXXXXvXX***X^X
XvX<<<vXX*XXX^X
XvXvXXX*****X^X
XvXvXXXXXXXXX^X
XvXv>>>>>>>>X^X
XvXXXXXXXXXv>>X
XFXXXXXXXXXXXXX
```

Symbols "<, >, V, ^" mean according movement, and * means went this way and came back if got trapped.

With regards,
Kayan A.











