# Projet File Rouge - Intelligence Artificielle

Projet universitaire (Licence 3 Informatique, Universite de Caen) implementant des concepts fondamentaux d'intelligence artificielle appliques au domaine du **Blocks World**.

**Etudiants :** DIALLO Aissatou (22311724) & MAOUDE Samir (22411671)

---

## Modules

### Modelisation (`modelling/`)
Socle commun de modelisation : variables, domaines et contraintes (unaires, difference, implication).

### Programmation par contraintes (`cp/`)
Solveurs de problemes de satisfaction de contraintes (CSP) :
- **Backtracking** simple
- **MAC** (Maintaining Arc Consistency) avec consistance d'arc AC1
- **HeuristicMACSolver** avec heuristiques de choix de variable (MRV, degre) et de valeur

### Planification (`planning/`)
Algorithmes de recherche pour trouver une sequence d'actions menant d'un etat initial a un etat but :
- **BFS** (recherche en largeur)
- **DFS** (recherche en profondeur)
- **Dijkstra**
- **A\*** avec heuristiques configurables

### Data Mining (`datamining/`)
Extraction de motifs frequents et regles d'association :
- Algorithme **Apriori** pour les itemsets frequents
- Extraction de **regles d'association** avec seuils de frequence/confiance configurables

### Blocks World (`blocksworld/`)
Application des modules precedents au probleme classique du monde des blocs :
- Generation de configurations valides avec contraintes
- Heuristiques dediees (blocs mal places, similarite cosinus)
- Integration data mining pour decouvrir des motifs dans les configurations
- Visualisation graphique des plans trouves
- Demonstrations executables dans `blocksworld/demos/`

---

## Prerequis

- **JDK 8** ou superieur
- Fichiers JAR externes (a placer dans un dossier `lib/`) :
  - `blocksworld.jar`
  - `bwgenerator.jar`

## Compilation

Depuis la racine du projet :

```bash
javac -d build/ -cp lib/bwgenerator.jar:lib/blocksworld.jar \
  blocksworld/*.java blocksworld/datamining/*.java \
  blocksworld/heuristics/*.java blocksworld/demos/*.java \
  modelling/*.java planning/*.java cp/*.java datamining/*.java
```

## Execution

Toutes les commandes s'executent depuis la racine du projet.

| Demo | Commande |
|------|----------|
| Contraintes | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.demos.ConstraintsDemo` |
| Planificateurs (BFS, DFS, Dijkstra, A*) | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.demos.PlannerDemo` |
| Solveur monde regulier | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.demos.RegularWorldSolverDemo` |
| Solveur monde croissant | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.demos.IncreasingWorldSolverDemo` |
| Solveur regulier + croissant | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.demos.IncreasingAndRegularWorldSolverDemo` |
| Data mining sur Blocks World | `java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar blocksworld.datamining.BwDataminingDemo` |

---

## Architecture

```
modelling/          Contraintes & variables
    |
    v
cp/                 Solveurs CSP
planning/           Algorithmes de recherche
datamining/         Apriori & regles d'association
    |
    v
blocksworld/        Application au monde des blocs
  demos/            Programmes executables
  heuristics/       Heuristiques de recherche
  datamining/       Integration data mining + blocks world
```
