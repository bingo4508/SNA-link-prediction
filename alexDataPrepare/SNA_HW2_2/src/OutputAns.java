import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
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
		BufferedReader neighborsFile = null;
		String line = null;
		HashMap<Integer, ArrayList<Integer>> neighborMap = new HashMap<Integer, ArrayList<Integer>>();
		String[] neighborStr;
		ArrayList<Integer> neighbors;
		try {
			neighborsFile = new BufferedReader(new FileReader("neighbor.txt"));
			line = neighborsFile.readLine();
			while (line != null && !line.equals(""))
			{
				neighborStr = line.split(" ");
				neighbors = new ArrayList<Integer>();
				for (int i = 1; i < neighborStr.length; i++)
					neighbors.add(Integer.parseInt(neighborStr[i]));
				neighborMap.put(Integer.parseInt(neighborStr[0]), neighbors);
				
				line = neighborsFile.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BufferedReader pairIndexFile = null, predictProbFile = null;
		PrintStream outputAns = null;
		try 
		{
			outputAns = new PrintStream(new File("Predict3.txt"));
			pairIndexFile = new BufferedReader(new FileReader("outputTestPairIndexFeatureSelect.txt"));
			
			int currentOutputNode = -1;
			int maxFriends;
			String pairStr[] = null;
			String token[];
			TreeSet<FriendshipProb> friendList = new TreeSet<FriendshipProb>();
			TreeSet<FriendshipProb> reallyFriendList = new TreeSet<FriendshipProb>();
			FriendshipProb newFriend;
			int testNodeCount = 1;
			line = pairIndexFile.readLine();
			if (line != null)
				pairStr = line.split(" ");
			while (line != null && !line.equals(""))
			{
				currentOutputNode = Integer.parseInt(pairStr[0]);
				predictProbFile = new BufferedReader(new FileReader("H:\\finalpredict2\\"+(testNodeCount++)+".predict"));
				predictProbFile.readLine();
				friendList.clear();
				reallyFriendList.clear();
				if (!neighborMap.containsKey(currentOutputNode))
					maxFriends = 1;
				else
					maxFriends = neighborMap.get(currentOutputNode).size()/4+1;
				maxFriends = maxFriends > 30 ? 30 : maxFriends;
				while (line != null && !line.equals("") && Integer.parseInt(pairStr[0]) == currentOutputNode)
				{
					line = predictProbFile.readLine();
					if (maxFriends != 0)
					{
						token = line.split(" ");
						newFriend = new FriendshipProb(Integer.parseInt(pairStr[1]), Double.parseDouble(token[1]));
						if (!neighborMap.containsKey(currentOutputNode) || !neighborMap.get(currentOutputNode).contains(newFriend.otherNodeId))
						{
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
						}
						else
							reallyFriendList.add(new FriendshipProb(Integer.parseInt(pairStr[1]), Double.parseDouble(token[1])));
					}
					
					line = pairIndexFile.readLine();
					if (line != null)
						pairStr = line.split(" ");
				}
				
				int getFriendsNum = 0;
				Iterator<FriendshipProb> r = reallyFriendList.descendingIterator();
				while (r.hasNext())
				{
					if (r.next().prob > friendList.first().prob)
						getFriendsNum++;
					else
						break;
				}
				System.out.println(""+getFriendsNum+" "+reallyFriendList.size());
				outputAns.print(""+currentOutputNode+":");
				Iterator<FriendshipProb> f = friendList.descendingIterator();
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
