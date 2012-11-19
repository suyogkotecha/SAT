

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

		positiveInClause1NegativeInClause2 = new Clause(SetOps.intersection(clause1PositiveSymbols.litSet, clause2NegativeSymbols.litSet));
		negativeInClause1PositiveInClause2 = new Clause(SetOps.intersection(clause1NegativeSymbols.litSet, clause2PositiveSymbols.litSet));
	}
}
