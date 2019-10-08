package ar.net.mgardos;

public class AccountTransferVisitor implements AccountTransactionVisitor {
    private double net = 0.0;

    @Override
    public void visit(TransferDeposit transferLeg) {
        net += transferLeg.signValue();
    }

    @Override
    public void visit(TransferWithdraw transferLeg) {
        net += transferLeg.signValue();
    }

    public double netValue() {
        return net;
    }
}
