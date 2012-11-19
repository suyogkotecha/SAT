import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;




/**
 * 
 */

/**
 * @author Suyog
 *
 */
public class Cnf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int M = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		double f = Double.parseDouble(args[2]);
		double e = Double.parseDouble(args[3]);
		M=5;N=3;
		System.out.println("M:"+M+" N:"+N+" f:"+f+" e:"+e);
		//fill matrix R
		
		Globals.fillMatrix(M, f, e);
		
		for(int i =0;i<M;i++)
		{
			for(int j=0;j<M;j++)
			{
				System.out.print(Globals.R[i][j]+" ");
			}
			System.out.println("");
		}
		
		Sentence st = new Sentence();
		generateClauseForAtleastOneTable(st,M,N);
		generateClauseForNotMoreThanOne(st,M,N);		
		generateClauseForMatrix(st,M,N);
		//System.out.println(st.toString());
		Clause cl = new Clause();
		cl.addLiteral(1, 1, 1);
		cl.addLiteral(1, 1, 2);
		Clause cl1 = new Clause();
		cl1.addLiteral(1, 2, 2);
		cl1.addLiteral(1, 2, 3);
		Clause cl3 = new Clause();
		cl3.addLiteral(1, 3, 4);
		cl3.addLiteral(1, 3, 5);
		Clause cl4 = new Clause();
		cl4.addLiteral(1, 4, 6);
		cl4.addLiteral(1, 4, 7);
		cl4.addLiteral(1, 4, 8);
		Sentence s = new Sentence(cl);
		s.addClause(cl1);
		s.addClause(cl3);
		s.addClause(cl4);
		System.out.println(s);
		Set <Sentence> pairs = returnPairs(s);
		System.out.println(pairs);
	}
	public static void generateClauseForAtleastOneTable(Sentence st, int M, int N)
	{
		for(int i=1;i<=M;i++)
		{
			Clause cl = new Clause();
			for(int j=1;j<=N;j++)
			{
				cl.addLiteral(1, j, i);
			}
			st.addClause(cl);
		}
		
	}
	public static void generateClauseForNotMoreThanOne(Sentence st,int M, int N)
	{
		for(int i=1;i<=M;i++)
		{
			
			for(int j=1;j<=N;j++)
			{				
				for(int k=1;k<=N;k++)
				{
					if(j==k)
						continue;
					Clause cl = new Clause();
					cl.addLiteral(-1, j, i);
					cl.addLiteral(-1, k, i);
					st.addClause(cl);
				}
			}
			
		}
	}
	public static void generateClauseForMatrix(Sentence st,int M,int N)
	{
		for(int i=0;i<M;i++)
		{
			for(int j=i+1;j<M;j++)
			{
				
				if(Globals.R[i][j] == 1)//friend
				{
					//generate friend clause
					generateFriendClause(st,M,N, i+1, j+1);
				}
				else if(Globals.R[i][j] == -1)
				{
					//generate enemy clause
					generateEnemyClause(st,M,N, i+1 , j+1);
				}
			}
		}
		
	}
	public static void generateFriendClause(Sentence st, int M, int N, int i, int j)
	{
		for(int k=1;k<=N;k++)
		{
			Clause cl = new Clause();
			cl.addLiteral(-1, k, i);
			cl.addLiteral(1, k, j);
			st.addClause(cl);
		}
	}
	public static void generateEnemyClause(Sentence st, int M, int N, int i, int j)
	{
		for(int k=1;k<=N;k++)
		{
			Clause cl = new Clause();
			cl.addLiteral(-1, k, i);
			cl.addLiteral(-1, k, j);
			st.addClause(cl);
		}
	}
	public static Set <Sentence> returnPairs(Sentence st)
	{
		Set <Sentence> pairs = new HashSet<Sentence>();
		Iterator<Clause> itr = st.clauses.iterator();
		int size = st.size();
		for(int i=0;i<size;i++)
		{
			for(int j=i+1;j<size;j++)
			{
				Sentence stTemp = new Sentence();
				stTemp.addClause(st.returnClause(i));
				stTemp.addClause(st.returnClause(j));
				System.out.println("sentence after i:"+i+" j:"+j+" pair: "+stTemp);
				pairs.add(stTemp);
			}
			
		}
		return pairs;
	}
}
