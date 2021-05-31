package us.ystar.demo.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PitchFileValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
