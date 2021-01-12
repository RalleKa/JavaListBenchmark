package main.task;

import java.util.List;

import main.Customer;

public class RemoveAll implements Task {
	private List<Customer> toRemove;
	
	public RemoveAll(List<Customer> toRemove) {
		this.toRemove = toRemove;
	}

	@Override
	public void perform(List<Customer> list) {
		list.removeAll(toRemove);
	}
}
