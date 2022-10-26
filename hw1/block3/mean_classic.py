#%%
import pandas as pd
import numpy as np
print(' reading file..')
data = pd.read_csv('/home/yuku/made3/3_dl/hadoop/hw1/block3/AB_NYC_2019.csv')
# %%
data
# %%
mean_classic = data['price'].mean(skipna= True)
# %%
mean_classic
# %%
