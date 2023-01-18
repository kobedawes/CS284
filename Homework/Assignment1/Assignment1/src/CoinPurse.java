import java.util.Arrays;


public class CoinPurse {

	private int galleons;
	private int sickles;
	private int knuts;
	private int space;
	private int type;
	private static final int GTK_CONV = 493; ///GALLEON TO KNUTS
	private static final int STK_CONV = 29; ///SICKLE TO KNUTS
	private final int  LIMIT = 256;
	static int [][] emptyArr = {{0, 0}, {0, 1}, {0, 2}};
	
	public CoinPurse() {
		
		this.galleons = 0;
		this.sickles = 0;
		this.knuts = 0;
		this.space = LIMIT - numCoins();
	}
	
	public CoinPurse(int g, int s, int k) {
		
		if ( g < 0 || s < 0|| k < 0 ) {
			throw new IllegalArgumentException(
					"Cannnot add negative coins");
		}
		
		if (LIMIT < (g + s + k)) {
			throw new IllegalArgumentException(
					"Too many coins must be less than " + LIMIT);
		}
		
		else {
			
			this.galleons = g;
			this.sickles = s;
			this.knuts = k;
			this.space = LIMIT - numCoins();
		}
	}
	
	public boolean isLegalDeposit(int coinAmnt) {
		
		if (this.space < coinAmnt) {
			throw new IllegalArgumentException(
					"Too many coins add less than or equal to " + space);
		
		}
		
		if (coinAmnt < 0 ) {
			throw new IllegalArgumentException(
					"Cannot add negative coins");
		}
		
		else {
			return true;
		}
	}
	public boolean withdraw(int coinAmnt, int type) {
		
		if (coinAmnt < 0 ) {
			
			throw new IllegalArgumentException(
					"Cannnot add negative coins");
		}
		
		if (type == 0 || type == 1 || type == 2) {
			
			switch (type) {
			
			
			case 0://galleon
				if (this.galleons < coinAmnt) {
					throw new IllegalArgumentException(
							"Overdraft! You only have " + galleons + " galleons in the bank");
				}	
				else {
					this.galleons -= coinAmnt;
					return true;
				}
				
				
			case 1: //sickles
				if (this.sickles < coinAmnt) {
					throw new IllegalArgumentException(
							"Overdraft! You only have " + sickles + " sickles in the bank");
				}
				else {
					this.sickles -= coinAmnt;
					return true;
				}

				
			case 2: //knuts
				if (this.knuts < coinAmnt) {
					throw new IllegalArgumentException(
							"Overdraft! You only have " + knuts + " knuts in the bank");
				}
				else {
					
					this.knuts-= coinAmnt;
					return true;
				}
			}
			return false;
		}
		else {
			return false;
		}
		
	}
		
		
	
	public void deposit(int amnt, int coinType) {
		
		this.type = coinType;
		
		if (isLegalDeposit(amnt) == true) {
			try {
				
				switch (type){
					case 0: //galleon
						this.galleons += amnt;
						break;
					case 1: //sickles
						this.sickles += amnt;
						break;
					case 2: //knuts
						this.knuts += amnt;
						break;
				}
			}
			
			catch (IllegalArgumentException dep) {
				System.out.println(dep.getMessage());
			}
			
		}
	}
	
	
	public void depositGalleon(int g) {
	
		deposit(g, 0);
	}
	
	public void depositSickles(int s) {
			
		deposit(s, 1);
	}
	
	public void depositKnuts(int k) {
		
		deposit(k, 2);
	}
	
	public void withdrawGalleon(int g) {
		
		withdraw(g, 0);
	}
	
	public void withdrawSickles(int s) {
			
		withdraw(s, 1);
			
	}
	
	public void withdrawKnuts(int k) {
		
		withdraw(k, 2);
	}
	
	public int numCoins() {
		int numCoins = galleons + sickles + knuts;
		return numCoins;
	}
	
	
	public int totalValue() {
		int total = (galleons * GTK_CONV) + (sickles * STK_CONV) + knuts;
		return total;
	}
	
	public static int totalValue(int[][] data) {
		/**takes in a integer array in the for of {amount, type} 
		 * where type 0, 1, 2 is  galleon, sickles, and knuts respectively
		 */
		int total = 0;
		for (int i[]: data) {
			
			switch (i[1]) {
				case 0:
					total += i[0] * GTK_CONV;//galleon amount in knuts
					break;
				case 1:
					total += i[0] * STK_CONV; //sickles amount in knuts
					break;
				case 2:
					total += i[0];
					break;	
			}
		}
		//System.out.println("Your Total is " + total);
		return total;
	}
	
	public int [][] exactChangeArr(int i, int [][] arr) {
		
		int cGalleons = galleons;
		int cSickles = sickles;
		int cKnuts = knuts;
		int change = i;
		
		
		if (cGalleons >= (change / GTK_CONV)) {
			
			arr[0][0] += change / GTK_CONV;
			change %= GTK_CONV;
			
		}
		
		else {
			
			arr[0][0] += cGalleons;
			change -= cGalleons* GTK_CONV;
			
		}
		
		if (cSickles >= (change / STK_CONV)) {
			
			arr[1][0] += change / STK_CONV;
			change %= STK_CONV;
		}
		
		else {
			
			arr[1][0] += cSickles;
			change -= cSickles * STK_CONV;
		}
		
		if (change > 0 && cKnuts >= change) {
			
			arr[2][0] += change;
			change -= change;
		}
		
		else {
			
			change -= cKnuts;
			arr[2][0] += change;
		}
		
		if (change == 0) {
		
			return arr;
		}
		
		return arr;
	}	
	
	public boolean exactChange(int i) {
		int[][] empArr = {{0, 0}, {0, 1}, {0, 2}};
		
		if (totalValue(exactChangeArr(i, empArr)) == i) {
			
			return true;
		
		}
		return false;
	}
	
	public boolean inRange(int [] array) {
		
		if (array[0] <= galleons && array[1] <= sickles && array[2] <= knuts) {
			return true;
		}
		return false;
	}
	
	public String withdraw(int n) {
		int[][] empArr = {{0, 0}, {0, 1}, {0, 2}};
	 
		int [][] array = exactChangeArr(n, empArr);
		int [] list = {array[0][0], array[1][0], array[2][0]};
		int [] list2 = {array[0][0], array[1][0] + 1, 0};
		
		if (totalValue(array) == n) {
			
			withdrawGalleon(list[0]);
			withdrawSickles(list[1]);
			withdrawKnuts(list[2]);
			return Arrays.toString(list);
		}
		
		else if (n < totalValue() && inRange(list2) == true) {
			
			withdrawGalleon(list2[0]);
			withdrawSickles(list2[1]);
			withdrawKnuts(list2[2]);
			return Arrays.toString(list2);
		}
		
		else {
			
			throw new IllegalArgumentException("Not enough coins");
		}
	}
		
	
	public String toString() {
		return this.galleons + " galleons " + this.sickles + " sickles " + this.knuts + " knuts";
	}
	
	
	public static void main(String[] args) {
		
		try {
			
			CoinPurse c3 = new CoinPurse(2, 5, 10);
			System.out.println(c3.withdraw(559));
			c3.withdrawGalleon(1);
			c3.depositSickles(20);
			System.out.println(c3);
			
			
			CoinPurse c4 = new CoinPurse(2, 5, 10);
			System.out.println(c4.withdraw(564));
			System.out.println(c4);
		}
		
		catch (IllegalArgumentException init) {
			System.out.println(init.getMessage());
		}
	}
	
}