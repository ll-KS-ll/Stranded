package com.brogames.bro;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Point;

public class Node implements Comparable<Node> {

	// I've no idea what I'm doing! :s
	// Wait I might have some sort of idea! :o
	// It kinda seems to work at least :D
	
	public Point pos; // node position
	public int costG;// the cost to get to this node from the starting node
	public int costH;// the estimated cost to get from this node to the end node
	public Node parentNode; // parent node

	public Node(Point pos) {
		this.pos = pos;
	}

	public int getF() {
		return costG + costH;
	}

	public int getG() {
		if(parentNode != null){
			Node node = parentNode;
			costG = 0;
			while(node.parentNode != null){
				node = node.parentNode;
				costG+=15;
			}
			return costG;
		}
		return 0;
	}

	public int getH(Node node) {
		int dx = Math.abs(node.pos.x*32 - pos.x*32);
		int dy = Math.abs(node.pos.y*32 - pos.y*32);
		costH = dx + dy;
		return costH;
	}

	public List<Node> getNeighbors(Tile[][] layer) {
		LinkedList<Node> neighbors = new LinkedList<Node>();

		try {
			if (!layer[pos.x + 1][pos.y].isObstacle()) {
				Point tempPos = new Point(pos.x + 1, pos.y);
				Node temp = new Node(tempPos);
				temp.parentNode = this;
				neighbors.add(temp);
			}
		} catch (Exception ex) {
		}

		try {
			if (!layer[pos.x - 1][pos.y].isObstacle()) {
				Point tempPos = new Point(pos.x - 1, pos.y);
				Node temp = new Node(tempPos);
				temp.parentNode = this;
				neighbors.add(temp);
			}
		} catch (Exception ex) {
		}

		try {
			if (!layer[pos.x][pos.y + 1].isObstacle()) {
				Point tempPos = new Point(pos.x, pos.y + 1);
				Node temp = new Node(tempPos);
				temp.parentNode = this;
				neighbors.add(temp);
			}
		} catch (Exception ex) {
		}

		try {
			if (!layer[pos.x][pos.y - 1].isObstacle()) {
				Point tempPos = new Point(pos.x, pos.y - 1);
				Node temp = new Node(tempPos);
				temp.parentNode = this;
				neighbors.add(temp);
			}
		} catch (Exception ex) {
		}

		return neighbors;
	}

	public boolean addNode(Object node) {
		int node1 = costG + costH;
		int node2 = ((Node) node).costG + ((Node) node).costH;
		if (node1 <= node2)
			return true;
		return false;
	}

	public int getX() {
		return pos.x;
	}

	public int getY() {
		return pos.y;
	}

	@Override
	public int compareTo(Node another) {
		if (getF() > another.getF()) {
			return 1;
		} else if (getF() < another.getF()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		Node node = (Node) o;
		if (pos.x == node.getX() && pos.y == node.getY())
			return true;
		return false;
	}
}