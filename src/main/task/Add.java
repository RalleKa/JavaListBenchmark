package main.task;

import java.util.List;

import main.Customer;

public class Add extends AbstractTask implements Task {
	public Add(int index, Customer toAdd) {
		super(index);
		setGargabe(toAdd);
	}

	@Override
	public void perform(List<Customer> list) {
		list.add(getGarbage());
	}
}
