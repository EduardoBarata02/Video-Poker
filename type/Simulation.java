package type;

import card.Deck;
import casino.Round;
import variants.DoubleBonus;
import variants.GameVariant;

/**
 * Class that implements the Simulation mode for VideoPoker game, betting, dealing and
 * playing with the perfect strategy provided for a provided number of plays
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Simulation extends Game{
	
	/**
	 * Simulation Constructor, responsible to call the main commands of the simulation mode
	 * and executing the game
	 * @param credit Initial player's credit
	 * @param bet Amount to be bet in every round
	 * @param nDeals number of deals the user wants to see made
	 */
	public Simulation(int credit, int bet, int nDeals) {
		super(credit);//Super constructor		
		int total=0;
		double sum_of_all_bets=0;
		int initialcredit=credit;
		GameVariant db = new DoubleBonus();
		Round round=null;
		
		for(int i=0;i<nDeals;i++) {//Do this cycle for every round the user wants us to simulate with the perfect strategy
			if(bet>currentplayer.getcredit()) {//Check if the player still has funds to play
				System.out.println("Out of funds...\n");
				break;
			}
			round= bet(bet,db);//bet
			sum_of_all_bets+=bet;//add the amount betted
			deal(round);//deal
			hold(round ,db.advice(currentplayer.hand));//Hold using the perfect strategy of the game variant
		}
		if(round==null) {//Check if at least one round was played
			System.out.println("No round was played...");
			System.exit(0);
		}
		total=round.getstats();//Get the total amount of deals done and print the statistics
		System.out.println("Total\t\t " + total);
		System.out.println("----------------------------");
		System.out.print("Credit\t\t " + currentplayer.getcredit() + " (");
		System.out.format("%.1f", 100 +((currentplayer.getcredit()-initialcredit)/sum_of_all_bets)*100);
		System.out.println("%)\n");	
		
		
	}

	@Override
	protected Round bet(int bet, GameVariant GV) {
		Round round;
		round = new Round(currentplayer,bet,GV);//Create a new round
		return round;
	}

	@Override
	protected void deal(Round round) {
		Playdeck= new Deck();//Create a new deal and shuffle it
		Playdeck.shuffle();
		round.receiveCards(Playdeck);//Receive the cards from the newly created deck		
	}

	@Override
	protected void hold(Round round, int[] hold) {
		round.replaceCards(hold,Playdeck);//Hold the cards dictated by the vector
		round.rewardPlayer();//get the player's reward
	}

}
