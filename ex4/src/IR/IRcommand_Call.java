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
	String func_name;
	TEMP result;
	TEMP_LIST args;
	
	
	public IRcommand_Call(TEMP result, String func_name, TEMP_LIST args)
	{
		this.result = result;
		this.func_name = func_name;
		this.args = args;
	}
	
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//missing
	}
}
