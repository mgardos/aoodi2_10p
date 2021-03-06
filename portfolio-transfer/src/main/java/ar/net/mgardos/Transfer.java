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

public class Transfer {
    private double value;
	private TransferLeg depositLeg;
	private TransferLeg withdrawLeg;

	public Transfer(double aValue, ReceptiveAccount fromAccount, ReceptiveAccount toAccount) {
	    value = aValue;
        depositLeg = new TransferDeposit(this);
        withdrawLeg = new TransferWithdraw(this);
	}

	public static Transfer registerFor(double aValue, ReceptiveAccount fromAccount,
			ReceptiveAccount toAccount) {
	    Transfer aTransfer = new Transfer(aValue, fromAccount, toAccount);

        fromAccount.register(aTransfer.withdrawLeg);
        toAccount.register(aTransfer.depositLeg);

		return aTransfer;
	}

	public double value() {
		return value;
	}

	public TransferLeg depositLeg() {
		return depositLeg;
	}
	
	public TransferLeg withdrawLeg() {
		return withdrawLeg;
	}
}
