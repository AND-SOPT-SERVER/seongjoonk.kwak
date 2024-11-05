package org.sopt.diary.common;

import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.diary.common.Failure.CommonFailureInfo;
import org.sopt.diary.common.Failure.DiaryFailureInfo;
import org.sopt.diary.common.Failure.FailureCode;
import org.sopt.diary.common.Failure.FailureResponse;
import org.sopt.diary.common.enums.validation.ValidationError;
import org.sopt.diary.exception.BusinessException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;

import static org.sopt.diary.common.enums.validation.Test.getDefaultFromHandlerMethodValidationException;

//<컨트롤러 가기전 예외, 컨트롤러에서의 예외, 그 후의 예외들> 이렇게 모아둘까..? 예외메세지 줄 떄 어느정도까지 줘야되는지도 궁금함
//boolean은 어떻게 검증함? 숫자 이상한거 넣어도 잘들어감...
//String값이 아닌거 넣어도 String으로 알아서 들어가는거같음(포맨이라그런가)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<FailureResponse> handleBusinessException(final BusinessException e) {
        return ResponseEntity.status(e.getFailureCode().getStatus()).body(FailureResponse.of(e.getFailureCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<FailureResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.INVALID_INPUT));
    }

    //@Valid 예외처리 (BindingResult)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<FailureResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = ValidationError.of(e.getBindingResult());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(HttpStatus.BAD_REQUEST, errors.toString()));
    }

    //헤더 없을 때 예외처리
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<FailureResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        System.out.println(e.getHeaderName() + "헤더가 없습니다");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.MISSING_REQUEST_HEADER));
    }

    // 타입 다를때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<FailureResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(FailureResponse.of(  //여기서 흠 어떤식으로 하는게 좋을까.. 따로 requestFailureInfo 만들어서 거기다가 잘못된 요청값 예외들 모아둘까..?
                        HttpStatus.BAD_REQUEST,
                        e.getPropertyName() + CommonFailureInfo.INVALID_HEADER_TYPE.getMessage())); // ex)userId의 타입이 잘못되었습니다.
    }

    //스프링 3.2 이후로 @Valid에러 여기서 잡히는듯..?
    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<FailureResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        final String defaultErrorMessage = getDefaultFromHandlerMethodValidationException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(HttpStatus.BAD_REQUEST, defaultErrorMessage));
    }

    //현재 enum필드에 아예 필드자체도 안들어갔을때 이 예외가 떠서 일단 이거로 해둠
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<FailureResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.INVALID_INPUT));
    }

    //request param 없을 때
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<FailureResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.MISSING_REQUEST_PARAM));
    }

    //httpmethod 잘못 넣거나, 요청값 잘못넣었을떄
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<FailureResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.INVALID_INPUT));
    }

    //존재하지 않는 엔드포인트로 접근할때
    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<FailureResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.INVALID_END_POINT));
    }

    //유니크 키 충돌
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<FailureResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureResponse.of(CommonFailureInfo.ALREADY_EXITST_TITLE));
    }


}
