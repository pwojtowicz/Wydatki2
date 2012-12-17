package pl.wppiotrek85.wydatkibase.support;

import java.util.Collection;

import pl.wppiotrek85.wydatkibase.entities.Account;
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
}
