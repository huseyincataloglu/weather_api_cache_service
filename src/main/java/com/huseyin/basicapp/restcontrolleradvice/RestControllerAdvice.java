package com.huseyin.basicapp.restcontrolleradvice;

import com.huseyin.basicapp.exception.*;
import com.huseyin.basicapp.weather.model.dto.ErrorDto;
import com.huseyin.basicapp.weather.model.entity.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {


    @ExceptionHandler(EndDateWithoutStartDateException.class)
    public ResponseEntity<?> date2WithoutDate1Exception(EndDateWithoutStartDateException ex,HttpServletRequest request){
        return responseBuilder(ex,request,HttpStatus.BAD_REQUEST,ErrorCode.START_DATE_MISSING_WHEN_END_DATE_PROVIDED.name());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleParse(DateTimeParseException ex, HttpServletRequest request) {
        return responseBuilder(
                ex,request,HttpStatus.BAD_REQUEST,ErrorCode.INVALID_DATE_FORMAT.name()
        );
    }

    @ExceptionHandler(InvalidIncludeException.class)
    public ResponseEntity<?> invalidIncludeException(InvalidIncludeException invalidIncludeException,HttpServletRequest request){
        return responseBuilder(
                invalidIncludeException,request,HttpStatus.BAD_REQUEST,ErrorCode.INVALIDE_INCLUDE.name()
        );
    }


    @ExceptionHandler(CurrentWithDateException.class)
    public ResponseEntity<?> currentWithDateException(CurrentWithDateException currentWithDateException,HttpServletRequest request){
        return responseBuilder(
                currentWithDateException,request,HttpStatus.BAD_REQUEST,ErrorCode.CURRENT_USED_WITH_DATES.name()
        );
    }
    @ExceptionHandler(StartDateMustBeBeforeException.class)
    public ResponseEntity<?> startDateException(StartDateMustBeBeforeException startDateMustBeBeforeException,HttpServletRequest request){
        return responseBuilder(
                startDateMustBeBeforeException,request,HttpStatus.BAD_REQUEST,ErrorCode.START_DATE_AFTER_END_DATE.name()
        );
    }
    @ExceptionHandler(StartDateEqualsEndDateException.class)
    public ResponseEntity<?> startDateEqualsEndDate(StartDateEqualsEndDateException startDateEqualsEndDateException,HttpServletRequest request){
        return responseBuilder(
                startDateEqualsEndDateException,request,HttpStatus.BAD_REQUEST,ErrorCode.START_DATE_EQUALS_END_DATE.name()
        );
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> parameterTypeMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException,HttpServletRequest request){
        return responseBuilder(methodArgumentTypeMismatchException,request,HttpStatus.BAD_REQUEST,ErrorCode.TYPE_MISMATCH.name());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> parameterCannotBeNull(MethodArgumentNotValidException methodArgumentNotValidException,HttpServletRequest request){
        return responseBuilder(methodArgumentNotValidException,request,HttpStatus.BAD_REQUEST,ErrorCode.LOCATION_NULL.name());
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> httpMessageNotReadableHandler(Throwable ex,HttpServletRequest request){
        return responseBuilder(ex,request,HttpStatus.INTERNAL_SERVER_ERROR,ErrorCode.UNKNOWN_ERROR.name());
    }



    private ResponseEntity<?> responseBuilder(
            Throwable ex,
            HttpServletRequest request,
            HttpStatus status,
            String errorCode

    ){
        ErrorDto errorResponse = new ErrorDto(
                LocalDateTime.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                request.getRequestURI(),
                ex.getMessage(),
                errorCode
        );
        return ResponseEntity
                .status(status).body(errorResponse);

    }



}
