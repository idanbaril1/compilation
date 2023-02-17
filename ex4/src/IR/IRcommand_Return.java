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

public class IRcommand_Return extends IRcommand
{
	TEMP value;
	String funcName;
	
	public IRcommand_Return(TEMP value, String funcName)
	{
		this.value = value;
		this.funcName = funcName;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String epilogueLabel = funcName + "_epilogue";
		MIPSGenerator.getInstance().loadReturnReg(value);
		if(!funcName.equals("main")){
			MIPSGenerator.getInstance().jump(epilogueLabel);
		}
		else{
			MIPSGenerator.getInstance().jump("program_end");
		}
	}
}
