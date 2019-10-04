package ar.net.mgardos;

public class TransferDeposit extends TransferTransaction {
    TransferDeposit(Transfer aTransfer) {
        super(aTransfer);
    }

    @Override
    public Transfer transfer() {
        return null;
    }

    @Override
    public double signValue() {
        return transfer.value();
    }

    @Override
    public void accept(AccountTransactionVisitor aVisitor) {
        aVisitor.visit(this);
    }
}
