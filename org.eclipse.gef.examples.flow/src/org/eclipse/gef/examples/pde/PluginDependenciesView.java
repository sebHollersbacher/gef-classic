package org.eclipse.gef.examples.pde;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.*;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.*;
import org.eclipse.draw2d.internal.graph.*;

/**
 * @author hudsonr
 * @since 2.1
 */
public class PluginDependenciesView extends ViewPart {

FigureCanvas canvas;

Map map = new HashMap();

/**
 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(Composite)
 */
public void createPartControl(Composite comp) {
	canvas = new FigureCanvas(comp);
//	canvas.setFont(new Font(null, "Arial", 8, 0));
	Label dummy = new Label();
	dummy.setBorder(new MarginBorder(3));
	dummy.setFont(canvas.getFont());

	IPluginDescriptor plugins[] = Platform.getPluginRegistry().getPluginDescriptors();
	
	ToggleButton.class.toString();
	
	DirectedGraph g = new DirectedGraph();
	g.setDefaultPadding(new Insets(20));
	
	Node ECLIPSE = new PluginNode("Eclipse");
	g.nodes.add(ECLIPSE);
	EdgeList subgraphRoots = new EdgeList();
	
	for (int i=0; i<plugins.length; i++) {
		IPluginDescriptor desc = plugins[i];
		if (ignoreDescriptor(desc))
			continue;
		Node n = new PluginNode(desc.getLabel());
		dummy.setText(desc.getLabel());
		n.width = dummy.getPreferredSize().width;
		g.nodes.add(n);
		put(desc, n);
	}
	
	for (int i=0; i<plugins.length; i++) {
		IPluginDescriptor desc = plugins[i];
		if (ignoreDescriptor(desc))
			continue;
		IPluginPrerequisite prereqs[] = desc.getPluginPrerequisites();
		for (int j = 0; j < prereqs.length; j++) {
			Node target = get(desc);
			Node source = get(prereqs[j].getUniqueIdentifier());
			g.edges.add(new PluginEdge(source, target, prereqs[j].isExported()));
		}
	}
	
	for (int i=0; i<plugins.length; i++) {
		IPluginDescriptor desc = plugins[i];
		if (ignoreDescriptor(desc))
			continue;
		Node n = get(desc);
		if (n.incoming.isEmpty()) {
			if (n.outgoing.isEmpty())
				g.nodes.remove(n);
			else {
				Edge e = new PluginEdge(ECLIPSE, n, true);
				e.weight = 0;
				subgraphRoots.add(e);
				g.edges.add(e);
			}
		}
	}

	new InitialRankSolver().visit(g);
	int r = 1;
//	System.out.println("Graph contains " + g.edges.size() + " edges.");
//	System.out.println("Graph contains " + g.nodes.size() + " nodes.");

	while (true) {
		boolean work = false;
		for (int i=0; i<g.nodes.size(); i++) {
			Node nod = g.nodes.getNode(i);
			if (nod instanceof PluginNode) {
				PluginNode n = (PluginNode)g.nodes.getNode(i);
				if (n.rank == r) {
					n.prune(g);
					work = true;
				}
			}
		}
		if (!work && r != 8)
			break;
		r++;
	}
	System.out.println("Pruned graph contains " + g.edges.size() + " edges.");
	
	new TightSpanningTreeSolver().visit(g);
	new RankAssigmentSolver().visit(g);
	new PopulateRanks().visit(g);
	new VerticalPlacement().visit(g);

	for (int i = 0; i < subgraphRoots.size(); i++) {
		Edge e = (Edge)subgraphRoots.getEdge(i);
		g.removeEdge(e);
	}
	g.removeNode(ECLIPSE);

	new MinCross().visit(g);
	new LocalOptimizer().visit(g);
	new HorizontalPlacement().visit(g);

	for (int i = 0; i < g.edges.size(); i++) {
		Edge e = (Edge)g.edges.get(i);
		System.out.println(e.source + " -> " + e.target + ";");
	}

	canvas.setContents(DirectedGraphDemo.buildGraph(g));
}

/**
 * @since 3.0
 * @param desc
 * @return
 */
private boolean ignoreDescriptor(IPluginDescriptor desc) {
	return desc.getUniqueIdentifier().indexOf("org.eclipse.gef") != -1;
}

void put(IPluginDescriptor desc, Node n){
	map.put(desc.getUniqueIdentifier(), n);
}

Node get(String ID) {
	return (Node)map.get(ID);
}


Node get(IPluginDescriptor desc) {
	return (Node)map.get(desc.getUniqueIdentifier());
}

/**
 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
 */
public void setFocus() {
	canvas.setFocus();
}

}
