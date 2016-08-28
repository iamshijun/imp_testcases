package com.google.guava.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

//TODO 补测试方法 
public class GuavaTableTest {
	//https://github.com/google/guava/wiki/NewCollectionTypesExplained#table
	
	private class Vertex{}
	
	public void usage(){
		Vertex v1,v2,v3;
		v1 = new Vertex();
		v2 = new Vertex();
		v3 = new Vertex();
		
		Table<Vertex, Vertex, Double> weightedGraph = HashBasedTable.create();
		weightedGraph.put(v1, v2, 4.0);
		weightedGraph.put(v1, v3, 20.0);
		weightedGraph.put(v2, v3, 5.0);

		weightedGraph.row(v1); // returns a Map mapping v2 to 4, v3 to 20
		weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 5
	}
		
	/*	Table supports a numer of views to let you use the data from any angle,including
	    
	    1. rowMap(), which views a Table<R, C, V> as a Map<R, Map<C, V>>. 
	         Similarly, rowKeySet() returns a Set<R>.
	    
		2. row(r) returns a non-null Map<C, V>.  
		     Writes to the Map will write through to the underlying Table.
		
		3. Analogous column methods are provided: columnMap(), columnKeySet(), and column(c). (Column-based access is somewhat less efficient than row-based access.)
		
		4. cellSet() returns a view of the Table as a set of Table.Cell<R, C, V>. 
		      Cell is much like Map.Entry, but distinguishes the row and column keys.
	*/
}
