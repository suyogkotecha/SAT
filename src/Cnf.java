import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*This code is picked up/derived from AIMA's code base
 * http://code.google.com/p/aima-java
 * http://code.google.com/p/aima-java/downloads/detail?name=aima-java-1.7.0-Chp4-Complete.zip&can=2&q=
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
		long heapSize = Runtime.getRuntime().totalMemory();
        
        //Print the jvm heap size.
        //System.out.println("Heap Size = " + heapSize);
		int M = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		double f = Double.parseDouble(args[2]);
		double e = Double.parseDouble(args[3]);
		//M=3;N=2;
		//System.out.println("M:"+M+" N:"+N+" f:"+f+" e:"+e);
		//fill matrix R
		M=16;
		N=2;
		/*f = 0.0;
		e = 1;*/
		
			Globals.fillMatrix(M, f, e);
		
		
		Sentence st = new Sentence();		
		generateClauseForAtleastOneTable(st,M,N);
		generateClauseForNotMoreThanOne(st,M,N);		
		generateClauseForMatrix(st,M,N);

		long lStartTime = new Date().getTime(); //start time
		System.out.print("PL:" + plResolution(st)+"\t");
		long lEndTime = new Date().getTime(); //end time
		System.out.print(((lEndTime - lStartTime)/1000.0)+"\t");
		WalkSat ws = new WalkSat();
		lStartTime = new Date().getTime(); //start time
		System.out.print(ws.walkSat(st, 100, M, N,0.5)+"\t");
		lEndTime = new Date().getTime(); //end time
		System.out.print(lEndTime - lStartTime);
		System.out.println();
		
		/*
		Clause cl = new Clause();
		cl.addLiteral(1, 3, 2);
		cl.addLiteral(-1, 2, 3);
		cl.addLiteral(1, 2, 1);
		
		Clause cl1 = new Clause();
		cl1.addLiteral(1, 2, 3);
		cl1.addLiteral(1, 4, 3);
		cl1.addLiteral(-1, 3, 5);
		//cl1.addLiteral(-1, 2, 3);
		//ClauseSymbols cs = new ClauseSymbols(cl, cl1);
		
		Sentence dummy = new Sentence(cl);
		dummy.addClause(cl1);
		
		System.out.println(dummy.filterOutClausesWithTwoComplementaryLiterals());*/
		
	}
	public static boolean plResolution(Sentence st)
	{
		Sentence clauses = null;
		Sentence newClauses = new Sentence();
		Set <Sentence> pairs = null;
		Sentence pair = null;
		Iterator <Clause> itr = null;
		Sentence resolvent = null;
		clauses = st.filterOutClausesWithTwoComplementaryLiterals();
		Sentence toRemove = null;
		while(true)
		{
			//newClauses = null;
			//newClauses = new Sentence();
			toRemove = new Sentence();
			
			pairs = null;
			pair = null;
			itr = null;
			resolvent = null;
			pairs = returnPairs(clauses);
			
			//System.out.println(pairs);
			//System.out.println("Individual pair");
			for (int i = 0; i < pairs.size(); i++) 
			{
				pair = getSentence(pairs, i);				
				if(pair.size()!=2)
				{
					System.out.println("ERROR: Cant have more/less than 2 sets in sentence");
					return false;
				}
				itr = pair.clauses.iterator();
				Clause cl1 = itr.next();
				Clause cl2 = itr.next();
				resolvent = plResolve(cl1,cl2 );
				resolvent = resolvent.filterOutClausesWithTwoComplementaryLiterals();
				if(resolvent.size()!=0)
				{
					toRemove.addClause(cl1);
					toRemove.addClause(cl2);
				}
				if(resolvent.hasEmptyClause())
				{
					//System.out.println("BINGO!");
					//System.out.println(resolvent);
					return false;
				}				
								
				newClauses.clauses = SetOps.union(newClauses.clauses, resolvent.clauses);			
			}
			if((newClauses.size() ==0 || newClauses == null) && clauses.size()!=0)
			{
				return true;
			}
			if (SetOps.intersection(newClauses.clauses, clauses.clauses).size() == newClauses.size()) 
			{// subset test
				//System.out.println(newClauses);
				return true;
			}
			//clauses.clauses = SetOps.difference(clauses.clauses, toRemove.clauses);
			//clauses.remove(toRemove);
			clauses.clauses = SetOps.union(newClauses.clauses, clauses.clauses);
			clauses = clauses.filterOutClausesWithTwoComplementaryLiterals();			
		}
		
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
				//System.out.println("sentence after i:"+i+" j:"+j+" pair: "+stTemp);
				pairs.add(stTemp);
			}
			
		}
		return pairs;
	}
	public static Clause createResolventClause(ClauseSymbols cs, Literal toRemove) 
	{
		List<Literal> positiveSymbols = new Converter<Literal>().setToList(SetOps
				.union(cs.clause1PositiveSymbols.litSet, cs.clause2PositiveSymbols.litSet));
		List<Literal> negativeSymbols = new Converter<Literal>().setToList(SetOps
				.union(cs.clause1NegativeSymbols.litSet, cs.clause2NegativeSymbols.litSet));
		if (positiveSymbols.contains(toRemove)) {
			positiveSymbols.remove(toRemove);
		}
		if (negativeSymbols.contains(toRemove)) {
			negativeSymbols.remove(toRemove);
		}
		//System.out.println(positiveSymbols);
		//System.out.println(negativeSymbols);
		
		Clause c1 = new Clause();
		for (int i = 0; i < positiveSymbols.size(); i++) {
			c1.addLiteral(positiveSymbols.get(i));
		}
		for (int i = 0; i < negativeSymbols.size(); i++) {
			Literal l = negativeSymbols.get(i);
			c1.addLiteral(-1,l.table,l.person);
		}
		return c1;
	}
	public static Sentence plResolve(Clause clause1, Clause clause2)
	{
		Sentence resolvents = new Sentence();
		//System.out.println(resolvents);
		ClauseSymbols cs = new ClauseSymbols(clause1, clause2);
		//Iterator<Literal> iter = cs.getComplementedSymbols().litSet.iterator();		
		Iterator<Literal> iter = cs.getComplementedSymbols();
		while (iter.hasNext()) {
			//!Literal symbol = iter.next();			
			resolvents.addClause((createResolventClause(cs, iter.next())));
		}
		//System.out.println(resolvents);
		return resolvents;
	}
	public static Sentence getSentence(Set <Sentence> pairs, int location)
	{
		Iterator <Sentence> itr = pairs.iterator();
		for(int i=0;itr.hasNext()&&i<location;i++)
		{
			itr.next();
		}
		return itr.next();		
	}
}
