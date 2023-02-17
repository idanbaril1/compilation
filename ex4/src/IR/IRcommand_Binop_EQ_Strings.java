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

public class IRcommand_Binop_EQ_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_EQ_Strings(TEMP dst,TEMP t1,TEMP t2)
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
		int t1idx=t1.getSerialNumber()%10;
		int t2idx=t2.getSerialNumber()%10;
		int dstidx=dst.getSerialNumber()%10;
		/*******************************/
		/* [1] Allocate fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		String label_AssignZero = getFreshLabel("AssignZero");
		String label_eqLoop = getFreshLabel("str_eq_loop");
		/******************************************/
		/* [2] if (t1==t2) goto label_AssignOne;  */
		/*     if (t1!=t2) goto label_AssignZero; */
		/******************************************/
		MIPSGenerator.getInstance().move("$s0", String.format("$t%d",t1idx));
		MIPSGenerator.getInstance().move("$s1", String.format("$t%d",t2idx));
		MIPSGenerator.getInstance().label(label_eqLoop);
		MIPSGenerator.getInstance().lb("$s2", 0, "$s0");
		MIPSGenerator.getInstance().lb("$s3", 0, "$s1");
		MIPSGenerator.getInstance().bne("$s2","$s3",label_AssignZero);
		MIPSGenerator.getInstance().beq("$s2","$zero",label_AssignOne);
		MIPSGenerator.getInstance().addi("$s0", "$s0", 1);
		MIPSGenerator.getInstance().addi("$s1", "$s1", 1);
		MIPSGenerator.getInstance().jump(label_eqLoop);
		/************************/
		/* [3] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*         goto end;    */
		/*                      */
		/************************/
		MIPSGenerator.getInstance().label(label_AssignOne);
		MIPSGenerator.getInstance().li(dst,1);
		MIPSGenerator.getInstance().jump(label_end);

		/*************************/
		/* [4] label_AssignZero: */
		/*                       */
		/*         t3 := 1       */
		/*         goto end;     */
		/*                       */
		/*************************/
		MIPSGenerator.getInstance().label(label_AssignZero);
		MIPSGenerator.getInstance().li(dst,0);
		MIPSGenerator.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		MIPSGenerator.getInstance().label(label_end);
	}
}
