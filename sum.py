import os
import matplotlib.pyplot as plt
import numpy as np
from scipy.spatial import ConvexHull

# Content of .res file
# [
# UC1: [
# 1,
# 2,
# 3,
# ]
# UC2: [
# 1,
# 2,
# 3,
# ]
# ]

def eval(data):
    mode = 0
    uc1 = []
    uc2 = []
    for line in data.split('\n'):
        if line.startswith('[') or line.startswith(']') or line == '':
            continue
        if line.startswith('UC1:'):
            mode = 1
            continue
        if line.startswith('UC2:'):
            mode = 2
            continue
        if mode == 1:
            uc1.append(int(line[:-1]))
        if mode == 2:
            uc2.append(int(line[:-1]))

    # Remove outliers
    uc1_90 = np.percentile(uc1, 90)
    uc2_90 = np.percentile(uc2, 90)
    uc1_10 = np.percentile(uc1, 10)
    uc2_10 = np.percentile(uc2, 10)
    uc1 = [x for x in uc1 if x <= uc1_90 and x >= uc1_10]
    uc2 = [x for x in uc2 if x <= uc2_90 and x >= uc2_10]
    return uc1, uc2

# Function to parse .res files
def parse_res_file(file_path):
    with open(file_path, 'r') as file:
        data = file.read()
    data = eval(data)
    #print averages
    print(file_path)
    print(sum(data[0])/len(data[0]))
    print(sum(data[1])/len(data[1]))
    return data[0], data[1], file_path

all_uc1 = []
all_uc2 = []
res_files = []

def plot_polygon(points, color='blue'):
    # Calculate the convex hull
    hull = ConvexHull(points)
    # Extract the vertices of the convex hull
    hull_points = points[hull.vertices]
    # Plot the convex hull
    plt.fill(hull_points[:, 0], hull_points[:, 1], color=color, alpha=0.5)



for filename in os.listdir('.'):
    if filename.endswith('.res') and filename != 'java.res':
        uc1, uc2, filepath = parse_res_file(filename)
        all_uc1.append(uc1)
        all_uc2.append(uc2)
        res_files.append(filepath)

# remove the last elements until all lists have the same length
min_len_uc1 = min([len(x) for x in all_uc1])
min_len_uc2 = min([len(x) for x in all_uc2])
min_len = min(min_len_uc1, min_len_uc2)
all_uc1 = [x[:min_len] for x in all_uc1]
all_uc2 = [x[:min_len] for x in all_uc2]

#generate contrasting colors
colors = ['blue', 'red', 'green', 'purple', 'orange', 'brown', 'pink', 'gray', 'olive', 'cyan']
# Assuming uc1 and uc2 are lists of x and y coordinates, respectively
for uc1, uc2 in zip(all_uc1, all_uc2):
    points = np.column_stack((uc1, uc2))  # Create an array of (x, y) points
    color = colors.pop()
    plot_polygon(points, color=color)
plt.legend(res_files)

plt.savefig('plot.png')
