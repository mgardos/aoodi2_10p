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

import java.util.List;

public interface SummarizingAccount {
	double balance();
	boolean registers(AccountTransaction transaction);
	boolean manages(SummarizingAccount account);
	List<AccountTransaction> transactions();
	void accept(SummarizingAccountVisitor aVisitor);
}
