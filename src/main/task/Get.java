package main.task;

import java.util.List;

import main.Customer;

public class Get extends AbstractTask implements Task{

	public Get(int index) {
		super(index);
	}

	@Override
	public void perform(List<Customer> list) {
		setGargabe(list.get(getIndex()));
	}
}
