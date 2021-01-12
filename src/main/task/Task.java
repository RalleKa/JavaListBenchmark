package main.task;

import java.util.List;

import main.Customer;

public interface Task {
	void perform(List<Customer> list);
}
