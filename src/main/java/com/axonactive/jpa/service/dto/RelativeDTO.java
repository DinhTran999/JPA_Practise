package com.axonactive.jpa.service.dto;

import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.enumerate.Relationship;
import lombok.Builder;//dùng builder sẽ làm Mapstruct chuyển sang private không dùng được
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RelativeDTO {
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private Relationship relationship;
}
