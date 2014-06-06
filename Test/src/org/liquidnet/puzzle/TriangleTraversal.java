package org.liquidnet.puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TriangleTraversal {

	private List<List<Node>> graph;
	public TriangleTraversal(){
		graph = new ArrayList<>();
	}
	private static class Node {
		private int weight;
		private int largestPathWeight;
		private Node next;
		public Node(int weight){
			this.weight = weight;
			this.largestPathWeight = weight;
		}
		public void setMaxWeightedPath(Node left, Node right){
			if (left.largestPathWeight > right.largestPathWeight){
				this.next = left;
				this.largestPathWeight = this.weight + left.largestPathWeight;
			}else{
				this.next = right;
				this.largestPathWeight = this.weight + right.largestPathWeight;
			}
		}
	}
	
	public void createGraph(String _fileName){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(_fileName));
			String line=null;
			int noElements=0;
			while((line=reader.readLine())!=null){
				if (line.length() >0){
					noElements++;
					List<Node> nodes = new ArrayList<Node>();
					graph.add(nodes);
					StringTokenizer st = new StringTokenizer(line);				
					while(st.hasMoreTokens()){
						nodes.add(new Node(Integer.parseInt(st.nextToken())));
					}
					if (nodes.size() != noElements){
						throw new RuntimeException("Input data error");
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.printf("File not found %s\n\r", _fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printPath(){
		if (graph.size() > 0 && graph.get(0) != null){
			Node pathElement = graph.get(0).get(0);
			System.out.printf("Largest Path Weight : %d\n", pathElement.largestPathWeight);
			System.out.printf("Path Elements :");
			
			while(pathElement != null){
				System.out.printf("  %d", pathElement.weight);
				pathElement = pathElement.next;
			}
		}
	}
	
	public void findLargestWeightedPath(){
		if (graph.size() >1){
			for(int i=graph.size()-2;i>=0;i--){
				List<Node> parent = graph.get(i);
				List<Node> children= graph.get(i+1);
				for(int j=0;j<parent.size();j++){
					parent.get(j).setMaxWeightedPath(children.get(j), children.get(j+1));
				}
			}
			printPath();
		}
	}
	
	public static void main(String args[]){
		if (args.length < 1){
			System.out.println("Usage: TrangleProblem <fileName>");
			return;
		}
		TriangleTraversal tringTr = new TriangleTraversal();
		tringTr.createGraph(args[0]);
		tringTr.findLargestWeightedPath();
	}
}
