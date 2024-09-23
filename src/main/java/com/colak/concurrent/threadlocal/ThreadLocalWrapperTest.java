package com.colak.concurrent.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * See <a href="https://gurselgazii.medium.com/enhancing-spring-boot-rest-apis-with-threadlocal-6c13eb63da8c">...</a>
 */
@Slf4j
class ThreadLocalWrapperTest {

    private static class UserContext {
        private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

        public static String getCurrentUser() {
            return currentUser.get();
        }

        public static void setCurrentUser(String user) {
            currentUser.set(user);
        }

        public static void clear() {
            currentUser.remove();
        }
    }

    public static void main(String[] args) {
        UserContext.setCurrentUser("test");
        // test
        log.info(UserContext.getCurrentUser());

        UserContext.clear();
        // null
        log.info(UserContext.getCurrentUser());
    }
}
