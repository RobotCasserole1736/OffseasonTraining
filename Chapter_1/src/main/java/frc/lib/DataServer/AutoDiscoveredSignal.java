package frc.lib.DataServer;

import java.lang.reflect.Field;

/*
 *******************************************************************************************
 * Copyright (C) 2018 FRC Team 1736 Robot Casserole - www.robotcasserole.org
 *******************************************************************************************
 *
 * This software is released under the MIT Licence - see the license.txt
 *  file in the root of this repo.
 *
 * Non-legally-binding statement from Team 1736:
 *  Thank you for taking the time to read through our software! We hope you
 *   find it educational and informative! 
 *  Please feel free to snag our software for your own use in whatever project
 *   you have going on right now! We'd love to be able to help out! Shoot us 
 *   any questions you may have, all our contact info should be on our website
 *   (listed above).
 *  If you happen to end up using our software to make money, that is wonderful!
 *   Robot Casserole is always looking for more sponsors, so we'd be very appreciative
 *   if you would consider donating to our club to help further STEM education.
 */

class AutoDiscoveredSignal {

    Field sourceField;
    Object sourceFieldParentObj;
    Signal sig;
    Class type;

    /**
     * Class to wrapper the usual Signal object with the java Reflection Field/Object information 
     * required to reference and read the value at runtime.
     * @param sourceField_in Java Reflection Field object found during recursive searching for @Signal annotated objects
     * @param sourceFieldParentObj_in Object that should get its value read at runtime to populate the signal's value
     * @param name Signal name
     * @param units Signal units string
     */
    AutoDiscoveredSignal(Field sourceField_in, Object sourceFieldParentObj_in, String name, String units){
        sourceField = sourceField_in;
        sourceFieldParentObj = sourceFieldParentObj_in;
        type = sourceField.getType();
        sourceField.setAccessible(true);
        sig = new Signal(name, units);
    }

    /**
     * Sample the value out of the referenced object for the ADS, and add it as a new sample into the signal.
     * @param time Time at which the sample was inititated.
     */
    void addSample(double time){
        // Switch behavior based on the type of field that was annotated
        try{
            if(type==int.class){
                sig.addSample(time, sourceField.getInt(sourceFieldParentObj));
            } else if(type==boolean.class){
                sig.addSample(time, sourceField.getBoolean(sourceFieldParentObj));
            } else if(type==double.class){
                sig.addSample(time, sourceField.getDouble(sourceFieldParentObj));
            } else if(type==float.class){
                    sig.addSample(time, sourceField.getFloat(sourceFieldParentObj));
            } else if(type.isEnum()){
                Object enumObj = sourceField.get(sourceFieldParentObj);
                try{
                    Field valField = enumObj.getClass().getDeclaredField("value");//most casserole enums define a value
                    sig.addSample(time, valField.getInt(enumObj));
                } catch(NoSuchFieldException e) {
                    //Silently ignore for now... find fallbacks or print errors int he future?
                }

            } else {
                System.out.println("WARNING: Signal " + sig.getDisplayName() + " cannot be populated from underlying type " + type.getName() + ". It is not yet supported, but you could add support!");
            }
        } catch(IllegalAccessException e) {
            System.out.println("WARNING: Signal " + sig.getDisplayName() + " threw an exception while we were attempting to read it.");
            System.out.println(e);
        }
    }

}
