package com.example.moreba2.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicDTO {

    int micId;

    String memberId;

    String Content;

    String insertDate;

    String ip;
}
