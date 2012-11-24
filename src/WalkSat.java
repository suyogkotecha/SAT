import java.util.Iterator;
import java.util.Random;


public class WalkSat {
	boolean model[][]= new boolean[100][100];
	boolean modelTemp[][] = new boolean[100][100];
	int modelTempVal = 0;
	boolean dynamicTemp[][] = new boolean[100][100];
	int dynamicTempVal = 0;
	public boolean walkSat(Sentence st, int flips, int M, int N, double probability)
	{
		generateModel(M, N);		
		Random generator = new Random();
		for(int i=0;i<flips;i++)
		{
			if(modelSatisfiable(st))	
			{
				//System.out.println("At iteration: "+i);
				return true;
			}
			Clause cl = returnRandomFalseClause(st);
			//System.out.println("Returned random false clause: "+cl);
			if(generator.nextDouble() >= probability) //select random symbol
			{
				int location = (int)(generator.nextDouble() * cl.size());
				Literal l = cl.returnLiteral(location);
				//flip for this literal in model
				//System.out.println("Flipping forPrb: "+l);
				flipBit(dynamicTemp, l);
			}
			else//select symbol which maximizes the sat clauses
			{
				Literal l = maximizeSat(cl, st);
				//System.out.println("Flipping forMax: "+l);
			}
		}
		
		/*Sentence ft = returnFalseSt(st);
		Clause cl = ft.returnClause(0);
		System.out.println("Returned false clause: "+cl);
		
		System.out.println(maximizeSat(cl, st));
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(model[i][j]+" ");
			}
			System.out.println();
		}
		 ft = returnFalseSt(st);
		cl = ft.returnClause(0);
		System.out.println("Returned false clause: "+cl);
		
		System.out.println(maximizeSat(cl, st));
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(model[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("Printing indi literals:");
		for(int i=0;i<cl.size();i++)
		{
			
			Literal l = cl.returnLiteral(i);
			System.out.println("For literal:"+l);
			System.out.println("Num of Clauses satisfied: "+numOfClausesSat(l, st));
		}*/
		return false;
	}
	public void generateModel(int M, int N)
	{
		Random generator = new Random();
		for(int i=0;i<M;i++)
		{
			for(int j=i+1;j<N;j++)
			{
				if(generator.nextDouble() >= 0.5)
					model[i][j] = true;
				else
					model[i][j] = false;
			}
		}		
		/*model[0][1] = true;
		model[1][1] = true;
		model[2][2] = true;*/
		        
	}
	public boolean modelSatisfiable(Sentence st)
	{
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			if(!clauseTruthVal(cl, model))
			{
				//System.out.println(cl);
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
		copyModels(dynamicTemp, model);
		//flip the bit
		flipBit(dynamicTemp, l);
		
		/*if(dynamicTemp[l.person-1][l.table-1])
			dynamicTemp[l.person-1][l.table-1] = false;
		else
			dynamicTemp[l.person-1][l.table-1] = true;*/
		
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			if(clauseTruthVal(cl,dynamicTemp))
			{
				//System.out.println("Satisfied clause: "+cl);
				num++;
			}
			/*else
			{
				System.out.println("XX Unsatisfied clause: "+cl);
			}*/
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
		
		int location = (int)(generator.nextDouble() * sFalsesTemp.size());
		//System.out.println("Size: "+sFalsesTemp.size()+" location: "+location);
		//System.out.println(sFalsesTemp+"\n\n");
		return sFalsesTemp.returnClause(location);
	}
	public void flipBit(boolean tModel[][], Literal l)
	{
		if(tModel[l.person-1][l.table-1])
			tModel[l.person-1][l.table-1] = false;
		else
			tModel[l.person-1][l.table-1] = true;
	}
}
