import pandas as pd
df1 = pd.read_csv("DSATUR.csv", header=None)
df2 = pd.read_csv("CSP_Technique0.csv", header=None)
for i in range(0, len(df1)):
    if df1[2][i] < df2[2][i]:
        print(df1[0][i], df2[0][i])
