package main.task;

import java.util.List;

import main.Customer;

public class Remove extends AbstractTask implements Task {
	public Remove(int index) {
		super(index);
	}

	@Override
	public void perform(List<Customer> list) {
		list.remove(getIndex());
	}
}
