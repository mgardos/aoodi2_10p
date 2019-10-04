package ar.net.mgardos;

public abstract class TransferTransaction implements TransferLeg {
    protected Transfer transfer;

    public TransferTransaction(Transfer aTransfer) {
        transfer = aTransfer;
    }

    @Override
    public Transfer transfer() {
        return null;
    }

    @Override
    public double value() {
        return transfer.value();
    }
}
