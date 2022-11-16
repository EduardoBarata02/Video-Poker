package card;

/**
 * Represents a Deck card with its respective rank and suit
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */

public class Card implements Comparable<Card> {
	//Suits and Ranks in string and int form
	static final String[] Suit = {"Clubs", "Diamonds", "Hearts", "Spades" };
    static final String[] Rank = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
    private int rank; //1 to 13
    private int suit; //1 to 4
    /**
     * Card Constructor. It receives an argument with the suit and another with the rank
     * checks if they are valid and then assigns them to the card.
     * @param s_ New card suit
     * @param r_ New card rank
     */
    public Card(int s_, int r_) {
		if(s_<0 || s_>4) //Suit out of bounds
			throw new ArithmeticException("Invalid suit:"+ s_);
		if(r_<0 || r_>13) //Rank out of boudns
			throw new ArithmeticException("Invalid rank:"+ r_);
		else {
			suit=s_;
			rank=r_;
		}
	}
    /**
     * Returns the suit of this Card
     * @return value of the card suit
     */
    public int getsuit() {
    	return suit;
    }
    /**
     * Returns the rank of this card
     * @return value of the card rank
     */
    public int getrank() {
    	return rank;
    }
    
    /**
     * Converts the object card into a printable object
     * @return Card converted to String 
     */
	@Override
	public String toString() {
		return Rank[rank] + Suit[suit].charAt(0);
	}

	/**
     * Compares 2 given cards by their rank 
     * @return greater then 0 this card is greater, lower to 0 compCard is greater, equal to 0 they are the same
     */
	@Override
	public int compareTo(Card compCard) {
		int cmp = ((Card) compCard).getrank();//Uses the Comparable class to compare 2 cards ranks
		return this.rank-cmp;
	}
	/**
	 * Main function with the only propose being for test usage
	 */
	public static void main(String args[])
    {
	    Card c1 = new Card(3,0);// A Spades
	    System.out.println(c1);
	    c1 = new Card(0,9);	// 10 Clubs
	    System.out.println(c1);
	    System.out.println(c1.getrank()+1);
    }
	

}
