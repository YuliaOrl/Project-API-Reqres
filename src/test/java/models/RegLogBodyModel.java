package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegLogBodyModel {
    private String email,
                   password;
    }

