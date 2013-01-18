package pl.wppiotrek85.wydatkibase.support;

import java.util.ArrayList;
import java.util.Collection;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;

public class ListSupport {

	public static boolean isAccountNameUsed(String name) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		Collection<Account> items = globals.getAccountsList();
		for (Account account : items) {
			if (account.getName().equals(name))
				return true;
		}

		return false;

	}

	public static boolean isProjectNameUsed(String name) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		Collection<Project> items = globals.getProjectsList();
		for (Project project : items) {
			if (project.getName().equals(name))
				return true;
		}
		return false;
	}

	public static boolean isParameterNameUsed(String name) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		Collection<Parameter> items = globals.getParametersList();
		for (Parameter parameter : items) {
			if (parameter.getName().equals(name))
				return true;
		}
		return false;
	}

	public static boolean isCategoryNameUsed(String name) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		Collection<Category> items = globals.getCategoriesList();
		for (Category category : items) {
			if (category.getName().equals(name))
				return true;
		}
		return false;
	}

	public static ArrayList<Integer> StringToIntegerArrayList(String items) {
		ArrayList<Integer> elements = new ArrayList<Integer>();
		String[] tmp = items.split(";");
		for (String string : tmp) {
			if (string.length() > 0)
				elements.add(Integer.parseInt(string));
		}
		return elements;
	}

	public static String ArrayListIntegerToString(ArrayList<Integer> items) {
		StringBuilder sb = new StringBuilder();
		for (Integer item : items) {
			sb.append(item.toString() + ";");
		}

		return sb.toString();
	}

	public static String ArrayToString(ModelBase[] items) {
		StringBuilder sb = new StringBuilder();
		for (ModelBase item : items) {
			sb.append(item.getId() + ";");
		}

		return sb.toString();
	}

	public static String ArrayToString(ArrayList<ModelBase> items) {
		StringBuilder sb = new StringBuilder();
		for (ModelBase item : items) {
			sb.append(item.getId() + ",");
		}
		return sb.toString();
	}
}
