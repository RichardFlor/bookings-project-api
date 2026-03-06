package br.richard.bookingsproject.utils;

import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.exceptions.InvalidFieldException;
import br.richard.bookingsproject.errors.exceptions.InvalidRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ConvertInputJsonToObject {
    private final Validator inputValidator;

    public <T> T convertToObject(String jsonString, Class<T> objectClass) {
        try {
            T inputObject = new ObjectMapper().readValue(jsonString, objectClass);

            var bindingResult = new BeanPropertyBindingResult(inputObject, objectClass.getSimpleName());
            inputValidator.validate(inputObject, bindingResult);

            if (bindingResult.hasErrors()) {
                var fields = bindingResult.getFieldErrors().stream().map(FieldError::getField);
                throw new InvalidFieldException(ExceptionCode.API_FIELDS_INVALID, fields.toArray(String[]::new));
            }

            return inputObject;

        } catch (JsonProcessingException e) {
            throw new InvalidRequestException();
        }
    }
}