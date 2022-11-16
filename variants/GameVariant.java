package variants;

import java.util.LinkedHashMap;

import card.Hand;
/**
 * Interface that every type of Video Poker game variant should follow
 * @author User
 *
 */
public interface GameVariant {
	
	/**
	 * Analyses a player's hand and provide information
	 * as to what cards to keep
	 * @param h - Hand of the player to be analysed
	 * @return int[] - vector of size 5, with 0's in the positions to be 
	 * discarded and 1's in the ones to be kept
	 */
	public int[] advice(Hand h);
	
	/**
	 * Initialises the Statistics board
	 */
	public void initialiseStatistics();
	
	/**
	 * Returns the statistics table
	 * @return The LinkedHashMap with the statistics
	 */
	public LinkedHashMap<String, Integer> getstats();
	
	/**
	 * Returns the value of a certain hand according to what the player betted
	 * @param name Name of the hand
	 * @param betamount Amount bet by the player
	 * @return value of the Hand
	 */
	public int handvalue(String name, int betamount);
	
	/**
	 * Evaluates a players Hand, returning the name of it and adding to the
	 * statistics that this hand was gotten
	 * @param h - Hand to be evaluated
	 * @return String - Name of the Hand the player has
	 */
	public String evaluateHand(Hand h);
}
