HEAD
# Assignment 3. Maze Escape

Student ID: 252678
Name:Inzhu Abdirasul
Group:SE-2512
Maze size (rows x columns):

This project implements a maze using Disjoint Set Union (DSU), Kruskal's algorithm, Prim's algorithm, DFS, Kosaraju's algorithm, and a route algorithm on a directed acyclic graph (DAG)

## Program output

The 13 lines your program prints on your data (`java -cp out Main`):

    Broken:87
    Kept:70
    Last broken wall:127
    MST cost:27238
    Heaviest broken wall:853
    Path length:23
    DFS visited:67
    Dead ends:29
    Zones:88
    Largest zone:1
    Is DAG:1
    Flow stages:16
    Fastest route cost:17

My maze:file:///C:/Users/user/Downloads/maze-escape-starter%20A3w7/maze-escape/output/maze.html

![maze](output/maze.png)

## Questions

Mission 0. What do find and union do, and what makes them fast in your code?
DSU helps us find which cells belong to the same group. It can join two groups and check if two cells are connected

Mission 1. Why is Broken always R*C - 1, and Kept always (R-1)*(C-1)?
The program reads the walls from the input file. It checks the walls and builds the maze

Mission 2. My MST cost from Kruskal and from Prim, and why they are equal:
Kruskal's algorithm builds a minimum spanning tree. It chooses edges with low costs and avoids cycles

Mission 3. Why is there exactly one path between two cells? How did you avoid a stack overflow on a huge maze?
Prim's algorithm also builds a minimum spanning tree. It starts from one vertex and adds the next best edge

Mission 4. Why does the second pass of Kosaraju go on the reversed graph? Why do the slides have no cycle exactly when Zones equals the number of cells?
DFS visits the cells in the maze. The program uses an iterative version of DFS to avoid problems with deep recursion

Mission 5. Why is it enough to go through the zones in the order of their numbers? What does this pass cost?
Kosaraju's algorithm finds strongly connected components in a directed graph. These are groups of vertices that can reach each other


## AI use

I used ChatGPT to understand the assignment, learn about the algorithms, and prepare this report. I checked the program output and reviewed the explanations.

## ## Timing (Mission 7)

Milliseconds, 5 runs each, with the average time.

| Maze | Kruskal | Prim |
|---|---:|---:|
| 100 x 100 | 18 | 17 |
| 300 x 300 | 93 | 108 |
| 1000 x 1000 | 1194 | 1877 |
Kruskal is faster for the 300 x 300 and 1000 x 1000 mazes. Prim is slightly faster for the 100 x 100 maze. For the largest maze, Kruskal takes 1194 ms, while Prim takes 1877 ms
## Complexity

| algorithm | time | space | assumptions |
|---|---|---|---|
| DSU find and union | Almost O(1) per operation | O(n) | Uses path compression and union by size |
| Kruskal | O(m log m) | O(n + m) | Sorts edges by weight and uses DSU |
| Prim | O(m log n) | O(n + m) | Uses a priority queue and adjacency lists |
| DFS | O(n + m) | O(n + m) | Uses an iterative search |
| Kosaraju | O(n + m) | O(n + m) | Uses two DFS passes |
| Route in the zone graph | O(Z + E) | O(Z + E) | The zone graph is a DAG and uses topological order |
## Before you submit

- data/walls.txt, data/slides.txt and output/maze.png are in the repository.
- `java tools/Check.java` shows all examples right.
- This README is filled in, the program output and the section AI use included.
- The work is pushed to main (at least one commit per mission) and the repository is public.
- The link is attached in Moodle.

# daa3
 9218cbd24d5ceb4539e19c7097e4384e616a1a7a
