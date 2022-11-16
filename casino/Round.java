package casino;

import java.util.Map;

import card.*;
import variants.DoubleBonus;
import variants.GameVariant;

/**
 * Class that simulates the basic commands every round of Poker would have
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Round{
	//Player that is playing this round
	protected Player player;
	//Amount betted by the play
	protected int bet;
	//Game Variant being played
	protected GameVariant GV;
	
	/**
	 * Round construct, it also debits the player the amount bet
	 * @param p Player playing the round
	 * @param b amount better
	 * @param d Variant being played
	 */
	public Round(Player p, int b, GameVariant d) {
		player = p;
		bet = b;
		GV = d;
		player.changecredit(-b);//Debit the amount bet
	}
	
	/**
	 * Assigns the first 5 cards of the Deck to the player
	 * @param deck Deck being used in the game
	 */
	public void receiveCards(Deck deck) {
		Card cards[] = new Card[5];
		for(int i = 0; i<5;i++) {//Draw the first 5 cards from the deck
			cards[i] = deck.drawcard();
		}
		player.receivecards(cards);
	}
	
	/**
	 * Receives an array with the positions of the cards that should be replaced and replace them in the players hand
	 * @param replacepos Array with 0's in the positions to be replaced and 1's in the positions to be kept
	 * @param thisdeck Deck from which we should draw the cards
	 */
	public void replaceCards(int[] replacepos, Deck thisdeck){
		for(int i=0; i<5;i++) {//Go through the vector with the replace positions and find which have a 0, replace those cards
			if(replacepos[i]==0) {
				player.hand.replacecard(i,thisdeck.drawcard());
				if(player.hand.getcard(i)==null)//Confirm that we were able to draw a card (Deck can be empty)
					break;
			}
		}	
	}
	
	/**
	 * Return a vector with the positions that should be kept and discarded by the player
	 * in order to follow the perfect strategy dictated by the game variant
	 * @return Vector with the position to be kept and discarded
	 */
	public int[] advice() {
		return GV.advice(player.hand);
	}
	
	
	/**
	 * Evaluate hand and reward the player accordingly to its hand value. It will also
	 * mark the end of the round, clearing the player's hand and return if the player won
	 * and with which hand or if he lost
	 * @return String with the winning hand and winning message or with losing message
	 */
	public String rewardPlayer() {
		String buffer;
		if((buffer=GV.evaluateHand(player.hand))!=null){//There is a recognisable and rewardable hand in the player's cards
			int reward = GV.handvalue(buffer, bet);//Check Hand value
			player.changecredit(reward);//Credit the value his winnings
			buffer = "Player wins with a "+buffer+" and his credit is: "+player.getcredit()+"\n";
		}
		else//player's hand is worthless
			buffer="Player loses and his credit is "+player.getcredit()+ "\n";
		player.hand.ClearHand();
		return buffer;
	}
	
	/**
	 * Gets the statistics for the whole game, placing them in a printable String and printing it
	 * @return Total amount of Hands evaluated
	 */
	public int getstats() {
		int count=0;
		System.out.println("Hand\t\t NB");
		System.out.println("----------------------------");
		for(Map.Entry<String,Integer>it:GV.getstats().entrySet()) {//iterate trough the LinkedHasMap
	          System.out.println(it.getKey() + "\t " + it.getValue());//Get the key (name of hand) and value (frequency hand was gotten)
	          count+=it.getValue();
		}
		System.out.println("----------------------------");
		return count;
	}
	
	/**
	 * Main function with the only propose being for test usage
	 */
	public static void main(String args[]) {
		GameVariant d = new DoubleBonus();
		Player player = new Player(10);
		Round round = new Round(player, 5, d);
		Deck deck = new Deck();
		deck.shuffle();
		System.out.println(deck.deckSize());
		round.receiveCards(deck);
		player.hand.sort();
		System.out.println(deck.deckSize());
		System.out.println(player.hand.printableHand());
		int replacepos[] = d.advice(player.hand);		
		round.replaceCards(replacepos,deck);
		player.hand.sort();
		round.rewardPlayer();
		System.out.println(player.hand.printableHand());
		System.out.println(deck.deckSize());
		
		
	}
	

}
