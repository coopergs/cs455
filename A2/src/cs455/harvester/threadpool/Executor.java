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
				p.returnThread(this);
			}
		}catch(InterruptedException e){
			return;
		}
	}

}
