package main.task;

import java.util.List;

import main.Customer;

public class AddAll implements Task{
private List<Customer> toAdd;
	
	public AddAll(List<Customer> toAdd) {
		this.toAdd = toAdd;
	}

	@Override
	public void perform(List<Customer> list) {
		list.addAll(toAdd);
	}
}
