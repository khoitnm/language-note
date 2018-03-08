package org.tnmk.common.exception;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.tnmk.common.exception.filter.BeanValidationExceptionTranslator;
import org.tnmk.common.exception.filter.ExceptionTranslator;
import org.tnmk.common.exception.model.error.ErrorResult;
import org.tnmk.common.infrastructure.validator.BeanValidator;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.testingmodel.Pet;
import org.tnmk.common.testingmodel.service.MockBusinessLogic;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExceptionTranslatorTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslatorTest.class);
    private ExceptionTranslator exceptionTranslator = new ExceptionTranslator(new BeanValidationExceptionTranslator());

    @Test
    public void beanValidationException() {
        try {
            BeanValidator beanValidator = new BeanValidator();
            Person person = new Person();
            person.setDob(LocalDate.of(2001, 01, 01));
            person.setComingOfAgeDate(LocalDate.of(2000, 01, 01));
            person.setEmails(Arrays.asList("aaa@gmail.com", "", null, "xyz", "bbb@gmail.com"));
            beanValidator.validate(person);
        } catch (BeanValidationException ex) {
            ErrorResult errorResult = exceptionTranslator.processValidationError(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getFieldErrors().get(0).getField());

            errorResult = exceptionTranslator.processInternalBaseException(ex);
            Assert.assertNotNull(errorResult.getCode());

            errorResult = exceptionTranslator.processUnknownInternalException(ex);
            Assert.assertNotNull(errorResult.getCode());
        }
    }

    @Test
    public void methodArgumentNotValidException() {
        try {
            //Mock BindingResult with Total 2 errors
            MapBindingResult bindingResult = new MapBindingResult(new HashMap<>(), "targetObjectName");
            bindingResult.rejectValue("field1", "errorCode1");
            bindingResult.reject("errorCode2", new Object[]{"errorArg2.1", 2.2}, "defaultMessage2");

            Method method = MockBusinessLogic.class.getMethods()[0];
            MethodParameter methodParameter = new MethodParameter(method, 0, 1);
            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
        } catch (MethodArgumentNotValidException ex) {
            ErrorResult errorResult = exceptionTranslator.processValidationError(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getFieldErrors().get(0).getField());
            Assert.assertEquals(2, errorResult.getFieldErrors().size());
        }
    }

    @Test
    public void processBadRequestException() {
        try {
            throw new TypeMismatchException(PersonFactory.createJasonBourne(), Pet.class);
        } catch (TypeMismatchException ex) {
            ErrorResult errorResult = exceptionTranslator.processBadRequestException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getUserMessage());
        }
    }

    @Test
    public void processAuthenticationException() {
        String rootMessage = "Authentication exception message";
        try {
            throw new AuthenticationException(rootMessage);
        } catch (AuthenticationException ex) {
            ErrorResult errorResult = exceptionTranslator.processUnauthenticationException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertTrue(errorResult.getUserMessage().toLowerCase().contains(rootMessage.toLowerCase()));
        }
    }

    @Test
    public void unexpectedException() {
        try {
            throw new UnexpectedException("Some unexpected exception");
        } catch (BaseException ex) {
            ErrorResult errorResult = exceptionTranslator.processInternalBaseException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getUserMessage());
        }
    }

    @Test
    public void nullPointerException() {
        try {
            String a = null;
            a.toLowerCase();
        } catch (NullPointerException ex) {
            ErrorResult errorResult = exceptionTranslator.processUnknownInternalException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getUserMessage());
        }
    }
}
