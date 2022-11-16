package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import card.Card;
import type.*;

/**
 * Main class of Video poker, it will receive the arguments from the terminal and
 * play accordingly to the game mode selected (currently available: Debug and Simulation)
 * @author Eduardo Barata 99930, André Roque 86694
 *
 */
public class Main {
	
	/**
	 * Main Class of the project it receives the arguments provided by the user in the terminal
	 * and starts either a debug or simulation mode or, in case of a wrong usage of the command
	 * it will display a help message
	 * @param args Command line passed arguments
	 */
	public static void main(String[] args) {
				
		if(args == null || args.length < 4 || args[0].charAt(0)!='-'){//User should type 4 arguments and the 1st one should start with a '-' for the option
			System.out.println("Wrong usage...");
			help();
		}
		
		int credit = StringtoIntVerify(args[1],"Invalid credit amount ");//Get credit typed in the arguments
		if(args[0].length()!=2) {//confirm we have only 2 character a '-' and either 'd' or 's'
			System.out.println("Wrong usage...");
			help();
		}
		switch(args[0].charAt(1)) {//Check which option was typed
			case 'd'://Debug option
				String buffer = new String(), cards[]=null, commands[]=null;
				try {//Try to open the command file and read it's contents to a String buffer  
					File file=new File(args[2]);   
					FileInputStream FIS=new FileInputStream(file);  
					int r=0;  
					while((r=FIS.read())!=-1)  //iterate through the file
					{  
						buffer += (char)r; //Add read character to the String buffer
					}
					if(FIS!=null)//if there ever was a file close it
						FIS.close();
				}  
				catch(Exception e){  //Error in reading the file
					System.out.println("Command file specifed does not exit!");
					System.exit(-1);
				}
				commands=buffer.split("\\s+"); //divides the String into a vector of Strings
				
				buffer = "";
				try{//Try to open the card file and read it's contents to a String buffer  
					File file=new File(args[3]);   
					FileInputStream FIS=new FileInputStream(file);  
					int r=0;  
					while((r=FIS.read())!=-1)  //iterate through the file
					{  
						buffer += (char)r; //Add read character to the String buffer
					}
					if(FIS!=null)//if there ever was a file close it
						FIS.close();
				}  
				catch(Exception e){  //Error in reading the file
					System.out.println("Card file specifed does not exit!");
					System.exit(-1);
				}
				cards=buffer.split("\\s+"); //divides the String into a vector of Strings
				
				ArrayList<Card> newdeck = new ArrayList<Card>();//Create a deck to hold the cards
				int suit=-1, rank=-1;
				
				for(int i=0;i<cards.length;i++) {//Go through the read card file and create the card objects
					if(cards[i].length()!=2) {//Cards should have exactly 2 of length -> RankSsuit
						System.out.println("Card file contains errors!");
						System.exit(-1);
					}
					switch(cards[i].charAt(0)){//Check card rank and convert it
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							rank=Character.getNumericValue(cards[i].charAt(0))-1;  
							break;
						case 'T': rank=9; break;
						case 'J': rank=10; break;
						case 'Q': rank=11; break;
						case 'K': rank=12; break;
						case 'A': rank=0; break;
						default:
							System.out.println("Invalid rank in card file: " + cards[i].charAt(0));
							System.exit(-1);
					}
					switch(cards[i].charAt(1)){//Check card suit and convert it
						case 'C': suit=0; break;
						case 'D': suit=1; break;
						case 'H': suit=2; break;
						case 'S': suit=3; break;
						default:
							System.out.println("Invalid Suit in card file: " + cards[i].charAt(1));
							System.exit(-1);
					}
					
					newdeck.add(new Card(suit,rank));//Add card to the deck
				}
				new Debug(credit,commands,newdeck);//Create a new Debug object game
				break;
			case 's'://Simulation mode
				int bet = StringtoIntVerify(args[2],"Invalid bet amount ");
				if(bet<1 || bet>5) {//Check if the bet amount typed is valid
					System.out.println("Bet amount should be between 1 and 5, inserted bet amount: "+ bet);
					System.exit(-1);
				}
				int nbDeals = StringtoIntVerify(args[3],"Invalid number of deals: ");
				if(nbDeals<0){//Check if the number of deals asked by the user is valid
					System.out.println("Number of Deals should be a positive integer! You inserted: "+ nbDeals);
					System.exit(-1);
				}
				if(credit<0){//Check if the player has a valid number of credit
					System.out.println("Player credit should be a positive integer! You inserted: "+ credit);
					System.exit(-1);
				}
				new Simulation(credit,bet,nbDeals);//Create a new Simulation object game
				break;
			default:
				System.out.println("Unavailable game mode!");
				help();
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
	/**
	 * Displays a help message for the user
	 */
	private static void help() {
		System.out.println("Help command:");
		System.out.println(" Debug mode:");
		System.out.println("\tjava -jar videopoker.jar -d credit cmd-file card-file");
		System.out.println(" Simulation mode:");
		System.out.println("\tjava -jar videopoker.jar -s credit bet nbdeal");
		System.out.println(" Help:");
		System.out.println("\tjava -jar -h");
		System.exit(-1);
	}

}
