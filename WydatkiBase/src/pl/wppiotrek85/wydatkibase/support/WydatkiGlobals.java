package pl.wppiotrek85.wydatkibase.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;

public class WydatkiGlobals {

	private static volatile WydatkiGlobals instance = null;

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

	public Project getProjectById(int projectId) {
		return projectsDictionary.get(projectId);

	}

	public Account getAccountById(int accountId) {
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
}
