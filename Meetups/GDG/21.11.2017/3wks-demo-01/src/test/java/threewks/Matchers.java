package threewks;

import com.googlecode.objectify.Ref;
import com.threewks.thundr.exception.BaseException;
import com.threewks.thundr.test.TestSupport;
import com.threewks.thundr.user.User;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Matchers {

    public static class MatcherException extends BaseException {
        public MatcherException(String format, Object... formatArgs) {
            super(format, formatArgs);
        }
    }

    public static TypeSafeMatcher<Class<?>> hasMethodAnnotation(final String methodName, final Class<? extends Annotation> annotation) {
        return new TypeSafeMatcher<Class<?>>() {
            @Override
            protected boolean matchesSafely(Class<?> clazz) {
                Method method = findMethod(clazz);
                if (method == null) {
                    throw new MatcherException("No such method %s for class %s", methodName, clazz.getSimpleName());
                }
                return method.getAnnotation(annotation) != null;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("missing required annotation: ").appendValue(annotation);
            }

            private Method findMethod(Class<?> clazz) {
                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals(methodName)) {
                        return method;
                    }
                }
                return null;
            }
        };
    }

    public static TypeSafeMatcher<Class<?>> hasHiddenConstructor() {
        return new TypeSafeMatcher<Class<?>>() {
            @Override
            protected boolean matchesSafely(Class<?> targetClass) {
                Constructor<?>[] cons = targetClass.getDeclaredConstructors();

                if (cons.length > 1 || cons[0].getParameterTypes().length > 1) {
                    return false;
                }

                boolean isConstructorPrivate = Modifier.isPrivate(cons[0].getModifiers());
                // Now call the constructor to make coverage happy
                callDefaultConstructorForTestCodeCoverage(cons[0]);
                return isConstructorPrivate;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("class with a single no-arg private constructor");
            }

            private void callDefaultConstructorForTestCodeCoverage(Constructor<?> con) {
                con.setAccessible(true);
                try {
                    con.newInstance((Object[]) null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        };
    }

    public static <T> Matcher<T> hasFieldWithUserRef(final String fieldName, final User user) {
        return new BaseMatcher<T>() {
            @Override
            public boolean matches(Object o) {
                Ref<User> userRef = TestSupport.getField(o, fieldName);
                if (user == null) {
                    return userRef == null;
                } else {
                    String username = userRef.getKey().getName();

                    return user.getUsername().equals(username);
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("User with username '%s' on field %s", user, fieldName));
            }
        };
    }

    private static String getUsernameFromRef(Object entity, String field) {
        Ref<User> userRef = TestSupport.getField(entity, field);
        return userRef == null ? null : userRef.getKey().getName();
    }
}
