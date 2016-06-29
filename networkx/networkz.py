import numpy as np
import matplotlib.pyplot as plt
import networkx as nx


#" Decentralized algorithms for evaluating centrality in complex networks"
# Katharina A.Lehmann Michael Kaufmann 

# graph structure example presensted in the paper

fh=open('../resources/dolphins.graph', 'rb')
G=nx.read_edgelist(fh)
"""
G=nx.Graph()

G.add_edge('A', 'B', weight=1)
G.add_edge('A', 'D', weight=1)
G.add_edge('B', 'C', weight=1)
G.add_edge('D', 'C', weight=1)
G.add_edge('D', 'E', weight=1)


#http://devblogs.nvidia.com/parallelforall/wp-content/uploads/2014/07/BC_example_scores.png
G.add_edge(1, 2, weight=1)
G.add_edge(1, 3, weight=1)
G.add_edge(1, 4, weight=1)
G.add_edge(2, 3, weight=1)
G.add_edge(3, 4, weight=1)
G.add_edge(4, 5, weight=1)
G.add_edge(4, 6 ,weight=1)
G.add_edge(5, 6, weight=1)
G.add_edge(5, 7, weight=1)
G.add_edge(5, 8, weight=1)
G.add_edge(6, 7, weight=1)
G.add_edge(6, 8, weight=1)
G.add_edge(7, 8, weight=1)
G.add_edge(7, 9, weight=1)
"""



n = len(G.nodes())
shortest_paths= ((n-1)*(n-2))/2   # multiple factor for undirected graph

print("LOAD CENTRALITY  normalized")
d = nx.load_centrality(G, normalized=True, weight=None)
print(d)

print("LOAD CENTRALITY NOT normalized")
d = nx.load_centrality(G, normalized=False, weight=None)
print(d)

print("STRESS CENTRALITY starting from Load")
#  try to normalize by myself 
#  SC = LC * (#shortest paths)
for key in d:
	print(d[key]*shortest_paths)

print("BETWEENESS  centrality not normalized")
bbcc = nx.betweenness_centrality(G, normalized=False,k=n,weight=None)

for key, value in sorted(bbcc.iteritems(), key=lambda (k,v): (v,k)):
	print "%s: %s" % (key, value)

print("CLOSENESS centrality not normalized:")
print(nx.closeness_centrality(G ,normalized=False))


# to print the graph (matplotlib required)
bc = nx.betweenness_centrality(G, normalized=False,weight=None)
print(bc)

node_size=[ bc[n]*2 for n in G.nodes()]

#pos = nx.spring_layout(G)
#nx.draw_networkx_nodes(G, pos, cmap=plt.get_cmap('jet'), node_color = values)
#nx.draw_networkx_edges(G, pos, edgelist=red_edges, edge_color='r', arrows=True)
#nx.draw_networkx_edges(G, pos, edgelist=black_edges, arrows=False)
nx.draw_networkx(G, node_size=node_size)
plt.show()
