package com.crevan.votesystem.to;

import com.crevan.votesystem.HasId;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntityTo implements HasId {

    protected Integer id;

    @Override
    public String toString() {
        return "Entity id=" + id;
    }
}
