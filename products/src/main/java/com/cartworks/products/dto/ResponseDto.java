package com.cartworks.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to hold response information"
)
@Data
@AllArgsConstructor
public class ResponseDto<T> {

    @Schema(
            description = "Status code in the response",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response",
            example = "Request processed successfully"
    )
    private String statusMsg;

    @Schema(
            description = "Response payload containing requested data or additional details"
    )
    private T data;
}
