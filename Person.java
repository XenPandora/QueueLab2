public class Person 
{
	//the employee number
	private int execNum;
	//the salary of the person
	private int salary;
	
	public Person()
	{
		execNum = 0;
		salary = 0;
	}
	
	public Person(int num)
	{
		execNum = num;
		salary = 0;
	}
	
	public void setSalary(int wage)
	{
		salary = wage;
	}
	
	public int getSalary()
	{
		return salary;
	}
	
	public int getExecNum()
	{
		return execNum;
	}
	
	public void getEmployeeStats()
	{
		System.out.println("Employee number: " +execNum +" Salary: "  + salary);
	}
}
