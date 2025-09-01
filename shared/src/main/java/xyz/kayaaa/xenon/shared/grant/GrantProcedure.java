package xyz.kayaaa.xenon.shared.grant;

import lombok.*;
import xyz.kayaaa.xenon.shared.tools.xenon.Serializable;

import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
public class GrantProcedure<T extends Serializable> {

    private UUID author;
    private UUID target;
    private T data;
    private long duration;
    private String reason;

}
