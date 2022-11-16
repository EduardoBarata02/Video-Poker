package variants;

import java.util.LinkedHashMap;

import card.Hand;

/**
 * This class implements a game variant of poker called Double Bonus
 * providing informations like statistics and value of certain Hands
 * when called
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class DoubleBonus extends HandEvaluator implements GameVariant {
	//Statistics are kept in a LinkedHashMap, with the name of Hands and their frequency
	private LinkedHashMap<String, Integer> Statistics;
	//Prize board
	private static final LinkedHashMap<String, Integer[]> Prizes = initializePrizes();
	
	/**
	 * Double Bonus construct, it initialises the statistics to zero
	 */
	public DoubleBonus() {
		initialiseStatistics();
	}
	
	@Override
	public int[] advice(Hand h) {
		int cardstokeep[] = new int[5];
		Hand aux=h.copy();//create a copy of the hand so we can sort it
		aux.sort();//Sort the hand copy
		//Check in which category the Hand falls, when it corresponds to one the HandEvaluator will
		//return a vector of ints with the cards to keep
		if((cardstokeep=HandEvaluator.RoyalFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.StraightFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourOfAKind(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToARoyalFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeAces(aux))!=null);
		else if((cardstokeep=HandEvaluator.FullHouse(aux))!=null);
		else if((cardstokeep=HandEvaluator.Flush(aux))!=null);
		else if((cardstokeep=HandEvaluator.Straight(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeOfAKind(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAStraightFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.TwoPair(aux))!=null);
		else if((cardstokeep=HandEvaluator.HighPair(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToARoyalFlush(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAnOutsideStraight(aux))!=null);
		else if((cardstokeep=HandEvaluator.LowPair(aux))!=null);
		else if((cardstokeep=HandEvaluator.AKQJUnsuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAStraightFlush1(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAnInsideStraightWith3HighCards(aux))!=null);
		else if((cardstokeep=HandEvaluator.QJSuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAFlushWith2HighCards(aux))!=null);
		else if((cardstokeep=HandEvaluator.TwoSuitedHighCards(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAnInsideStraightWith2HighCards(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAStraighFlush2(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAnInsideStraightWith1HighCard(aux))!=null);
		else if((cardstokeep=HandEvaluator.KQJUnsuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.JTSuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.QJUnsuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAFlushWith1HighCard(aux))!=null);
		else if((cardstokeep=HandEvaluator.QTSuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAStraightFlush3(aux))!=null);
		else if((cardstokeep=HandEvaluator.KQUnsuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.KJUnsuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.Ace(aux))!=null);
		else if((cardstokeep=HandEvaluator.KTSuited(aux))!=null);
		else if((cardstokeep=HandEvaluator.JackQueenKing(aux))!=null);
		else if((cardstokeep=HandEvaluator.FourToAnInsideStraightWithNoHighCards(aux))!=null);
		else if((cardstokeep=HandEvaluator.ThreeToAFlushWithNoHighCards(aux))!=null);
		else
			return HandEvaluator.Discard();
		int[] cardstokeepaftersort = {0,0,0,0,0};
		if(cardstokeep!=null) {//HandEvaluator returned a vector
			for(int i=0; i<5;i++)//Go through the returned vector
				if(cardstokeep[i]==1)//If that position is equal to 1 (Keep card)
					for(int k=0;k<5;k++)//Check in which position the original hand has the card compared to the sorted hand
						if(aux.getcard(i).equals(h.getcard(k)))
							cardstokeepaftersort[k]=1;
		}
		return cardstokeepaftersort;
	}
	
	
	@Override
	public String evaluateHand(Hand h) {
		int aux[] = new int[5];
		Hand hcopy=h.copy();//create a copy of the hand so we can sort it
		hcopy.sort();//Sort the copy
		
		//Check which Hand the player's card fall under and assign the value to the statistics and return a String with the name of the hand
		if(HandEvaluator.RoyalFlush(hcopy) != null) {
			Statistics.put("Royal Flush", Statistics.get("Royal Flush") + 1);
			return "ROYAL FLUSH";
		}
		if(HandEvaluator.StraightFlush(hcopy) != null) {
			Statistics.put("Straight Flush", Statistics.get("Straight Flush") + 1);
			return "STRAIGHT FLUSH";
		}
		if((aux = HandEvaluator.FourOfAKind(hcopy)) != null) {//For statistics proposes Four of a kind fall under the same category by the have different rewards
			if(((aux[0]==1) && (h.getcard(aux[0]).getrank() == 0)) ||((aux[1]==1) && (h.getcard(aux[1]).getrank() == 0))){
				Statistics.put("Four of a Kind", Statistics.get("Four of a Kind") + 1);
				return "FOUR ACES";
			}
		}
		if((aux = HandEvaluator.FourOfAKind(hcopy)) != null) {//For statistics proposes Four of a kind fall under the same category by the have different rewards
			if(((aux[0]==1) && (h.getcard(aux[0]).getrank()<5)) ||((aux[1]==1) && (h.getcard(aux[1]).getrank() < 5))) {
				Statistics.put("Four of a Kind", Statistics.get("Four of a Kind") + 1);
				return "FOUR 2-4";
			}
		}
		if(HandEvaluator.FourOfAKind(hcopy) != null) {//For statistics proposes Four of a kind fall under the same category by the have different rewards
			Statistics.put("Four of a Kind", Statistics.get("Four of a Kind") + 1);
			return "FOUR 5-K";
		}
		if(HandEvaluator.FullHouse(hcopy) != null) {
			Statistics.put("Full House", Statistics.get("Full House") + 1);
			return "FULL HOUSE";
		}
		if(HandEvaluator.Flush(hcopy) != null) {
			Statistics.put("Flush\t", Statistics.get("Flush\t") + 1);
			return "FLUSH";
		}
		if(HandEvaluator.Straight(hcopy) != null) {
			Statistics.put("Straight", Statistics.get("Straight") + 1);
			return "STRAIGHT";
		}
		if(HandEvaluator.ThreeOfAKind(hcopy) != null) {
			Statistics.put("Three of a Kind", Statistics.get("Three of a Kind") + 1);
			return "THREE OF A KIND";
		}
		if(HandEvaluator.TwoPair(hcopy) != null) {
			Statistics.put("Two Pair", Statistics.get("Two Pair") + 1);
			return "TWO PAIR";
		}
		if(HandEvaluator.HighPair(hcopy) != null) {
			Statistics.put("Jacks or Better", Statistics.get("Jacks or Better") + 1);
			return "JACKS OR BETTER";
		}
			Statistics.put("Other\t", Statistics.get("Other\t") + 1);
			return null;
	}
	
	public void initialiseStatistics() {
		Statistics = new LinkedHashMap<String, Integer>();
		//Initialise the LinkedHashMap with the statistics we want to measure
		Statistics.put("Jacks or Better",0);
		Statistics.put("Two Pair",0);
		Statistics.put("Three of a Kind",0);
		Statistics.put("Straight",0);
		Statistics.put("Flush\t",0);
		Statistics.put("Full House",0);
		Statistics.put("Four of a Kind",0);
		Statistics.put("Straight Flush",0);
		Statistics.put("Royal Flush",0);
		Statistics.put("Other\t",0);
	}
	
	/**
	 * Creates and returns a LinkedHashMap containing the prize table for short and
	 * full pay of DoubleBonus poker
	 * @return LinkedHashMap<String,Integer[]> PrizeTable
	 */
	private static LinkedHashMap<String, Integer[]> initializePrizes() {
		LinkedHashMap<String, Integer[]> scoreBoard = new LinkedHashMap<String, Integer[]>();
		//Create a Prize Board with the value of each hand according to the amount bet
		scoreBoard.put("ROYAL FLUSH",     new Integer[]{250, 500, 750, 1000, 4000});
		scoreBoard.put("STRAIGHT FLUSH",  new Integer[]{50,  100, 150, 200,  250 });
		scoreBoard.put("FOUR ACES",       new Integer[]{160, 320, 480, 640,  800 });
		scoreBoard.put("FOUR 2-4",        new Integer[]{80,  160, 240, 320,  400 });
		scoreBoard.put("FOUR 5-K",        new Integer[]{50,  100, 150, 200,  250 });
		scoreBoard.put("FULL HOUSE",      new Integer[]{10,  20,  30,  40,   50  });
		scoreBoard.put("FLUSH", 		  new Integer[]{7,   14,  21,  28,   35  });
		scoreBoard.put("STRAIGHT",        new Integer[]{5,   10,  15,  20,   25  });
		scoreBoard.put("THREE OF A KIND", new Integer[]{3,   6,   9,   12,   15  });
		scoreBoard.put("TWO PAIR",        new Integer[]{1,   2,   3,   4,    5   });
		scoreBoard.put("JACKS OR BETTER", new Integer[]{1,   2,   3,   4,    5   });
		
		return scoreBoard;
	}

	@Override
	public LinkedHashMap<String, Integer> getstats() {
		return Statistics;
	}
	
	@Override
	public int handvalue(String name, int betamount) {
		return Prizes.get(name)[betamount-1];
	}
	
}
