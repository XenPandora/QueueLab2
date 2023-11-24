import java.util.Queue;
import java.util.LinkedList;

public class Department 
{
	//ID number of the department
	private int departmentID;
	//people in the department
	private Queue<Person> executives;
	//number of people in the department, used to calculate salaries
	private int size;
	
	public Department(int n)
	{
		departmentID = n;
		executives = new LinkedList<Person>(); 
		size = 0;
	}
	
	//returns department ID number
	public int getID()
	{
		return departmentID;
	}
	
	//finds the first person in the department and returns it
	public Person peekWorker()
	{
		return executives.peek();
	}
	
	//removes the fist person in the department and returns it
	public Person pollWorker()
	{
		return executives.poll();
	}
	
	//Byron, once again I'm sorry if you are seeing this method name
	//returns the worker that is in the queue that hs the same number as the input and removes it
	public Person kidnap(int worker)
	{
		Queue<Person> temp = new LinkedList<Person>();
		Person victim = null;
		while(!executives.isEmpty())
		{
			if(executives.peek().getExecNum() == worker)
			{
				victim = executives.poll();
			}
			temp.offer(executives.poll());
		}
		executives = temp;
		return victim;
	}
	
	public boolean searchWorker(int worker)
	{
		Queue<Person> temp = new LinkedList<Person>();
		boolean result = false;
		while(!executives.isEmpty())
		{
			if(executives.peek().getExecNum() == worker)
			{
				result = true;
			}
			temp.offer(executives.poll());
		}
		executives = temp;
		
		return result;
	}
	
	//assigns everyone in the department a salary
	//starts at the highest and reduces by 5000
	public void payRoll()
	{
		Queue<Person> temp = new LinkedList<Person>();
		int salary = 40000 + (5000*(size-1));
		//sets the salary of the workers and puts them in the temp queue
		while(!executives.isEmpty())
		{
			executives.peek().setSalary(salary);
			salary -= 5000;
			temp.offer(executives.poll());
		}
		executives = temp;
	}
	
	//Byron if you're reading the name of this method, I'm sorry
	//join method that adds a person to the queue of executives
	//name is skinned to enslave
	public void enslave(Person person)
	{
		executives.offer(person);
		size++;
	}
	
	public void quit(int quitter)
	{
		Queue<Person> temp = new LinkedList<Person>();
		while(!executives.isEmpty())
		{
			if(executives.peek().getExecNum() == quitter)
			{
				System.out.println("Worker " +executives.peek().getExecNum() +" has left and will never return");
				executives.poll();
				size--;
			}
			else
			{
				temp.offer(executives.poll());
			}
		}
		executives = temp;
	}
	
	public void displayWorkers()
	{
		System.out.println("\nDepartment idNum: " +departmentID +" Employees:" );
		Queue<Person> temp = new LinkedList<Person>();
		while(!executives.isEmpty())
		{
			executives.peek().getEmployeeStats();
			temp.offer(executives.poll());
		}
		executives = temp;
	}
}
