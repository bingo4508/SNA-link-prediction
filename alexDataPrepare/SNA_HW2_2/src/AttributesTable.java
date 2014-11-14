import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class AttributesTable {
	private String columnTypeFileName;
	private String nodesProfileFileName;
	private HashMap<Integer, String> columnTypeMap;
	private HashMap<Integer, HashMap<Integer, Data>> nodesProfileMap;
	
	
	public static void main(String[] args)
	{
		AttributesTable table = new AttributesTable("column_type.json", "pre_nodes_profile.csv");
		table.init();
		
		Scanner input = new Scanner(System.in);
		int nodeIndex, column;
		/*while (input.hasNext())
		{
			nodeIndex = input.nextInt();
			column = input.nextInt();
			table.output(nodeIndex, column);
		}*/
		/*
		HashSet<Integer> possibleValue = new HashSet<Integer>();
		column = 49;
		for (HashMap<Integer, Data> node:table.nodesProfileMap.values())
		{
			if (node.get(column).missing)
				System.out.println("missing");
			else if (!possibleValue.contains(((NumericalData)(node.get(column))).value))
				possibleValue.add(((NumericalData)(node.get(column))).value);
		}
		for (int v:possibleValue)
			System.out.println(v);
		*/
	}
	
	public AttributesTable(String columnTypeFileName, String nodesProfileFileName)
	{
		this.columnTypeFileName = columnTypeFileName;
		this.nodesProfileFileName = nodesProfileFileName;
		columnTypeMap = new HashMap<Integer, String>(54);
		nodesProfileMap = new HashMap<Integer, HashMap<Integer, Data>>(100056);
	}
	
	public void init()
	{
		BufferedReader columnTypeInput = null;
		String line = null;
		try 
		{
			columnTypeInput = new BufferedReader(new FileReader(columnTypeFileName));
			columnTypeInput.readLine();
			line = columnTypeInput.readLine();
			while (!line.equals("}"))
			{
				columnTypeMap.put(Integer.parseInt(line.substring(line.indexOf('_')+1, line.indexOf(":")-1)), line.substring(line.indexOf(':')+3, line.lastIndexOf('\"')));
				line = columnTypeInput.readLine();
			}
			columnTypeInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader nodesProfileInput = null;
		String columnsString[];
		HashMap<Integer, Data> attributes;
		try {
			nodesProfileInput = new BufferedReader(new FileReader(nodesProfileFileName));
			nodesProfileInput.readLine();
			line = nodesProfileInput.readLine();
			while (line != null && line.length() > 2)
			{
				columnsString = line.split(",", -1);
				attributes = new HashMap<Integer, Data>(54);
				for (int i = 1; i < 55; i++)
				{
					if (columnTypeMap.get(i).equals("categorical"))
						attributes.put(i, new CategoricalData(columnsString[i]));
					else if (columnTypeMap.get(i).equals("numerical"))
						attributes.put(i, new NumericalData(columnsString[i]));
					else
						System.out.println("Error");
				}
				nodesProfileMap.put(Integer.parseInt(columnsString[0]), attributes);
				line = nodesProfileInput.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void output(int nodeId, int column)
	{
		System.out.println(""+nodeId+" "+column+": "+ nodesProfileMap.get(nodeId).get(column));
	}
	
	public Data getAttribute(int nodeId, int column)
	{
		return nodesProfileMap.get(nodeId).get(column);
	}
	
	public HashMap<Integer, Data> getNode(int nodeId)
	{
		return nodesProfileMap.get(nodeId);
	}
	
	public Set<Integer> getNodes()
	{
		return nodesProfileMap.keySet();
	}
	
	public void deleteNode(int n)
	{
		nodesProfileMap.remove(n);
	}
}

abstract class Data
{
	protected boolean missing;
	//public abstract void input(String columnString);
}

class CategoricalData extends Data
{
	ArrayList<Integer> values;
	public CategoricalData(String columnString)
	{
		if (columnString.equals(""))
			missing = true;
		else
		{
			String valuesArray[] = columnString.split(" ");
			missing = false;
			values = new ArrayList<Integer>();
			for (String v:valuesArray)
				values.add(Integer.parseInt(v));
		}
	}
	@Override
	public String toString()
	{
		String s = "";
		if (!missing)
			for (Integer v:values)
				s += v + " ";
		else
			s = "missing";
		return s;
	}
}

class NumericalData extends Data
{
	int value;
	public NumericalData(String columnString)
	{
		if (columnString.equals(""))
			missing = true;
		else
		{
			missing = false;
			value = Integer.parseInt(columnString);
		}
	}
	@Override
	public String toString()
	{
		if (missing)
			return "missing";
		else
			return ""+value+" ";
	}
}