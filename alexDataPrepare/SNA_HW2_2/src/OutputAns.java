import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.TreeSet;


class FriendshipProb implements Comparable
{
	public int otherNodeId;
	public double prob;
	
	public FriendshipProb(int id, double p)
	{
		otherNodeId = id;
		prob = p;
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		FriendshipProb fp = (FriendshipProb) arg0;
		if (fp.otherNodeId == otherNodeId)
			return 0;
		else if (fp.prob > prob)
			return -1;
		else if (fp.prob < prob)
			return 1;
		return 1;
	}
}

public class OutputAns {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader pairIndexFile = null, predictProbFile = null;
		PrintStream outputAns = null;
		String line = null;
		try 
		{
			outputAns = new PrintStream(new File("Predict.txt"));
			pairIndexFile = new BufferedReader(new FileReader("I:\\outputTestPairIndexFeatureSelect.txt"));
			predictProbFile = new BufferedReader(new FileReader("I:\\outputSvmlibFileTestWithWrongLabelFeatureSelect.scale.predict"));
			int currentOutputNode = -1;
			int maxFriends;
			String pairStr[] = null;
			String token[];
			TreeSet<FriendshipProb> friendList = new TreeSet<FriendshipProb>();
			FriendshipProb newFriend;
			predictProbFile.readLine();
			line = pairIndexFile.readLine();
			if (line != null)
				pairStr = line.split(" ");
			while (line != null && !line.equals(""))
			{
				currentOutputNode = Integer.parseInt(pairStr[0]);
				friendList.clear();
				maxFriends = 30;
				while (line != null && !line.equals("") && Integer.parseInt(pairStr[0]) == currentOutputNode)
				{
					line = predictProbFile.readLine();
					token = line.split(" ");
					newFriend = new FriendshipProb(Integer.parseInt(pairStr[1]), Double.parseDouble(token[2]));
					if (friendList.size() == maxFriends)
					{
						if (friendList.first().compareTo(newFriend) < 0)
						{
							friendList.pollFirst();
							friendList.add(newFriend);
						}
					}
					else
						friendList.add(newFriend);
					
					line = pairIndexFile.readLine();
					if (line != null)
						pairStr = line.split(" ");
				}
				
				outputAns.print(""+currentOutputNode+":");
				Iterator<FriendshipProb> f = friendList.descendingSet().descendingIterator();
				if (f.hasNext())
					outputAns.print(""+f.next().otherNodeId);
				while (f.hasNext())
					outputAns.print(","+f.next().otherNodeId);
				outputAns.println();
			}
			
			pairIndexFile.close();
			predictProbFile.close();
			outputAns.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
