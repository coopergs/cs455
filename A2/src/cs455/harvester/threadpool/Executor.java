package cs455.harvester.threadpool;

import cs455.harvester.tasks.Task;

public class Executor extends Thread{
	
	private Task t;
	private Pool p;
	
	public Executor(Pool p){
		this.p = p;
	}
	
	public void setTask(Task t){
		this.t = t;
	}
	
	public void begin(){
		synchronized(this){
			this.notify();
		}
	}

	public void run() {
		try{
			while(true){
				if(Thread.currentThread().isInterrupted())
					return;
				synchronized(this){
					this.wait();
				}
				t.execute();
				Thread.sleep(1000);//niceness
				p.returnThread(this);
				if(p.checkIfDone()){
					//Done
					//Would write files here: REMOVED because of permission issues
				}
			}
		}catch(InterruptedException e){
			return;
		}
	}

}
