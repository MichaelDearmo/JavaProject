/**
 * Program Name: iDeaStore.java
 * Purpose: 
 * Coder: Michael Dearmo 0679488
 * Date: Mar. 15, 2019
 * 
1.  Import java.text.NumberFormat to be able to display trailing zero
2.  Create variables
3.  Create Scanner
4.  Output messages that give instructions to the user and ask for their name
5.  Create a do while loop that processes user input
6.  The do while loop validates the input of the user to make sure it is in the specified range
7.  Calculate HST
8.  Calculate the total before rounding
9.  Rounded total cost to nearest nickel
10. Invoke method inside of a do-while loop that displays how much change they received and the breakdown of bills and coins.
11. Close Scanner
12. Create method that removes a specified element from the array list
13. Create method that calculates the HST on the total purchase amount
14. Create method that rounds to the nearest 0.05
15. Create method that calculates the change and displays how many bills and coins the user will receive
 */
package iDeaStore;
import java.text.NumberFormat;
import java.util.*;

public class iDeaStore
{

	public static void main(String[] args)
	{
		//Allows output to display trailing zero
		Locale locale = Locale.ENGLISH;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setMinimumFractionDigits(2);
		//create variables
		String userName;
		boolean flag = true;
		ArrayList<Double> itemCost = new ArrayList<Double>();
		int counter = 1;
		int counter2 = 0;
		double itemPrice = 0;
		double totalPrice = 0;
	 //Create Scanner
		Scanner myInput = new Scanner(System.in);
	//Output messages that give instructions to the user and ask for their name
	 System.out.println("+------------------------------------+");
	 System.out.println("|    iDea Checkout System Ver 1.0    |");
	 System.out.println("+------------------------------------+");

	 System.out.print("Hi! Welcome to the iDea store. Please enter your name ");
	 userName = myInput.nextLine();
	 System.out.println("\nOK, " + userName + ", enter the price of each purchase in dollars and cents and hit the ENTER key.");
	 System.out.println("For example, if your item costs $5.99 you would enter 5.99");
	 System.out.println("\nIf you make a mistake when you entere a price enter a zero for the next entry.");
	 System.out.println("The last price you entered will be subtracted from your subtotal");
	 System.out.println("\nWhen you've entered all the amounts please enter -1 to indicate you are done");
	 System.out.println("I'll then caluclate your total owing");
	 System.out.println("\nLets begin!");
	 
	 //Create a do while loop that processes user input
	 //The do while loop validates the input of the user to make sure it is in the specified range
	 do
	 {
		 System.out.print("Please enter the cost of item #" + counter + " ");
		 itemPrice = myInput.nextDouble();
		 if(itemPrice > 0.05 && itemPrice < 179.99)
		 {
			 itemCost.add(itemPrice);
			 totalPrice += itemPrice;
			 System.out.println("That was $" + nf.format(itemPrice) + ". Your sub-total is $" + nf.format(totalPrice));
			 System.out.println();
			 counter ++;
			 counter2 ++;
			 flag = false;
		 }
		 else if(itemPrice == -1)
		 {
			 System.out.println();
			 System.out.println("Okay " + userName + " your individual purchase prices are:");
				for (int count = 0; count <= itemCost.size() - 1; count++)
				{
					System.out.println("$" + nf.format(itemCost.get(count)));
				}
				System.out.println("Your sub-total is $" + nf.format(totalPrice));
			 flag = true;
		 }
		 else if(itemPrice == 0)
		 {
			 totalPrice -= itemCost.get(counter2 - 1);
			 double subTotal = Math.round(totalPrice *100.0)/100.0;
			 System.out.println("Zero entered: removing last item " + itemCost.get(counter2 - 1) + "\nYour subtotal is now " + nf.format(subTotal));
		   removeLastEntry(itemCost, (counter2 - 1));
			 counter --;
			 counter2 --;
			 flag = false;
		 }
		 else
		 {
			 System.out.println("INVALID PRICE PLEASE RE-ENTER");
			 flag = false;
		 }
		
	 }while(!flag);
//calculate HST
	 double hstCost = calculateHST(totalPrice);
	 hstCost = Math.round(hstCost * 100.0)/100.0;
	 System.out.println();
	 System.out.println("The HST on your purchase is $" + nf.format(hstCost));
//calculate the total before rounding
	 double notRounded = hstCost + totalPrice;
//rounded total cost to nearest nickel
	 double totalPriceHst = totalOwingToNearestFive(totalPrice,hstCost);
	 System.out.println();
	 System.out.println("Total cost is $" + nf.format(notRounded) + " rounded to the nearest nickel is $" + nf.format(totalPriceHst));
	

//invoke method inside of a do-while loop that displays how much change they received and the breakdown of bills and coins.
	 boolean flag1 = true;
	 do {
		 System.out.print("Please enter the amount of money you will be paying with: ");
		 double payment = myInput.nextDouble();
		 if(payment > totalPriceHst)
		 {
	
			 System.out.println(calculateChange(totalPriceHst, payment));
			 System.out.println("Please check that you recieved the correct change");
			 System.out.println("\nThank you " + userName + " for shopping at the iDea store!");
			 flag1 = true;
		 }
		 else
		 {
			 System.out.println(calculateChange(totalPriceHst, payment));
			 System.out.println("Please try again\n");
			 flag1 = false;
		 }
	 }while(!flag1);

//close Scanner
	 myInput.close();
	}//end main

//Method that removes a specified element from the array list
	public static void removeLastEntry(ArrayList<Double>Array , int removedElement)
	{
		Array.remove(removedElement);
	}
//Method that calculates the HST on the total purchase amount
	public static double calculateHST(double totalPrice)
	{
		final double HST = 0.13;
		return totalPrice *= HST;
	}
//Method that rounds to the nearest 0.05
	public static double totalOwingToNearestFive(double totalPrice, double hst)
	{
		double roundedToFive = totalPrice + hst;
		roundedToFive = Math.round(roundedToFive * 20.0)/20.0;
		return roundedToFive;
	}
  //Method that calculates the change and displays how many bills and coins the user will recieve
	public static String calculateChange(double cost, double cashGiven)
	{
		//Allows output to display trailing zero
		Locale locale = Locale.ENGLISH;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setMinimumFractionDigits(2);
		//Variables
		int twenties = 0;
		int tens = 0;
		int fives = 0;
		int toonie = 0;
		int loonie = 0;
		int quarter = 0;
		int dime = 0;
		int nickel = 0;
		double change = cashGiven - cost;
		//Convert dollar amount into cents to maintain precision
		int cents = (int)Math.round(100*change);
		String error = "\nThat is not a sufficient amount of money";
		String bills = "";
		if(cashGiven > cost)
		{
		System.out.println("From $" + nf.format(cashGiven) + " your change is $" + nf.format(change));
		if(cents >= 2000)
		{
			twenties = cents / 2000;
			cents = cents % 2000;
		}
		if(cents >= 1000)
		{
			tens = cents / 1000;
			cents = cents % 1000;
		}
		if(cents >= 500)
		{
			fives = cents / 500;
			cents = cents % 500;
		}
		if(cents >= 200)
		{
			toonie = cents / 200;
			cents = cents % 200;
		}
		if(cents >= 100)
		{
			loonie = cents / 100;
			cents = cents % 100;
		}
		if(cents >= 25)
		{
			quarter = cents / 25;
			cents = cents % 25;
		}
		if(cents >= 10)
		{
			dime = cents / 10;
			cents = cents % 10;
		}
		if(cents >= 5)
		{
			nickel = cents / 5;	
		}
		if(twenties > 0)
		{
			bills = "\n" + twenties + " $20 dollar bill(s)";
		}
		if(tens > 0)
		{
			bills = bills + "\n" + tens + " $10 dollar bill(s)";
		}
		if(fives > 0)
		{
			bills = bills + "\n" + fives + " $5 dollar bill(s)";
		}
		if(toonie > 0)
		{
			bills = bills + "\n" + toonie + " Toonie(s)";
		}
		if(loonie > 0)
		{
			bills = bills + "\n" + loonie + " Loonie(s)";
		}
		if(quarter > 0)
		{
			bills = bills + "\n" + quarter + " Quarter(s)";
		}
		if(dime > 0)
		{
			bills = bills + "\n" + dime + " Dime(s)";
		}
		if(nickel > 0)
		{
			bills = bills + "\n" + nickel + " Nickel(s)";
		}
		return bills;
		}
		else
		{
			
			return error;
		}
	}
}
//end class