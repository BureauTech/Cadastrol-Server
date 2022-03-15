package br.com.fatec.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.server.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUseCod(Long useCod);

    public List<UserEntity> findAll();
}
