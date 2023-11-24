import java.util.Random;
public class SuperMarket
{
	// Data Fields
	//regular customer counter
	private Counters[] regularCounter;
	//express counters
	private Counters[] expressCounter;
	//super express counter
	private Counters superExpressCounter;
	/** Simulated clock. */
	private int clock;
	/** Time that the agent will be done with the current passenger.*/ 
	private int timeDone;
	//number of items allowed to be in the super express queue
	private int numSuper;
	//number of items allowed to be in the express queue
	private int numExp;
	//number of express queues
	private int numExpLines;
	//number of regular queues
	private int numStandLines;
	//arrival rate of customers
	private int arrivalRate = 5;
	//maximum number of items a customer is allowed
	private int maxItems;
	
	//Statistics to keep track of
	//total customers that entered the store
	private int customerCount;
	//total number of items processed in the store
	private int itemCount;
	//total free time of the simulation
	private int freeTime;

	public void engine()
	{
		Random rand = new Random();
		//Create lines
		for(int x = 0; x<numStandLines; x++)
		{
			regularCounter[x] = new Counters();
		}
		for(int x = 0; x<numExpLines; x++)
		{
			expressCounter[x] = new Counters();
		}
		
		//increments time on everyone
		boolean done = false;
		while(!done)
		{
			//announces current time
			System.out.println("Clock: " +clock);
			
			//run 1 second of the simulation
			clock++;
			
			//decides whether or not to add a new customer
			int chance = rand.nextInt(1, 10);
			if(chance > arrivalRate)
			{
				//New customer enters line
				customerCount++;
				int items = rand.nextInt(1, maxItems);
				itemCount+=items;
				Customer temp = new Customer(items);
				putInLine(temp);
			}
			
			boolean somethingHappened = false;
			
			if(!superExpressCounter.isEmpty())
			{
				if(canExit(superExpressCounter.getFirst()))
				{
					System.out.println("A customer with " +superExpressCounter.removeFirst().getItems() +" has left the super express counter");
					somethingHappened = true;
				}
			}

			for(int x = 0; x<numExpLines; x++)
			{
				if(!expressCounter[x].isEmpty())
				{
					if(canExit(expressCounter[x].getFirst()))
					{
						System.out.println("A customer with " +expressCounter[x].removeFirst().getItems() +" has left express counter number " +(x+1));
						somethingHappened = true;
					}
				}
			}
			
			for(int x = 0; x<numStandLines; x++)
			{
				if(!regularCounter[x].isEmpty())
				{
					if(canExit(regularCounter[x].getFirst()))
					{
						System.out.println("A customer with " +regularCounter[x].removeFirst().getItems() +" has left standard counter number " +(x+1));
						somethingHappened = true;
					}
				}
			}
			
			if(!somethingHappened)
			{
				freeTime++;
			}
			
			if(clock > timeDone)
			{
				done = true;
			}
		}
		
		
		System.out.println("\nStatistics: ");
		
		printStats();
	}
	
	private void printStats()
	{
		System.out.println("Average waiting time for each line: ");
		int superWait = superExpressCounter.getTotalWait()/superExpressCounter.getTotal();
		System.out.println("Super express line: " +superWait);
		for(int x = 0; x<numExpLines; x++)
		{
			int avgWait = expressCounter[x].getTotalWait() / expressCounter[x].getTotal();
			System.out.println("Express counter number " +(x+1) +": " +avgWait);
		}
		for(int x = 0; x<numStandLines; x++)
		{
			int avgWait = regularCounter[x].getTotalWait() / regularCounter[x].getTotal();
			System.out.println("Regular counter number " +(x+1) +": " +avgWait);
		}		
		int generalWait = superWait;
		for(int x = 0; x<numExpLines; x++)
		{
			int avgWait = expressCounter[x].getTotalWait() / expressCounter[x].getTotal();
			generalWait +=avgWait;
		}
		for(int x = 0; x<numStandLines; x++)
		{
			int avgWait = regularCounter[x].getTotalWait() / regularCounter[x].getTotal();
			generalWait +=avgWait;
		}
		generalWait = generalWait / (1 + numExpLines + numStandLines);
		System.out.println("Average waiting time in general: " +generalWait);
		
		
		
		
		System.out.println("Max length of each line: ");
		System.out.println("Super express line: " +superExpressCounter.getMaxSize());
		for(int x = 0; x<numExpLines; x++)
		{
			System.out.println("Express counter number " +(x+1) +": " +expressCounter[x].getMaxSize());
		}
		for(int x = 0; x<numStandLines; x++)
		{
			System.out.println("Regular counter number " +(x+1) +": " +regularCounter[x].getMaxSize());
		}
		
		
		
		System.out.println("Number of customers for each line: ");
		System.out.println("Super express line: " +superExpressCounter.getTotal());
		for(int x = 0; x<numExpLines; x++)
		{
			System.out.println("Express counter number " +(x+1) +": " +expressCounter[x].getTotal());
		}
		for(int x = 0; x<numStandLines; x++)
		{
			System.out.println("Regular counter number " +(x+1) +": " +regularCounter[x].getTotal());
		}
		System.out.println("Number of customers in general: " +customerCount);
		
		
		
		System.out.println("Number of items processed in each line: ");
		System.out.println("Super express line: " +superExpressCounter.getTotalItems());
		for(int x = 0; x<numExpLines; x++)
		{
			System.out.println("Express counter number " +(x+1) +": " +expressCounter[x].getTotalItems());
		}
		for(int x = 0; x<numStandLines; x++)
		{
			System.out.println("Regular counter number " +(x+1) +": " +regularCounter[x].getTotalItems());
		}
		System.out.println("Items processed in general: " +itemCount);
		
		
		
		System.out.println("Free time for each counter: ");
		int superFree = timeDone - superExpressCounter.getTotalWait();
		System.out.println("Super express line: " +superFree);
		for(int x = 0; x<numExpLines; x++)
		{
			int free = timeDone - expressCounter[x].getTotalWait();
			System.out.println("Express counter number " +(x+1) +": " +free );
		}
		for(int x = 0; x<numStandLines; x++)
		{
			int free = timeDone - regularCounter[x].getTotalWait();
			System.out.println("Regular counter number " +(x+1) +": " +free );
		}
		
		System.out.println("Overall free time: " +freeTime);
	}

	//helper method that puts a customer in a line
		public void putInLine(Customer victim)
		{
			if(victim.getItems() < numSuper)
			{
				superExpressCounter.addCustomer(victim);
				System.out.println("A customer with " +victim.getItems() +" items has entered the super express counter");
			}
			else if(victim.getItems() < numExp)
			{
				if(expressCounter[0].getSize() < expressCounter[1].getSize())
				{
					expressCounter[0].addCustomer(victim);
					System.out.println("A customer with " +victim.getItems() +" items has entered the first express counter");
				}
				else
				{
					expressCounter[1].addCustomer(victim);
					System.out.println("A customer with " +victim.getItems() +" items has entered the second express counter");
				}
			}
			else
			{
				Counters smallest = regularCounter[0];
				for(int x = 1; x<regularCounter.length; x++)
				{
					if(smallest.getSize() > regularCounter[x].getSize())
					{
						smallest = regularCounter[x];
					}
						
				}
				smallest.addCustomer(victim);
				System.out.println("A customer with " +victim.getItems() +" items has entered a regular counter");
			}
		}

		private boolean canExit(Customer victim)
		{
			if(victim.canExit())
			{
				return true;
			}
			else
			{
				victim.incrementExecution();
				victim.incrementWait();
				return false;
			}
		}
		
	public void enterData()
	{
		numStandLines = 3;
		numExpLines = 2;
		superExpressCounter = new Counters();
		//creates an array of regular customer queues with the the length of numStandLines
		regularCounter = new Counters[numStandLines];
		//creates an array of 2 express queues
		expressCounter = new Counters[numExpLines];
		clock = 0;
		numSuper = 3;
		numExp = 5;
		maxItems = 10;
		timeDone = 3600;
		
		
		//enter starting statistics
		customerCount = 0;
		itemCount = 0;
		freeTime = 0;
	}

	public static void main(String args[]) 
	{	
		SuperMarket sim = new SuperMarket();
		sim.enterData();
		sim.engine();
		System.exit(0);
	}
}
