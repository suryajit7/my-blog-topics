package com.framework.util;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.GenericValidator;
import org.assertj.core.api.AbstractAssert;

import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.util.regex.Pattern;

import static com.framework.data.Constants.NAME_REGEX;
import static com.framework.data.Constants.RFC5322_EMAIL_REGEX;
import static java.util.Arrays.stream;
import static java.util.Locale.getISOCountries;

/**
 * Custom assertions suitable for Common Fields.
 */
@Slf4j
public class AssertField extends AbstractAssert<AssertField, String> {

    public AssertField(String string) {
        super(string, AssertField.class);
    }

    public static AssertField assertThat(String string) {
        return new AssertField(string);
    }

    public AssertField isValidName() {
        isNotNull();
        Boolean isValidName = Pattern.compile(NAME_REGEX).matcher(actual).matches();
        if (isValidName) {
            log.info("Given Name is valid.");
        } else {
            log.error("Given Name is NOT valid: ".concat(actual));
            failWithMessage("Given Name is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isPhoneNumber() {
        isNotNull();
        try {
            Boolean isValidPhoneNumber = PhoneNumberUtil.getInstance()
                    .isValidNumber(PhoneNumberUtil.getInstance()
                            .parse(actual, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name()));
            if (isValidPhoneNumber) {
                log.info("Given string is a valid phone number in Earth.");
            }
        } catch (NumberParseException e) {
            log.error("Given string is NOT a valid phone number in Earth: ".concat(actual));
            failWithMessage("Given string is NOT a valid phone number in Earth: ".concat(actual));
        } return this;
    }

    public AssertField isPhoneNumberFrom(String geographicalCode) {
        isNotNull();
        try {
            Boolean isNumberFromGivenGeographical = PhoneNumberUtil.getInstance()
                    .isNumberGeographical(PhoneNumberUtil.getInstance()
                            .parse(actual, geographicalCode));

            if (isNumberFromGivenGeographical) {
                log.info("The PhoneNumber belongs to given geographical area.");
            }
        } catch (NumberParseException e) {
            log.error("The PhoneNumber DOES NOT belongs to given geographical area: ".concat(actual));
            failWithMessage("The PhoneNumber DOES NOT belongs to given geographical area: ".concat(actual));
        } return this;
    }

    public AssertField isEmailID() {
        isNotNull();
        Boolean isValidEmail = Pattern.compile(RFC5322_EMAIL_REGEX).matcher(actual).matches() && GenericValidator.isEmail(actual);
        if (isValidEmail) {
            log.info("Given EmailID is valid.");
        } else {
            log.error("Given EmailID is NOT valid: ".concat(actual));
            failWithMessage("Given EmailID is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isDate(String datePattern) {
        isNotNull();
        if (GenericValidator.isDate(actual, datePattern, true)) {
            log.info("Given Date is valid.");
        } else {
            log.error("Given Date is NOT valid: ".concat(actual));
            failWithMessage("Given Date is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isURL() {
        isNotNull();
        if (GenericValidator.isUrl("https://".concat(actual))) {
            log.info("Given URL is valid.");
        } else {
            log.error("Given URL is NOT valid: ".concat(actual));
            failWithMessage("Given URL is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isCreditCard() {
        isNotNull();
        if (GenericValidator.isCreditCard(actual)) {
            log.info("Given CreditCard is valid.");
        } else {
            log.error("Given CreditCard is NOT valid: ".concat(actual));
            failWithMessage("Given CreditCard is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isCurrencyCode() {
        isNotNull();
        try {
            Monetary.getCurrency(actual);
            log.info("Given CurrencyCode is valid.");
        } catch (UnknownCurrencyException e) {
            log.error("Given CurrencyCode is NOT valid: ".concat(actual));
            failWithMessage("Given CurrencyCode is NOT valid: ".concat(actual));
        } return this;
    }

    public AssertField isCountryCodeISO() {
        isNotNull();

        Boolean isCountryCodeISO = stream(getISOCountries())
                .anyMatch(country -> country.contains(actual));

        if (isCountryCodeISO) {
            log.info("Given Country is valid.");
        } else {
            log.error("Given Country is NOT valid: ".concat(actual));
            failWithMessage("Given Country is NOT valid: ".concat(actual));
        } return this;
    }

}

