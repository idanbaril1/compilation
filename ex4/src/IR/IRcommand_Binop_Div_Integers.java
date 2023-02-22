/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommand_Binop_Div_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Div_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int i2 = t2.getSerialNumber()%10;
		String afterErrorLabel = getFreshLabel("after_illegal_div_error");
		MIPSGenerator.getInstance().bnez(String.format("$t%d", i2), afterErrorLabel);
		MIPSGenerator.getInstance().la("$s0","string_illegal_div_by_0");
		MIPSGenerator.getInstance().print_string("$s0");
		MIPSGenerator.getInstance().exit();

		MIPSGenerator.getInstance().label(afterErrorLabel);
		MIPSGenerator.getInstance().div(dst,t1,t2);
	}
}
