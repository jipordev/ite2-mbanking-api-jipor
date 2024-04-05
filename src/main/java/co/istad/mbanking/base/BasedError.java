package co.istad.mbanking.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedError<T> {

    // Request Entity too large, Bad Request, ...
    private String code;

    // Detail error for user
    private T description;

}
