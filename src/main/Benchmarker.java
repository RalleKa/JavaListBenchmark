package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.magicwerk.brownies.collections.BigList;
import org.magicwerk.brownies.collections.GapList;
import main.task.Add;
import main.task.AddAll;
import main.task.Get;
import main.task.Remove;
import main.task.RemoveAll;
import main.task.Task;

public class Benchmarker {
	private static final int getOperations = 30;
	
	public static final int SIZE = 100000;
	public static final int TESTS = SIZE / 2;
	public static final int RANDOM = SIZE / 10 * (2 + getOperations);
	
	private List<Customer>[] toTest;
	private Customer[] initialElements;
	
	private Task [] getRandom;
	private Task[] getLocal;
	private Task[] addRandom;
	private Task[] addLocal;
	private Task[] removeRandom;
	private Task[] removeLocal;
	private Task[] removeAll;
	private Task[] addAll;
	private Task[] randomRandom;
	
	private long start;
	
	public static void main(String[] args) {
		ArrayList<Customer> arrayList = new ArrayList<Customer>(SIZE);
		MultiGappedList<Customer> multiGappedList = new MultiGappedList<Customer>(SIZE * 2);
		GapList<Customer> gapList = new GapList<>(SIZE);
		BigList<Customer> bigList = new BigList<>();
		LinkedList<Customer> linkedList = new LinkedList<>();	
		

		Benchmarker main = new Benchmarker(bigList, multiGappedList, gapList, arrayList, linkedList);
		
		main.testAll();
	}
	
	
	Benchmarker(List<Customer>...toTest) {
		this.toTest = toTest;
		prepareTests();
	}
	
	private void prepareTests() {
		// at first we empty all lists
		for (List<Customer> list: toTest) {
			list.clear();
		}
		
		// Then we add random Elements to the List to make an initial fill
		initialElements = new Customer[SIZE];
		for (int i = 0; i < initialElements.length; i++) {
			Customer n = new Customer(i);
			initialElements[i] = n;
			
			for (List<Customer> list: toTest) {
				list.add(n);
			}
		}
		
		 // preparing the actual tasks
		getRandom = new Get[TESTS];
		for (int i = 0; i < getRandom.length; i++) {
			int randomIndex = (int) (Math.random() * SIZE);
			getRandom[i] = new Get(randomIndex);
		}
		
		getLocal = new Get[TESTS];
		for (int i = 0; i < getLocal.length; i++) {
			getLocal[i] = new Get(i);
		}
		
		addRandom = new Add[TESTS];
		for (int i = 0; i < addRandom.length; i++) {
			int randomIndex = (int) (Math.random() * SIZE);
			Customer customer = new Customer((int) (Math.random() * Integer.MAX_VALUE));
			addRandom[i] = new Add(randomIndex, customer);
		}
		
		addLocal = new Add[TESTS];
		for (int i = 0; i < addLocal.length; i++) {
			Customer customer = new Customer((int) (Math.random() * Integer.MAX_VALUE));
			addLocal[i] = new Add(i, customer);
		}
		
		removeRandom = new Remove[TESTS];
		for (int i = 0; i < removeRandom.length; i++) {
			int randomIndex = (int) (Math.random() * (SIZE - i - 1));
			removeRandom[i] = new Remove(randomIndex);
		}
		
		removeLocal = new Remove[TESTS];
		int random = (int) (Math.random() * (SIZE - TESTS - 1));
		for (int i = 0; i < removeLocal.length; i++) {
			removeLocal[i] = new Remove(random);
		}
		
		int all = 1000;
		
		removeAll = new RemoveAll[TESTS / all];
		for (int i = 0; i < removeAll.length; i++) {
			ArrayList<Customer> toRemove = new ArrayList<Customer>();
			
			for (int j = 0; j < all; j++) {
				int randomIndex = (int) (Math.random() * (SIZE - i * all - j));
				toRemove.add(initialElements[randomIndex]);
			}
			
			removeAll[i] = new RemoveAll(toRemove);
		}
		
		addAll = new AddAll[TESTS / all];
		for (int i = 0; i < addAll.length; i++) {
			ArrayList<Customer> toAdd = new ArrayList<Customer>();
			
			for (int j = 0; j < all; j++) {
				Customer customer = new Customer((int) (Math.random() * Integer.MAX_VALUE));
				toAdd.add(customer);
			}
			
			addAll[i] = new AddAll(toAdd);
		}
		
		randomRandom = new Task[RANDOM];
		for (int i = 0; i < randomRandom.length; i+=(2+getOperations)) {
			// get something
			int randomIndex;
			for (int j = 0; j < getOperations; j++) {
				randomIndex  = (int) (Math.random() * SIZE);
				randomRandom[i + j] = new Get(randomIndex);
			}
			
			randomIndex = (int) (Math.random() * SIZE);
			randomRandom[i + getOperations] = new Remove(randomIndex);
			
			randomIndex = (int) (Math.random() * 1000) - 500;
			randomIndex = Math.min(SIZE, Math.max(0, randomIndex));
			Customer customer = new Customer((int) (Math.random() * Integer.MAX_VALUE));
			randomRandom[i + 1 + getOperations] = new Add(randomIndex, customer);
		}
	}
	
	public void testAll() {
		testGetRandom();
		testGetLocal();
		
		testRemoveAll();
		testAddAll();
		
		testRemoveRandom();
		testAddRandom();
		
		testRemoveLocal();
		testAddLocal();
		
		testRandomRandom();
	}
	
	public void testAddRandom() {
		performSomeTest("AddRandom:", addRandom);
	}
	
	public void testAddLocal() {
		performSomeTest("AddLocal:", addLocal);
	}
	
	public void testRemoveRandom() {
		performSomeTest("RemoveRandom:", removeRandom);
	}
	
	public void testRemoveLocal() {
		performSomeTest("RemoveLocal:", removeLocal);
	}
	
	public void testGetRandom() {
		performSomeTest("GetRandom:", getRandom);
	}
	
	public void testGetLocal() {
		performSomeTest("getLocal:", getLocal);
	}
	
	public void testRandomRandom() {
		performSomeTest("RandomRandom:", randomRandom);
	}
	
	public void testRemoveAll() {
		performSomeTest("RemoveAll", removeAll);
	}
	
	public void testAddAll() {
		performSomeTest("AddAll", addAll);
	}
	
	private void performSomeTest(String discription, Task[] tasks) {
		long[] time = new long[toTest.length];
		int index = 0;
		System.out.println(discription);
		
		for (List<Customer> list: toTest) {
			start();
			
			for (int i = 0; i < tasks.length; i++) {
				tasks[i].perform(list);
			}
			
			time[index] = end();
			System.out.println(String.format("\t%50s %12d", list.getClass(), time[index++]));
		}
	}
	
	private void start() {
		start = System.nanoTime();
	}
	
	private long end() {
		return System.nanoTime() - start;
	}
}
