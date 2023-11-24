import java.util.Queue;

import java.util.LinkedList;
import java.util.Scanner;

public class Office
{
	//total departments in the office
	private Queue<Department> departments;
	//Queue of workers waiting to be placed in a department
	private Queue<Person> workers;
	//Number of workers in the office (used for execNum in Person class)
	private int workerNum;
	//Number of departments in the office (used for departmentID in Department class)
	private int departmentNum;
	
	public Office()
	{
		departments = new LinkedList<Department>();
		workers = new LinkedList<Person>();
		workerNum = 0;
		departmentNum = 0;
	}
	
	//returns the number of departments in the office
	public int getDepartmentNum()
	{
		return departmentNum;
	}
	
	//returns the number of total number of workers
	public int getWorkerNum()
	{
		return workerNum;
	}
	
	public int getDepartmentSize()
	{
		return departments.size();
	}
	
	//checks the worker queue to see if there are no workers
	public boolean checkWorkerQueue()
	{
		return workers.isEmpty();
	}
	
	//peeks the worker queue
	public Person peekWorker()
	{
		return workers.peek();
	}
	
	//removes the first worker from the worker queue and returns it
	public Person pollWorker()
	{
		return workers.poll();
	}
	
	//returns the department
	//user inputs the department number they want
	public Department returnDepartment(int num)
	{
		Queue<Department> temp = departments;
		while(!temp.isEmpty())
		{
			if(temp.peek().getID() == num)
			{
				break;
			}
			else
			{
				temp.poll();
			}
		}
		return temp.peek();
	}
	
	public String displayDepartments()
	{
		String output = "| ";
		for(int x = 0; x<departmentNum; x++)
		{
			output = output +x  +" | ";
		}
		return output;
	}
	
	//displays the incomes of all the workers in all departments
	public void displayWorkerStats()
	{
		Queue<Department> temp = new LinkedList<Department>();
		while(!departments.isEmpty())
		{
			departments.peek().payRoll();
			departments.peek().displayWorkers();
			temp.offer(departments.poll());
		}
		departments = temp;
	}
	
	//adds 1 department to the department queue and increments 1 on departmentNum
	public void add()
	{
		Department department = new Department(departmentNum);
		departments.offer(department);
		System.out.println("Department " +department.getID() +" created");
		departmentNum++;
	}
	
	//adds 1 worker to the worker queue and increments 1 on workerNum
	public void hire()
	{
		Person victim = new Person(workerNum);
		workers.offer(victim);
		System.out.println("Worker " +victim.getExecNum() +" created");
		workerNum++;
	}
	
	//assigns a worker from the worker queue to a specific department
	public void join(int department)
	{
		Queue<Department> tempDepartment = new LinkedList<Department>();
		Person convict = workers.poll();
		while(!departments.isEmpty())
		{
			if(departments.peek().getID() == department)
			{
				departments.peek().enslave(convict);
			}
			tempDepartment.offer(departments.poll());
		}
		departments= tempDepartment;
	}
	

	//changes a worker from one department to another
	public void change(int victim, int loser)
	{
		Queue<Department> tempDepartment = new LinkedList<>();
		//first runs through the entire department to find the worker and put them back into worker queue
		while(!departments.isEmpty())
		{
			if(departments.peek().searchWorker(victim))
			{
				workers.offer(departments.peek().kidnap(victim));
			}
			tempDepartment.offer(departments.poll());
		}
		departments = tempDepartment;
		
		//reassigns worker to desired department
		join(loser);
	}
	
	public void fire(int victim)
	{
		Queue<Department> tempDepartment = new LinkedList<>();
		while(!departments.isEmpty())
		{
			if(departments.peek().searchWorker(victim))
			{
				departments.peek().quit(victim);
			}
			tempDepartment.offer(departments.poll());
		}
		departments = tempDepartment;
	}
	
	//this is the actual simulation
	public void runSim()
	{
		Scanner scan = new Scanner(System.in);
		Office office = new Office();
		System.out.println("Welcome to a human rights violation \nWe do not have any departments, please input the number of departments you want");
		int officeNum = scan.nextInt();
		//populates departments queue
		for(int x = 0; x<officeNum; x++)
		{
			office.add();
		}
		System.out.println("We have " +office.getDepartmentNum() +" Departments");
		System.out.println("We do not have any workers, please enter number of workers");
		int workerNum = scan.nextInt();
		//populates workers queue
		for(int x = 0; x<workerNum; x++)
		{
			office.hire();
		}
		System.out.println("We have " +office.getWorkerNum() +" Workers");
		System.out.println("We now need to put these workers in their own departments");
		//puts workers in departments
		//empties out the workers queue
		while(!office.checkWorkerQueue())
		{
			System.out.println("Which department should we put worker number " +office.peekWorker().getExecNum() +" ?");
			System.out.println("Options: " +office.displayDepartments());
			int department = scan.nextInt();
			office.join(department);
		}
		System.out.println("Viewing current department stats:");
		
		office.displayWorkerStats();
		
		System.out.println("Is there anything else you would like to do? \nOptions: Transfer | Fire | Exit");
		String answer = scan.next();
		boolean terminate = false;
		while(!terminate)
		{
			if(answer.equalsIgnoreCase("Transfer"))
			{
				System.out.println("Who would you like to transfer?");
				int worker = scan.nextInt();
				System.out.println("What department are we transfering this worker to?");
				int department = scan.nextInt();
				office.change(worker, department);
				System.out.println("Displaying current department stats: ");
				office.displayWorkerStats();
				System.out.println("Task completed, please enter another command");
				answer = scan.next();
			}
			else if(answer.equalsIgnoreCase("Fire"))
			{
				System.out.println("Who are we firing?");
				int loser = scan.nextInt();
				office.fire(loser);
				System.out.println("Displaying current department stats: ");
				office.displayWorkerStats();
				System.out.println("Task completed, please enter another command");
				answer = scan.next();
			}
			else if(answer.equalsIgnoreCase("Exit"))
			{
				System.out.println("Final department stats: ");
				office.displayWorkerStats();
				System.out.println("Goodbye");
				terminate = true;
			}
			else
			{
				System.out.println("Directions unclear, terminating system");
				answer = "Exit";
			}
		}
	}
	
	public static void main(String [] args)
	{
		Office theOffice = new Office();
		theOffice.runSim();
		System.exit(0);

	}
}
