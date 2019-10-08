package ar.net.mgardos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountSummaryVisitor implements AccountTransactionVisitor {
    private static final NumberFormat DOUBLE_FORMAT = new DecimalFormat("#0.00");
    List<String> lines = new ArrayList<>();

    @Override
    public void visit(Deposit aDeposit) {
        lines.add("Deposito por " + DOUBLE_FORMAT.format(aDeposit.value()));
    }

    @Override
    public void visit(Withdraw aWithdraw) {
        lines.add("Extraccion por " + DOUBLE_FORMAT.format(aWithdraw.value()));
    }

    @Override
    public void visit(TransferDeposit aTransferDeposit) {
        lines.add("Transferencia por " + DOUBLE_FORMAT.format(aTransferDeposit.signValue()));
    }

    @Override
    public void visit(TransferWithdraw aTransferWithdraw) {
        lines.add("Transferencia por " + DOUBLE_FORMAT.format(aTransferWithdraw.signValue()));
    }

    public List<String> summary() {
        return lines;
    }
}
