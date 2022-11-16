package casino;

import card.*;
/**
 * Class that represents a player which contains a Hand of cards and a credit balance
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Player {
	/**
	 * Player's credit
	 */
	private int credit;
	/**
	 * Player's Hand
	 */
	public Hand hand;
	
	/**
	 * Player constructor, assigns the passed argument to the credit and creates a new Hand
	 * @param c_ Inicial credit of the player
	 */
	public Player(int c_) {
		credit=c_;
		hand = new Hand();
	}
	/**
	 * Returns the player's credit
	 * @return Players credit
	 */
	public int getcredit() {
		return credit;
	}
	/**
	 * Assigns the vector of cards passed through argument to the player's hand
	 * @param add vector of Cards to be added
	 */
	public void receivecards(Card add[]) {
		for(Card aux : add)
			hand.addcard(aux);		
	}
	/**
	 * Adds or removes(in case c_ is negative) credit to the player
	 * @param c_ credit to be added or removed (positive/negative value)
	 */
	public void changecredit(int c_) {
		credit +=c_;
	}
}

