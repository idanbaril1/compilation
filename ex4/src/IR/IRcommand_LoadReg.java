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

public class IRcommand_LoadReg extends IRcommand
{
	TEMP dst;
	TEMP src;
	int offset;
	
	public IRcommand_LoadReg(TEMP dst,TEMP src, int offset)
	{
		this.dst = dst;
		this.src = src;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int dstidx = dst.getSerialNumber()%10;
		int srcidx = src.getSerialNumber()%10;
		MIPSGenerator.getInstance().move("$s0",String.format("$t%d", srcidx));
		MIPSGenerator.getInstance().lw(String.format("$t%d", dstidx),"$s0", offset);
	}
}
