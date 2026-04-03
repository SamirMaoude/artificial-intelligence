# Projet File Rouge - Intelligence Artificielle

Projet universitaire (Licence 3 Informatique, Universite de Caen, 2024-2025) portant sur le probleme du **Monde des Blocs** (*Blocks World*), un domaine classique en intelligence artificielle pour illustrer et tester des algorithmes de planification.

**Etudiants :** DIALLO Aissatou (22311724) & MAOUDE Samir (22411671)

---

## Le Monde des Blocs

On dispose de *n* blocs numerotes de 0 a *n-1*, organises en *m* piles (certaines pouvant etre vides). L'objectif est de trouver une sequence de deplacements de blocs permettant de passer d'une configuration initiale a une configuration but.

Chaque configuration est decrite par trois types de variables :
- **on_b** : position du bloc *b* (pose sur un autre bloc *b'* ou sur la table dans la pile *p*)
- **fixed_b** : booleen indiquant si le bloc *b* est indeplacable (un autre bloc est pose dessus)
- **free_p** : booleen indiquant si la pile *p* est vide

Les actions possibles consistent a deplacer un bloc libre vers une destination libre (bloc ou pile vide), avec preconditions et effets sur ces variables.

---

## Modules

### 1. Modelisation (`modelling/`)
Socle commun de modelisation : variables avec domaines, et contraintes binaires (difference, implication) permettant de garantir la validite des configurations (pas de cycles, coherence des positions).

### 2. Planification (`planning/`)
Algorithmes de recherche dans l'espace d'etats pour trouver un plan (sequence d'actions) menant d'un etat initial a un etat but :
- **BFS** (recherche en largeur)
- **DFS** (recherche en profondeur)
- **Dijkstra** (plus court chemin par cout)
- **A\*** avec heuristiques admissibles : **blocs mal places** et **similarite cosinus**

### 3. Programmation par contraintes (`cp/`)
Solveurs de problemes de satisfaction de contraintes (CSP) utilises pour generer automatiquement des configurations valides du monde des blocs. Deux types de configurations non circulaires sont cibles :
- **Reguliere** : au sein de chaque pile, l'ecart entre les numeros de deux blocs consecutifs est constant (ex: pile (1,3,5,7) avec un ecart de 2)
- **Croissante** : un bloc ne peut etre pose que sur un bloc de numero plus petit ou directement sur la table

Trois solveurs sont implementes :
- **Backtracking** simple
- **MAC** (Maintaining Arc Consistency) avec consistance d'arc AC1
- **HeuristicMACSolver** avec heuristiques de choix de variable (MRV, degre) et de valeur

### 4. Extraction de connaissances (`datamining/`)
A partir de bases de donnees de 10 000 configurations du monde des blocs generees aleatoirement, on cherche a extraire des motifs frequents et des regles d'association. Les variables sont "propositionnalisees" en booleens :
- **on_{b,b'}** : vrai si le bloc *b* est directement sur le bloc *b'*
- **on-table_{b,p}** : vrai si le bloc *b* est sur la table dans la pile *p*
- **fixed_b** / **free_p** : indeplacabilite des blocs et liberte des piles

L'algorithme **Apriori** extrait les itemsets frequents (frequence min. 2/3) puis les regles d'association (confiance min. 95/100). Resultat : **83 motifs** et **168 regles** extraits sur des mondes de 10 blocs et 3 piles.

### 5. Blocks World (`blocksworld/`)
Application et integration des modules precedents au monde des blocs :
- Generation de configurations avec contraintes regulieres et croissantes
- Heuristiques dediees pour la planification
- Integration data mining sur les configurations
- Visualisation graphique et simulation des plans trouves
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
