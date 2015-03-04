package cs455.harvester.threadpool;

import java.util.LinkedList;

public class Pool {
	
	private LinkedList<Thread> pool;
	
	public Pool(int size){
		pool = new LinkedList<Thread>();
		for(int i=0;i<size;i++){
			Thread t = new Executor(this);
			pool.add(t);
			t.start();
		}
	}
	
	public Executor checkoutThread(){
		if(pool.size()==0)
			try {
				synchronized(pool){
					pool.wait();
				}
			} catch (InterruptedException e) {return null;}
		return (Executor) pool.removeFirst();
	}
	
	public void returnThread(Thread t){
		synchronized(pool){
			pool.addLast(t);
			pool.notify();
		}
	}

}
