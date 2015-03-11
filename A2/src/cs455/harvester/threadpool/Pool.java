package cs455.harvester.threadpool;

import java.util.LinkedList;

import cs455.harvester.tasks.Task;

public class Pool {
	
	private LinkedList<Thread> pool;
	private LinkedList<Task> tasks;
	//private int maxThreadList;
	
	public Pool(int size){
		pool = new LinkedList<Thread>();
		tasks = new LinkedList<Task>();
		//maxThreadList = size;
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
	
	public void addTask(Task t){
		synchronized(tasks){
			tasks.addLast(t);
			tasks.notify();
		}
	}
	
	private Task pullTask(){
		if(tasks.size()==0)
			try {
				synchronized(tasks){
					tasks.wait();
				}
			} catch (InterruptedException e) {return null;}
		return tasks.removeFirst();
	}
	
	public void printTaskNum(){
		synchronized(tasks){
			System.out.println("Tasks: " + tasks.size());
		}
	}
	
	public void printThreadNum(){
		synchronized(pool){
			System.out.println("Threads: " + pool.size());
		}
	}
	
	public void matchPairs(){
		while(true){
			Task t = pullTask();
			Executor e = checkoutThread();
			e.setTask(t);
			e.begin();
		}
	}

}
