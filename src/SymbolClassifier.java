import java.util.Iterator;

/*This code is picked up/derived from AIMA's code base
 * http://code.google.com/p/aima-java
 * http://code.google.com/p/aima-java/downloads/detail?name=aima-java-1.7.0-Chp4-Complete.zip&can=2&q=
 */

public class SymbolClassifier {

	public Clause getSymbolsIn(Clause cl)
	{
		Clause symbols = new Clause();
		Iterator <Literal> itr = cl.litSet.iterator();
		while(itr.hasNext())
		{
			Literal ltr = itr.next();
			symbols.addLiteral(ltr);
		}
		return symbols;
	}
	public Clause getPositiveSymbolsIn(Clause cl)
	{
		Clause positiveSym = new Clause();
		Iterator <Literal> itr = cl.litSet.iterator();
		while(itr.hasNext())
		{
			Literal l = itr.next();
			if(l.symbol == 1)
			{
				positiveSym.addLiteral(l);
			}			
		}
		return positiveSym;
	}
	public Clause getNegativeSymbolsIn(Clause cl)
	{
		Clause negativeSym = new Clause();
		Iterator <Literal> itr = cl.litSet.iterator();
		while(itr.hasNext())
		{
			Literal l = itr.next();
			if(l.symbol == -1)
			{
				negativeSym.addLiteral(l);
			}
		}
		return negativeSym;
	}
}
