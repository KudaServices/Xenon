package rip.kuda.xenon.shared.grant;

import lombok.*;
import rip.kuda.xenon.shared.tools.xenon.Serializable;

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
