import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class PairSimilarity {
	
	public static void main(String[] args)
	{
		AttributesTable table = new AttributesTable("column_type.json", "pre_nodes_profile.csv");
		table.init();
		
		
		/*
		for (int i = 1; i < table.getNode(1).size()+1; i++)
		{
			System.out.println("@attribute \'column_"+i+"\' numeric");
		}
		for (int i = 1; i < table.getNode(1).size()+1; i++)
		{
			System.out.println("@attribute \'missingCountColumn_"+i+"\' { 0, 1, 2}");
		}*/
		
	}
	
	public static SimilarityData calcSimilarity(Data data1, Data data2)
	{
		if (data1 instanceof CategoricalData)
		{
			CategoricalData d1 = (CategoricalData)data1;
			CategoricalData d2 = (CategoricalData)data2;
			
			ArrayList<Integer> commonData = new ArrayList<Integer>();
			commonData.addAll(d1.values);
			commonData.retainAll(d2.values);
			
			return new SimilarityData(commonData.size());
		}
		else if (data2 instanceof NumericalData)
		{
			NumericalData d1 = (NumericalData)data1;
			NumericalData d2 = (NumericalData)data2;
			
			return new SimilarityData(Math.abs(d1.value-d2.value));		//actually it's difference instead of similarity
		}
		else
			System.out.println("Error");
		return null;
	}
	
	public static HashMap<Integer, Data> aggregateNodeAttributes(HashMap<Integer, Data> node1, HashMap<Integer, Data> node2)
	{
		HashMap<Integer, Data> aggregatedAttributes = new HashMap<Integer, Data>(node1.size()*2);
		for (int i = 1; i < node1.size()+1; i++)
		{
			if (!node1.get(i).missing && !node2.get(i).missing)
			{
				aggregatedAttributes.put(i, calcSimilarity(node1.get(i), node2.get(i)));
				aggregatedAttributes.put(i+node1.size(), new MissingCountData(0));
			}
			else if (!node1.get(i).missing || !node2.get(i).missing)
			{
				aggregatedAttributes.put(i, new SimilarityData());
				aggregatedAttributes.put(i+node1.size(), new MissingCountData(1));
			}
			else
			{
				aggregatedAttributes.put(i, new SimilarityData());
				aggregatedAttributes.put(i+node1.size(), new MissingCountData(2));
			}
		}
		return aggregatedAttributes;
	}
	
	public static HashMap<Integer, Data> aggregateNodeAttributesFeatureSelect(HashMap<Integer, Data> node1, HashMap<Integer, Data> node2)
	{
		int []selectedFeatures = new int[]{2, 4, 10, 22, 23};
		HashMap<Integer, Data> aggregatedAttributes = new HashMap<Integer, Data>(node1.size()*2);
		for (int i = 0; i < selectedFeatures.length; i++)
		{
			if (!node1.get(selectedFeatures[i]).missing && !node2.get(selectedFeatures[i]).missing)
			{
				aggregatedAttributes.put(i+1, calcSimilarity(node1.get(selectedFeatures[i]), node2.get(selectedFeatures[i])));
				aggregatedAttributes.put(i+1+selectedFeatures.length, new MissingCountData(0));
			}
			else if (!node1.get(selectedFeatures[i]).missing || !node2.get(selectedFeatures[i]).missing)
			{
				aggregatedAttributes.put(i+1, new SimilarityData());
				aggregatedAttributes.put(i+1+selectedFeatures.length, new MissingCountData(1));
			}
			else
			{
				aggregatedAttributes.put(i+1, new SimilarityData());
				aggregatedAttributes.put(i+1+selectedFeatures.length, new MissingCountData(2));
			}
		}
		return aggregatedAttributes;
	}
	
}

class SimilarityData extends Data
{
	double value;
	public SimilarityData()
	{
		missing = true;
	}
	public SimilarityData(double d)
	{
		missing = false;
		value = d;
	}
	@Override
	public String toString()
	{
		if (missing)
			return "?";
		else
			return ""+value;
	}
}

class MissingCountData extends Data
{
	int value;
	public MissingCountData(int c)
	{
		missing = false;
		value = c;
	}
	@Override
	public String toString()
	{
		if (missing)
			return "missing";
		else
			return ""+value;
	}
}