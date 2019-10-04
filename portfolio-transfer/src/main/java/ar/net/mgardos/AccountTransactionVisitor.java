package ar.net.mgardos;

public interface AccountTransactionVisitor {
    default void visit(Deposit aDeposit) {}
    default void visit(Withdraw aWithdraw) {}
    default void visit(TransferDeposit transferLeg) {}
    default void visit(TransferWithdraw transferLeg) {}
}
