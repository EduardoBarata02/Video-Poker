package card;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Represents a normal Deck of Cards either loaded by the user or automatically generated (with 52 cards)
 * This class contains an Array of Cards;
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Deck {	
	//List of cards that form the deck
	private ArrayList<Card> deck;
	/**
	 * Constructor for Deck, this constructor (with no arguments) will generate an ordered standard 52 card Deck.
	 */
	public Deck() {
		deck = new ArrayList<Card>();
		for(int suit = 0; suit < 4; suit++){//Iterate trough every suit and then every rank to place cards by order
            for(int rank = 0; rank < 13 ; rank++){
            	Card newCard = new Card(suit, rank);
                deck.add(newCard);
            }
        }
		
	}
	/**
	 * Constructor for Deck, this constructor (with an Array of cards provided in arguments) 
	 * will generate a Deck with the passed cards in its arguments.
	 * @param d_ Array of Cards to place in the Deck
	 */
	public Deck(ArrayList<Card> d_){
		deck = new ArrayList<Card>();
		for(Card c : d_) {//Adds every card passed in the array list
			deck.add(c);
		}
		
	}
	/**
	 * Adds a specified Card to the current deck
	 * @param add Card to be added in the deck
	 */
	public void addCard(Card add) {
		if(deck!=null)//Confirm a deck is already created
			deck.add(add);
		else
			System.out.println("No deck created");
	}
	/**
	 * Shuffles the deck
	 */
	public void shuffle() {
		if(deck!=null)//Confirm a deck is already created
			Collections.shuffle(deck);//shuffles deck
		else
			System.out.println("No deck created");
	}
	/**
	 * Draws a Card from the top of the deck, returning it
	 * @return Returns the drawn Card
	 */
	public Card drawcard() {
		if(deck==null) {//Confirm a deck is already created
			System.out.println("No deck created");
			return null;
		}
		if(deck.size()==0) {//Confirm we still have cards in the deck to be drawn
			System.out.println("Out of cards\n");
			return null;
		}
		return deck.remove(0);
	}
	/**
	 * Returns the size of the deck (the number of remaining Cards in the deck)
	 * @return Size of the deck
	 */
	public int deckSize() {
		return deck.size();
	}
	
	/**
	 * Main function with the only propose being for test usage
	 */
	public static void main(String args[])
    {
		Card aux;
		Deck newdeck = new Deck();
		newdeck.shuffle();
		for(int suit = 1; suit <= 4; suit++){
	        for(int rank = 1; rank <= 13 ; rank++){
	            aux = newdeck.drawcard();
	            System.out.println(aux.toString());
	        }
	    }		
    }
	
}
