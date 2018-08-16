import numpy as np
import matplotlib.pyplot as plt


with open('radius_agg.dat') as f:
    radius = f.readlines()

with open('particles_agg.dat') as f:
    particles = f.readlines()

with open('distance_traveled_agg.dat') as f:
    distance = f.readlines()



x1 = []
y1 = []
yerr1 = []

x2 = []
y2 = []
yerr2 = []

x3 = []
y3 = []
yerr3 = []


i = 0
for i in range(len(radius)):
    y_i = float(radius[i].split(' ')[0])
    yerr_i = float(radius[i].split(' ')[1])
    x1.append(i+1)
    y1.append(y_i)
    yerr1.append(yerr_i)

    y_i = float(particles[i].split(' ')[0])
    yerr_i = float(particles[i].split(' ')[1])
    x2.append(i+1)
    y2.append(y_i)
    yerr2.append(yerr_i)

    y_i = float(distance[i].split(' ')[0])
    yerr_i = float(distance[i].split(' ')[1])
    x3.append(i+1)
    y3.append(y_i)
    yerr3.append(yerr_i)



plt.style.use('ggplot')

fig, [ax1, ax2, ax3]  = plt.subplots(nrows=3, sharex=True, figsize=(9, 6))

ax1.errorbar(x1, y1, yerr=yerr1, fmt='.')
ax1.set(ylabel='Radio')

ax2.errorbar(x2, y2, yerr=yerr2, fmt='.')
ax2.set(ylabel='Particulas')

ax3.errorbar(x3, y3, yerr=yerr3, fmt='.')
ax3.set(ylabel='Particulas')


#ax.xaxis.set_ticks([i+1 for i in range(0, len(x), max([ math.floor(len(x)/10), 1]))])


#plt.savefig('radius.png', format='png', bbox_inches = 'tight', dpi = 100)


# ========================================================================================



plt.show()