package frc.lib.DataServer;

import java.util.HashSet;

public class IDGen {

    static HashSet<String> usedIDs = new HashSet<String>();

    /**
     * Utility to generate a string UUID from a starting string "suggestion". The
     * goal is to generate something still human readable so debugging is not
     * frustrating, but still generate a true UUID that can be used as such.
     * 
     * @param name_in Starting suggestion
     * @return a string which is unique across all calls to this function.
     */
    static String makeUUID(String name_in) {
        int i = 0;

        /* Build up a normalized version of the input string */
        String base = name_in.toLowerCase().replace(" ", "_").replaceAll("[\\W]|_", "");

        /* Start with no suffix */
        String ext = "";

        /* Build the target output string */
        String retval = base + ext;

        /* Iterate over suffixes until we have an unused string */
        while (usedIDs.contains((retval))) {
            ext = "_" + Integer.toString(i++);
            retval = base + ext;
        }

        /* Mark that the ID has been used */
        usedIDs.add(retval);

        return retval;
    }

}
