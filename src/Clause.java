import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Clause {
	public Set<Literal> litSet;
	
	Clause()
	{
		litSet  = new HashSet<Literal>();
	}
	Clause(Set<Literal> litSet)
	{
		//System.out.println("Clause cons called with litset");
		/*this.litSet  = new HashSet<Literal>();
		Iterator <Literal> itr = litSet.iterator();
		while(itr.hasNext())
		{
			Literal ltr = itr.next();
			//this.litSet.add(new Literal(ltr));
			this.litSet.add(ltr);
		}*/
		this.litSet = litSet;
	}
	Clause(Clause cl)
	{
		/*this.litSet  = new HashSet<Literal>();
		Set <Literal> lS = cl.litSet;		
		Iterator <Literal> itr = lS.iterator();
		while(itr.hasNext())
		{
			Literal ltr = itr.next();
			//this.litSet.add(new Literal(ltr));
			this.litSet.add(ltr);
		}*/
		this.litSet = cl.litSet;
	}
	void addLiteral(int symbol, int table, int person)
	{
		litSet.add(new Literal(symbol,table,person));
	}
	void addLiteral(Literal l)
	{
		//litSet.add(new Literal(l));
		litSet.add(l);
	}
	public String toString()
	{
		if(size() == 0)
			return null;
		StringBuffer str = new StringBuffer();
		Iterator<Literal> itr = litSet.iterator();
		
		str.append("(");
		while(itr.hasNext()) 
		{
			Literal ltr = (Literal) itr.next();
			str = str.append(ltr.toString()).append(" V");
		}
		str = str.deleteCharAt(str.length()-1);
		str.append(")");
		return str.toString();
	}
	public boolean equals(Object o)
	{
		//System.out.println("Equals called Clause");
		Clause another = (Clause)o;
		Set<Literal> a = new HashSet<Literal>();
		Iterator<Literal> itr = litSet.iterator();
		while(itr.hasNext())
		{
			Literal ltr = (Literal) itr.next();
			a.add(new Literal(ltr.symbol,ltr.table,ltr.person));
		}
		//Set<Literal> a = new Clause(this).litSet;
		Set<Literal> b = new HashSet<Literal>();		
		Iterator<Literal> itr1 = another.litSet.iterator();
		while(itr1.hasNext())
		{
			Literal ltr = (Literal) itr1.next();
			b.add(new Literal(ltr.symbol,ltr.table,ltr.person));
		}
		//Set<Literal> b = new Clause(another).litSet;
		
		for(Iterator<Literal> i = a.iterator();i.hasNext();)
		{
			Literal ltrA = (Literal)i.next();
			for(Iterator<Literal> j = b.iterator();j.hasNext();)
			{
				Literal ltrB = (Literal)j.next();
				if(ltrA.equals(ltrB)) //remove ltrB from b
				{
					j.remove();
				}
			}
		}
		
		if(b.size()==0)
			return true;
		else
		{
			//System.out.println(this.toString());
			return false;
		}
	}
	public int hashCode()
	{
		return litSet.toString().hashCode();		
	}
	public Clause returnAllPositives()
	{
		Clause cl = new Clause();
		Iterator<Literal> itr = this.litSet.iterator();
		while(itr.hasNext())
		{
			Literal l = itr.next();
			if(l.isPositive())
			{
				cl.addLiteral(l);				
			}
		}
		return cl;
	}
	public Clause returnAllNegatives()
	{
		Clause cl = new Clause();
		Iterator<Literal> itr = this.litSet.iterator();
		while(itr.hasNext())
		{
			Literal l = itr.next();
			if(!l.isPositive())
			{
				cl.addLiteral(1,l.table,l.person);				
			}
		}
		return cl;
	}
	/*public Set<Literal> clauseDup(Clause c)
	{
		Set<Literal>
	}*/
	public int size()
	{
		if(litSet == null)
			return 0;
		return litSet.size();
	}
	public Literal returnLiteral(int location)
	{
		Iterator <Literal> itr = this.litSet.iterator();
		Literal l = null;
		for(int i=0;i<location && itr.hasNext();i++)
		{
			itr.next();
		}
		//~cl = new Clause(itr.next());
		l = itr.next();
		return l;
	}
}
