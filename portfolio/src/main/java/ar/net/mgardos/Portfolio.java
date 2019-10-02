/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package ar.net.mgardos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Portfolio implements SummarizingAccount{
    private List<SummarizingAccount> accounts;

	public static final String ACCOUNT_NOT_MANAGED = "No se maneja esta cuenta";
	public static final String ACCOUNT_ALREADY_MANAGED = "La cuenta ya está manejada por otro portfolio";
	
	public static Portfolio createWith(SummarizingAccount... accounts) {
		Portfolio p = new Portfolio();
        List<SummarizingAccount> uniqueAccounts = Arrays.stream(accounts).distinct().collect(Collectors.toList());

        if (accounts.length != uniqueAccounts.size()) {
            throw new RuntimeException(ACCOUNT_ALREADY_MANAGED);
        }

		for (SummarizingAccount account : accounts) {
		    p.addAccount(account);
        }

		return p;
	}

	public Portfolio(){
        accounts = new ArrayList<>();
	}

	public double balance() {
	    return accounts.stream().map(account -> account.balance()).reduce(0d, (subtotal, accountBalance) -> subtotal + accountBalance);
	}
	
	public boolean registers(AccountTransaction transaction) {
	    return accounts.stream().map(account -> account.registers(transaction)).reduce(false, (isRegistered, isAccountRegisters) -> isRegistered || isAccountRegisters);
	}

	public List<AccountTransaction> transactionsOf(SummarizingAccount account) {
		throw new UnsupportedOperationException();
	}
	
	public boolean manages(SummarizingAccount account) {
	    return this == account || accounts.stream().map(portfolioAccount -> portfolioAccount.manages(account)).reduce(false, (isManaged, isAccountManages) -> isManaged || isAccountManages);
	}
	
	public List<AccountTransaction> transactions() {
	    return accounts.stream().flatMap(account -> account.transactions().stream()).collect(Collectors.toList());
	}

	public void addAccount(SummarizingAccount anAccount) {
		accounts.add(anAccount);
	}
}
