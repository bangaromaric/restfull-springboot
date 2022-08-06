package ga.banga.restfull.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class ApiError {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private HashMap<String, String> errors;
    private List<String> errorss;

//    public ApiError(HttpStatus status, String message, List<String> errors) {
//        super();
//        this.status = status;
//        this.message = message;
//        this.errors = errors;
//    }


    public ApiError() {
    }

    public ApiError(HttpStatus status, int statusCode, String message, HashMap<String, String> errors) {
        super();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, int statusCode, String message, String error) {
        super();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errorss = Arrays.asList(error);
    }

    public ApiError(HttpStatus status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
