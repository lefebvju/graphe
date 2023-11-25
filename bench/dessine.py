import networkx as nx
import matplotlib.pyplot as plt

# Remplacez "nom_fichier.col" par le nom de votre fichier .col
nom_fichier = "huck.col"

# Lecture du fichier .col
with open(nom_fichier, 'r') as fichier:
    lignes = fichier.readlines()

# Les informations du graphe commencent généralement après une ligne qui commence par "p edge"
for ligne in lignes:
    if ligne.startswith('p edge'):
        info_graphe = ligne.split()
        nombre_sommets = int(info_graphe[2])
        nombre_aretes = int(info_graphe[3])
        break

# Création d'un graphe non orienté
graphe = nx.Graph()

# Ajout des sommets au graphe
for i in range(1, nombre_sommets + 1):
    graphe.add_node(i)

# Ajout des arêtes au graphe
for ligne in lignes:
    if ligne.startswith('e'):
        _, sommet1, sommet2 = ligne.split()
        graphe.add_edge(int(sommet1), int(sommet2))

# Tracé du graphe
pos = nx.spring_layout(graphe)  # Vous pouvez utiliser d'autres algorithmes de disposition
nx.draw(graphe, pos, with_labels=True, node_size=700, node_color='skyblue', font_size=8, font_color='black', font_weight='bold')
plt.title('Graphe à partir du fichier {}'.format(nom_fichier))
plt.show()
