package main.task;

import main.Customer;

public abstract class AbstractTask implements Task{
	private int index;
	private Customer garbage;
	
	public AbstractTask(int index)  {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setGargabe(Customer garbage) {
		this.garbage = garbage;
	}
	
	public Customer getGarbage() {
		return garbage;
	}
}
