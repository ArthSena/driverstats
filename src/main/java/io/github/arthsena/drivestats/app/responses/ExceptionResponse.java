package io.github.arthsena.drivestats.app.responses;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("exception")
public class ExceptionResponse {

    private int code;
    private String message;

}
