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

public class IRcommand_Binop_Mul_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Mul_Integers(TEMP dst,TEMP t1,TEMP t2)
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
		int i1 =t1.getSerialNumber()%10;
		int i2 =t2.getSerialNumber()%10;
		int dstIdx = dst.getSerialNumber()%10;
		
		String after_overflow_label = getFreshLabel("after_overflow");
		String positives_overflow_label = getFreshLabel("positives_overflow");
		String negatives_overflow_label = getFreshLabel("negatives_overflow");

		MIPSGenerator.getInstance().mul(dst,t1,t2);
		//check for overflow
		MIPSGenerator.getInstance().li("$s0",-32768);
		MIPSGenerator.getInstance().blt(String.format("$t%d",dstIdx),"$s0",negatives_overflow_label);

		MIPSGenerator.getInstance().li("$s0",32767);
		MIPSGenerator.getInstance().blt("$s0", String.format("$t%d",dstIdx),positives_overflow_label);
		MIPSGenerator.getInstance().jump(after_overflow_label);
		//negatives overflow
		MIPSGenerator.getInstance().label(negatives_overflow_label);		
		MIPSGenerator.getInstance().li(String.format("$t%d",dstIdx), -32768);
		MIPSGenerator.getInstance().jump(after_overflow_label);
		//positives overflow
		MIPSGenerator.getInstance().label(positives_overflow_label);
		MIPSGenerator.getInstance().li(String.format("$t%d",dstIdx), 32767);

		MIPSGenerator.getInstance().label(after_overflow_label);
	}
}
