package type;

import java.util.ArrayList;

import card.Card;
import card.Deck;
import casino.*;
import variants.*;

/**
 * Class that implements the Debug mode for VideoPoker game, reading and executing
 * the commands according to what the user asks using the Deck provided
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Debug extends Game{
	
	/**
	 * Debug constructor responsible to execute the brains of the Debug game mode
	 * @param credit - player's initial credit
	 * @param commands - list of commands to be executed by the program
	 * @param Deck - Custom deck provided by the player
	 */
	public Debug(int credit, String[] commands, ArrayList<Card> Deck){
		
		super(credit);//Super constructor
		int previousvalidbet=5, flag=0, bet=0, aux=0, total=0;
		double sum_of_all_bets=0;
		int initialcredit=credit;
		char charcommand=0;
		Playdeck = new Deck(Deck);//Create a Deck
		GameVariant db = new DoubleBonus();
		Round round=null;
		initialcredit=credit;
		for(int i=0;i<commands.length; i++) {//Go through all the commands provided by the user in the command file
			try{//Try to get command the user typed if it fails we try to go to the next command available
		        charcommand = commands[i].charAt(0);
			}
		    catch (StringIndexOutOfBoundsException ex){
		    	continue;
		    }
			
			
			switch(charcommand) {//Check which command was typed
			
				case 'b'://bet command
					aux=0;
					if(Playdeck.deckSize()==0) {//Check if there are still cards in the provided Deck
						System.out.println("Out of cards. You can't play more\n");
						break;
					}
					if(i<(commands.length-1))//Check if it's not the last command in the file
						if(commands[i+1].charAt(0)>48 && commands[i+1].charAt(0)<58){//if its not then we check if the user typed an amount to be betted
								aux = StringtoIntVerify(commands[i+1],"b: Invalid bet amount: ");
								i++;
						}
					System.out.println("-cmd b " + (aux==0? "":aux));//Aux = bet if it was typed 
					if(flag!=0) {//Checks if there isn't a round being played already
						System.out.println("b: Ilegal command\n");
						break;
					}
					if(aux!=0){//If the user typed a bet ammount
						if(aux<1 || aux>5) {//check if the bet amount is valid
							System.out.println("b: Ilegal amount\n");//invalid
							break;
						}
						bet=aux;
						previousvalidbet=bet;
					}
					else//No bet was typed, use the previous typed one or 5 if none was typed
						bet=previousvalidbet;
					flag=1;
					round = bet(bet,db);//Create a round
					sum_of_all_bets+=bet;//Sum the amount bet
					break;
				case '$'://Credit command
					System.out.println("-cmd $\n" + "Player's credit is " +super.currentplayer.getcredit()+"\n");
					break;
				case 'd'://Deal command
					System.out.println("-cmd d");
					if(flag!=1) {//We can only deal after a bet command was typed
						System.out.println("d: Ilegal command\n");
						break;
					}
					if(Playdeck.deckSize()<5) {//Check if we have 5 cards to be dealt to the player
						System.out.println("Not enought cards to continue playing!\n");
						break;
					}
					flag=2;//update flag that deal was done
					deal(round);
					break;
				case 'h'://Hold command
					int hold[] = {0,0,0,0,0};
					System.out.print("-cmd h ");
					if(flag!=2){//We can only hold after the deal command
						System.out.println("\nh: Ilegal command\n");
						break;
					}
					aux=i;
					while((commands.length>(aux+1)) && commands[aux+1].charAt(0)>48 && commands[aux+1].charAt(0)<58){//Check which cards the player wants us to hold
						int temp=StringtoIntVerify(commands[aux+1],"h: Ilegal positon\n")-1;
						if(temp<0 || temp>4) {//Check if it's a valid position
							System.out.println("\nh: Ilegal position\n");
							aux++;
							continue;
						}
						hold[temp]=1;
						System.out.print((temp+1) + " ");
						aux++;
					}
					System.out.println("");
					i=aux;
					hold(round, hold);
					flag=0;
					break;
				case 'a'://Advice command
					System.out.println("-cmd a");
					if(flag!=2){//We can only provide an advice after the deal command
						System.out.println("a: Ilegal command\n");
						break;
					}
					int aux1[] = round.advice();//Get advice from the round class
					if(aux1!=null) {//An advice was provided
						if(aux1[0]==0 && aux1[1]==0 && aux1[2]==0 && aux1[3]==0 && aux1[4]==0) {//Vector is all equal to 0
							System.out.println("Player should discard all cards\n");
							break;
						}
						//Some position is equal to 1 so we should hold those
						String buffer = new String("Player should hold cards: ");
						for(int k=0; k<5;k++)//Go through the vector
							if(aux1[k]==1)//Check which postions shoudl be kept
								buffer+=(k+1) + " ";
						System.out.println(buffer+"\n");
					}
					else
						System.out.println("Discard all");
					break;
				case 's'://Statistics command
					System.out.println("-cmd s");
					if(round==null) {//Atleas a round was played
						System.out.println("Unable to show statistics. No game has been played yet...\n");
						break;
					}
						total=round.getstats();//Get the total amount of played rounds and print stats
						System.out.println("Total\t\t " + total);
						System.out.println("----------------------------");
						System.out.print("Credit\t\t " + super.currentplayer.getcredit() + " (");
						System.out.format("%.1f", 100+((super.currentplayer.getcredit()-initialcredit))/sum_of_all_bets *100);
						System.out.println("%)\n");						
					break;
				default://No right command was typed
					System.out.println("-cmd " + commands[i].charAt(0) + "\nIlegal Command!\n");
			}
			
		}
	
	}
	/**
	 * Given a String with evaluates and tries to extract and Integer from it
	 * @param s String which can contain an integer
	 * @param err Error message to be printed to stdout
	 * @return value read
	 */
	protected static int StringtoIntVerify(String s, String err) {
		int newint = 0;
		try{//try to convert String to int
           newint = Integer.parseInt(s);
        }
        catch (NumberFormatException ex){//if it fails print error message and close program
            System.out.println(err + s);
            System.exit(-1);
        }
		return newint;
		
	}

	@Override
	protected Round bet(int bet, GameVariant GV) {
		System.out.println("Player is betting: "+bet+"\n");
		Round round = new Round(super.currentplayer,bet,GV);//Creates a new round 
		return round;
	}
	
	@Override
	protected void deal(Round round) {
		if(round!=null) {//If there is a round being played
			round.receiveCards(Playdeck);//Give the player the cards
			if(currentplayer.hand.sizeHand()!=5) {//Check if 5 cards were assigned
				return;
			}
			System.out.println("Player's hand "+super.currentplayer.hand.printableHand() +"\n");
		}
		
	}

	@Override
	protected void hold(Round round, int[] hold) {
		round.replaceCards(hold, Playdeck);//Replace the cards
		if(currentplayer.hand.sizeHand()!=5)//Check if there are 5 cards in the player's hand
			return;
		System.out.println("Player's hand: " + super.currentplayer.hand.printableHand());
		System.out.println(round.rewardPlayer());
		
	}

}
