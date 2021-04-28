/*
 * COMP249
 * Assignment #4
 * 2021-04-24
 */

package television;

public interface Watchable {
    enum Compatibility {
        SAME("same time"),
        DIFFERENT("different times"),
        OVERLAP("some overlap");

        String description;

        Compatibility(String description) {
            this.description = description;
        }
    }
    Compatibility compatible(Show s);
}
