package pl.wppiotrek85.wydatkibase.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.entities.Transaction;

public class WydatkiGlobals {

	private static volatile WydatkiGlobals instance = null;

	private static final boolean isLocalVersion = false;

	private static final String server = "http://imdemo.billennium.pl:121/pwojtowiczWS";
	private static final String userLogin = "wppiotrek85";

	public static WydatkiGlobals getInstance() {
		if (instance == null) {
			synchronized (WydatkiGlobals.class) {
				if (instance == null) {
					instance = new WydatkiGlobals();
				}
			}
		}
		return instance;
	}

	private WydatkiGlobals() {
	}

	private LinkedHashMap<Integer, Category> categoriesDictionary;
	private LinkedHashMap<Integer, Account> accountsDictionary;
	private LinkedHashMap<Integer, Parameter> parametersDictionary;
	private LinkedHashMap<Integer, Project> projectsDictionary;
	private LinkedHashMap<Integer, ItemsContainer<Transaction>> transactionsContainer;
	private Object currentEditObject;

	public void setCurrentEditObject(Object item) {
		this.currentEditObject = item;
	}

	public Object getCurrentEditObject() {
		return this.currentEditObject;
	}

	public Project getProjectById(int projectId) {
		return projectsDictionary.get(projectId);

	}

	public Account getAccountById(int accountId) {
		if (accountsDictionary == null)
			return null;
		return accountsDictionary.get(accountId);

	}

	public Category getCategoryById(int categoryId) {
		return categoriesDictionary.get(categoryId);

	}

	public Parameter getParameterById(int parameterId) {
		return parametersDictionary.get(parameterId);

	}

	public void updateAccountsList(Account account) {
		if (accountsDictionary.get(account.getId()) != null)
			accountsDictionary.remove(account.getId());
		accountsDictionary.put(account.getId(), account);

	}

	public void updateParametersList(Parameter parameter) {
		if (parametersDictionary.get(parameter.getId()) != null)
			parametersDictionary.remove(parameter.getId());
		parametersDictionary.put(parameter.getId(), parameter);
	}

	public void updateCategoriesList(Category category) {
		if (categoriesDictionary.get(category.getId()) != null)
			categoriesDictionary.remove(category.getId());
		categoriesDictionary.put(category.getId(), category);
	}

	public void updateProjectsList(Project project) {
		if (projectsDictionary.get(project.getId()) != null)
			projectsDictionary.remove(project.getId());
		projectsDictionary.put(project.getId(), project);

	}

	public ArrayList<Account> getAccountsList() {
		if (accountsDictionary == null)
			return null;
		ArrayList<Account> accounts = new ArrayList<Account>();
		Collection<Account> items = accountsDictionary.values();
		for (Account account : items) {
			accounts.add(account);
		}
		return accounts;
	}

	public ArrayList<Parameter> getParametersList() {
		if (parametersDictionary == null)
			return null;
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		Collection<Parameter> items = parametersDictionary.values();
		for (Parameter parameter : items) {
			parameters.add(parameter);
		}
		return parameters;
	}

	public ArrayList<Category> getCategoriesList() {
		if (categoriesDictionary == null)
			return null;
		ArrayList<Category> categories = new ArrayList<Category>();
		Collection<Category> items = categoriesDictionary.values();
		for (Category category : items) {
			categories.add(category);
		}
		return categories;
	}

	public ArrayList<Project> getProjectsList() {
		if (projectsDictionary == null)
			return null;

		ArrayList<Project> projects = new ArrayList<Project>();
		Collection<Project> items = projectsDictionary.values();
		for (Project project : items) {
			projects.add(project);
		}
		return projects;
	}

	public ItemsContainer<Transaction> getTransactionsContainer(int accountId) {
		if (transactionsContainer == null)
			return null;
		return transactionsContainer.get(accountId);
	}

	public void setParameters(ArrayList<Parameter> list) {
		LinkedHashMap<Integer, Parameter> dictionary = new LinkedHashMap<Integer, Parameter>();
		for (Parameter item : list) {
			dictionary.put(item.getId(), item);
		}
		this.parametersDictionary = dictionary;
	}

	public void setProjects(ArrayList<Project> list) {
		LinkedHashMap<Integer, Project> dictionary = new LinkedHashMap<Integer, Project>();
		for (Project item : list) {
			dictionary.put(item.getId(), item);
		}
		this.projectsDictionary = dictionary;
	}

	public void setAccounts(ArrayList<Account> list) {
		LinkedHashMap<Integer, Account> dictionary = new LinkedHashMap<Integer, Account>();
		for (Account item : list) {
			dictionary.put(item.getId(), item);
		}
		this.accountsDictionary = dictionary;
	}

	public void setCategories(ArrayList<Category> list) {
		LinkedHashMap<Integer, Category> dictionary = new LinkedHashMap<Integer, Category>();
		for (Category item : list) {
			dictionary.put(item.getId(), item);
		}
		this.categoriesDictionary = dictionary;
	}

	public void setTransactionsContainer(int accountId,
			ItemsContainer<Transaction> container, boolean canAdd) {
		if (this.transactionsContainer == null || canAdd == false) {
			if (this.transactionsContainer == null)
				this.transactionsContainer = new LinkedHashMap<Integer, ItemsContainer<Transaction>>();

			if (!canAdd) {
				this.transactionsContainer.remove(accountId);
				this.transactionsContainer.put(accountId, container);
			}
		} else {
			ArrayList<Transaction> oldTransactions = new ArrayList<Transaction>(
					Arrays.asList(this.transactionsContainer.get(accountId)
							.getItems()));
			ArrayList<Transaction> newTransactions = new ArrayList<Transaction>(
					Arrays.asList(container.getItems()));

			oldTransactions.addAll(newTransactions);

			container.setItems(oldTransactions
					.toArray(new Transaction[oldTransactions.size()]));
			container.setTotalCount(container.getTotalCount());

			this.transactionsContainer.remove(accountId);
			this.transactionsContainer.put(accountId, container);
		}
	}

	public boolean isLocalVersion() {
		return isLocalVersion;
	}

	public String getServer() {
		return server;
	}

	public String getUserLogin() {
		return userLogin;
	}

	// private LinkedHashMap<Integer,ItemsContainer<Transaction>>
	// accountsTransactions;
	//
	// public void setAccountTransactions(int accountId,
	// ItemsContainer<Transaction> container) {
	// LinkedHashMap<Integer, ItemsContainer<Transaction>> dictionary = new
	// LinkedHashMap<Integer, ItemsContainer<Transaction>>();
	// dictionary.put(accountId, container);
	//
	// accountsTransactions = dictionary;
	// }

}
