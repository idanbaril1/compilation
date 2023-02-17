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

public class IRcommand_Epilogue extends IRcommand
{
	String funcName;
	AST_ARGS_LIST args;
	
	public IRcommand_Epilogue(String funcName, AST_ARGS_LIST args)
	{
		this.funcName = funcName;
		this.args = args;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String label_epilogue = funcName + "_epilogue";
		MIPSGenerator.getInstance().label(label_epilogue);
		AST_ARGS_LIST tempArgs = args;
		for(int i=1;tempArgs!=null;i++){
			String argName = tempArgs.head.name;
			MIPSGenerator.getInstance().restoreArg(argName);
			tempArgs = tempArgs.tail;
		}
		MIPSGenerator.getInstance().funcEnd();
		
	}
}
