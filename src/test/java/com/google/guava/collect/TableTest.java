package com.google.guava.collect;

import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.kibou.collect.TIterables;
import com.kibou.collect.TIterables.ToStringStyle;

//TODO 补测试方法 
public class TableTest {
	//https://github.com/google/guava/wiki/NewCollectionTypesExplained#table
	
	private class Vertex{
		long id;
		Vertex(long id){
			this.id = id;
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Vertex){
				return obj == null ? false : id == ((Vertex)obj).id;
			}return false;
		}
		@Override
		public String toString() {
			return "Vertex:"+id;
		}
	}
	
	@Test
	public void testTable(){
		Vertex v1,v2,v3;
		v1 = new Vertex(1L);
		v2 = new Vertex(2L);
		v3 = new Vertex(3L);
		
		//    Row,    Column, Value
		Table<Vertex, Vertex, Double> weightedGraph = HashBasedTable.create();
		weightedGraph.put(v1, v2, 4.0);
		weightedGraph.put(v1, v3, 20.0);
		weightedGraph.put(v2, v3, 5.0);

		Map<Vertex, Double> row = weightedGraph.row(v1); // returns a Map mapping v2 to 4, v3 to 20
		Map<Vertex, Double> column = weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 5
		
		print("row1: ",row);
		print("column1: ",column);
		
		System.out.println("=====================");
		
		print("rowMap: " ,weightedGraph.rowMap());// Map<Row,Map<Column,Value>>
		print("rowKeySet : ",weightedGraph.rowKeySet()); // similar  rowMap.keySet()
		Assert.assertEquals(weightedGraph.rowKeySet(), weightedGraph.rowMap().keySet());
		
		print("columnMap: " ,weightedGraph.columnMap()); // Map<Column,Map<Row,Value>>
		print("columnKeySet : ", weightedGraph.columnKeySet());
		Assert.assertEquals(weightedGraph.columnKeySet(), weightedGraph.columnMap().keySet());
		
		Set<Cell<Vertex, Vertex, Double>> cellSet = weightedGraph.cellSet();//Table.Cell just like Map.Entry , cellSet just like Map.entrySet
		TIterables.toString(cellSet,"Table.Cells : \n",new TIterables.Formatter<Table.Cell<Vertex, Vertex, Double>>() {
			@Override
			public String format(Cell<Vertex, Vertex, Double> cell) {
				return "{row: " + cell.getRowKey() + ",column: " + cell.getColumnKey() + ",value:" + cell.getValue() + "}";
			}
		},"",ToStringStyle.MULTILINE);
	}
		
	void print(Object obj){
		print(null,obj);
	}
	void print(String title,Object obj){
		System.out.println(title + (obj == null ? "null" : obj.toString()));
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
