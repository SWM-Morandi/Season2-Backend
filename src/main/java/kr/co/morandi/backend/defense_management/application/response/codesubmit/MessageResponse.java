package kr.co.morandi.backend.defense_management.application.response.codesubmit;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageResponse {
    private String result;
    private double execute_time;
    private String output;
    private String sseId;
}
