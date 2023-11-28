### Conrad Chloé & Lefebvre Julien 

Il s'agit d'un projet de résolution du problème de la coloration de graphe en utilisant une méthode complète via le solveur choco et une méthode incomplète (l'algorithme DSatur).

## Les fichiers composant le projet sont décrit ci-dessous :
* ColorationGraphCSP : implémente la méthode complète via le solveur Choco
* DsaturGraphColoring : Implémente l'algorithme DSatur 
* fileReaderDsatur : Permet de lire les fichiers du benchmark pour créer une instance de résolution avec DSatur.
* LectureFichierCol : Permet de lire les fichiers du benchmark pour créer une instance de résolution via le solveur choco.
* Main : Permet de lancer la résolution de tous les graphs du benchmark via l'approche complète.
* MainDsatur : Permet de lancer la résoluation de tous les graphs du benchmark avec l'algorithme DSatur.

Tous les fichiers de codes sont dans le dossier src et toutes les instances du benchmark sont dans le dossier bench.

## Précision sur le fichier main :
Le changement de la valeur de l'attribut technique dans le fichier change l'heuristique utilisée dans la résolution 
* 0: Domaine sur degré pondéré 
* 1: First fail

## Fichiers csv : 
- Résulats obtenus sur l'ensemble des graphs du benchmark pour les différentes méthodes utilisées.

## Fichiers python :
* Fichier dans le dossier bench : Permet de visualiser les graphs
* Fichier à la racine du projet : Permet de comparer les résultats obtenus via la méthode complète et incomplète pour trouver les cas dans lesquels la deuxième méthode est meilleure.