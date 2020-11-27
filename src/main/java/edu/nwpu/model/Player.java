package edu.nwpu.model;

import edu.nwpu.enums.ElectStrategy;
import edu.nwpu.enums.Role;
import edu.nwpu.enums.Survival;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Long id;
    private String name;
    private int seq;
    private Role role;
    private Survival survival;
    private ElectStrategy electStrategy;
}
