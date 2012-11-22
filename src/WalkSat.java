import java.util.Iterator;
import java.util.Random;


public class WalkSat {
	boolean model[][]= new boolean[100][100];
	boolean modelTemp[][] = new boolean[100][100];
	int modelTempVal = 0;
	boolean dynamicTemp[][] = new boolean[100][100];
	int dynamicTempVal = 0;
	public boolean walkSat(Sentence st, int flips, int M, int N)
	{
		generateModel(M, N);
		flips=1;
		for(int i=0;i<flips;i++)
		{
			if(modelSatisfiable(st))		
				return true;
			
		}
		return false;
	}
	public void generateModel(int M, int N)
	{
		for(int i=0;i<M;i++)
		{
			for(int j=i+1;j<N;j++)
			{
				model[i][j] = false;
			}
		}
		model[0][1] = true;
		model[1][2] = true;
		model[2][0] = true;
		//model[1][2] = false;
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(model[i][j]+" ");
			}
			System.out.println();
		}
	}
	public boolean modelSatisfiable(Sentence st)
	{
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			if(!clauseTruthVal(cl, model))
			{
				System.out.println(cl);
				return false;
			}
		}
		return true;
	}
	public boolean clauseTruthVal(Clause cl, boolean m[][])
	{
		Iterator <Literal> itr = cl.litSet.iterator();
		while(itr.hasNext())
		{
			Literal l = itr.next();
			int sym = l.symbol;						
			if(sym == 1)
			{
				if(m[l.person-1][l.table-1])
					return true;
			}
			else if(sym == -1)
			{
				if(!m[l.person-1][l.table-1])
					return true;
			}
		}
		return false;
	}
	public void copyModels(boolean destination[][], boolean source[][])
	{
		for(int i=0;i<100;i++)
		{
			for(int j=0;j<100;j++)
			{
				destination[i][j] = source[i][j];
			}
		}
	}
	public Literal maximizeSat(Clause cl, Sentence st)
	{
		copyModels(dynamicTemp, model);
		Iterator <Literal> itr = cl.litSet.iterator();
		modelTempVal = 0;
		Literal temp = null; 
		while(itr.hasNext())
		{
			Literal l = itr.next();
			if((dynamicTempVal=numOfClausesSat(l,  st)) > modelTempVal)
			{
				modelTempVal = dynamicTempVal;
				temp = l;
				copyModels(modelTemp, dynamicTemp);
			}
		}
		copyModels(model, modelTemp);
		return temp;
	}
	public int numOfClausesSat(Literal l, Sentence st)
	{
		int num=0;
		//get the original matrix
		copyModels(dynamicTemp, modelTemp);
		//flip the bit
		if(dynamicTemp[l.person-1][l.table-1])
			dynamicTemp[l.person-1][l.table-1] = false;
		else
			dynamicTemp[l.person-1][l.table-1] = true;
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			if(clauseTruthVal(cl,dynamicTemp))
				num++;
		}
		return num;
	}
	public Sentence returnFalseSt(Sentence st)
	{
		Sentence sTemp = new Sentence();
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			if(!clauseTruthVal(cl, model))
				sTemp.addClause(cl);
		}
		return sTemp;
	}
	public Clause returnRandomFalseClause(Sentence st)
	{
		Sentence sFalsesTemp = returnFalseSt(st);
		Random generator = new Random();		
		int location = (int)generator.nextDouble() * sFalsesTemp.size();		 
		return sFalsesTemp.returnClause(location);
	}
}
