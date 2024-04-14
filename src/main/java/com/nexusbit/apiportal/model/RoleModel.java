package com.nexusbit.apiportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "roles")
public class RoleModel {

    @Id
    private String role;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<AuthorityModel> authorities;
}
