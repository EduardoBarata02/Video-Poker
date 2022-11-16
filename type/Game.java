package type;

import card.Deck;
import casino.Player;
import casino.Round;
import variants.GameVariant;

/**
 * Abstract Game class that has a constructor and the methods that a game mode
 * has to obligatory utilise like bet, deal and hold. Allows for the possibility 
 * to add other game modes to the program
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public abstract class Game {
	//Current player playing the game
	protected Player currentplayer;
	//Deck being used
	protected Deck Playdeck;
	
	/**
	 * Game constructor, creates a new Player with the balance provided in the arguments
	 * @param amount Player's initial balance
	 */
	public Game(int amount) {
		currentplayer = new Player(amount);
	}
	/**
	 * Classes that extend this class should use this method to create a 
	 * new round
	 * @param bet Amount the player bet
	 * @param GV Game variant being played 
	 * @return Round object
	 */
	protected abstract Round bet(int bet, GameVariant GV);
	
	/**
	 * Classes that extend this class should use this method to deal the cards
	 * to the player
	 * 
	 * @param round Current round object which is being played
	 */
	protected abstract void deal(Round round);
	
	/**
	 * Classes that extend this class should use this method to specify and replace
	 * the cards the player wants to discard according to his strategy
	 * @param round Current round object which is being played
	 * @param hold vector with the cards to be hold
	 */
	protected abstract void hold(Round round, int[] hold);
	
}
