package frc.lib.WebServer;

public class Utils {

    static String nameTransform(String name_in) {
        // This is technically very bad cuz it maps input names (arbitrary strings) to
        // fewer bits
        // Sort of a super snazzy silly hash. This might cause collisions if the user is
        // not careful.
        return name_in.toLowerCase().replace(" ", "_").replaceAll("[\\W]|_", "");
    }

}
