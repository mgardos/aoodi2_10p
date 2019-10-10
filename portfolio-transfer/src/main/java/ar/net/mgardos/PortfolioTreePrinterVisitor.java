package ar.net.mgardos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PortfolioTreePrinterVisitor implements SummarizingAccountVisitor {
    private Map<SummarizingAccount, String> accountNames;
    private List<String> lines = new ArrayList<>();
    private int spaces;

    public PortfolioTreePrinterVisitor(Map<SummarizingAccount, String> names) {
        accountNames = names;
    }

    @Override
    public void visit(Portfolio aPortfolio) {
        lines.add(leadingSpaces() + accountNames.getOrDefault(aPortfolio, "Unidentified portfolio"));
        spaces++;

        aPortfolio.visitAccounts(this);
        spaces--;
    }

    @Override
    public void visit(ReceptiveAccount anAccount) {
        lines.add(leadingSpaces() + accountNames.getOrDefault(anAccount, "Unidentified portfolio"));
    }

    public List<String> printableLines() {
        return lines;
    }

    private String leadingSpaces() {
        return IntStream.range(0, spaces).mapToObj(i -> " ").collect(Collectors.joining());
    }
}
