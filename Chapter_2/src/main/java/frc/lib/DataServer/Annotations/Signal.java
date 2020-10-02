package frc.lib.DataServer.Annotations;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Signal {

    String name() default "NO_NAME";
    String units() default "";

}
