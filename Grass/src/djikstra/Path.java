package djikstra;

import java.util.ArrayList;
import java.util.Collections;

import grass.data.Tile;

public class Path {
	private ArrayList<Node> nodes;
	private Node endNode;
	private ArrayList<Node> unvisited = new ArrayList<Node>();
	private Node currentNode;
	private boolean finished = false;
	private ArrayList<Tile> path = new ArrayList<Tile>();
	private ArrayList<Tile> tiles;
	private ArrayList<Node> visited = new ArrayList<Node>();
	private int inc = 0;
	public Path(int sx,int sy,int ex, int ey, ArrayList<Tile> tiles){
		this.nodes = generateNodes(tiles);
		this.tiles = tiles;
		currentNode = findNode(sx,sy);
		currentNode.setDistance(0);
		endNode = findNode(ex,ey);
		for(Node n: nodes){
			if(n!=currentNode){
				unvisited.add(n);
			}
		}
		do{
			considerNeighbours();
			currentNode = getUnvisitedNeighbour();
		}while(!finished&&endNode.getParent()==null&&getUnvisitedNeighbour()!=null);
		if(unvisited.size()==0||endNode.getParent()==null){
			System.err.println("could not find path");
		}else{
			System.out.println("conclusion reached");
		}
		
		Node k = endNode;
		while(k.getParent()!=null){
			path.add(findTile(k.getX(),k.getY()));
			k = k.getParent();
		}
		Collections.reverse(path);
		for(Tile t: path){
			System.out.println(t.getGridX()+":"+t.getGridY());
		}
	}
	private ArrayList<Node> generateNodes(ArrayList<Tile> t){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(Tile b: t){
			if(b.isPassable()){
				nodes.add(new Node(b.getGridX(),b.getGridY()));
			}
		}
		return nodes;
	}
	private Node findNode(int x,int y){
		for(Node n: nodes){
			if(n.getX()==x&&n.getY()==y){
				return n;
			}
		}
		return null;
	}
	private void considerNeighbours(){
		for(Node m: unvisited){
			if(m.isNeighbour(currentNode)){
				double d = currentNode.getDistance()+m.getDistance(currentNode);
				if(d<m.getDistance()){
					m.setDistance(d);
					m.setParent(currentNode);
				}
			}
		}
		if(currentNode==endNode){
			finished = true;
		}
		System.out.println("considering "+inc);
		inc++;
		unvisited.remove(currentNode);
		visited.add(currentNode);
	}
	private Node getUnvisitedNeighbour(){
		if(unvisited.size()==0){ return null; }
		for(Node m: visited){
			for(Node n: unvisited){
				if(n.isNeighbour(m)){
					return n;
				}
			}
		}
		return null;
	}
	private Tile findTile(int x, int y){
		for(Tile t: tiles){
			if(t.getGridX()==x&&t.getGridY()==y){
				return t;
			}
		}
		return null;
	}
	public ArrayList<Tile> getPath(){
		return path;
	}
}
