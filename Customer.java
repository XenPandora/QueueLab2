
public class Customer 
{
	private int items;
	//how long the customer waits in seconds
	int waitTime;
	//the customer's current time
	int execute;
	
	public Customer(int i)
	{
		items = i;
		waitTime = 0;
		execute = 0;
	}
	
	public void setItems(int i)
	{
		items = i;
	}
	
	public int getItems()
	{
		return items;
	}
	
	public void incrementWait()
	{
		waitTime++;
	}
	
	public void incrementExecution()
	{
		execute++;
	}
	
	public int getWait()
	{
		return waitTime;
	}
	
	public boolean canExit()
	{
		return execute == items*5;
	}
}
