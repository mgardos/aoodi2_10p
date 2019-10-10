package ar.net.mgardos;

public interface SummarizingAccountVisitor {
    default void visit(Portfolio aPortfolio) {}
    default void visit(ReceptiveAccount anAccount) {}
}
