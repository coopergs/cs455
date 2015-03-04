package cs455.harvester.threadpool;

import cs455.harvester.tasks.TestTask;

public class Test {
	
	public static void main(String[] args){
		Pool p = new Pool(5);
		for(int i=0;i<100;i++){
			Executor e = p.checkoutThread();
			e.setTask(new TestTask());
			e.begin();
		}
	}

}
