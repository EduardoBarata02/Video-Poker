package card;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the cards on the player's hand, with each hand being an array of cards
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Hand{
	//A player´s hand is composed by 5 cards
	private ArrayList<Card> hand;
	
	/**
	 * Hand constructor, creates an empty ArrayList of Cards
	 */
	public Hand(){
		hand = new ArrayList<Card>();
	}
	/**
	 * Returns the card at the specified position
	 * @param position position from each we should get the card
	 * @return Card Card at the specified position
	 */
	public Card getcard(int position) {
		if(position<0 || position>hand.size())//Check if the position asked is valid in this context or not
			throw new ArrayIndexOutOfBoundsException(position + "is out of bounds for hand");
		return hand.get(position);
	}
	
	/**
	 * Remove the card from the hand at the specified position
	 * @param position position from each we should remove the card
	 */
	public void removecard(int position) {
		if(position<0 || position>hand.size())//Check if the position asked is valid in this context or not
			throw new ArrayIndexOutOfBoundsException(position + " is out of bounds for hand");
		hand.remove(position);
	}
	
	/**
	 * Receives a card and adds it to the hand
	 * @param c_ Card to the added to the Hand
	 */
	public void addcard(Card c_){
		if(hand.size()>=5)//A hand can only have 5 cards so check if by adding the card we will exceed this capacity
			throw new ArrayIndexOutOfBoundsException("Hand is full");
		hand.add(c_);
	}
	
	/**
	 * Replace the card in Hand at specified position
	 * @param i position of the card to be replace
	 * @param card replacement card
	 */
	public void replacecard(int i, Card card) {
		hand.set(i, card);//replaces the card in the i position
	}
	
	/**
	 * Removes every Card from currently in Hand
	 */
	public void ClearHand() {
		hand.removeAll(hand);
	}
	
	/**
	 * Return the number of cards currently in the hand
	 * @return Size of the hand
	 */
	public int sizeHand() {
		int count=0;
		for (Card i: hand)//Iterate through the Hand and see which items aren't null, count them
			if(i!=null)
				count++;
		return count;
	}

	/**
	 * Generates a string with the cards in Hand, where each line contains a single card with it's rank and suit
	 * @return Cards in Hand
	 */
	public String printableHand(){
		String buffer = "";
		for(Card i : hand) {//Iterate through the Hand and see which items aren't null and add them to a String buffer
			if(i!=null)
				buffer += i.toString()+" ";
		}
		return buffer;
	}
	
	/**
	 * Creates a shallow copy of the Hand returning the copied Hand
	 * @return Shallow copied Hand
	 */
	public Hand copy() {
		Hand aux= new Hand();
		for(Card i : hand)//Shallow Copy the Hand's array of cards (we don't have to worry that the cards will disappear)
			aux.addcard(i);
		return aux;		
	}
	
	/**
	 * Sorts the Hand using the card's ranks as criteria
	 */
	public void sort() {
		Collections.sort(hand);
	}
	
	/**
	 * Main function with the only propose being for test usage
	 */
	public static void main(String args[])
    {
	    Card c1,c2,c3, c4; 
	    c1 = new Card(3,1);
	    c2 = new Card(1,10);
	    c3 = new Card(2,12);
	    c4 = new Card(2,11);
	    Hand current = new Hand(), aux;
	    current.addcard(c1);
	    current.addcard(c2);
	    current.addcard(c3);
	    System.out.println(current.printableHand());
	    current.removecard(2);
	    System.out.println(current.printableHand());
	    current.ClearHand();
	    current.addcard(c4);
	    current.addcard(c3);
	    current.addcard(c2);
	    current.addcard(c1);
	    aux=current.copy();
	    System.out.println(current.printableHand());
	    current.sort();
	    System.out.println(current.printableHand());
	    System.out.println(aux.printableHand());
	    System.out.println(current.sizeHand());
	    
    }
}
