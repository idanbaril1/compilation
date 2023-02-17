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

public class IRcommand_Call extends IRcommand
{
	String funcName;
	TEMP result;
	TEMP_LIST args;
	
	
	public IRcommand_Call(TEMP result, String funcName, TEMP_LIST args)
	{
		this.result = result;
		this.funcName = funcName;
		this.args = args;
	}
	
	private TEMP_LIST addArgToListEnd(TEMP arg, TEMP_LIST sublist){
		TEMP_LIST listCopy = sublist;
		while(listCopy.tail!=null){
			listCopy = listCopy.tail;
		}
		listCopy.tail = new TEMP_LIST(arg, null);
		return sublist;
	}
	private TEMP_LIST reverseArgs(TEMP_LIST sublist){
		if(sublist.tail == null) return sublist;
		TEMP_LIST reversed = reverseArgs(sublist.tail);
		return addArgToListEnd(sublist.head, reversed);	
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP_LIST reversedArgs = null;
		if(args != null) reversedArgs = reverseArgs(args);
		while(reversedArgs!=null){
			MIPSGenerator.getInstance().storeArg(reversedArgs.head);			
			reversedArgs = reversedArgs.tail;
		}
		MIPSGenerator.getInstance().jal(funcName);
		MIPSGenerator.getInstance().loadFuncReturnValue(result);
	}
}
