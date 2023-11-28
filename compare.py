# lire deux fichier csv et comparer la deuxieme colonne de chaque fichier et retourner les lignes differentes
#
# Usage: python compare.py file1.csv file2.csv
#
# Output: file3.csv
#
import pandas as pd
df1 = pd.read_csv("DSATUR.csv", header=None)
df2 = pd.read_csv("CSP3.csv", header=None)
for i in range(0, len(df1)):
    if df1[2][i] < df2[2][i]:
        print(df1[0][i], df2[0][i])
