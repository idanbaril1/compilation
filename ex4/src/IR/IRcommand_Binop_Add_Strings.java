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

public class IRcommand_Binop_Add_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Add_Strings(TEMP dst,TEMP t1,TEMP t2)
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
		int t1Idx = t1.getSerialNumber() % 10;
		int t2Idx = t2.getSerialNumber() % 10;
		int dstIdx = dst.getSerialNumber() % 10;

		MIPSGenerator.getInstance().li("$a0", 100);
		MIPSGenerator.getInstance().li("$v0", 9);
		MIPSGenerator.getInstance().syscall();
		MIPSGenerator.getInstance().move(String.format("$t%d", dstIdx),"$v0");
		MIPSGenerator.getInstance().move("$a0",String.format("$t%d", dstIdx));
		MIPSGenerator.getInstance().move("$s0", String.format("$t%d", t1Idx));
		String loop1Label = getFreshLabel("concat_loop1");
		String loop1End = getFreshLabel("concat_loop1_end");
		/* copy string1 to buffer */
		MIPSGenerator.getInstance().label(loop1Label);
		MIPSGenerator.getInstance().lb("$s1",0,"$s0");
		MIPSGenerator.getInstance().beqz("$s1", loop1End);
		MIPSGenerator.getInstance().sb("$s1",0,"$a0");
		MIPSGenerator.getInstance().addi("$a0","$a0",1);
		MIPSGenerator.getInstance().addi("$s0","$s0",1);
		MIPSGenerator.getInstance().jump(loop1Label);
		MIPSGenerator.getInstance().label(loop1End);

		MIPSGenerator.getInstance().move("$s0", String.format("$t%d", t2Idx));
		String loop2Label = getFreshLabel("concat_loop2");
		String loop2End = getFreshLabel("concat_loop2_end");
		/* copy string2 to buffer */
		MIPSGenerator.getInstance().label(loop2Label);
		MIPSGenerator.getInstance().lb("$s1",0,"$s0");
		MIPSGenerator.getInstance().beqz("$s1", loop2End);
		MIPSGenerator.getInstance().sb("$s1",0,"$a0");
		MIPSGenerator.getInstance().addi("$a0","$a0",1);
		MIPSGenerator.getInstance().addi("$s0","$s0",1);
		MIPSGenerator.getInstance().jump(loop2Label);
		MIPSGenerator.getInstance().label(loop2End);
		MIPSGenerator.getInstance().sb("$s1",0,"$a0");
	}
}
