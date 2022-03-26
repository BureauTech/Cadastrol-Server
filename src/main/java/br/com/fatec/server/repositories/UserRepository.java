package br.com.fatec.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.projections.UserProjection;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUseCod(Long useCod);

    public <T> T findByUseCod(Long useCod, Class<T> projection);

    public Optional<UserEntity> findByUseEmail(String email);

    public List<UserEntity> findAll();

    public Page<UserProjection.WithoutPassword> findAllProjectedByOrderByUseCodAsc(Pageable pageable);
}
