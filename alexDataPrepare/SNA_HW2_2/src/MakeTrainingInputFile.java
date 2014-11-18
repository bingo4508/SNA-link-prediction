import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MakeTrainingInputFile {
	
	public static void main(String[] args)
	{
		//seek the last line
		/*try {
			File file = new File("I:\\outputTestPairIndexFeatureSelect.txt");
			RandomAccessFile rfile = new RandomAccessFile(file,"r"); 
			rfile.seek(file.length()-30);
			System.out.println(rfile.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//create test data
		AttributesTable table = new AttributesTable("column_type.json", "pre_nodes_profile.csv");
		table.init();
		
		BufferedReader testNodesFile = null;
		PrintStream outputTestLibsvmStream = null;
		PrintStream outputTestNodePairIndex = null;
		String line = null;
		String nodes[];
		try 
		{
			outputTestLibsvmStream = new PrintStream(new File("I:\\outputSvmlibFileTestWithWrongLabelFeatureSelect_2_4_10_22_23.txt"));
			outputTestNodePairIndex = new PrintStream(new File("I:\\outputTestPairIndexFeatureSelect_2_4_10_22_23.txt"));
			
			testNodesFile = new BufferedReader(new FileReader("test_nodes.txt"));
			line = testNodesFile.readLine();
			testNodesFile.close();
			nodes = line.split(" ");
			int n;
			for (String ns:nodes)
			{
				n = Integer.parseInt(ns);
				for (int otherNode:table.getNodes())
				{
					if (n != otherNode)
					{
						outputLibsvmFeatureSelect(false, n, otherNode, table, outputTestLibsvmStream);
						outputTestNodePairIndex.println(""+n+" "+otherNode);
					}
				}
			}
			outputTestLibsvmStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// merge positive and negative
		/*BufferedReader file = null;
		PrintStream outputArffStream = null, outputLibsvmStream = null;
		String line = null;
		try 
		{*/
			/*outputArffStream = new PrintStream(new File("outputArffFileWhole.txt"));
			file = new BufferedReader(new FileReader("outputArffFilePositive.txt"));
			line = file.readLine();
			while (line != null && !line.equals(""))
			{
				outputArffStream.println(line);
				line = file.readLine();
			}
			file.close();
			file = new BufferedReader(new FileReader("outputArffFileNegative.txt"));
			line = file.readLine();
			while (line != null && !line.equals(""))
			{
				outputArffStream.println(line);
				line = file.readLine();
			}
			file.close();
			outputArffStream.close();
			*/
			/*outputLibsvmStream = new PrintStream(new File("outputLibsvmFileWholeFeatureSelect_2_4_10_22_23.txt"));
			file = new BufferedReader(new FileReader("outputLibsvmFileNegativeFeatureSelect_2_4_10_22_23.txt"));
			line = file.readLine();
			while (line != null && !line.equals(""))
			{
				outputLibsvmStream.println(line);
				line = file.readLine();
			}
			file.close();
			file = new BufferedReader(new FileReader("outputLibsvmFilePositiveFeatureSelect_2_4_10_22_23.txt"));
			line = file.readLine();
			while (line != null && !line.equals(""))
			{
				outputLibsvmStream.println(line);
				line = file.readLine();
			}
			file.close();
			outputLibsvmStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*AttributesTable table = new AttributesTable("column_type.json", "pre_nodes_profile.csv");
		table.init();
		
		HashSet<Pair<Integer, Integer>> positiveEdges = createEdges("train_edges.txt");
		//HashSet<Pair<Integer, Integer>> negativeEdges = createEdges("negative_pairs.txt");
		PrintStream outputArffStream = null;
		PrintStream outputLibsvmStream = null;
		PrintStream outputNodePairIndex = null;
		try {
			//outputArffStream = new PrintStream(new File("outputArffFilePositive.txt"));
			outputLibsvmStream = new PrintStream(new File("outputLibsvmFilePositiveFeatureSelect_2_4_10_22_23.txt"));
			outputNodePairIndex = new PrintStream(new File("outputNodePairPositiveFeatureSelect_2_4_10_22_23.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println("Finish init");
		/*for (Pair<Integer, Integer> p:positiveEdges)
		{
			//outputArff(true, p.getFirst(), p.getSecond(), table, outputArffStream);
			//outputLibsvm(true, p.getFirst(), p.getSecond(), table, outputLibsvmStream);
			outputLibsvmFeatureSelect(true, p.getFirst(), p.getSecond(), table, outputLibsvmStream);
			outputNodePairIndex.println(""+p.getFirst()+" "+p.getSecond());
		}*/
		/*for (Pair<Integer, Integer> p:negativeEdges)
		{
			//outputArff(false, p.getFirst(), p.getSecond(), table, outputArffStream);
			//outputLibsvm(false, p.getFirst(), p.getSecond(), table, outputLibsvmStream);
			outputLibsvmFeatureSelect(false, p.getFirst(), p.getSecond(), table, outputLibsvmStream);
			outputNodePairIndex.println(""+p.getFirst()+" "+p.getSecond());
		}*/
		//outputArffStream.close();
		//outputLibsvmStream.close();
		//outputNodePairIndex.close();
		System.out.println("Output finished");
		/*Scanner input = new Scanner(System.in);
		int nodeIndex1, nodeIndex2;
		
		while (input.hasNext())
		{
			nodeIndex1 = input.nextInt();
			nodeIndex2 = input.nextInt();
			outputArff(true, nodeIndex1, nodeIndex2, table);
			outputLibsvm(false, nodeIndex1, nodeIndex2, table);
		}*/
	}
	
	public static HashSet<Pair<Integer, Integer>> createEdges(String edgesFileName)
	{
		HashSet<Pair<Integer, Integer>> edges = new HashSet<Pair<Integer, Integer>>();
		BufferedReader edgesInput = null;
		String line = null;
		String node[];
		try 
		{
			edgesInput = new BufferedReader(new FileReader(edgesFileName));
			edgesInput.readLine();
			edgesInput.readLine();
			line = edgesInput.readLine();
			while (line != null && !line.equals(""))
			{
				node = line.split(" ");
				edges.add(new Pair<Integer, Integer>(Integer.parseInt(node[0]), Integer.parseInt(node[1])));
				line = edgesInput.readLine();
			}
			edgesInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return edges;
	}
	
	public static void outputArff(boolean link, int nodeIndex1, int nodeIndex2, AttributesTable table, PrintStream outputArffStream)
	{
		HashMap<Integer, Data> aggregatedAttributes = PairSimilarity.aggregateNodeAttributes(table.getNode(nodeIndex1), table.getNode(nodeIndex2));
		for (int i = 1; i < aggregatedAttributes.size()+1; i++)
			outputArffStream.print(""+aggregatedAttributes.get(i)+",");
		outputArffStream.println(link?"yes":"no");
	}
	
	public static void outputLibsvm(boolean link, int nodeIndex1, int nodeIndex2, AttributesTable table, PrintStream outputLibsvmStream)
	{
		HashMap<Integer, Data> aggregatedAttributes = PairSimilarity.aggregateNodeAttributes(table.getNode(nodeIndex1), table.getNode(nodeIndex2));
		outputLibsvmStream.print(link?"1":"2");
		for (int i = 1; i < aggregatedAttributes.size()+1; i++)
		{
			if (!aggregatedAttributes.get(i).missing)
				outputLibsvmStream.print(" "+i+":"+aggregatedAttributes.get(i));
		}
		outputLibsvmStream.println();
	}
	
	public static void outputLibsvmFeatureSelect(boolean link, int nodeIndex1, int nodeIndex2, AttributesTable table, PrintStream outputLibsvmStream)
	{
		HashMap<Integer, Data> aggregatedAttributes = PairSimilarity.aggregateNodeAttributesFeatureSelect(table.getNode(nodeIndex1), table.getNode(nodeIndex2));
		outputLibsvmStream.print(link?"1":"2");
		for (int i = 1; i < aggregatedAttributes.size()+1; i++)
		{
			if (!aggregatedAttributes.get(i).missing)
				outputLibsvmStream.print(" "+i+":"+aggregatedAttributes.get(i));
		}
		outputLibsvmStream.println();
	}
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond);
    }

    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair otherPair = (Pair) other;
    		return (this.first.equals(otherPair.first) && this.second.equals(otherPair.second)) 
    				|| (this.first.equals(otherPair.second) && this.second.equals(otherPair.first));
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public A getFirst() {
    	return first;
    }

    public void setFirst(A first) {
    	this.first = first;
    }

    public B getSecond() {
    	return second;
    }

    public void setSecond(B second) {
    	this.second = second;
    }
}