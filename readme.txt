# Etudiants
- DIALLO Aissatou 22311724
- MAOUDE Samir 22411671


# Projet File rouge

## Compilation et Exécution

Assurez vous de copier les fichier jar de des blocworlds dans le dossier lib avant compilation:
- blocksworld.jar
- bwgenerator.jar

Placez-vous dans le dossier src:

cd src/

- blocksworld:
    #Compilation: 
    javac -d ../build/ -cp ../lib/bwgenerator.jar:../lib/blocksworld.jar blocksworld/*.java blocksworld/datamining/*.java blocksworld/heuristics/*.java blocksworld/demos/*.java modelling/*.java planning/*.java cp/*.java datamining/*.java
    
    
    #Execution: 
    
    - ConstraintsDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demos.ConstraintsDemo

    - PlannerDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demos.PlannerDemo

    - RegularWorldSolverDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demos.RegularWorldSolverDemo

    - IncreasingWorldSolverDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demos.IncreasingWorldSolverDemo

    - IncreasingAndRegularWorldSolverDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.demos.IncreasingAndRegularWorldSolverDemo
    
    - BwDataminingDemo: java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.datamining.BwDataminingDemo