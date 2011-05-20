package de.axisbank.datenbank;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.Arbeitgeber;
import de.axisbank.daos.Ausgaben;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Kreditverbindlichkeiten;
import de.axisbank.daos.User;
import de.axisbank.datenbank.DB;

public class DBTest {

	@Test
	public void testSelect() {

		User a = new User();
		a.setPasswort("Superman");
		a.setBenutzername("Kreditberater");

		User[] o = (User[]) DB.select(a);
		if (o != null)
			if (o.length > 0)
				for (User as : o)
					for (Method m : as.getClass().getDeclaredMethods()) {
						if (m.getName().startsWith("get"))
							try {
								Object aa = m.invoke(as, new Object[] {});
								if (aa instanceof Ausgaben[])
									System.out.println(((Ausgaben[]) aa)[0].getBetrag());
								else
									System.out.println(m.getName().substring(3) + " \t\t\t=\t " + aa);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
					}
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testInsert() {
	}

}
