import java.util.HashSet;
import java.util.Set;



/*This code is picked up/derived from AIMA's code base
 * http://code.google.com/p/aima-java
 * http://code.google.com/p/aima-java/downloads/detail?name=aima-java-1.7.0-Chp4-Complete.zip&can=2&q=
 */

public class ClauseSymbols {
	Clause clause1PositiveSymbols;
	Clause clause2PositiveSymbols;
	Clause clause1NegativeSymbols;
	Clause clause2NegativeSymbols;
	Clause clause1Symbols;
	Clause clause2Symbols;
	Clause positiveInClause1NegativeInClause2;
	Clause negativeInClause1PositiveInClause2;
	public ClauseSymbols(Clause clause1, Clause clause2) {
		// TODO Auto-generated constructor stub
		SymbolClassifier classifier = new SymbolClassifier();
		clause1Symbols = classifier.getSymbolsIn(clause1);
		clause1PositiveSymbols = classifier.getPositiveSymbolsIn(clause1);
		clause1NegativeSymbols = classifier.getNegativeSymbolsIn(clause1);

		clause2Symbols = classifier.getSymbolsIn(clause2);
		clause2PositiveSymbols = classifier.getPositiveSymbolsIn(clause2);
		clause2NegativeSymbols = classifier.getNegativeSymbolsIn(clause2);
		System.out.println("In constructor");
		System.out.println(clause1PositiveSymbols);
		System.out.println(clause2NegativeSymbols);
		System.out.println(clause1NegativeSymbols);
		System.out.println(clause2PositiveSymbols);
		System.out.println(SetOps.intersection(clause1PositiveSymbols.litSet, clause2NegativeSymbols.litSet).size());
		System.out.println(SetOps.intersection(clause1NegativeSymbols.litSet, clause2PositiveSymbols.litSet).size());
		Set <Literal> s1 = new HashSet<Literal>();
		s1 = SetOps.intersection(clause1PositiveSymbols.litSet, clause2NegativeSymbols.litSet);
		System.out.println("After intersection: "+s1+" "+s1.size());
		if(s1.size()==0)			
			positiveInClause1NegativeInClause2 = new Clause();
		else
			positiveInClause1NegativeInClause2 = new Clause(s1);
		System.out.println("After cons new obj: "+positiveInClause1NegativeInClause2);
		Set <Literal> s = new HashSet<Literal>();
		s = SetOps.intersection(clause1NegativeSymbols.litSet, clause2PositiveSymbols.litSet);
		if(s.size()==0)			
			negativeInClause1PositiveInClause2 = new Clause();
		else
			negativeInClause1PositiveInClause2 = new Clause(s);
		
		System.out.println(negativeInClause1PositiveInClause2);
		System.out.println("Constr done");
	}
	public Clause getComplementedSymbols()
	{	
		System.out.println(positiveInClause1NegativeInClause2);
		System.out.println(negativeInClause1PositiveInClause2);
		Clause complementSymbols = new Clause(SetOps.union(positiveInClause1NegativeInClause2.litSet, negativeInClause1PositiveInClause2.litSet));
		System.out.println("Union"+complementSymbols.size());
		System.out.println(complementSymbols);
		return complementSymbols;
	}
}
