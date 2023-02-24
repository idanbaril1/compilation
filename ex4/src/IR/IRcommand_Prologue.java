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
import AST.AST_ARGS_LIST;

public class IRcommand_Prologue extends IRcommand
{
	String funcName;
	AST_ARGS_LIST args;
	
	public IRcommand_Prologue(String funcName, AST_ARGS_LIST args)
	{
		this.funcName = funcName;
		this.args = args;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String label_prologue = funcName + "_prologue";
		MIPSGenerator.getInstance().label(label_prologue);
		for(int i=0; i<10; i++){
			MIPSGenerator.getInstance().storeReg(String.format("$t%d", i));
		}

		AST_ARGS_LIST tempArgs = args;
		for(int i=1;tempArgs!=null;i++){
			String argName = tempArgs.head.name;
			MIPSGenerator.getInstance().allocate(argName, "721");
			MIPSGenerator.getInstance().loadArg(argName);
			tempArgs = tempArgs.tail;
		}
		
		MIPSGenerator.getInstance().funcStart();
	}
}
