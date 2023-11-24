import java.util.Queue;

import java.util.LinkedList;
public class Counters 
{
	private Queue<Customer> people;
	
	//keeps track of total customers
	private int total;
	//keeps track of the maximum amount of people in a counter
	private int maxSize;
	//keeps track of the current number of people in a counter
	private int size;
	//keeps track of total wait time
	private int totalWait;
	//keeps track of items processed in each line
	private int totalItems;

	
	public Counters()
	{
		people = new LinkedList<Customer>();
		total = 0;
		maxSize = 0;
		size = 0;
		totalWait = 0;
		totalItems = 0;
	}
	
	public void addCustomer(Customer victim)
	{
		people.offer(victim);
		total++;
		size++;
	}
	
	public void removeCustomer()
	{
		people.poll();
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getTotalWait()
	{
		return totalWait;
	}
	
	public int getTotalItems()
	{
		return totalItems;
	}
	
	public boolean isEmpty()
	{
		return people.isEmpty();
	}
	
	public Customer getFirst()
	{
		return people.peek();
	}
	
	//method used when removing a person from a queue
	//edits maxSize if the current size is larger
	//increments totalWait by how long a customer had to wait
	//increments totalItems by how many items a customer processed
	public Customer removeFirst()
	{
		if(maxSize < size)
		{
			maxSize = size;
		}
		
		size--;
		
		totalWait += people.peek().getWait();
		totalItems += people.peek().getItems();
		
		return people.poll();
	}
}
