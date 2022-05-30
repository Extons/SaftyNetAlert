package com.saftynetalert.saftynetalert.payload.response;

/**
 * @author ufhopla
 * on 30/05/2022.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
