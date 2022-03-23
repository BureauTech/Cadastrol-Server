package br.com.fatec.server.projections;

public interface UserProjection {
    public interface WithoutPassword {
        Long getUseCod();
        String getUseName();
        String getUseEmail();
        String getUsePhone();
    }
}
