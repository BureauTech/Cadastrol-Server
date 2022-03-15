package br.com.fatec.server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.GenerationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "use_cod")
    @JsonView(value = {View.UserView.Basic.class})
    Long useCod;
    
    @Column(name = "use_name")
    @JsonView(value = {View.UserView.Basic.class})
    private String useName;
    
    @Column(name = "use_email")
    private String useEmail;
    
    @Column(name = "use_phone")
    @JsonView(value = {View.UserView.Basic.class})
    private String usePhone;

    @Column(name = "use_password")
    private String usePassword;

    @Column(name = "use_is_admin")
    private Boolean useIsAdmin;
}
