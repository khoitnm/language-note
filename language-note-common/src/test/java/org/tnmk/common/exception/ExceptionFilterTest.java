package org.tnmk.common.exception;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.exception.filter.BeanValidationExceptionTranslator;
import org.tnmk.common.exception.filter.ExceptionTranslator;
import org.tnmk.common.exception.model.error.ErrorResult;
import org.tnmk.common.infrastructure.validator.BeanValidator;
import org.tnmk.common.testingmodel.Person;

import java.time.LocalDate;
import java.util.Arrays;

public class ExceptionFilterTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFilterTest.class);
    private ExceptionTranslator exceptionTranslator = new ExceptionTranslator(new BeanValidationExceptionTranslator());

    @Test
    public void beanValidationException(){
        try {
            BeanValidator beanValidator = new BeanValidator();
            Person person = new Person();
            person.setDob(LocalDate.of(2001,01,01));
            person.setComingOfAgeDate(LocalDate.of(2000,01,01));
            person.setEmails(Arrays.asList("aaa@gmail.com","",null,"xyz","bbb@gmail.com"));
            beanValidator.validate(person);
        }catch (BeanValidationException ex){
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
    public void unexpectedException(){
        try {
            throw new UnexpectedException("Some unexpected exception");
        }catch (BaseException ex){
            ErrorResult errorResult = exceptionTranslator.processInternalBaseException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getUserMessage());
        }
    }

    @Test
    public void nullPointerException(){
        try {
            String a = null;
            a.toLowerCase();
        }catch (NullPointerException ex){
            ErrorResult errorResult = exceptionTranslator.processUnknownInternalException(ex);
            Assert.assertNotNull(errorResult.getCode());
            Assert.assertNotNull(errorResult.getUserMessage());
        }
    }
}
