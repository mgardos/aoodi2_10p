package ar.net.mgardos;

public class AccountTransferVisitor implements AccountTransactionVisitor {
    private double net = 0.0;

    public void visit(TransferDeposit transferLeg) {
        net += transferLeg.signValue();
    }

    public void visit(TransferWithdraw transferLeg) {
        net += transferLeg.signValue();
    }

    public double netValue() {
        return net;
    }
}
