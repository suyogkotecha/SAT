import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Sentence {
	public Set<Clause> clauses;
	Sentence()
	{
		clauses = new HashSet<Clause>();
	}
	Sentence(Clause e)
	{
		clauses = new HashSet<Clause>();
		clauses.add(e);		
	}
	Sentence(Set <Clause> st)
	{
		this.clauses = new HashSet<Clause>();
		Iterator <Clause> itr = st.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			clauses.add(new Clause(cl));
		}
	}
	Sentence(Sentence st)
	{
		clauses = new HashSet<Clause>();
		Iterator <Clause> itr = st.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();
			clauses.add(new Clause(cl));
		}
	}
	void addClause(Clause e)
	{
		clauses.add(e);
	}
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		Iterator<Clause> itr = clauses.iterator();
		str.append("(");
		while(itr.hasNext()) 
		{
			Clause cls = itr.next();
			str.append(cls.toString()).append("^\n");
		}		
		str = str.deleteCharAt(str.length()-1);
		str.append(")");
		return str.toString();
	}
	public boolean equals(Object o)
	{
		Sentence a = new Sentence(this);
		Sentence b = new Sentence((Sentence)o);
		Iterator <Clause> forA = a.clauses.iterator();
		
		while(forA.hasNext())
		{
			Clause cA = forA.next();
			Iterator <Clause> forB = b.clauses.iterator();
			while(forB.hasNext())
			{
				Clause cB = forB.next();
				if(cA.equals(cB))
				{
					forB.remove();
				}
			}
		}
		if(b.size() == 0)
			return true;
		else
			return false;
	}
	public int hashCode()
	{
		return clauses.toString().hashCode();
	}
	public Sentence filterOutClausesWithTwoComplementaryLiterals()
	{
		Sentence filtered = new Sentence();
		Iterator <Clause> itr = this.clauses.iterator();
		while(itr.hasNext())
		{
			Clause cl = itr.next();			
			if(SetOps.intersection(cl.returnAllPositives().litSet,cl.returnAllNegatives().litSet).size() == 0)
			{
				filtered.addClause(cl);
			}
		}
		return filtered;
	}
	public int size()
	{
		return this.clauses.size();
	}
	public Clause returnClause(int location)
	{
		Iterator <Clause> itr = this.clauses.iterator();
		
		Clause cl = null;
		for(int i=0;i<location && itr.hasNext();i++)
		{
			itr.next();
		}
		//~cl = new Clause(itr.next());
		cl = itr.next();
		return cl;
	}
	public boolean hasEmptyClause()
	{
		Iterator <Clause> itr = this.clauses.iterator();
		while(itr.hasNext())
		{
			Clause e = itr.next();
			if(e.size()==0)
				return true;
		}
		return false;		
	}

}
