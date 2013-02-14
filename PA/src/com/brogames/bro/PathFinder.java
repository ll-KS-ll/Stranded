package com.brogames.bro;

import java.util.LinkedList;
import java.util.List;
import android.graphics.Point;
import android.util.Log;

public class PathFinder {
	
	private long time;
	
	public List<Node> searchPath(Tile[][] layer, Point startPos, Point endPos) {
		time = System.currentTimeMillis();
		
		PriorityList openList = new PriorityList();
		LinkedList<Node> closedList = new LinkedList<Node>();
		
		Node startNode = new Node(startPos);
		Node endNode = new Node(endPos);
		startNode.costG = 0;
		startNode.costH = startNode.getH(endNode);
		startNode.parentNode = null;
		openList.add(startNode);
		
		while (!openList.isEmpty()) {
			Node node = (Node)openList.removeFirst();
			
			if(System.currentTimeMillis() - time > 60)
				return null;
				
			if(node.equals(endNode))
				return constructPath(node);
			
			List<Node> neighbors = node.getNeighbors(layer);
			
			for(int i=0; i<neighbors.size(); i++){
				Node neighborNode = neighbors.get(i);
				
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				
				int G = node.getG() + neighborNode.getG();
				
				if(!isOpen && !isClosed || G < neighborNode.getG()){
					neighborNode.parentNode = node;
					neighborNode.costG = G;
					neighborNode.costH = neighborNode.getH(endNode);
					if(isClosed)
						closedList.remove(neighborNode);
					if(!isOpen)
						openList.add(neighborNode);
				}
			}
			closedList.add(node);
		}
		return null;
	}
	
	
	protected List<Node> constructPath(Node node){
		LinkedList<Node> path = new LinkedList<Node>();
		
		while(node.parentNode != null){
			path.addFirst(node);
			node = node.parentNode;
		}
		long timePassed = System.currentTimeMillis() - time;
		Log.d("PathFinder", "Path found in " + timePassed + "ms");
		return path;
	}
	
	@SuppressWarnings("rawtypes")
	public static class PriorityList extends LinkedList{
		
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public void add(Comparable object){
			for(int i=0; i<size(); i++){
				if(object.compareTo(get(i)) <= 0){
					add(i, object);
					return;
				}
			}
			addLast(object);
		}
	}
}