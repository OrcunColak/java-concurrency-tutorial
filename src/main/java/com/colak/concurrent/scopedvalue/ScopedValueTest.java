package com.colak.concurrent.scopedvalue;

// See https://medium.com/@lavneesh.chandna/scoped-values-demystified-rethinking-threadlocal-in-the-age-of-virtualthread-43fc1a33f879
class ScopedValueTest {

    private static ScopedValue<String> scopedValue = ScopedValue.newInstance();

    public static void main() {
        ScopedValueTest instance = new ScopedValueTest();
        ScopedValue.where(scopedValue, "Test value")
                .run(() -> {
                    System.out.println("Value is: " + scopedValue.get());
                    instance.doSomething();
                });
    }

    private void doSomething() {
        System.out.println("Doing something while accessing scoped value: " + scopedValue.get());
        doSomethingAgain();
    }

    private void doSomethingAgain() {
        System.out.println("Doing something again while accessing scoped value: " + scopedValue.get());
    }

}
