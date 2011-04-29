package de.axis_bank.datenbank;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import de.axis_bank.daos.Antragsteller;
import de.axis_bank.daos.Arbeitgeber;
import de.axis_bank.daos.Ausgaben;
import de.axis_bank.daos.DaoObject;
import de.axis_bank.daos.Kreditverbindlichkeiten;

public class DBTest {

	@Test
	public void testSelect() {
		Antragsteller a = new Antragsteller();
		a.setKreditverbindlichkeiten(new Kreditverbindlichkeiten[] { new Kreditverbindlichkeiten() });

		Object o = DB.select(a);
		if (o != null) {
			if (o.getClass().isArray())
				for (int i = 0; i < Array.getLength(o); i++) {
					DaoObject dd = (DaoObject) Array.get(o, i);
					if (dd instanceof Antragsteller) {
						for (Method m : dd.getClass().getDeclaredMethods()) {
							if (m.getName().startsWith("get"))
								try {
									Object aa = m.invoke(dd, new Object[] {});
									if (aa instanceof Ausgaben[])
										System.out.println(((Ausgaben[]) aa)[0]
												.getBetrag());
									else
										System.out.println(m.getName()
												.substring(3)
												+ " \t\t\t=\t "
												+ aa);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
						}
					}
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
