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

public class IRcommand_NewArray extends IRcommand
{
	TEMP size;
	TEMP result;
	
	public IRcommand_NewArray(TEMP result, TEMP size)
	{
		this.result = result;
		this.size = size;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int resIdx = result.getSerialNumber() % 10;
		int sizeIdx = size.getSerialNumber() % 10;
		MIPSGenerator.getInstance().li("$v0",9);
		MIPSGenerator.getInstance().move("$a0", String.format("$t%d",sizeIdx));
		MIPSGenerator.getInstance().addi("$a0", "$a0", 1);
		MIPSGenerator.getInstance().muli("$a0", "$a0", 4);
		MIPSGenerator.getInstance().syscall();
		MIPSGenerator.getInstance().move(String.format("$t%d", resIdx), "$v0");
		MIPSGenerator.getInstance().store(String.format("$t%d", sizeIdx), 0, String.format("$t%d", resIdx));
	}
}
