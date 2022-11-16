package variants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import card.Card;
import card.Hand;

/**
 * Defines the rules of the poker game, accordingly to the poker variant Double bonus 10/7 
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public abstract class HandEvaluator{
	/**
	 * Abstract method that indicates the structure of how a class should analyses the player's hand 
	 * and returns a vector with the positions of cards to keep and discord
	 * @param h Player's Hand
	 * @return vector with the positions to be discards (=0) and the ones to be kept (=1)
	 */
	public abstract int[] advice(Hand h);
	
	/**
	 * Abstract method that indicates the structure of how a class should evaluates the Player's
	 * Hand using the methods listed in this class
	 * @param h player's Hand
	 * @return String with the Hand's name or null if no Hand corresponds to it
	 */
	public abstract String evaluateHand(Hand h);
	
	/* Evaluates if there is a RoyalFlush (a Ten, a Jack, a Queen, a King and an Ace of the same suit)
	 * in the hand, and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ The hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] RoyalFlush(Hand h_) {
		int suit = h_.getcard(0).getsuit(), flag=0;
    	List<Integer> royalFlushRankList = Arrays.asList(0,9,10,11,12);
    	Card aux;
    	int keep[] = new int[5];
    	//Check if its RoyalFlush
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		//Check if its the same suit as the 1st card and it belongs in the List of the Royal Flush
    		if(aux.getsuit() == suit && royalFlushRankList.contains(aux.getrank()))
    			flag++;
    	}
    	if(flag==5) {//Every criteria was met
    		keep[0]=keep[1]=keep[2]=keep[3]=keep[4]=1;		
    		return keep;
    	}
    	return null;
	}
	
	/*Evaluates if there is a StraightFlush (5 consecutive cards of same suit)
	 * in the hand, and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	
	protected static int[] StraightFlush(Hand h_) {
		int suit = h_.getcard(0).getsuit(), flag=0;
    	Card aux;
    	int keep[] = new int[5];
    	//Check for Straight Flush
    	flag=0;
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(aux.getsuit() == suit)//Get the suit of the card
    			flag++;
    	}
    	if(flag==5) {//All cards must be of the same suit
    		flag=0;
    		for(int i = 0; i < 4; i++){//Goes through the 1st 4 cards and checks if every next card is +1 of the previous
        		if(h_.getcard(i).getrank() == (h_.getcard(i+1).getrank()-1))
        			flag++;
        	}
    		if(flag==4) {//Every card was +1 of the previous
    			keep[0]=keep[1]=keep[2]=keep[3]=keep[4]=1;
    			return keep;
    		}
    	}
    	return null;
	}
	
	/*Evaluates if there is a Four of a Kind (4 cards of the same value)
	 * in the hand, and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourOfAKind(Hand h_){
		int rank,flag=0;
    	Card aux;
    	int keep[] = new int[5];
    	//Check if its four of a kind
    	for(int j=0;j<2;j++) {//Four of a Kind can be either in the 1st four cards or the last four cards
    		rank=h_.getcard(j).getrank();//get the card rank
    		flag=0;//Reset counter
	    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
	    		aux=h_.getcard(i);
	    		if(aux.getrank() ==  rank){//Check which cards are equal to the rank gotten
	    			flag++;
	    			keep[i]=1;
	    		}
	    		else
	    			keep[i]=0;
	    	}
	    	if(flag==4){//4 cards are equal in the Hand, Four of a kind!
    			return keep;
    		}	    		
    	}
    return null;	
	}
	
	/* Evaluates if there are 4 cards of a RoyalFlush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourToARoyalFlush(Hand h_){

		int suit, flag=0;
    	List<Integer> royalFlushRankList = Arrays.asList(0,9,10,11,12);
    	Card aux;
    	int keep[] = new int[5];
    	//Check if its 4 to a Royal Flush
    	for(int k=0;k<2;k++) {//4 to a Royal Flush can be either in the 1st four cards or the last four cards
    		flag=0;
	    	if(royalFlushRankList.contains(h_.getcard(k).getrank()))//Check if the card is in a list of the Royal Flush Rank List
	    		suit=h_.getcard(k).getsuit();//if it is get the suit of the card
	    	else
	    		continue;
	    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
	    		aux=h_.getcard(i);
	    		//The card suit is equal to the one gotten and it belongs in the Royal Flush Rank List
	    		if(aux.getsuit() == suit && royalFlushRankList.contains(aux.getrank())) {
	    			flag++;
	    			keep[i]=1;
	    		}
	    		else
	    			keep[i]=0;
	    	}
	    	if(flag==4)//Four to a Royal Flush was found
	    		return keep;
    	}
		return null;
	}
	
	/*Evaluates if there are 3 Aces, 
	 *and in the affirmative case returns the positions
	 *of the cards that constitute the sequence
	 *@param h_ the hand to be evaluated
	 *@return array with the positions where the sequence occurs
	 */

	protected static int[] ThreeAces(Hand h_){
		int flag=0;
    	Card aux;
    	int keep[] = new int[5];
    	//Check if its Three Aces
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(aux.getrank() == 0) {//Check if the card's rank if an Ace
    			flag++;
    			keep[i]=1;
    		}
    		else
    			keep[i]=0;
    	}
    	if(flag==3)//3 Aces where found
    		return keep;
    	return null;
		
	}
	
	
	/* Evaluate if there is a FullHouse (a pair and a three of a kind), in the hand, 
	 * and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FullHouse(Hand h_) {
		HashMap<Integer,Integer> rankquantity = new HashMap<>();//rank, and quantity hash map
		Card aux;
		int keep[] = new int[5];
		
		//Check if its Full House
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(rankquantity.containsKey(aux.getrank())){//Check if the HashMap already has the key we found
    			rankquantity.put(aux.getrank(), rankquantity.get(aux.getrank())+1);//Increment the key value
    		}
    		else{
    			rankquantity.put(aux.getrank(), 1);//HashMap doesn't have the rank we found, place it there
    		}
    	}
    	if(rankquantity.containsValue(3) && rankquantity.containsValue(2)){//Hand contains exactly 2 ranks, one with 3 cards and the other with 2
    		keep[0]=keep[1]=keep[2]=keep[3]=keep[4]=1;
    		return keep;
    	}
    	return null;
	}
	
	/* Evaluate if there is a Flush (any 5 cards of the same suit) in the hand, 
	 * and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] Flush(Hand h_){
		int keep[] = new int[5], suit, flag=0;		
		//Check if its Flush
    	suit=h_.getcard(0).getsuit();
    	for(int i=0;i<5;i++)//Goes through all the cards in the Hand
    		if(h_.getcard(i).getsuit()==suit)//gets the card's suit
    			flag++;
    	if(flag==5){//The 5 cards all have the same suit
    		keep[0]=keep[1]=keep[2]=keep[3]=keep[4]=1;
    		return keep;
    	}
    	return null;
	}
	
	/* Evaluate if there is a Straight(a sequence of 5 cards of consecutive value) in the hand, 
	 * and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] Straight(Hand h_){
		Card aux;
		List<Integer> HighStraight = new ArrayList<>(Arrays.asList(0,12,11,10,9));
		int keep[] = {1,1,1,1,1}, flag=0;
		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
			aux=h_.getcard(i);
			if(HighStraight.contains(aux.getrank())){//Check if the Hand contains the cards present for a High Straight
				flag++;
				HighStraight.remove((Integer)aux.getrank());//Remove to make sure we don't have repeated cards
			}
		}
		if(flag==5)//High Straight was found
			return keep;	
		flag=0;
		for(int i=0;i<4;i++) {//Goes through the first 4 cards in the Hand and check if the next card is exactly one more than itself
			aux=h_.getcard(i);
			if(h_.getcard(i).getrank() == (h_.getcard(i+1).getrank()-1)) {
				flag++;
			}
		}
		
		if(flag==4)//Straight was found
			return keep;
		return null;
	}
	
	/* Evaluate if there is a Three of a Kind (3 cards of the same rank) in the hand, 
	 * and in the affirmative case returns the positions of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	protected static int[] ThreeOfAKind(Hand h_){
		int rank, flag=0, keep[] = new int[5];
		Card aux;
		//Check if its three of a kind
    	for(int j=0;j<3;j++) {//the sequence can contain either the 1st, 2nd or 3rd cards
    		rank=h_.getcard(j).getrank();//get the rank
    		flag=0;
	    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
	    		aux=h_.getcard(i);
	    		if(aux.getrank() == rank){//Check if the cards rank is equal to the one gotten before
	    			flag++;
	    			keep[i]=1;
	    		}
	    		else
	    			keep[i]=0;
	    	}
	    	if(flag==3)//3 cards of the same rank were found
    			return keep;	    		
    	}
    return null;		
	}
	
	/* Evaluates if there are 4 cards of a Straight Flush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	protected static int[] FourToAStraightFlush(Hand h_){
		int flag, suit=0, keep[]= {0,0,0,0,0}, max=0;
		Card aux;
    	//Check if its four to a straight Flush
    	flag=0;
    	//At least 4 cards have the same suit, there are 3 comparisons we have to make to discover the odd one
    	if(h_.getcard(0).getsuit()==h_.getcard(1).getsuit())//1st and 2nd card have the same suit
    		suit = h_.getcard(0).getsuit();
    	else if(h_.getcard(1).getsuit()==h_.getcard(2).getsuit())//1st and 2nd didn't have, check if the 2nd and 3rd have the same suit
    		suit = h_.getcard(1).getsuit();//1st card is the odd one
    	else//2nd card is the odd one 
    		suit = h_.getcard(2).getsuit();
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(aux.getsuit() == suit)//Check if the suit is equal to the one we extracted
    			flag++;
    	}
    	if(flag>=4) {//at least 4 cards have the same suit
    		flag=0;
    		for(int i = 0; i < 4; i++){//Goes through all the cards in the Hand
    			//Check if the card and its successor have the suit we got and they are consecutive
        		if((h_.getcard(i).getsuit()==suit && h_.getcard(i+1).getsuit()==suit && h_.getcard(i).getrank() == (h_.getcard(i+1).getrank()-1))){
        			flag++;
        			keep[i]=keep[i+1]=1;
        		}
        		//Check if the card and its successor have the suit we got and they are consecutive with 1 interval
        		else if((h_.getcard(i).getsuit()==suit && h_.getcard(i+1).getsuit()==suit && h_.getcard(i).getrank() == (h_.getcard(i+1).getrank()-2))) {
        			if(max==1)//We can only have 1 card missing (the interval) for the flush
        				return null;
        			max=1;
        			flag++;
        			keep[i]=keep[i+1]=1;
        		}
        		else
        			keep[i+1]=0;
        	}
    		if(flag==3) {//there are 3 consecutive cards with a maximum of 1 interval
    			return keep;
    		}
    	}
		return null;
	}
	
	/* Evaluates if there is a Two Pair (2 pairs of the same rank) sequence in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] TwoPair(Hand h_){
		HashMap<Integer,Integer> rankquantity= new HashMap<>();
    	int count = 0, keep[] = {0,0,0,0,0};
    	Card aux;
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(rankquantity.containsKey(aux.getrank())){//Check if the rank gotten is already in the HashMap
    			rankquantity.put(aux.getrank(), rankquantity.get(aux.getrank())+1);//increment the quantity
    			count++;//there is a par (there could be a trio or 4 cards)
    			keep[i]=1;
    			for(int j=0;j<5;j++)//find the pair and also check the position to keep
    				if(h_.getcard(j).getrank()==aux.getrank())
    					keep[j]=1;
    		}
    		else{
    			rankquantity.put(aux.getrank(), 1);
    		}

    	}
    	//There are 2 pairs and there is an isolate card (HashMap contains value(1))
    	if(count==2 && rankquantity.containsValue(1))
    		return keep;
    	
    	return null;	
	}
	
	/* Evaluates if there is a High  Pair ( a pair of Jacks, Queens, Kings or Aces) in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] HighPair(Hand h_){
		List<Integer> HighCards = Arrays.asList(0,10,11,12);
		HashMap<Integer,Integer> rankquantity= new HashMap<>();
    	int count = 0, keep[] = new int[5], highcard=0;
    	Card aux;
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(rankquantity.containsKey(aux.getrank())){//Check if the rank we have is already in the HashMap
    			rankquantity.put(aux.getrank(), rankquantity.get(aux.getrank())+1);//Increment the amount
    			if(HighCards.contains(aux.getrank())) {
    				highcard=aux.getrank();
    				count++;//High Card Pair
    			}
    		}
    		else{//Place the rank in the HashMap
    			rankquantity.put(aux.getrank(), 1);
    		}

    	}
    	if(count==1 && rankquantity.containsValue(1)) {//1 High Pair and there are unpaired cards
    		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    			if(h_.getcard(i).getrank()==highcard)//Check which cards correspond to the pair
    				keep[i]=1;//keep them
    			else
    				keep[i]=0;
    		}
    		return keep;
    	}
    	return null;	
	}
	
	/* Evaluates if there are 4 cards of a Flush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 * of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourToAFlush(Hand h_){
		int suit, flag=0, keep[] = new int[5];
		//At least 4 cards have the same suit, there are 3 comparisons we have to make to discover the odd one
		if(h_.getcard(0).getsuit()==h_.getcard(1).getsuit()||h_.getcard(0).getsuit()==h_.getcard(2).getsuit())
			suit=h_.getcard(0).getsuit();//the 1st card is equal to at least 2 cards therefore it can't be the odd one
		else
			suit = h_.getcard(1).getsuit();//if the 1st card is odd then the 2nd can't be
    	for(int i=0;i<5;i++)//Goes through all the cards in the Hand
    		if(h_.getcard(i).getsuit()==suit) {//Check if the suit is equal to the suit we got
    			flag++;
    			keep[i]=1;
    		}
    		else
    			keep[i]=0;

    	if(flag==4){//4 cards have the same suit
    		return keep;
    	}
    	return null;
	}
	
	/* Evaluates if there are 3 cards of a RoyalFlush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 * of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] ThreeToARoyalFlush(Hand h_){
		int suit, flag=0;
    	List<Integer> royalFlushRankList = Arrays.asList(0,9,10,11,12);
    	Card aux;
    	int keep[] = new int[5];
    	//Check if its three to a Royal Flush
    	for(int i=0;i<3;i++) {//One of the three cards can either be on the 1st, 2nd and 3rd spots
	    	if(royalFlushRankList.contains(h_.getcard(i).getsuit()))//get the suit of the i card
	    		suit=h_.getcard(i).getsuit();
	    	else if (royalFlushRankList.contains(h_.getcard(i+1).getsuit()))//get the suit of the i+1 card
	    		suit=h_.getcard(i+1).getsuit();
	    	else //get the suit of the i+2 card
	    		suit=h_.getcard(i+2).getsuit();
	    	flag=0;
	    	for(int j=0;j<5;j++){//Goes through all the cards in the Hand
	    		aux=h_.getcard(j);
	    		if(aux.getsuit() == suit && royalFlushRankList.contains(aux.getrank())) {//Check if the card bellongs to Royal Flush and is the same rank
	    			flag++;
	    			keep[j]=1;
	    		}
	    		else
	    			keep[j]=0;
	    	}
	    	if(flag==3) {//3 suited cards that belong to royal flush
	    		return keep;
	    	}
    	}
		return null;
	}
	/* Evaluates if there are 4 cards of a Straight sequence in the hand and the card needed for a flush is missing in
	 * one of the extremes of the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	
	 protected static int[] FourToAnOutsideStraight(Hand h_){
	        int flag, keep[]= {0,0,0,0,0};
	        //Check for StraightFlush
	        flag=0;
	        for(int i=0;i<4;i++){//Goes through the first 4 cards in the Hand
	            if(h_.getcard(i).getrank() == (h_.getcard(i+1).getrank()-1)){//Check if the next card is exactly 1 more then the previous
	                flag++;
	                keep[i]=keep[i+1]=1;
	            }
	            else {
	                if(flag!=0 && flag!=3)
	                    break;
	                keep[i+1]=0;
	            }
	        }
	        if(flag==3) {//3 cards correspond are exactly one more then the previous one
	            return keep;
	        }
	        return null;
	    }
	
	/* Evaluates if there is a Pair of low cards ( with rank 2,3,4,5,6,7,8,9 or 10) in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] LowPair(Hand h_){
		List<Integer> LowCards = Arrays.asList(1,2,3,4,5,6,7,8,9);
		HashMap<Integer,Integer> rankquantity= new HashMap<>();
    	int count = 0, keep[] = new int[5], lowcard=0;
    	Card aux;
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(rankquantity.containsKey(aux.getrank())){//Check if the HashMap contains the card rank
    			int value = rankquantity.get(aux.getrank());
    			rankquantity.put(aux.getrank(), value+1);
    			if(LowCards.contains(aux.getrank())) {//The Pair is a low pair
    				lowcard=aux.getrank();
    				count++;//We found our low pair
    			}
    		}
    		else{
    			rankquantity.put(aux.getrank(), 1);
    		}

    	}
    	if(count==1 && rankquantity.containsValue(1)) {
    		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    			if(h_.getcard(i).getrank()==lowcard)//Locate where the low pair is and keep it
    				keep[i]=1;
    			else
    				keep[i]=0;
    		}
    		return keep;
    	}
    	return null;
		
	}
	
	/* Evaluates if there are a King, a Queen, a Jack and an Ace in the hand, with at least one with a different suit of the remaining ones, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] AKQJUnsuited(Hand h_){
		int aux1[] = {0,0,0,0,0}, aux2[]=new int[5], aux3[]=new int[5];	
		//If there is a KQUnsuited and QJUnsuited and AK unsuited there is AKQJ unsuited
		if((aux1=HandEvaluator.KQUnsuited(h_))!=null && (aux2=HandEvaluator.QJUnsuited(h_))!=null
				&& (aux3=HandEvaluator.TwoUnsuitedsuitedcards(h_,0, 12, 0))!=null) {
			for(int i=0;i<5;i++)//Goes through all the positions in the keep card vector
				if(aux2[i]==1 || aux3[i]==1)//Select the corresponding positions
					aux1[i]=1;
			return aux1;
		}
		return null;		
	}
	
	/* Evaluates if there are 3 cards of a StraightFlush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] ThreeToAStraightFlush1(Hand h_){
		int keep[] = new int[5], count=0, high=0, suit=-1, highest=-1, lowest=100;		
		List<Integer> HighCards = Arrays.asList(0,10,11,12);
		
		if(HandEvaluator.TwoUnsuitedsuitedcards(h_, 1, 2, 1)!=null 
				&& HandEvaluator.TwoUnsuitedsuitedcards(h_, 3, 2, 1)!=null )
    		return null;//its 234suited
		
    	for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    		count=0;
    		if(h_.getcard(i).getrank()==0)//Is a low ace
    			continue;//ignore it
    		suit=h_.getcard(i).getsuit();//get the card's suit
    		for(int j=i;j<5;j++){
    			if(h_.getcard(j).getrank()==0)//Is a low ace
        			continue;//ignore it
    			if(suit==h_.getcard(j).getsuit())//Check how many cards correspond to the suit we got
    				count++;
    		}
    		if(count==3)//3 cards are suited
    			break;
    	}
    	if(count==3) {//3 cards are suited
    		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    			//Check if the card is of the suit we are looking and get the highets and the lowest
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()>highest)
    				highest= h_.getcard(i).getrank();
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()<lowest)
    				lowest= h_.getcard(i).getrank();
    			if(HighCards.contains(h_.getcard(i).getrank()) && h_.getcard(i).getsuit()==suit)
        			high++;//the number of high cards
    		}	
	    	if(highest-lowest<5)//The distance between the highest card and lowest should be lower then 5
	    		if(high>=(highest-lowest-2)) {//The number of high cards equals or exceeds the number of gaps
	    			for(int i=0;i<5;i++)
	    				if(h_.getcard(i).getsuit()==suit)//check which cards to keep
	    					keep[i]=1;
	    				else
	    					keep[i]=0;
	    			return keep;
	    		}
    	}
    		
		return null;
	}
	
	/* Evaluates if there are 4 cards of a Straight sequence, where at least  in the hand and the card needed for a Straight is missing in
	 * between these 4 cards. It also detects the extreme cases A234 and JQKA
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	protected static int[] FourToAnInsideStraightWith3HighCards(Hand h_){
		return HandEvaluator.FourToAnInsideStraightWithXHighCards(h_, 3);
	}
	
	/* Evaluates if there are a Queen and a Jack of the same suit
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] QJSuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 11, 10, 1);
	}
	
	/* Evaluates if there are 3 cards of a Flush sequence , with at least 2 High Cards
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	
	
	protected static int[] ThreeToAFlushWith2HighCards(Hand h_){
		return HandEvaluator.ThreeToAFlushWithXHighCards(h_,2);
	}
	
	/* Evaluates if there is a pair of High Cards of the same Suit
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	
	protected static int[] TwoSuitedHighCards(Hand h_){
		Card aux;
		List<Integer> HighCards = Arrays.asList(0,10,11,12);
		int suit, keep[] = new int[5], flag=0;
		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
			aux=h_.getcard(i);
			if(HighCards.contains(aux.getrank())) {//If the card is of a High Rank
				suit = aux.getsuit();//get its suit
				for(int j=i+1;j<5;j++){//Goes through all the cards in the Hand
					aux=h_.getcard(j);
					if(HighCards.contains(aux.getrank()) && aux.getsuit()==suit) {//Check if the suit is equal and if the card is also a high card
						for(int k=0;k<5;k++)//All positions are to discard except the i and j position
							keep[k]=0;
						keep[i]=keep[j]=1;
						flag=1;
						break;
					}
				}
			}
		}
		if(flag==1)//2 suited high cards were found
			return keep;

		return null;
		
	}
	
	/* Evaluates if there are 3 cards of a StraightFlush sequence in the hand, 
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourToAnInsideStraightWith2HighCards(Hand h_){
		return HandEvaluator.FourToAnInsideStraightWithXHighCards(h_, 2);
	}
	
	/* Evaluates if there are 3 cards of a StraightFlush sequence in the hand, with 2 high cards
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] ThreeToAStraighFlush2(Hand h_){
		int keep[] = new int[5], aux1[], aux2[], count=0, high=0, suit=-1, highest=-1, lowest=100;		
		List<Integer> HighCards = Arrays.asList(0,10,11,12);
		
		if((aux1=HandEvaluator.TwoUnsuitedsuitedcards(h_, 1, 2, 1))!=null 
				&& (aux2=HandEvaluator.TwoUnsuitedsuitedcards(h_, 3, 2, 1))!=null ) {
			for(int i=0;i<5;i++)//Goes through all the positions in the keep card vector
				if(aux2[i]==1)//Select the corresponding positions
					aux1[i]=1;
			return aux1;
		}
		
    	for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    		count=0;
    		suit=h_.getcard(i).getsuit();//get the cards suit
    		for(int j=i;j<5;j++){//Check which cards have the same suit
    			if(suit==h_.getcard(j).getsuit())
    				count++;
    		}
    		if(count==3)//3 cards have the same suit
    			break;
    	}
    	if(count==3) {//3 cards have the same suit
    		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    			//Get the highest and the lowest card in that suit
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()>highest)
    				highest= h_.getcard(i).getrank();
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()<lowest)
    				lowest= h_.getcard(i).getrank();
    			if(HighCards.contains(h_.getcard(i).getrank()) && h_.getcard(i).getsuit()==suit)
        			high++;//its a high card in that suit
    		}	
    		//It either has with one gap or with two gaps and one high card
	    	if(highest-lowest==3 || (high==1 && ((highest-lowest)==4))){
				for(int i=0;i<5;i++)
					if(h_.getcard(i).getsuit()==suit)//Keep the corresponding cards
						keep[i]=1;
					else
						keep[i]=0;
				return keep;
	    	}
    	}
		return null;
	}
	
	/* Evaluates if there are 4 cards of a Straight sequence in the hand, with 1 of those cards being a High Card,
	 *  and the card needed for a Straight is missing in
	 * between these 4 cards. It also detects the extreme cases A234 and JQKA
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourToAnInsideStraightWith1HighCard(Hand h_){
		return HandEvaluator.FourToAnInsideStraightWithXHighCards(h_, 1);
	}
	
	/* Evaluates if there is a sequence King Queen and Jack of the same suit
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] KQJUnsuited(Hand h_){
		int aux1[] = new int[5], aux2[]=new int[5];
		//If there is a KQUnsuited and QJUnsuited there is KQJ unsuited
		if((aux1=HandEvaluator.KQUnsuited(h_))!=null && (aux2=HandEvaluator.QJUnsuited(h_))!=null) {
			for(int i=0;i<5;i++)//Combine both positions to keep by both vectors
				if(aux2[i]==1)
					aux1[i]=1;
			return aux1;
		}
		return null;
	}
	
	/* Evaluates if there is a sequence Jack and Ten of the same suit
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] JTSuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 10, 9, 1);	
	}
	
	/* Evaluates if there is a Queen and a Jack of different suits
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] QJUnsuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 11, 10, 0);		
	}
	
	/* Evaluates if there are 3 cards of Flush sequence in the hand, with 1 high card
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	

	protected static int[] ThreeToAFlushWith1HighCard(Hand h_){
		return HandEvaluator.ThreeToAFlushWithXHighCards(h_,1);
	}
	
	/* Evaluates if there is a Queen and a Ten of different suits
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] QTSuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 11, 9, 1);
	}
	
	/* Evaluates if there are 3 cards of a StraightFlush sequence in the hand
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] ThreeToAStraightFlush3(Hand h_){
		int keep[] = new int[5], count=0, suit=-1, highest=-1, lowest=100;
		
    	for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
    		count=0;
    		suit=h_.getcard(i).getsuit();//get the cards suit
    		for(int j=i;j<5;j++){//Check which cards have the same suit
    			if(suit==h_.getcard(j).getsuit())
    				count++;
    		}
    		if(count==3)//3 cards have the same suit
    			break;
    	}
    	if(count==3) {//3 cards have the same suit
    		for(int i=0;i<5;i++) {//get the highest and lowest card in that suit
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()>highest)
    				highest= h_.getcard(i).getrank();
    			if(h_.getcard(i).getsuit()==suit && h_.getcard(i).getrank()<lowest)
    				lowest= h_.getcard(i).getrank();
    		}	
    	
	    	if((highest-lowest)==4){//There can only be two gaps and no high cards
				for(int i=0;i<5;i++)
					if(h_.getcard(i).getsuit()==suit)//keep the cards that are of that suit
						keep[i]=1;
					else
						keep[i]=0;
				return keep;
	    		}
    	}
    		
		return null;
	}
	
	/* Evaluates if there is a Queen and a King with the same suit
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
		
	protected static int[] KQUnsuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 12, 11, 0);
	}
	
	/* Evaluates if there is a King and a Jack of different suits
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */

	protected static int[] KJUnsuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 12, 10, 0);
	}
	
	/* Evaluates if there are Aces in the hand
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] Ace(Hand h_){
		int flag=0,keep[] = new int[5];
		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
			if(h_.getcard(i).getrank()==0){//if there is an ace
				keep[i]=1;//keep it
				flag++;
			}
			else
				keep[i]=0;
		}
		if(flag==1)//there is only one ace
			return keep;
		return null;
	}
	
	/* Evaluates if there is a King and a Ten of the same suits
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] KTSuited(Hand h_){
		return HandEvaluator.TwoUnsuitedsuitedcards(h_, 12, 9, 1);
	}
	
	/* Evaluates if there are a Jack, a Queen and a King in the hand
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */

	protected static int[] JackQueenKing(Hand h_){
		int rank,flag=0,keep[] = new int[5];
		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
			rank=h_.getcard(i).getrank();
			if(rank==10||rank==11||rank==12) {//if its a jack or a queen or a king
				keep[i]=1;//keep it
				flag++;
			}
			else
				keep[i]=0;
		}
		if(flag==1)//there is only one jack or queen or king
			return keep;
		return null;
	}
	
	/* Evaluates if there are 4 cards of a Straight sequence in the hand, with none of those cards being a High Card,
	 *  and the card needed for a Straight is missing in
	 * between these 4 cards. It also detects the extreme cases A234 and JQKA
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
		
	protected static int[] FourToAnInsideStraightWithNoHighCards(Hand h_){
		return HandEvaluator.FourToAnInsideStraightWithXHighCards(h_, 0);
	}
	
	/* Evaluates if there are 3 cards of a Flush sequence, with none of them being high cards
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @return array with the positions where the sequence occurs
	 */
	protected static int[] ThreeToAFlushWithNoHighCards(Hand h_){
		return HandEvaluator.ThreeToAFlushWithXHighCards(h_,0);
	}
	
	/* Evaluates if there are 4 cards of a Straight sequence in the hand, with x of those cards being a High Card,
	 *  and the card needed for a Straight is missing in
	 * between these 4 cards. It also detects the extreme cases A234 and JQKA
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @param x Number of high cards
	 * @return array with the positions where the sequence occurs
	 */
	
	protected static int[] FourToAnInsideStraightWithXHighCards(Hand h_, int x) {
		int keep[] = {1,1,1,1,1}, flag=0, numbHigh=0;
		Card aux;
		List<Integer> TJQKA = new ArrayList<>(Arrays.asList(0,9,10,11,12));
		List<Integer> HighCards = Arrays.asList(0,10,11,12);
    		
		int highest=-1, lowest=100;
		
		if((h_.getcard(4).getrank()-h_.getcard(1).getrank())==4) {//the distance between the 2nd and 5th card is 4
			//get the highest and the lowest
			highest=h_.getcard(4).getrank();
			lowest=h_.getcard(1).getrank();
			keep[0]=0;//discard the 1st card
		}
		else if((h_.getcard(3).getrank()-h_.getcard(0).getrank())==4) {//the distance between the 2nd and 5th card is 4
			//get the highest and the lowest
			highest=h_.getcard(3).getrank();
			lowest=h_.getcard(0).getrank();
			keep[4]=0;//discard the last card
		}
		else {
    		flag=0; numbHigh=0;
    		for(int i=0;i<5;i++){//Goes through all the cards in the Hand
        		aux=h_.getcard(i);
        		if(TJQKA.contains(aux.getrank())){//Its an inside straight with a High Ace
        			if(HighCards.contains(aux.getrank()))//Contains a high card
        				numbHigh++;
        			TJQKA.remove((Integer)aux.getrank());//Remove the card to guarantee we don' have duplicates
        			flag++;
        			keep[i]=1;
        		}
        		else
        			keep[i]=0;
        	}
    		if(flag==4 && numbHigh==x)//4 cards of the Straight high ace were found and exactly X high cards
    			return keep;
			return null;
		}
		numbHigh=0;
		for(int i=0;i<5;i++){//Goes through all the cards in the Hand
			if(h_.getcard(i).getrank()>=lowest && h_.getcard(i).getrank()<=highest){//The card gotten is between the sequence we want to analyze
				if(i!=4 && (h_.getcard(i).getrank()==h_.getcard(i+1).getrank()))//Check if the next card isn't equal to it
					return null;//Not a 4 to an inside straight
				if(HighCards.contains(h_.getcard(i).getrank()))
					numbHigh++;//Number of high cards the Straight contains
			}
		}
		if(numbHigh==x)//We found an X amount of high cards
			return keep;
		
		return null;
	}
	
	/* Evaluates if there are 3 cards of a Flush sequence, with X of them being high cards
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @param x - int : the number of high cards
	 * @return array with the positions where the sequence occurs
	 */
		
	protected static int[] ThreeToAFlushWithXHighCards(Hand h_, int x) {
		int suit=0, flag=0, keep[] = new int[5], suits[]=new int[4];
		
		HashMap<Integer,Integer> suitquantity = new HashMap<>();//rank, and quantity hash map
		Card aux;
    	flag=0;
		
    	for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    		aux=h_.getcard(i);
    		if(suitquantity.containsKey(aux.getsuit())){//Check if the HashMap already contains the suit
    			suitquantity.put(aux.getsuit(), suitquantity.get(aux.getsuit())+1);//Increment the suit quantity
    		}
    		else{
    			suitquantity.put(aux.getsuit(), 1);
    			suits[flag++]=aux.getsuit();//place all available suits in an array
    		}
    	}
    	flag=0;
    	if(suitquantity.containsValue(3)){//There are 3 cards of the same suit
    		for(int i=0;i<suits.length;i++)//Go through the vector of suits and find which has the 3 suits
    			if(suitquantity.get(suits[i])==3){
    				suit=suits[i];
    				break;
    			}
    		for(int i=0;i<5;i++){//Goes through all the cards in the Hand
    			if(h_.getcard(i).getsuit()==suit){//for the cards corresponding to that suit
    				if(h_.getcard(i).getrank()>9 || h_.getcard(i).getrank()==0)
    	    			flag++;//Count the number of high cards
    				keep[i]=1;//keep them
    			}
    			else
    				keep[i]=0;
    		}
    		if(flag==x)//there are exactly x high cards
    			return keep;
    	}
		return null;
	}
	
	/* Evaluates if there are 2 cards of the ranks a and b, and if they are suited or not
	 * and in the affirmative case returns the positions
	 *  of the cards that constitute the sequence
	 * @param h_ the hand to be evaluated
	 * @param a - int : rank of one of the cards
	 * @param b - int : rank of another card
	 * @param suited - int : 1 to evaluate if the cards have the same suit, and 0 otherwise
	 * @return array with the positions where the cards occur
	 */
		
	protected static int[] TwoUnsuitedsuitedcards(Hand h_, int a, int b, int suited) {
		Card aux;
		int suit, keep[] = new int[5];
		for(int i=0;i<5;i++) {//Goes through all the cards in the Hand
			aux=h_.getcard(i);
			if(aux.getrank()==a) {//check if the card is equal to the 1st one (a)
				suit = aux.getsuit();//Get the suit of the card
				for(int j=0;j<5;j++){//Goes through all the cards in the Hand
					aux=h_.getcard(j);
					if(aux.getrank()==b){//check if the card is equal to the 2nd one (b)
						if(suited==1){//if we want the cards to be suited
							if(aux.getsuit()==suit) {//Check is the cards are suited
								for(int k=0;k<5;k++)//if they are discard every position except this 2 cards
									keep[k]=0;
								keep[i]=keep[j]=1;
								return keep;
							}
						}
						else {//We don't need the cards to be suited
							for(int k=0;k<5;k++)//Discard every position except this 2 cards
								keep[k]=0;
							keep[i]=keep[j]=1;
							return keep;
						}
					}
				}
			}
		}
		return null;
	}
	
	/* Creates an array with 0, to be used when discarding a hand
	 * @return Array of length 5 with 0's
	 * */	
	protected static int[] Discard(){
		int keep[] = new int[5];
		for(int i=0;i<5;i++)//Discard all
			keep[i]=0;
		return keep;		
	}

	
	/**
	 * Main function with the only propose being for test usage
	 */
	public static void main(String args[]){
		Hand test = new Hand();
		int temp[] = new int[5];
		test.addcard(new Card(3,12));
		test.addcard(new Card(0,11));
		test.addcard(new Card(0,10));
		test.addcard(new Card(1,8));
		test.addcard(new Card(2,7));
		test.sort(); 
		System.out.println(test.printableHand());
		if((temp=HandEvaluator.RoyalFlush(test))!=null)
				System.out.println("Royal Flush");
		else if((temp=HandEvaluator.StraightFlush(test))!=null)
			System.out.println("Straight Flush"); 
		else if((temp=HandEvaluator.FourOfAKind(test))!=null)	
			System.out.println("Four of a kind"); 
		else if((temp=HandEvaluator.FourToARoyalFlush(test))!=null)
			System.out.println("4 to a Royal Flush");
		else if((temp=HandEvaluator.ThreeAces(test))!=null)
			System.out.println("Three aces");
		else if((temp=HandEvaluator.FullHouse(test))!=null)
			System.out.println("Full House");
		else if((temp=HandEvaluator.Flush(test))!=null)
			System.out.println("Flush"); 
		else if((temp=HandEvaluator.Straight(test))!=null)
			System.out.println("Straight"); 
		else if((temp=HandEvaluator.ThreeOfAKind(test))!=null)
			System.out.println("Three of a kind");
		else if((temp=HandEvaluator.FourToAStraightFlush(test))!=null)
			System.out.println("Four to a Straight flush");
		else if((temp=HandEvaluator.TwoPair(test))!=null)
			System.out.println("Two Pair");
		else if((temp=HandEvaluator.HighPair(test))!=null)
			System.out.println("High Pair");
		else if((temp=HandEvaluator.FourToAFlush(test))!=null)
			System.out.println("Four to a flush");
		else if((temp=HandEvaluator.ThreeToARoyalFlush(test))!=null)
			System.out.println("Three to a royal flush");
		else if((temp=HandEvaluator.FourToAnOutsideStraight(test))!=null)
			System.out.println("Four to an outisde Straight");
		else if((temp=HandEvaluator.LowPair(test))!=null)
			System.out.println("Low Pair");
		else if((temp=HandEvaluator.AKQJUnsuited(test))!=null)
			System.out.println("AKQJ Unsuited");
		else if((temp=HandEvaluator.ThreeToAStraightFlush1(test))!=null)
			System.out.println("Three to a straight flush (type 1)");
		else if((temp=HandEvaluator.FourToAnInsideStraightWith3HighCards(test))!=null)
			System.out.println("Four to an inside straight with 3 high cards");
		else if((temp=HandEvaluator.QJSuited(test))!=null)
			System.out.println("QJ Suited");
		else if((temp=HandEvaluator.ThreeToAFlushWith2HighCards(test))!=null)
			System.out.println("Three to a flush with 2 High Cards");
		else if((temp=HandEvaluator.TwoSuitedHighCards(test))!=null)
			System.out.println("Two suited high cards");
		else if((temp=HandEvaluator.FourToAnInsideStraightWith2HighCards(test))!=null)
			System.out.println("Four to an inside straight with 2 high cards");
		else if((temp=HandEvaluator.ThreeToAStraighFlush2(test))!=null)
			System.out.println("Three to a straight flush (type 2)");
		else if((temp=HandEvaluator.FourToAnInsideStraightWith1HighCard(test))!=null)
			System.out.println("Four to an inside straight with 1 high card");
		else if((temp=HandEvaluator.KQJUnsuited(test))!=null)
			System.out.println("KQJ unsuited");
		else if((temp=HandEvaluator.JTSuited(test))!=null)
			System.out.println("JT suited");
		else if((temp=HandEvaluator.QJUnsuited(test))!=null)
			System.out.println("QJ unsuited");
		else if((temp=HandEvaluator.ThreeToAFlushWith1HighCard(test))!=null)
			System.out.println("Three to a flush with 1 high card");
		else if((temp=HandEvaluator.QTSuited(test))!=null)
			System.out.println("QT suited");
		else if((temp=HandEvaluator.ThreeToAStraightFlush3(test))!=null)
			System.out.println("Three to a straight flush (type 3)");
		else if((temp=HandEvaluator.KQUnsuited(test))!=null)
			System.out.println("KQ, KJ Unsuited");
		else if((temp=HandEvaluator.KJUnsuited(test))!=null)
			System.out.println("KQ, KJ Unsuited");
		else if((temp=HandEvaluator.Ace(test))!=null)
			System.out.println("Ace");
		else if((temp=HandEvaluator.KTSuited(test))!=null)
			System.out.println("KT suited");
		else if((temp=HandEvaluator.JackQueenKing(test))!=null)
			System.out.println("Jack Queen or King");
		else if((temp=HandEvaluator.FourToAnInsideStraightWithNoHighCards(test))!=null)
			System.out.println("Four to an inside straight with no high cards");
		else if((temp=HandEvaluator.ThreeToAFlushWithNoHighCards(test))!=null)
			System.out.println("Three to a flush with no high cards");
		else
			temp=HandEvaluator.Discard();
		if(temp!=null)
			for(int i=4; i>=0;i--)
				if(temp[i]==0)
					test.removecard(i);
		System.out.println(test.printableHand());
	}
	
	
}
