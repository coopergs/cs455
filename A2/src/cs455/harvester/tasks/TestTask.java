package cs455.harvester.tasks;

public class TestTask implements Task{
	
	public void execute(){
		System.out.println("Execute!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
