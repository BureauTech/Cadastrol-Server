package br.com.fatec.server.projections;

public interface UserProjection {
    public interface WithouPassword {
        Long getUseCod();
        String getUseName();
        String getUseEmail();
        String getUsePhone();
        Boolean getUseIsAdmin();
    }
}
