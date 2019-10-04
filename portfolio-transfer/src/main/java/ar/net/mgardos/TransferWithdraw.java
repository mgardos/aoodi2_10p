package ar.net.mgardos;

public class TransferWithdraw extends TransferTransaction {
    TransferWithdraw(Transfer aTransfer) {
        super(aTransfer);
    }

    @Override
    public Transfer transfer() {
        return null;
    }

    @Override
    public double signValue() {
        return -transfer.value();
    }

    @Override
    public void accept(AccountTransactionVisitor aVisitor) {
        aVisitor.visit(this);
    }
}
