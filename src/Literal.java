public class Literal {
	public int symbol;
	public int table;
	public int person;
	Literal(int symbol, int table, int person)
	{
		this.symbol = symbol;		
		this.table = table;
		this.person = person;				
	}
	Literal(Literal l)
	{
		this.symbol = l.symbol;
		this.table = l.table;
		this.person = l.person;
	}
	public String toString()
	{
		StringBuffer retStr=new StringBuffer();
		if(symbol == -1)
		{
			retStr = retStr.append(" -X");
		}
		else if(symbol == 1)
		{
			retStr = retStr.append("  X");
		}
		
		retStr.append(person).append(table);
		return retStr.toString();
	}
	public boolean equals(Object o)
	{
		Literal another = (Literal)o;		
		if(this.symbol == another.symbol && this.table == another.table && this.person == another.person)
			return true;
		
		if(this.symbol != another.symbol || this.table != another.table || this.person != another.person)
			return false;
		return true;
	}
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	public boolean isPositive()
	{
		if(this.symbol == 1)
			return true;
		else 
			return false;
	}
}
