package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import impl.Calc;

public class RunApp {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int opcion = 1;
		while (opcion > 0) {
			System.out.println("Seleccione una operación:\n");
			System.out.println("\t1) Suma.");
			System.out.println("\t2) Resta.");
			System.out.println("\t3) Multiplicación.");
			System.out.println("\t4) División.");
			System.out.println("\tIngrese 0 para salir.");
			System.out.print("\nOpción: ");

			opcion = new Scanner(System.in).nextInt();

			if (opcion<0 || opcion>4) {
				System.out.println("Opción no válida");
			} else if (opcion==0) {
				System.out.println("\nHasta luego!");
			} else {
				String[] operations = {"suma", "resta", "multi", "div"};
				List<Object> params = new ArrayList<Object>();

				System.out.print("\n\tIngrese el primer número: ");
				params.add(new Scanner(System.in).nextDouble());
				System.out.print("\tIngrese el segundo número: ");
				params.add(new Scanner(System.in).nextDouble());
				double result = (double) invocacionDinamica(Calc.class, operations[opcion-1], params);
				System.out.println(String.
						format("\n\tLa %s de %.2f y %.2f es %.2f\n",
								operations[opcion-1],
								params.get(0),
								params.get(1),
								result));
			}
		}
	}

	public static Object invocacionDinamica(Class<?> class_, String methodName, List<Object> params) {
		Object result = null;
		try {
			Method method = null;
			for (Method m : Class.forName(class_.getCanonicalName()).getMethods()) {
				if (m.getName().equals(methodName)) {
					method = m;
					break;
				}
			}
			if (method != null) {
				Constructor<?> constructor = Class.forName(class_.getCanonicalName()).getConstructor();
				Object myObj = constructor.newInstance();
				result = method.invoke(myObj, params.toArray(new Object[params.size()]));
			}
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| InstantiationException e) {
			e.printStackTrace();
		}
		return result;
	}

}
