package com.freedomPass.project.model.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = com.freedomPass.project.model.validation.ValidNameImpl.class)
public @interface ValidName {

    String message() default "This is not a valid name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String min() default "0";

    String max() default "99999999999999";

}
