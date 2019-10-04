package ar.net.mgardos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountSummaryVisitor implements AccountTransactionVisitor {
    private static final NumberFormat DOUBLE_FORMAT = new DecimalFormat("#0.00");
    private double depositValue = 0.0;
    private double withdrawValue = 0.0;
    private double transferValue = 0.0;

    @Override
    public void visit(Deposit aDeposit) {
        depositValue += aDeposit.value();
    }

    @Override
    public void visit(Withdraw aWithdraw) {
        withdrawValue += aWithdraw.value();
    }

    @Override
    public void visit(TransferDeposit aTransferDeposit) {
        transferValue += aTransferDeposit.signValue();
    }

    @Override
    public void visit(TransferWithdraw aTransferWithdraw) {
        transferValue += aTransferWithdraw.signValue();
    }

    public List<String> summary() {
        List<String> lines = new ArrayList<>();
        lines.add("Deposito por " + DOUBLE_FORMAT.format(depositValue));
        lines.add("Extraccion por " + DOUBLE_FORMAT.format(withdrawValue));
        lines.add("Transferencia por " + DOUBLE_FORMAT.format(transferValue));

        return lines;
    }
}
