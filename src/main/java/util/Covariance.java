package util;

import java.lang.reflect.*;

/**
 * based on an example by Chris Rathman found it on
 * http://www.angelfire.com/tx4/cus/shapes/covariance.html
 *
 * <p>The main problem is that this thingy is really slow.
 */
abstract class Covariance {

  public static Object dispatch(
      Class dispatchClass, Object receiver, String methodName, Object[] args)
      throws CovarianceException, IllegalAccessException, InvocationTargetException {

    Class[] inherentArgTypes = new Class[args.length];
    for (int i = 0; i < args.length; i++) {
      inherentArgTypes[i] = args[i].getClass();
    }

    Method[] instanceMethods = receiver.getClass().getMethods();

    Method chosenMethod = null;
    Class[] chosenParameterTypes = null;
    Method consideredMethod = null;

    Loop:
    for (int i = 0; i < instanceMethods.length; i++) {
      consideredMethod = instanceMethods[i];
      if (!consideredMethod.getName().equals(methodName)) {
        continue Loop;
      }
      if (Modifier.isStatic(consideredMethod.getModifiers())) {
        continue Loop;
      }
      Class[] parameterTypes = consideredMethod.getParameterTypes();
      // ignore functions with wrong number of parameters
      if (parameterTypes.length != args.length) {
        continue Loop;
      }
      // coerce the primitives to objects
      boxPrimitives(parameterTypes);
      // ignore functions with incompatible parameter types
      for (int j = 0; j < parameterTypes.length; j++) {
        if (!parameterTypes[j].isAssignableFrom(inherentArgTypes[j])) {
          continue Loop;
        }
      }

      // if this is the first match then use it
      if (chosenMethod == null) {
        chosenMethod = consideredMethod;
        chosenParameterTypes = parameterTypes;
        continue Loop;
      }

      // if this method is more specific in compatibility then use it
      for (int j = 0; j < chosenParameterTypes.length; j++) {
        if (!chosenParameterTypes[j].isAssignableFrom(parameterTypes[j])) {
          continue Loop;
        }
      }

      // this is the best fit so far
      chosenMethod = instanceMethods[i];
      chosenParameterTypes = parameterTypes;
    }

    // return to the caller indicating that method was not found
    if (chosenMethod == null) {
      throw (new CovarianceException("Method not found"));
    }

    // return to the caller indicating that a covariant method was not found
    if (chosenMethod.getDeclaringClass() == dispatchClass) {
      throw (new CovarianceException("Covariant method not found"));
    }

    // invoke the covariant method and return the result
    return chosenMethod.invoke(receiver, args);
  }

  public static Class[] boxPrimitives(Class[] parameterTypes) {
    for (int i = 0; i < parameterTypes.length; i++) {
      if (parameterTypes[i] == byte.class) parameterTypes[i] = Byte.class;
      if (parameterTypes[i] == short.class) parameterTypes[i] = Short.class;
      if (parameterTypes[i] == int.class) parameterTypes[i] = Integer.class;
      if (parameterTypes[i] == long.class) parameterTypes[i] = Long.class;
      if (parameterTypes[i] == boolean.class) parameterTypes[i] = Boolean.class;
      if (parameterTypes[i] == float.class) parameterTypes[i] = Float.class;
      if (parameterTypes[i] == double.class) parameterTypes[i] = Double.class;
      if (parameterTypes[i] == char.class) parameterTypes[i] = Character.class;
    }
    return parameterTypes;
  }
}
